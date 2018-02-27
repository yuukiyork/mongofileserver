package com.wardensky.mongofileserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wardensky.mongofileserver.srv.MongoGridFsDao;

@Controller
public class MyController {
	
	@Autowired
	MongoGridFsDao dao;
	
	@RequestMapping("/test")
	public String test(Model model) {
		dao.uploadFile(null, "11");
        return "publish";
    }  
}
