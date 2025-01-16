package aixiya.framework.backend.platform.foundation.handler;


import aixiya.framework.backend.platform.foundation.api.common.exception.OcrBaiduException;
import aixiya.framework.backend.platform.foundation.api.common.exception.OcrDownloadException;
import aixiya.framework.backend.platform.foundation.api.utils.constants.RestCodeConstants;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.aixiya.framework.backend.common.handler.BaseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author wangqun865@163.com
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class BaseFuncHandler extends BaseExceptionHandler {

    @ExceptionHandler(OcrDownloadException.class)
    public AixiyaFwResponse ocrDownloadExceptionHandler(HttpServletResponse response, OcrDownloadException ex) {
        //response.setStatus(430);
        log.error(ex.getMessage(), ex);
        return new AixiyaFwResponse().data(RestCodeConstants.OCR_DOWNLOAD_ERROR+"","", ex.getMessage());
    }

    @ExceptionHandler(OcrBaiduException.class)
    public AixiyaFwResponse ocrBaiduExceptionHandler(HttpServletResponse response,OcrBaiduException ex) {
        log.error(ex.getMessage(), ex);
        return new AixiyaFwResponse().data(RestCodeConstants.OCR_BAIDU_ERROR+"","", ex.getMessage());
    }


    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AixiyaFwResponse handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(),e);
        return new AixiyaFwResponse().fail("系统内部异常");
    }



}
