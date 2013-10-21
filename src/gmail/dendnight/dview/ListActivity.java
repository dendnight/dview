package gmail.dendnight.dview;

import gmail.dendnight.utils.DictParam;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

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
		// ����״̬��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_list);
		Intent intent = getIntent();
		String text = intent.getStringExtra(DictParam.TITLE);
		Toast.makeText(getApplicationContext(), "ѡ��:" + text, Toast.LENGTH_SHORT).show();

	}
}
