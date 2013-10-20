package gmail.dendnight.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * ͼ����ع���
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��20�� ����2:32:19  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class GalleryUtil {

	/**
	 * ɨ��ͼƬ
	 */
	public static void ss() {

	}

	/**
	 * ��ȡ��ҳ�������
	 * 
	 * @param contentResolver
	 * @return
	 */
	public static List<Map<String, Object>> mainGallery(ContentResolver contentResolver) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		// ͼƬ·��
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// �ļ����µ�ͼƬ����
		String sqlCount = "COUNT(" + MediaStore.Images.Media._ID + ") as " + DictParam.COUNT;

		// ͼƬ�����ֶ�
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.DATA, sqlCount };

		// ���ļ��з���
		String where = " 0 = 0 ) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

		// ���ļ��з���
		String orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " asc";

		// ��ѯ
		Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		// ImageView imageView = null;
		while (cursor.moveToNext()) {

			// ԭͼƬ·��
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// ����ͼ
			Bitmap bitmap = BitmapUtil.getBitmap(path, DictParam.WIDTH, DictParam.HEIGHT);

			// ���ļ���·�� "/storage/sdcard/DCIM/2343311610103519.jpg"; "/sdcard/"
			if (path.startsWith("/storage/")) {
				path = path.substring(8, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			} else {
				path = path.substring(0, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			}

			// �ļ��б���
			String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			// ͬһ�ļ���ͼƬ����
			String count = "(" + cursor.getString(cursor.getColumnIndex(DictParam.COUNT)) + ")";

			// ���༭ʱ��
			long added = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(added);

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			String date = f.format(ca.getTime());

			map = new HashMap<String, Object>();
			map.put(DictParam.IMAGE, bitmap);
			map.put(DictParam.TITLE, title);

			map.put(DictParam.COUNT, count);
			map.put(DictParam.PATH, path);
			map.put(DictParam.DATE, date);

			list.add(map);
		}
		// �ر�cursor
		cursor.close();
		return list;
	}
}
