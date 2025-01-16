package com.aster.cloud.svc.log.api.util;


import com.aster.cloud.commons.core.SpringContextHolder;
import com.aster.cloud.svc.log.api.context.event.publisher.BizLogEventPublisher;
import lombok.experimental.UtilityClass;

/**
 * 系统日志工具类
 *
 * @author 王骞
 * @date 2021-01-13
 */
@UtilityClass
public class LogUtils {

    public void publishBizLog() {
        BizLogEventPublisher bizLogEventPublisher = SpringContextHolder.getBean(BizLogEventPublisher.class);
        bizLogEventPublisher.publishEvent();
    }

}
