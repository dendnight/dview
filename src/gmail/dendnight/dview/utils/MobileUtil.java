package gmail.dendnight.dview.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * �豸����
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013��10��24�� ����10:20:46  
 *  
 * �޸���ʷ:
 * ����    ����    �汾  �޸�����
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MobileUtil {

	/**
	 * 
	 * @param format
	 *            ���ڸ�ʽ
	 * @param taken
	 *            1970����ĺ�����
	 * @return
	 */
	public static String fDate(String format, long taken) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(taken);
		SimpleDateFormat f = new SimpleDateFormat(format, Locale.getDefault());

		return f.format(calendar.getTime());
	}
}
