package com.aster.cloud.svc.log.api.context.event;

import com.aster.cloud.svc.log.api.context.ILogEvent;
import com.aster.cloud.svc.log.api.entity.BizLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务日志事件
 *
 * @author 王骞
 * @date 2021-01-13
 */
@Getter
@AllArgsConstructor
public class BizLogEvent implements ILogEvent<BizLog> {

    private final BizLog bizLog;

    @Override
    public BizLog getLog() {
        return bizLog;
    }

}
