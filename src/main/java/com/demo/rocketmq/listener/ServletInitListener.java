
package com.demo.rocketmq.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebListener
public class ServletInitListener implements ServletContextListener {
	
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		System.out.println("ServletContext销毁------------------");
		DefaultMQProducer defaultMQProducer = (DefaultMQProducer) ctx.getBean("defaultMQProducer");
		DefaultMQPushConsumer defaultMQPushConsumer = (DefaultMQPushConsumer) ctx.getBean("defaultMQPushConsumer");
		defaultMQProducer.shutdown();
		defaultMQPushConsumer.shutdown();
	}
	
	/**
	 * ServletContext 初始化后执行
	 */
	@Override
	public void contextInitialized(ServletContextEvent context) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		System.out.println("ServletContext初始化------------------");
		DefaultMQProducer defaultMQProducer = (DefaultMQProducer) ctx.getBean("defaultMQProducer");
		DefaultMQPushConsumer defaultMQPushConsumer = (DefaultMQPushConsumer) ctx.getBean("defaultMQPushConsumer");
		
		try {
			defaultMQProducer.start();
			
			defaultMQPushConsumer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		
	}
	
}
