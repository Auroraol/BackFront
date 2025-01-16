package aixiya.framework.backend.platform.foundation.api.common.exception;


import aixiya.framework.backend.platform.foundation.api.utils.constants.RestCodeConstants;
import com.aixiya.framework.backend.common.exception.AixiyaFwRuntimeException;

/**
 * @Author wangqun865@163.com
 */
public class OcrBaiduException extends AixiyaFwRuntimeException {
    public OcrBaiduException(String message) {
        super(message, RestCodeConstants.OCR_BAIDU_ERROR);
    }

}
