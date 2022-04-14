package springold.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author：Weitj
 * @Description：
 * @Date： 2022/01/24 10:51
 * @Version 1.0
 */
public class DateFormatUtil {
    public static final String  formatStr = "yyyy-MM-dd HH:ss";
    public static SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     * @Description:  返回一个当前日期的指定格式的字符串
     * @Author: Weitj
     * @Date: 2022/01/24 10:55
     * @return: java.lang.String
     */
    public static String  formatNow(){
        sdf.applyPattern(formatStr);
        return sdf.format(new Date());
    }
}
