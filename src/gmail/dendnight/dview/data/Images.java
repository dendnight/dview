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
 * 图片数据
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月20日 下午2:32:19  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class Images {

	// 位图
	static Bitmap bitmap = null;
	// 原图片路径
	static String path = null;
	// 缩略图路径
	static String thumbnail = null;
	// 同一文件夹图片数量
	static String count = null;
	// 时间
	static String date = null;

	/**
	 * 获取主页相关数据
	 * 
	 * @param contentResolver
	 * @return
	 */
	public static List<Map<String, Object>> listData(ContentResolver contentResolver) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		// 图片路径
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// 文件夹下的图片数量
		String sqlCount = "COUNT(" + MediaStore.Images.Media._ID + ") as " + DictParam.COUNT;

		// 缩略图路径
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;

		// 图片所需字段
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.DATA, sqlCount,
				MediaStore.Images.Media.BUCKET_ID, thumbnailUrl };

		// 按文件夹分组
		String where = " 0 = 0 ) GROUP BY (" + MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

		// 按添加时间排序
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// 查询
		Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		while (cursor.moveToNext()) {

			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

			// 父文件夹路径 "/storage/sdcard/DCIM/2343311610103519.jpg"; "/sdcard/"
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

			// 文件夹标题
			String folder = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			count = "(" + cursor.getString(cursor.getColumnIndex(DictParam.COUNT)) + ")";

			// 最后编辑时间
			long taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			date = MobileUtil.fDate("yyyy-MM-dd HH:mm", taken);

			// 文件夹编号
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
		// 关闭cursor
		cursor.close();
		return list;
	}

	/**
	 * 网格数据
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

		// 图片路径
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// 缩略图路径
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;

		// 图片所需字段
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DATE_TAKEN, thumbnailUrl };

		// 按文件夹查询
		String where = MediaStore.Images.Media.BUCKET_ID + "= '" + folderId + "' ";

		// 按添加时间排序
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// 查询
		Cursor cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
		String oldDate = null;
		while (cursor.moveToNext()) {

			// 原图片路径
			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

			// 缩略图路径
			thumbnail = cursor.getString(cursor.getColumnIndex(DictParam.THUMBNAIL));
			if (null == thumbnail || "".equals(thumbnail.trim())) {
				thumbnail = path;
			}
			// 位图
			bitmap = BitmapUtil.getBitmap(thumbnail, DictParam.WIDTH, DictParam.HEIGHT);

			// 最后编辑时间
			long taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
			date = MobileUtil.fDate("yyyy-MM-dd HH:mm", taken);

			map = new HashMap<String, Object>();
			map.put(DictParam.IMAGE, bitmap);
			// 时间相同的图片
			if (null == oldDate || !oldDate.equals(date)) {
				map.put(DictParam.DATE, date);
			}
			if (null != date) {
				oldDate = date;
			}
			map.put(DictParam.PATH, path);
			list.add(map);
		}
		// 关闭cursor
		cursor.close();
		return list;
	}

	/**
	 * 按文件夹ID查询对应数据
	 * 
	 * @param contentResolver
	 * @param folderId
	 * @return
	 */
	public static Cursor webGallery(ContentResolver contentResolver, String folderId) {
		if (null == folderId || "".equals(folderId.trim())) {
			return null;
		}

		// 图片路径
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		// 缩略图路径
		String thumbnailUrl = "(SELECT _data FROM thumbnails WHERE thumbnails.image_id = images._id) AS "
				+ DictParam.THUMBNAIL;
		// 图片所需字段
		String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.DATA, thumbnailUrl };

		// 按文件夹查询
		String where = MediaStore.Images.Media.BUCKET_ID + "= '" + folderId + "' ";

		// 按添加时间排序
		String orderBy = MediaStore.Images.Media.DATE_TAKEN + " desc";

		// 查询
		return MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);

	}
}
