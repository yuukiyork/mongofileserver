package com.wardensky.mongofileserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
	@RequestMapping(path = {"/test"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String test() {
		System.out.println("你好");
		return "你好";
	}
}
