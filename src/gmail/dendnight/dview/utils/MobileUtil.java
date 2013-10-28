package gmail.dendnight.dview.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 设备工具
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月24日 上午10:20:46  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MobileUtil {

	/**
	 * 
	 * @param format
	 *            日期格式
	 * @param taken
	 *            1970至今的毫秒数
	 * @return
	 */
	public static String fDate(String format, long taken) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(taken);
		SimpleDateFormat f = new SimpleDateFormat(format, Locale.getDefault());

		return f.format(calendar.getTime());
	}
}
