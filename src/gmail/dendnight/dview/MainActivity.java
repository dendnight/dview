package gmail.dendnight.dview;

import gmail.dendnight.dview.R;
import gmail.dendnight.utils.GalleryUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

/**
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月20日 下午2:37:48  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 扫描图片
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
		// Uri.parse("file://"
		// + Environment.getExternalStorageDirectory())));

		// 列表
		ListView listView = (ListView) findViewById(R.id.listView);

		// 自定义适配器
		MainAdapter mainAdapter = new MainAdapter(this, GalleryUtil.mainGallery(this.getContentResolver()));

		listView.setAdapter(mainAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}