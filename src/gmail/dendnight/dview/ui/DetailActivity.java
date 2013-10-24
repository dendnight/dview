package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.R;
import gmail.dendnight.dview.dict.DictParam;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * ������ʾ
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��24�� ����11:02:00  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class DetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Intent intent = getIntent();
		String path = intent.getStringExtra(DictParam.PATH);

		Bitmap bitmap = BitmapFactory.decodeFile(path);

		ImageView gallery = (ImageView) findViewById(R.id.gallery);

		gallery.setImageBitmap(bitmap);
	}
}
