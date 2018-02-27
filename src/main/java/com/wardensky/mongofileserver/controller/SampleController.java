package com.wardensky.mongofileserver.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
	@RequestMapping("/addUser1")
	public String addUser1(String username, String password) {
		System.out.println("username is:" + username);
		System.out.println("password is:" + password);
		return "你好";
	}

	@RequestMapping("/")
	@ResponseBody
	public String home() {
		System.out.println("haha");
		StringBuilder sb = new StringBuilder();
		StringBuffer sb1 = new StringBuffer();
		ArrayList<String> list = new ArrayList<String>();
		HashMap<String,String> map = new HashMap<String, String>();
		return "Hello World--------aaaaa!";
	}

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SampleController.class, args);
		int oldCapacity = 7;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity + "");
	}
}
