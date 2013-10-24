package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.data.Images;
import gmail.dendnight.dview.dict.DictParam;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 网格显示
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
public class GridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_grid);
		// 容器
		GridView gridView = (GridView) findViewById(R.id.gridView);

		// 获取值
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);

		// 值
		GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), Images.gridData(getContentResolver(),
				folderId));

		// 设置值至GridView容器
		gridView.setAdapter(gridAdapter);

		// 绑定单击事件
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {
				@SuppressWarnings("unchecked")
				Map<String, Object> hashMap = (Map<String, Object>) adapterview.getAdapter().getItem(position);

				Intent i = new Intent();
				i.putExtra(DictParam.PATH, (String) hashMap.get(DictParam.PATH));
				i.setClass(GridActivity.this, DetailActivity.class);
				startActivity(i);
			}
		});
	}
}
