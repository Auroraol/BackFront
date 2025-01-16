package com.aster.cloud.svc.log.api.context.event;


import com.aster.cloud.svc.log.api.context.ILogEvent;
import com.aster.cloud.svc.log.api.context.ILogListener;
import com.aster.cloud.svc.log.api.entity.BizLog;
import com.aster.cloud.svc.log.api.feign.BizLogFeignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 业务日志监听事件
 *
 * @author 王骞
 * @date 2021-01-13
 */
@Slf4j
@AllArgsConstructor
@Component
public class BizLogListener implements ILogListener<BizLog> {

    /**
     * 远程访问业务日志服务
     */
    private final BizLogFeignService bizLogFeignService;

    @Async
    @Order
    @EventListener(BizLogEvent.class)
    @Override
    public void saveLog(ILogEvent<BizLog> event) {
        bizLogFeignService.saveLog(event.getLog());
    }


}
