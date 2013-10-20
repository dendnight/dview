package gmail.dendnight.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 位图相关工具
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月20日 下午2:46:26  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class BitmapUtil {

	/**
	 * 创建自定义Bitmap
	 * 
	 * @param path
	 *            完整路径
	 * @param reqWidth
	 *            需要的Bitmap宽度
	 * @param reqHeight
	 *            需要的Bitmap高度
	 * @return
	 */
	public static Bitmap getBitmap(String path, int reqWidth, int reqHeight) {
		// 首先解码与inJustDecodeBounds = true来检查尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// 计算inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqHeight, reqHeight);
		options.inJustDecodeBounds = false;
		return ImageCrop(BitmapFactory.decodeFile(path, options));
	}

	/**
	 * 按正方形裁切图片
	 */
	private static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

		int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;

		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}

	/**
	 * 计算inSampleSize
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 原始图像的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// 计算比率的高度和宽度,高度和宽度要求
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// 选择最小的比例作为inSampleSize值,这将保证最终的图像与两个维度大于或等于请求的高度和宽度。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

}
