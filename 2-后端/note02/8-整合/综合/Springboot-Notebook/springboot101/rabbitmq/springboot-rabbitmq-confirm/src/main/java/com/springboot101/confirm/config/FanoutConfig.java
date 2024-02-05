package com.springboot101.confirm.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FanoutConfig {
    /**
     * 声明交换机
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("confirm.fanout");
    }

    /**
     * 第1个队列
     */
    @Bean(name = "confirmTestQueue")
    public Queue confirmTestQueue() {
        return new Queue("fanout.queue1", true, false, false);
    }


    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding confirmTestFanoutExchangeAndQueue(
            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange,
            @Qualifier("confirmTestQueue") Queue confirmTestQueue) {
        return BindingBuilder.bind(confirmTestQueue).to(fanoutExchange);
    }
}


