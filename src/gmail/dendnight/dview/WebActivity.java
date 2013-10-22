package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import gmail.dendnight.utils.GalleryUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.WebView;

public class WebActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		// 获取值
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);
		Cursor cursor = GalleryUtil.webGallery(getContentResolver(), folderId);

		StringBuilder data = new StringBuilder();
		data.append("<html><head><title>list</title><meta name=\"Author\" content=\"dendnight\"></head><body>");

		String oldDate = null;
		while (cursor.moveToNext()) {

			// 原图片路径
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			// 缩略图路径
			String thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			// 最后编辑时间
			long taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(taken);

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			String date = f.format(ca.getTime());

			// 时间相同的图片
			if (null == oldDate || !oldDate.equals(date)) {
				data.append("<h3>" + date + "</h3>");
			}
			if (null != date) {
				oldDate = date;
			}
			data.append("<img src=\"" + thumbnail + "\" style=\"width:100xp;height:100px;\"/>");

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
		// webView.loadUrl("file:///android_asset/test.html");
	}

}
