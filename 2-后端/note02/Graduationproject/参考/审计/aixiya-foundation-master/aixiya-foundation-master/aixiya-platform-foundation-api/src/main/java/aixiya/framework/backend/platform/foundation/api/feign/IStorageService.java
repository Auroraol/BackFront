package aixiya.framework.backend.platform.foundation.api.feign;

import aixiya.framework.backend.platform.foundation.api.fallback.StorageServiceFallback;
import aixiya.framework.backend.platform.foundation.api.model.DownloadUrlVo;
import aixiya.framework.backend.platform.foundation.api.model.DownloadVo;
import com.aixiya.framework.backend.common.api.AixiyaFwResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author wangqun865@163.com
 */
@FeignClient(value = "foundation-center",fallback = StorageServiceFallback.class)
public interface IStorageService {
    /**
     *  文件上传确认
     *  文件ID和业务ID绑定,type取决于之前上传是否有
     *  *文件类型为空或错误时，提交全部业务ID下的文件*
     *
     * @param files
     * @return
     */
    @RequestMapping(value="/public/api/storage/simple/confirm",method = RequestMethod.POST)
    AixiyaFwResponse simpleConfirm(@RequestBody @Validated List<DownloadVo> files);


    /**
     * 根据业务ID和文件类型获取文件 在某系统的下载地址,文件类型为空时，按业务ID获取全部文件
     * @return
     */
    @RequestMapping(value = "/public/api/storage/download/urls", method = RequestMethod.POST)
    @ResponseBody
    AixiyaFwResponse downloadUrls(@RequestBody DownloadUrlVo downloadUrlVo) ;

}
