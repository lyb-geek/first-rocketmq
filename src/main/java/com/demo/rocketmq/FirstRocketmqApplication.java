
package com.demo.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("com.demo.rocketmq")
@ComponentScan("com.demo.rocketmq")
@EnableAutoConfiguration
public class FirstRocketmqApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FirstRocketmqApplication.class, args);
	}
}
