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

		// ����ͼ·��
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;

		// ͼƬ�����ֶ�
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DATA, sqlCount,
				MediaStore.Images.Media.BUCKET_ID, thumbnailUrl };

		// ���ļ��з���
		String where = " 0 = 0 ) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

		// �����ʱ������
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// ��ѯ
		Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		while (cursor.moveToNext()) {

			// ԭͼƬ·��
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// ����ͼ·��
			String thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			// λͼ
			Bitmap bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

			// ���ļ���·�� "/storage/sdcard/DCIM/2343311610103519.jpg"; "/sdcard/"
			if (path.startsWith("/storage/")) {
				path = path.substring(8, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			} else if (path.startsWith("/mnt/")) {
				path = path.substring(4, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			} else {
				path = path.substring(4, path.lastIndexOf("/"));
				path = path.substring(0, path.lastIndexOf("/") + 1);
			}

			// �ļ��б���
			String folder = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			// ͬһ�ļ���ͼƬ����
			String count = "(" + cursor.getString(cursor.getColumnIndex(DictParam.COUNT)) + ")";

			// ���༭ʱ��
			long taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(taken);

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
			String date = f.format(ca.getTime());

			// �ļ��б��
			String folderId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));

			map = new HashMap<String, Object>();
			map.put(DictParam.IMAGE, bitmap);
			map.put(DictParam.FOLDER, folder);

			map.put(DictParam.COUNT, count);
			map.put(DictParam.PATH, path);
			map.put(DictParam.DATE, date);

			map.put(DictParam.FOLDER_ID, folderId);
			list.add(map);
		}
		// �ر�cursor
		cursor.close();
		return list;
	}

	public static List<Map<String, Object>> listGallery(ContentResolver contentResolver, String folderId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		// ͼƬ·��
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// ����ͼ·��
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;

		// ͼƬ�����ֶ�
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.DATA, thumbnailUrl };

		// ���ļ��в�ѯ
		String where = MediaStore.Images.Media.BUCKET_ID + "= '" + folderId + "' ";

		// �����ʱ������
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// ��ѯ
		Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		String oldDate = null;
		while (cursor.moveToNext()) {

			// ԭͼƬ·��
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// ����ͼ·��
			String thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			// λͼ
			Bitmap bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

			// ���༭ʱ��
			long taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(taken);

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			String date = f.format(ca.getTime());

			map = new HashMap<String, Object>();
			map.put(DictParam.IMAGE, bitmap);

			if (null == oldDate || !oldDate.equals(date.trim())) {
				map.put(DictParam.DATE, date);
			}
			oldDate = date;

			map.put(DictParam.PATH, path);
			list.add(map);
		}
		// �ر�cursor
		cursor.close();
		return list;
	}
}
