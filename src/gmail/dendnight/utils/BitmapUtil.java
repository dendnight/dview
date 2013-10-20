package gmail.dendnight.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * λͼ��ع���
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��20�� ����2:46:26  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class BitmapUtil {

	/**
	 * �����Զ���Bitmap
	 * 
	 * @param path
	 *            ����·��
	 * @param reqWidth
	 *            ��Ҫ��Bitmap���
	 * @param reqHeight
	 *            ��Ҫ��Bitmap�߶�
	 * @return
	 */
	public static Bitmap getBitmap(String path, int reqWidth, int reqHeight) {
		// ���Ƚ�����inJustDecodeBounds = true�����ߴ�
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// ����inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqHeight, reqHeight);
		options.inJustDecodeBounds = false;
		return ImageCrop(BitmapFactory.decodeFile(path, options));
	}

	/**
	 * �������β���ͼƬ
	 */
	private static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // �õ�ͼƬ�Ŀ���
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// ���к���ȡ������������߳�

		int retX = w > h ? (w - h) / 2 : 0;// ����ԭͼ��ȡ���������Ͻ�x����
		int retY = w > h ? 0 : (h - w) / 2;

		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}

	/**
	 * ����inSampleSize
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// ԭʼͼ��ĸ߶ȺͿ��
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// ������ʵĸ߶ȺͿ��,�߶ȺͿ��Ҫ��
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// ѡ����С�ı�����ΪinSampleSizeֵ,�⽫��֤���յ�ͼ��������ά�ȴ��ڻ��������ĸ߶ȺͿ�ȡ�
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

}
