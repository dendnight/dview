package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.data.Images;
import gmail.dendnight.dview.dict.DictParam;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		String data = Images.webGallery(getContentResolver(), folderId);

		// web容器
		WebView webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		// 访问本地图片
		String baseUrl = "file:///";

		// 显示本地网页
		webView.loadDataWithBaseURL(baseUrl, data, "text/html", "UTF-8", null);
		webView.addJavascriptInterface(new JavascriptInterface(), "imagelistner");
		// webView.loadUrl("file:///android_asset/test.html");
	}

	// js通信接口
	class JavascriptInterface {

		public void openImage(String path) {
			//
			Intent intent = new Intent();
			intent.putExtra(DictParam.PATH, path);
			intent.setClass(WebActivity.this, DetailActivity.class);
			WebActivity.this.startActivity(intent);
		}
	}

}
