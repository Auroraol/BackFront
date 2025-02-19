package aixiya.framework.backend.platform.foundation.controller;

import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
@Validated
@RestController
@RequestMapping("public/")
public class TestLog {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public AixiyaFwResponse downloadUrls(@RequestBody @Valid DownloadUrlVo downloadUrlVo) {
        //List<DownloadVo> downloadVoList = obsService.downloadUrls(downloadUrlVo);
        List<DownloadVo> downloadVoList = new ArrayList<>();
        DownloadVo downloadVo = new DownloadVo();
        downloadVo.setCenterId("1");
        downloadVo.setSourceType("5");
        downloadVo.setUpUserName("2");
        downloadVo.setUpUserId("3");
        downloadVo.setTypeName("4");
        downloadVoList.add(downloadVo);
        return new AixiyaFwResponse().data(downloadVoList);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public AixiyaFwResponse test2(@RequestParam("fileId") @Valid @NotBlank String fileId ,
                               @RequestParam("isDeep") @Valid @NotNull String isDeep ,
                               @RequestParam("isAnonymous") @Valid  int isAnonymous) {
        List<DownloadVo> downloadVoList = new ArrayList<>();
        DownloadVo downloadVo = new DownloadVo();
        downloadVo.setCenterId("1");
        downloadVo.setSourceType("1");
        downloadVo.setUpUserName("2");
        downloadVo.setUpUserId("3");
        downloadVo.setTypeName("4");
        downloadVoList.add(downloadVo);
        return new AixiyaFwResponse().data(downloadVoList);
    }

    @RequestMapping(value = "/test3", method = RequestMethod.DELETE)
    public AixiyaFwResponse test3(DownloadUrlVo downloadUrlVo) {
        List<DownloadVo> downloadVoList = new ArrayList<>();
        DownloadVo downloadVo = new DownloadVo();
        downloadVo.setCenterId("1");
        downloadVo.setSourceType("1");
        downloadVo.setUpUserName("2");
        downloadVo.setUpUserId("3");
        downloadVo.setTypeName("4");
        downloadVoList.add(downloadVo);
        return new AixiyaFwResponse().data(downloadVoList);
    }

    @RequestMapping(value = "/test4", method = RequestMethod.PUT)
    @ResponseBody
    public AixiyaFwResponse test4(@RequestBody  DownloadUrlVo downloadUrlVo ,@RequestParam("fileId") @Valid @NotBlank String fileId ) {
        List<DownloadVo> downloadVoList = new ArrayList<>();
        DownloadVo downloadVo = new DownloadVo();
        int a = 1/0;
        downloadVo.setCenterId("1");
        downloadVo.setSourceType("1");
        downloadVo.setUpUserName("2");
        downloadVo.setUpUserId("3");
        downloadVo.setTypeName("4");
        downloadVoList.add(downloadVo);
        return new AixiyaFwResponse().data(downloadVoList);
    }
}
