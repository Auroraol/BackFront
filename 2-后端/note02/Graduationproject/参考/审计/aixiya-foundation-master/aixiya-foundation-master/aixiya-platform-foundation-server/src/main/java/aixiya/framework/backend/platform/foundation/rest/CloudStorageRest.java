package aixiya.framework.backend.platform.foundation.rest;

import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import aixiya.framework.backend.platform.foundation.service.ObsService;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("public/api/storage")
public class CloudStorageRest {

    @Autowired
    private ObsService obsService;

    /**
     * 文件上传确认
     * 文件ID和业务ID绑定
     *
     * @param files
     */
    @RequestMapping(value = "/simple/confirm", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse simpleConfirm(@RequestBody @Validated List<DownloadVo> files) {
        try {
            String request = JSON.toJSONString(files);
            log.info("调用API{}请求{}","/simple/confirm",request);
            obsService.simpleConfirm(files);
            return new AixiyaFwResponse().success("success");
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new AixiyaFwResponse().fail(e.getMessage());
        }
    }

    /**
     * 根据业务ID和文件类型获取文件 在某系统的下载地址,文件类型为空时，按业务ID获取全部文件
     * @return
     */
    @RequestMapping(value = "/download/urls", method = RequestMethod.POST)
    @ResponseBody
    public AixiyaFwResponse downloadUrls(@RequestBody @Validated DownloadUrlVo downloadUrlVo) {
        try {
            String request = JSON.toJSONString(downloadUrlVo);
            log.info("调用API{}请求{}","/download/urls",request);
            List<DownloadVo> downloadVoList = obsService.downloadUrls(downloadUrlVo);
            return new AixiyaFwResponse().data(downloadVoList);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return new AixiyaFwResponse().fail(e.getMessage());
        }


    }

}
