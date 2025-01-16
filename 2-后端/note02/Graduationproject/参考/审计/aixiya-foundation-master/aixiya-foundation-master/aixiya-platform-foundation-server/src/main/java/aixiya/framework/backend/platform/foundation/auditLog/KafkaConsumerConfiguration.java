package aixiya.framework.backend.platform.foundation.auditLog;

import aixiya.framework.backend.platform.foundation.mapper.AuditLogBusinessMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * @Author wangqun865@163.com
 */
@Configuration
public class KafkaConsumerConfiguration {

    @Bean(name = "auditLogConsumerAwareErrorHandler")
    public ConsumerAwareListenerErrorHandler auditLogConsumerAwareErrorHandler() {
        /*return (message, exception, consumer) -> {
            //todo 发送报警邮件
            return null;
        };*/
        return new ConsumerAwareListenerErrorHandler() {
            @Autowired
            private AuditLogBusinessMapper auditLogBusinessMapper;

            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException exception,
                                      Consumer<?, ?> consumer) {
                Logger logger = LoggerFactory.getLogger(this.getClass());

                //todo 发送报警邮件
                try {
                    logger.error("--- 发生消费异常 ---");
                    String messageStr = message.toString();
                    logger.error("--消费message :" + messageStr);
                    ConsumerRecord<?, ?> record = (ConsumerRecord<?, ?> )message.getPayload();
                    String jsonValue = (String)record.value();
                    logger.error("--消费jsonValue:" + jsonValue);
                    logger.error("--消费exception: " + exception.getMessage());
                    //todo 是否入失败库
                } catch (Exception e) {
                    logger.error("kafka 失败监听器异常 :" , e);
                } finally {
                    return null;
                }
                //auditLogBusinessMapper.insert(null);
            }
        };
    }


}
