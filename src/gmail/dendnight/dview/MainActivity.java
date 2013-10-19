package gmail.dendnight.dview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

	/** 缩略图路径 */
	private static final String THUMBNAIL = "thumbnail";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);

		// 适配器
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
		Map<String, Object> map = null;

		// 图片路径
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// 文件夹下的图片数量
		String sqlCount = "COUNT(" + MediaStore.Images.Media._ID + ") as " + COUNT;

		// 缩略图路径
		String thumbnailPath = "(SELECT " + MediaStore.Images.Thumbnails.DATA
				+ " FROM thumbnails WHERE thumbnails.image_id = images._id) AS " + THUMBNAIL;

		// 图片所需字段
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Images.Media.DATA, sqlCount, thumbnailPath };

		// 按文件夹分组
		String where = " 0 = 0 ) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

		// 按文件夹分组
		String orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " asc";

		// 查询
		Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), uri, projection, where, orderBy);

		while (cursor.moveToNext()) {

			// 原图片路径
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// 缩略图路径
			String tPath = cursor.getString(cursor.getColumnIndex(THUMBNAIL));

			// 缩略图
			Bitmap bitmap = null;
			if (null != tPath && "" != tPath.trim()) {
				bitmap = BitmapFactory.decodeFile(path);
			}

			// 父文件夹路径 "/storage/sdcard/DCIM/2343311610103519.jpg"; "/sdcard/"
			if (path.startsWith("/storage/")) {
				path = path.substring(8, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			}

			// 文件夹标题
			String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			// 同一文件夹图片数量
			String count = "(" + cursor.getString(cursor.getColumnIndex(COUNT)) + ")";

			map = new HashMap<String, Object>();
			map.put(IMAGE, tPath);
			map.put(PATH, path);
			map.put(TITLE, title);
			map.put(COUNT, count);
			list.add(map);
		}
		return list;
	}
}