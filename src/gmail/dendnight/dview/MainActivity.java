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
 * Create at:	2013��10��20�� ����2:37:48  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ɨ��ͼƬ
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
		// Uri.parse("file://"
		// + Environment.getExternalStorageDirectory())));

		// �б�
		ListView listView = (ListView) findViewById(R.id.listView);

		// �Զ���������
		MainAdapter mainAdapter = new MainAdapter(this, GalleryUtil.mainGallery(this.getContentResolver()));

		listView.setAdapter(mainAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}