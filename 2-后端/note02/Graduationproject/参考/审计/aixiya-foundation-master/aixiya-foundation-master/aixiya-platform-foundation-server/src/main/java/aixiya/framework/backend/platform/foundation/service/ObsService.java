package aixiya.framework.backend.platform.foundation.service;



import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import aixiya.framework.backend.platform.foundation.api.model.UploadVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author wangqun865@163.com
 */
public interface ObsService {
    /**
     * 前端简单文件上传
     * @param UploadVo
     * @return
     */
    List<DownloadVo> SimpleUpload(UploadVo UploadVo);

    /**
     * 简单文件下载
     * @param response
     * @param id
     * @param withStyle
     */
    void simpleDownloadFile(HttpServletResponse response, String id, String withStyle);

    /**
     * 文件下载 供OCR识别使用
     * @param id
     * @return
     */
    byte[] downloadFileForOcr(String id) ;

    /**
     * 文件上传确认，文件ID和业务ID绑定
     *
     * @param files
     */
    void simpleConfirm(List<DownloadVo> files);


    /**
     * 获取文件url
     * @param downloadUrlVo
     * @return
     */
    List<DownloadVo> downloadUrls(DownloadUrlVo downloadUrlVo) ;

    /**
     * 删除文件
     * @param fileId
     * @param deep
     */
    void deleteFile(String fileId, boolean deep, boolean isAnonymous);
}
