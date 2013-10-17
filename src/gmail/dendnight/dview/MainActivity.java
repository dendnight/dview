package gmail.dendnight.dview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
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

	/** ��Ŀ¼ */
	private static final String ROOT = "/mnt/sdcard";

	/** �ų�����10MB��ͼƬ */
	private static final long IMAGE_MAX_SIZE = 10485760;

	/** �б����� */
	private List<Map<String, Object>> list = null;

	/** �б������� */
	private Map<String, Object> map = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);

		list = new ArrayList<Map<String, Object>>();

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, imageData(ROOT), R.layout.activity_main_item,
				new String[] { IMAGE, PATH, TITLE, COUNT }, new int[] { R.id.image, R.id.path, R.id.title, R.id.count });

		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ��ȡͼƬ����<br>
	 * �ж����ļ������Ƿ���ͼƬ,������򽫵�һ��ͼƬ����λͼ��ʾ�ڶ�Ӧλ��
	 * 
	 * @return
	 */
	private List<Map<String, Object>> imageData(String url) {
		File[] files = new File(url).listFiles();

		// �ǿ�������ļ�ϵͳ
		if (null != files) {
			for (int i = 0; i < files.length; i++) {

				// �ж��Ƿ����ļ���
				if (files[i].isDirectory()) {
					imageData(files[i].getAbsolutePath());
				}

				// �ж��ļ��Ƿ񳬹�10MB
				if (IMAGE_MAX_SIZE < files[i].length()) {
					files[i] = null;
					continue;
				}

				// �ж��Ƿ���ͼƬ�ļ�
				// boolean isImage = isImage(files[i]);
				// if (!isImage) {
				// continue;
				// }

				// ��ӵ�list
				map = new HashMap<String, Object>();
				map.put(IMAGE, R.drawable.ic_launcher);
				map.put(PATH, files[i].getAbsolutePath());

				map.put(TITLE, files[i].getName());
				map.put(COUNT, files[i].length());
				list.add(map);

				// ������һ���ļ���
				return list;
			}
		}
		return list;
	}

	/**
	 * ��ͼƬ�ļ�,�Һ�׺Ϊ����ͼƬ��׺
	 * 
	 * @param file
	 * @return
	 */
	private boolean isImage(File file) {
		if (null == file) {
			return false;
		}

		return file.getName().matches("/(.*)+\\.(jpg|bmp|gif|png)$/i");

		// Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
		// if (null == bitmap) {
		// // Toast.makeText(this, "ͼƬ��ʽ����", 0).show();
		// return false;
		// } else {
		// return true;
		// }

	}
}
