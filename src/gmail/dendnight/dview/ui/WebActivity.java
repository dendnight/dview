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
 * ��ҳ��ʾ
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��24�� ����11:00:46  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
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

		// ��ȡֵ
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);
		Cursor cursor = Images.webGallery(getContentResolver(), folderId);

		StringBuilder data = new StringBuilder();
		data.append("<html><head><title>list</title><meta name=\"Author\" content=\"dendnight\"></head><body>");

		String oldDate = null;
		// ԭͼƬ·��
		String path = null;
		// ����ͼ·��
		String thumbnail = null;
		// ����
		String date = null;
		// ���༭ʱ��
		long taken = 0l;
		while (cursor.moveToNext()) {

			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

			date = MobileUtil.fDate("MM-dd", taken);

			// ʱ����ͬ��ͼƬ
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
		// �ر�cursor
		cursor.close();
		data.append("</bady>");
		// web����
		WebView webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		// ���ʱ���ͼƬ
		String baseUrl = "file:///";

		// ��ʾ������ҳ
		webView.loadDataWithBaseURL(baseUrl, data.toString(), "text/html", "UTF-8", null);
		webView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
		// webView.loadUrl("file:///android_asset/test.html");
	}

	// jsͨ�Žӿ�
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
