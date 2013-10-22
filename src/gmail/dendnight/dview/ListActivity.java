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
 * ͼ���б�
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
public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		// ����
		GridView gridView = (GridView) findViewById(R.id.gridView);

		// ��ȡֵ
		Intent intent = getIntent();
		String folderId = intent.getStringExtra(DictParam.FOLDER_ID);

		// ֵ
		ListAdapter listAdapter = new ListAdapter(getApplicationContext(), GalleryUtil.listGallery(
				getContentResolver(), folderId));

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
