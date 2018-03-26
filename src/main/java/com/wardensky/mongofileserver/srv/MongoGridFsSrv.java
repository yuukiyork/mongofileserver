
package com.wardensky.mongofileserver.srv;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.wardensky.mongofileserver.vo.FileResult;
import com.wardensky.mongofileserver.vo.UeditorResult;

public interface MongoGridFsSrv {

	public FileResult uploadFile(MultipartFile file);
	
	public UeditorResult ueditorUpload(MultipartFile upload, String domain);

	public void downloadFile(String f_id, HttpServletResponse response);

	public InputStream fileIo(String f_id);
	
	public FileResult deleteFile(String f_id);
}
