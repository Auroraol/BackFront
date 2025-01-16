package com.aster.cloud.svc.log.api.context.event.publisher;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.aster.cloud.svc.log.api.context.event.BizLogEvent;
import com.aster.cloud.svc.log.api.entity.BizLog;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 日志事件发布
 *
 * @author 王骞
 * @date 2021-01-13
 */
@Component
@AllArgsConstructor
public class BizLogEventPublisher {

    private final ApplicationEventPublisher publisher;


    public void publishEvent() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        BizLog bizLog = new BizLog();
        bizLog.setCreateBy("王骞");
        bizLog.setBizType("测试");
        bizLog.setBizId("sys");
        bizLog.setTitle("主动触发");
        bizLog.setRemoteAddr(ServletUtil.getClientIP(request));
        bizLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        bizLog.setMethod(request.getMethod());
        bizLog.setParams(HttpUtil.toParams(request.getParameterMap()));
        // 发送异步日志事件
        publisher.publishEvent(new BizLogEvent(bizLog));
    }


}
