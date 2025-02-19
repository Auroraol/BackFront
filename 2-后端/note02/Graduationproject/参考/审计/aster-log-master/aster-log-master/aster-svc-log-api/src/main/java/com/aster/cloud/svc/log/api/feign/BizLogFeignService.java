package com.aster.cloud.svc.log.api.feign;


import com.aster.cloud.commons.core.api.Result;
import com.aster.cloud.svc.log.api.constant.LogConstant;
import com.aster.cloud.svc.log.api.entity.BizLog;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 王骞
 * @date 2021-01-11
 */
@Api(tags = "业务日志服务")
@Component
@FeignClient(contextId = "bizLogFeignService", value = LogConstant.SERVICE_NAME)
public interface BizLogFeignService {

    /**
     * 保存日志
     * @param bizLog 日志实体
     * @return succes、false
     */
    @PostMapping("/biz/save")
    Result<Boolean> saveLog(@RequestBody BizLog bizLog);
}
