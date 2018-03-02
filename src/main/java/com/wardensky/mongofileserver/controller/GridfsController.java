package com.wardensky.mongofileserver.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wardensky.mongofileserver.srv.MongoGridFsSrv;
import com.wardensky.mongofileserver.vo.FileResult;
import com.wardensky.mongofileserver.vo.UeditorResult;

@Controller
public class GridfsController {
	
	@Autowired
	private MongoGridFsSrv srv;

	@RequestMapping("/gridfs-upload")
	@ResponseBody
	public FileResult upload(MultipartFile upload) {
		return srv.uploadFile(upload);
	}
	
	@RequestMapping("/ueditor-upload")
	@ResponseBody
	public UeditorResult ueditorUpload(MultipartFile upload, String domain) {
		return srv.ueditorUpload(upload, domain);
	}
	
	@RequestMapping("/gridfs-download")
	public void download(String f_id, HttpServletResponse response) {
		srv.downloadFile(f_id, response);
	}

	@RequestMapping("/gridfs-delete")
	@ResponseBody
	public FileResult delete(String f_id) {
		return srv.deleteFile(f_id);
	}
	
	
}
