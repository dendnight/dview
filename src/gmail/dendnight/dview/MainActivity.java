package gmail.dendnight.dview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	/** ͼƬ */
	private static final String IMAGE = "image";

	/** �ļ���·�� */
	private static final String PATH = "path";

	/** ͼƬ���� */
	private static final String TITLE = "title";

	/** ͬһ�ļ�����ͼƬ���� */
	private static final String COUNT = "count";

	/** ����ͼƬ���� */
	private static final String[] STORE_IMAGES = { MediaStore.Images.Media._ID, 
			MediaStore.Images.Media.BUCKET_ID,
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
			MediaStore.Images.Media.DATA};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
				+ Environment.getExternalStorageDirectory())));
		ListView listView = (ListView) findViewById(R.id.listView);

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, imageData(), R.layout.activity_main_item, new String[] {
				IMAGE, PATH, TITLE, COUNT }, new int[] { R.id.image, R.id.path, R.id.title, R.id.count });

		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ��ȡͼƬ����<br>
	 * 
	 * @return
	 */
	private List<Map<String, Object>> imageData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(IMAGE, R.drawable.ic_launcher);
		map.put(PATH, "asda");
		map.put(TITLE, "asdasd");
		map.put(COUNT, "����û");
		list.add(map);

		return list;
	}

}