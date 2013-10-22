package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import gmail.dendnight.utils.GalleryUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

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
		setContentView(R.layout.activity_list);

		// 容器
		GridView gridView = (GridView) findViewById(R.id.gridView);

		// 获取值
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);

		// 值
		ListAdapter listAdapter = new ListAdapter(getApplicationContext(), GalleryUtil.listGallery(
				getContentResolver(), folderId));

		// 设置值至GridView容器
		gridView.setAdapter(listAdapter);

		// 绑定单击事件
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {

			}
		});
	}
}
