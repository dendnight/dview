package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.data.Images;
import gmail.dendnight.dview.dict.DictParam;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��20�� ����2:37:48  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ListActivity extends Activity {

	// �˵��л�
	String s = DictParam.GRID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// ɨ��ͼƬ
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
		// Uri.parse("file://"
		// + Environment.getExternalStorageDirectory())));

		// �б�
		ListView listView = (ListView) findViewById(R.id.listView);

		// �Զ���������
		ListAdapter mainAdapter = new ListAdapter(this, Images.listData(this.getContentResolver()));

		listView.setAdapter(mainAdapter);

		// ���item��ת
		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {

				Map<String, Object> hashMap = (Map<String, Object>) adapterview.getAdapter().getItem(position);

				Intent intent = new Intent();
				intent.putExtra(DictParam.FOLDER_ID, (String) hashMap.get(DictParam.FOLDER_ID));
				if (DictParam.GRID.equals(s)) {
					intent.setClass(ListActivity.this, GridActivity.class);
				} else {
					intent.setClass(ListActivity.this, WebActivity.class);
				}
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
		// �˳�
		if (item.getItemId() == R.id.action_exit) {
			ListActivity.this.finish();
		}
		// �л���ҳ����ʾ
		if (item.getItemId() == R.id.action_switch) {
			if (DictParam.GRID.equals(s)) {
				s = DictParam.WEB;
				item.setTitle(R.string.action_grid);
			} else {
				s = DictParam.GRID;
				item.setTitle(R.string.action_web);
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}
}