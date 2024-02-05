package com.springboot101.confirm.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LFJ
 * @Date: 2024-02-05 22:06
 */

@Configuration
public class MqConfirmConfig {
	    //普通交换机
		public static final String CONFIRM_EXCHANGE="confirm.exchange";
		//普通队列
		public static final String CONFIRM_QUEUE_NAME="confirm_queue";
		//RoutingKey
		public static final String CONFIRM_EXCHANGE_ROUTING_KEY="key1";

		//备份交换机
		public static final String BACKUP_EXCHANGE="backup.exchange";
		//备份队列
		public static final String BACKUP_QUEUE_NAME="backup.queue";
		//报警队列
		public static final String WARNING_QUEUE_NAME="warning.queue";

		/**
		 * 声明Direct交换机
		 * @return Direct类型交换机
		 */
		@Bean
		public DirectExchange confirmExchange(){
			//绑定确认交换机与备份交换机
			Map<String,Object> argument=new HashMap<>();
			argument.put("alternate-exchange",BACKUP_EXCHANGE);
			return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).withArguments(argument).build();
		}

		/**
		 * 声明fanout备份交换机
		 * @return fanout类型交换机
		 */
		@Bean
		public FanoutExchange backupExchange(){
			return new FanoutExchange(BACKUP_EXCHANGE);
		}

		//普通队列
		@Bean
		public Queue confirmQueue(){
			return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
		}

		//备份队列
		@Bean
		public Queue backupQueue(){
			return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
		}

		//警告队列
		@Bean
		public Queue warningQueue(){
			return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
		}

		//绑定普通交换机和普通队列
		@Bean
		public Binding EAndQBind(@Qualifier("confirmExchange") DirectExchange confirmExchange, @Qualifier("confirmQueue")Queue confirmQueue){
			return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_EXCHANGE_ROUTING_KEY);
		}

		//绑定备份交换机和备份队列
		@Bean
		public Binding backupQueueBindingBackupExchange(@Qualifier("backupQueue") Queue backupQueue,
														@Qualifier("backupExchange")FanoutExchange backupExchange){
			return BindingBuilder.bind(backupQueue).to(backupExchange);
		}

		//绑定备份交换机和报警队列
		@Bean
		public Binding warningQueueBindingBackupExchange(@Qualifier("warningQueue") Queue waringQueue,
														 @Qualifier("backupExchange")FanoutExchange backupExchange){
			return BindingBuilder.bind(waringQueue).to(backupExchange);
		}
}