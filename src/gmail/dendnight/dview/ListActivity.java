package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import gmail.dendnight.utils.GalleryUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

/**
 * 图库列表
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月21日 下午3:00:15  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏状态栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_list);
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);
		Toast.makeText(getApplicationContext(), "选中:" + folderId, Toast.LENGTH_SHORT).show();

		GridView gridView = (GridView) findViewById(R.id.list_gridView);

		ListAdapter listAdapter = new ListAdapter(getApplicationContext(), GalleryUtil.listGallery(
				getContentResolver(), folderId));

		gridView.setAdapter(listAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {

			}
		});
	}
}
