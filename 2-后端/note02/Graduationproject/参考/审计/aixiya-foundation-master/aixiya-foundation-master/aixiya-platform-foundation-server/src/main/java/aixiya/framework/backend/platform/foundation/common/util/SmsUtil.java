package aixiya.framework.backend.platform.foundation.common.util;

import java.util.Random;

/**
 * @Author wangqun865@163.com
 */
public class SmsUtil {

    /**
     * 生成指定位数随机数
     */
    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
}
