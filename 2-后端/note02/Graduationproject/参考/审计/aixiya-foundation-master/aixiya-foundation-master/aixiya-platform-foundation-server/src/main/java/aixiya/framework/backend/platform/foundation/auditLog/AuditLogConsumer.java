package aixiya.framework.backend.platform.foundation.auditLog;

import aixiya.framework.backend.platform.foundation.mapper.AuditLogBusinessMapper;
import com.aixiya.framework.backend.common.entity.auditLog.AuditLogBusiness;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author wangqun865@163.com
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AuditLogConsumer {
    private static final String AuditLogTopic = "foundation-auditlog";

    private final AuditLogBusinessMapper auditLogBusinessMapper;

    @KafkaListener(topics = {AuditLogTopic}, groupId = "audit-log-consumer", errorHandler = "auditLogConsumerAwareErrorHandler")
    public void onMessage4(ConsumerRecord<?, ?> record ) throws Exception {

        try {
            log.info("auditLog consumer topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
            String auditLogBusinessJson = (String) record.value();
            AuditLogBusiness auditLogBusiness = JSON.parseObject(auditLogBusinessJson, AuditLogBusiness.class);

            QueryWrapper<AuditLogBusiness> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("batch_uuid" , auditLogBusiness.getBatchUuid());
            AuditLogBusiness queryAuditLogBusiness = auditLogBusinessMapper.selectOne(queryWrapper);
            if (queryAuditLogBusiness == null) {
                auditLogBusinessMapper.insert(auditLogBusiness);
            } else {
                log.error("kafka 消费重复!");
            }
        } catch (Exception e) {
            log.error("kafka 消费失败" , e);
            log.error("kafka 消费失败 value:"  + (String)record.value());
            throw new Exception("kafka 消费失败 :" + e.getMessage());
        }

    }

}
