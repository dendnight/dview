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
	private static Bitmap bitmap = null;
	// 原图片路径
	private static String path = null;
	// 缩略图路径
	private static String thumbnail = null;
	// 同一文件夹图片数量
	private static String count = null;
	// 时间
	private static String date = null;
	// 文件夹标题
	private static String folder = null;
	// 最后编辑时间
	private static long taken = 0l;
	// 文件夹编号
	private static String folderId = null;
	// 图片路径
	private static Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	// 之前的时间
	private static String oldDate = null;
	// 结果集
	private static Cursor cursor = null;

	/**
	 * 获取主页相关数据
	 * 
	 * @param contentResolver
	 * @return
	 */
	public static List<Map<String, Object>> listData(ContentResolver contentResolver) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

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
		cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
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

			folder = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

			count = "(" + cursor.getString(cursor.getColumnIndex(DictParam.COUNT)) + ")";

			// 最后编辑时间
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
		cursor = MediaStore.Images.Media.query(contentResolver, uri, projection, where, orderBy);
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

			taken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
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
	 * 按文件夹ID查询对应数据拼接成html
	 * 
	 * @param contentResolver
	 * @param folderId
	 * @return
	 */
	public static String webGallery(ContentResolver contentResolver, String folderId) {
		if (null == folderId || "".equals(folderId.trim())) {
			return null;
		}

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

			date = MobileUtil.fDate("yyyy年MM月dd日", taken);

			// 时间相同的图片
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
		// 关闭cursor
		cursor.close();
		data.append("</body>");
		return data.toString();
	}
}
