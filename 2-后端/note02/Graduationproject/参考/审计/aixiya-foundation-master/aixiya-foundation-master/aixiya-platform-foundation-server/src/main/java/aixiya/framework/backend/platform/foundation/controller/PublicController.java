package aixiya.framework.backend.platform.foundation.controller;

import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import aixiya.framework.backend.platform.foundation.api.model.UploadVo;
import aixiya.framework.backend.platform.foundation.service.ObsService;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("public")
public class PublicController {
    @Autowired
    private ObsService obsService;

    /**
     * 上传文件 支持批量 总大小5G以下  超过5G请调用分段上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/storage/upload/simple", method = RequestMethod.POST)
    public AixiyaFwResponse simpleUpload(@Validated UploadVo file) {
        List<DownloadVo> downloadVoList = obsService.SimpleUpload(file);
        return new AixiyaFwResponse().data(downloadVoList);
    }


    /**
     * 下载文件
     * @param id    文件id
     * @param response
     */
    @RequestMapping(value = "/storage/download/file/{id}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable String id, HttpServletResponse response) {
        this.obsService.simpleDownloadFile(response, id, null);
    }

    /**
     * 下载特殊样式文件
     * @param id
     * @param response
     */
    @RequestMapping(value = "/storage/download/pre/{id}", method = RequestMethod.GET)
    public void downloadPreFile(@PathVariable String id, HttpServletResponse response) {
        this.obsService.simpleDownloadFile(response, id, "image/resize,p_150,limit_0");
    }
}
