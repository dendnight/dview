package gmail.dendnight.dview.data;

import gmail.dendnight.dview.dict.DictParam;
import gmail.dendnight.dview.utils.BitmapUtil;
import gmail.dendnight.dview.utils.MobileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * ͼƬ����
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
public class Images {

	// λͼ
	private static Bitmap bitmap = null;
	// ԭͼƬ·��
	private static String path = null;
	// ����ͼ·��
	private static String thumbnail = null;
	// ͬһ�ļ���ͼƬ����
	private static String count = null;
	// ʱ��
	private static String date = null;
	// �ļ��б���
	private static String folder = null;
	// ���༭ʱ��
	private static long taken = 0l;
	// �ļ��б��
	private static String folderId = null;
	// ͼƬ·��
	private static Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	// ֮ǰ��ʱ��
	private static String oldDate = null;
	// �����
	private static Cursor cursor = null;

	/**
	 * ��ȡ��ҳ�������
	 * 
	 * @param contentResolver
	 * @return
	 */
	public static List<Map<String, Object>> listData(ContentResolver contentResolver) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

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
		cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		while (cursor.moveToNext()) {

			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

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

			folder = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			count = "(" + cursor.getString(cursor.getColumnIndex(DictParam.COUNT)) + ")";

			// ���༭ʱ��
			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			date = MobileUtil.fDate("yyyy-MM-dd HH:mm", taken);

			folderId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));

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

	/**
	 * ��������
	 * 
	 * @param contentResolver
	 * @param folderId
	 * @return
	 */
	public static List<Map<String, Object>> gridData(ContentResolver contentResolver, String folderId) {
		if (null == folderId || "".equals(folderId.trim())) {
			return null;
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		// ����ͼ·��
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;

		// ͼƬ�����ֶ�
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DATE_TAKEN, thumbnailUrl };

		// ���ļ��в�ѯ
		String where = MediaStore.Images.Media.BUCKET_ID + "= '" + folderId + "' ";

		// �����ʱ������
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// ��ѯ
		cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		while (cursor.moveToNext()) {

			// ԭͼƬ·��
			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// ����ͼ·��
			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			// λͼ
			bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			date = MobileUtil.fDate("yyyy-MM-dd HH:mm", taken);

			map = new HashMap<String, Object>();
			map.put(DictParam.IMAGE, bitmap);
			// ʱ����ͬ��ͼƬ
			if (null == oldDate || !oldDate.equals(date)) {
				map.put(DictParam.DATE, date);
			}
			if (null != date) {
				oldDate = date;
			}
			map.put(DictParam.PATH, path);
			list.add(map);
		}
		// �ر�cursor
		cursor.close();
		return list;
	}

	/**
	 * ���ļ���ID��ѯ��Ӧ����ƴ�ӳ�html
	 * 
	 * @param contentResolver
	 * @param folderId
	 * @return
	 */
	public static String webGallery(ContentResolver contentResolver, String folderId) {
		if (null == folderId || "".equals(folderId.trim())) {
			return null;
		}

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
		cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		StringBuilder data = new StringBuilder();
		data.append("<html><head><title>list</title><meta name=\"Author\" content=\"dendnight\"></head><body>");

		while (cursor.moveToNext()) {

			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));

			date = MobileUtil.fDate("yyyy��MM��dd��", taken);

			// ʱ����ͬ��ͼƬ
			if (null == oldDate || !oldDate.equals(date)) {
				data.append("<h3>" + date + "</h3>");
			}
			if (null != date) {
				oldDate = date;
			}
			data.append("<img src=\"" + thumbnail
					+ "\" style=\"width:110px;height:110px;\" onclick=\"window.imagelistner.openImage('" + path
					+ "');return false;\"/>&nbsp;");

		}
		// �ر�cursor
		cursor.close();
		data.append("</body>");
		return data.toString();
	}
}
