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

	/**
	 * 单文件上传
	 * @param upload 上传文件
	 * @return 上传结果
	 */
	@RequestMapping("/gridfs-upload")
	@ResponseBody
	public FileResult upload(MultipartFile upload) {
		return srv.uploadFile(upload);
	}
	/**
	 * 百度ueditor上传文件
	 * @param upload 上传文件
	 * @param domain 由于ueditor上传后会在编辑器中显示图片,所以此处需要文件服务器域名或或IP:PORT
	 * @return 上传结果
	 */
	@RequestMapping("/ueditor-upload")
	@ResponseBody
	public UeditorResult ueditorUpload(MultipartFile upload, String domain) {
		return srv.ueditorUpload(upload, domain);
	}
	
	/**
	 * 根据f_id获得文件
	 * @param f_id 
	 * @param response
	 */
	@RequestMapping("/gridfs-download")
	public void download(String f_id, HttpServletResponse response) {
		srv.downloadFile(f_id, response);
	}

	/**
	 * 根据f_id删除文件
	 * @param f_id
	 * @return
	 */
	@RequestMapping("/gridfs-delete")
	@ResponseBody
	public FileResult delete(String f_id) {
		return srv.deleteFile(f_id);
	}
	
	
}
