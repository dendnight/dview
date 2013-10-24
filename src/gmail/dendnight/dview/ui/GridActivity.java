package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.data.Images;
import gmail.dendnight.dview.dict.DictParam;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * ������ʾ
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��21�� ����3:00:15  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class GridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_grid);
		// ����
		GridView gridView = (GridView) findViewById(R.id.gridView);

		// ��ȡֵ
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);

		// ֵ
		GridAdapter listAdapter = new GridAdapter(getApplicationContext(), Images.gridData(getContentResolver(),
				folderId));

		// ����ֵ��GridView����
		gridView.setAdapter(listAdapter);

		// �󶨵����¼�
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view, int position, long l) {

			}
		});
	}
}
