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
		String data = Images.webGallery(getContentResolver(), folderId);

		// web����
		WebView webView = (WebView) findViewById(R.id.webView);

		webView.getSettings().setJavaScriptEnabled(true);
		// ���ʱ���ͼƬ
		String baseUrl = "file:///";

		// ��ʾ������ҳ
		webView.loadDataWithBaseURL(baseUrl, data, "text/html", "UTF-8", null);
		webView.addJavascriptInterface(new JavascriptInterface(), "imagelistner");
		// webView.loadUrl("file:///android_asset/test.html");
	}

	// jsͨ�Žӿ�
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
