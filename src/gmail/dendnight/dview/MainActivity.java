package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import gmail.dendnight.utils.GalleryUtil;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		// 隐藏状态栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

		// 点击item跳转
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {

				Map<String, Object> hashMap = (Map<String, Object>) adapterview.getAdapter().getItem(position);

				Intent intent = new Intent();
				intent.putExtra(DictParam.TITLE, (String) hashMap.get(DictParam.TITLE));
				intent.setClass(MainActivity.this, ListActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// 退出
		if (item.getItemId() == R.id.action_exit) {
			MainActivity.this.finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

}