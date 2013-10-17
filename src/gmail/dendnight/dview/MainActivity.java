package gmail.dendnight.dview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
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

	/** ��Ŀ¼*/
	private static final String ROOT = "/";
	
	/** �б�����*/
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(
				this, imageData(ROOT),
				R.layout.activity_main_item,
				new String[] { IMAGE, PATH, TITLE, COUNT },
				new int[] { R.id.image, R.id.path, R.id.title,R.id.count }
		);

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
	 * @return
	 */
	private List<Map<String, Object>> imageData(String url) {
		Map<String, Object> map = null;
		File[] files = new File(url).listFiles();

		// �����ļ�ϵͳ
		for (File file : files) {

			// �ж��Ƿ����ļ���
			if(file.isDirectory()){
				imageData(file.getAbsolutePath());
			}
			
			// �ж��Ƿ���ͼƬ�ļ�
			if(isImage(file)){

				map = new HashMap<String, Object>();
				map.put(IMAGE, R.drawable.ic_launcher);
				map.put(PATH, file.getAbsolutePath());
				map.put(TITLE, file.getName());
				map.put(COUNT, file.length());

				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * ��ͼƬ�ļ�,�Һ�׺Ϊ����ͼƬ��׺
	 * @param file
	 * @return
	 */
	private boolean isImage(File file){
        
		return true;
    }  
}
