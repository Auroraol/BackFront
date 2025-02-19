package aixiya.framework.backend.platform.foundation.common.util;

import com.baidu.aip.ocr.AipOcr;

/**
 * @Author wangqun865@163.com
 */
public class OcrClientSingleton {

    private static final String AIP_APP_ID = "16172345";

    private static final String AIP_API_KEY = "xPNN1E7IA61ZzezbQHPrgcpo";

    private static final String AIP_SECRET_KEY = "7a5CP43wTGxFDYXYk1xbBZhVTB2AbPyE";

    private static final Integer CONNECT_TIME_OUT = 1000;

    private static final Integer SOCKET_TIME_OUT = 10000;

    private static AipOcr client = new AipOcr(AIP_APP_ID, AIP_API_KEY, AIP_SECRET_KEY);
    public static AipOcr getInstance() {
        client.setConnectionTimeoutInMillis(CONNECT_TIME_OUT);
        client.setSocketTimeoutInMillis(SOCKET_TIME_OUT);
        return client;
    }


}
