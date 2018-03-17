
package com.demo.rocketmq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rocketmq.template.RocketMQTemplate;

@RestController
public class SenderController {
	
	@Autowired
	private RocketMQTemplate rocketMQTemplate;
	
	@RequestMapping(value = "/send")
	public String sendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		for (int i = 0; i < 1; i++) {
			rocketMQTemplate.send("helloTopic", "这是一个rocketmq发送例子");
		}
		
		return "send success";
	}
	
}
