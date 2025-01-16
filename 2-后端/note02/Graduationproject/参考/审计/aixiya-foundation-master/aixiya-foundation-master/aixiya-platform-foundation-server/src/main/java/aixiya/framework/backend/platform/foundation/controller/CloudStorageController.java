package aixiya.framework.backend.platform.foundation.controller;

import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import aixiya.framework.backend.platform.foundation.service.ObsService;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("authless/storage")
public class CloudStorageController {

    @Autowired
    private ObsService obsService;


    /**
     * 根据业务ID和文件类型获取文件 在某系统的下载地址,文件类型为空时，按业务ID获取全部文件
     * @return
     */
    @RequestMapping(value = "/download/urls", method = RequestMethod.GET)
    public AixiyaFwResponse downloadUrls(@RequestBody @Validated DownloadUrlVo downloadUrlVo) {
        List<DownloadVo> downloadVoList = obsService.downloadUrls(downloadUrlVo);
        return new AixiyaFwResponse().data(downloadVoList);
    }

    /**
     * 删除文件
     * @param fileId
     * @param deep false 不删除桶文件   true 删除桶文件
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.DELETE)
    public AixiyaFwResponse deleteFile(@RequestParam("fileId") @Valid @NotBlank String fileId ,
                                    @RequestParam("deep") @Valid @NotBlank String deep ,
                                    @RequestParam("anonymous") @Valid @NotBlank String anonymous ) {
        boolean isDeep = false;
        boolean isAnonymous = false;
        if (deep.equals("1")) {
            isDeep = true;
        }
        if (anonymous.equals("1")) {
            isAnonymous = true;
        }
        obsService.deleteFile(fileId ,isDeep , isAnonymous);
        return new AixiyaFwResponse();
    }


}
