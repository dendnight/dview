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

	/** 图片 */
	private static final String IMAGE = "image";

	/** 文件夹路径 */
	private static final String PATH = "path";

	/** 图片名称 */
	private static final String TITLE = "title";

	/** 同一文件夹下图片数量 */
	private static final String COUNT = "count";

	/** 所需图片属性 */
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
	 * 获取图片数据<br>
	 * 
	 * @return
	 */
	private List<Map<String, Object>> imageData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(IMAGE, R.drawable.ic_launcher);
		map.put(PATH, "asda");
		map.put(TITLE, "asdasd");
		map.put(COUNT, "日你没");
		list.add(map);

		return list;
	}

}