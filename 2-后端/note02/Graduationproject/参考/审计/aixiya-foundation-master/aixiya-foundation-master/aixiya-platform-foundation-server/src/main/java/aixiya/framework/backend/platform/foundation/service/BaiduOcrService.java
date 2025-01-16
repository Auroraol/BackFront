package aixiya.framework.backend.platform.foundation.service;


import aixiya.framework.backend.platform.foundation.api.model.BaseOcrResponse;

/**
 * @Author wangqun865@163.com
 */
public interface BaiduOcrService {

    BaseOcrResponse ocrIdCardFront(String fileId, final byte[] file, String type);

}
