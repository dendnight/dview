package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.data.Images;
import gmail.dendnight.dview.dict.DictParam;
import gmail.dendnight.dview.utils.MobileUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.WebView;

/**
 * 网页显示
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月24日 上午11:00:46  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class WebActivity extends Activity {

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		// 获取值
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);
		Cursor cursor = Images.webGallery(getContentResolver(), folderId);

		StringBuilder data = new StringBuilder();
		data.append("<html><head><title>list</title><meta name=\"Author\" content=\"dendnight\"></head><body>");

		String oldDate = null;
		// 原图片路径
		String path = null;
		// 缩略图路径
		String thumbnail = null;
		// 日期
		String date = null;
		// 最后编辑时间
		long taken = 0l;
		while (cursor.moveToNext()) {

			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

			date = MobileUtil.fDate("MM-dd", taken);

			// 时间相同的图片
			if (null == oldDate || !oldDate.equals(date)) {
				data.append("<h3>" + date + "</h3>");
			}
			if (null != date) {
				oldDate = date;
			}
			data.append("<img src=\"" + thumbnail
					+ "\" style=\"width:110px;height:110px;\" onclick=\"window.imagelistner.openImage('" + path
					+ "');return false;\"/>");

		}
		// 关闭cursor
		cursor.close();
		data.append("</bady>");
		// web容器
		WebView webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		// 访问本地图片
		String baseUrl = "file:///";

		// 显示本地网页
		webView.loadDataWithBaseURL(baseUrl, data.toString(), "text/html", "UTF-8", null);
		webView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
		// webView.loadUrl("file:///android_asset/test.html");
	}

	// js通信接口
	class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String path) {
			//
			Intent intent = new Intent();
			intent.putExtra(DictParam.PATH, path);
			intent.setClass(context, DetailActivity.class);
			context.startActivity(intent);
		}
	}

}
