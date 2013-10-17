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

	/** 图片 */
	private static final String IMAGE = "image";

	/** 文件夹路径 */
	private static final String PATH = "path";

	/** 图片名称 */
	private static final String TITLE = "title";

	/** 同一文件夹下图片数量 */
	private static final String COUNT = "count";

	/** 根目录 */
	private static final String ROOT = "/mnt/sdcard";

	/** 排除超过10MB的图片 */
	private static final long IMAGE_MAX_SIZE = 10485760;

	/** 列表数据 */
	private List<Map<String, Object>> list = null;

	/** 列表子数据 */
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
	 * 获取图片数据<br>
	 * 判断是文件夹里是否有图片,如果有则将第一张图片生成位图显示在对应位置
	 * 
	 * @return
	 */
	private List<Map<String, Object>> imageData(String url) {
		File[] files = new File(url).listFiles();

		// 非空则遍历文件系统
		if (null != files) {
			for (int i = 0; i < files.length; i++) {

				// 判断是否是文件夹
				if (files[i].isDirectory()) {
					imageData(files[i].getAbsolutePath());
				}

				// 判断文件是否超过10MB
				if (IMAGE_MAX_SIZE < files[i].length()) {
					files[i] = null;
					continue;
				}

				// 判断是否是图片文件
				// boolean isImage = isImage(files[i]);
				// if (!isImage) {
				// continue;
				// }

				// 添加到list
				map = new HashMap<String, Object>();
				map.put(IMAGE, R.drawable.ic_launcher);
				map.put(PATH, files[i].getAbsolutePath());

				map.put(TITLE, files[i].getName());
				map.put(COUNT, files[i].length());
				list.add(map);

				// 跳入下一个文件夹
				return list;
			}
		}
		return list;
	}

	/**
	 * 是图片文件,且后缀为正常图片后缀
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
		// // Toast.makeText(this, "图片格式错误！", 0).show();
		// return false;
		// } else {
		// return true;
		// }

	}
}
