
package com.wardensky.mongofileserver.srv;

import java.io.InputStream;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wardensky.mongofileserver.dao.MongoGridFsDao;
import com.wardensky.mongofileserver.enums.FileResultEnum;
import com.wardensky.mongofileserver.vo.FileResult;
import com.wardensky.mongofileserver.vo.UeditorResult;

@Service
public class MongoGridFsSrvImpl implements MongoGridFsSrv {

	private static final Logger LOG = Logger.getLogger(MongoGridFsSrvImpl.class);
	private DecimalFormat df =new DecimalFormat("#.00");  
	
	/**
	 * 最大文件大小,单位MB
	 */
	private static final int	MAXSIZE_MB = 100;
	
	@Autowired
	private MongoGridFsDao dao;
	
	@Override
	public FileResult uploadFile(MultipartFile file) {
		FileResult result = FileResult.init();
		if (null == file || file.isEmpty()) {
			return result.build(FileResultEnum.FILEISNULL);
		}
		if (file.getSize() > MAXSIZE_MB * 1024 * 1024) {
			double fileSize = (double)file.getSize() / (1024 * 1024);
			String fileSizeStr = df.format(fileSize);
			LOG.error("-------->上传文件：" + file.getOriginalFilename() + "大小：" + fileSizeStr + "MB超过限制,当前允许的最大文件大小为：" + MAXSIZE_MB + "MB");
			return result.build(FileResultEnum.MAXSIZE.code, String.format(FileResultEnum.MAXSIZE.msg, file.getOriginalFilename(), fileSizeStr + "MB", MAXSIZE_MB + "MB"));
		}
		String f_id = dao.uploadFile(file, file.getOriginalFilename());
		LOG.info("保存上传附件：f_id：" + f_id + "文件名：" + file.getOriginalFilename() + "成功!");
		return result.build(f_id, file.getOriginalFilename());
	}

	@Override
	public UeditorResult ueditorUpload(MultipartFile imgFile, String domain) {
		UeditorResult result = new UeditorResult();
		if(imgFile==null){
			result.state = "false";
			return result;
		}
		if (imgFile.getSize() > MAXSIZE_MB * 1024 * 1024) {
			double fileSize = (double)imgFile.getSize() / (1024 * 1024);
			String fileSizeStr = df.format(fileSize);
			LOG.error("-------->上传文件：" + imgFile.getOriginalFilename() + "大小：" + fileSizeStr + "MB超过限制,当前允许的最大文件大小为：" + MAXSIZE_MB + "MB");
			result.state = String.format(FileResultEnum.MAXSIZE.msg, imgFile.getOriginalFilename(), fileSizeStr + "MB", MAXSIZE_MB + "MB");
			return result;
		}
		String f_id = dao.uploadFile(imgFile, imgFile.getOriginalFilename());
		LOG.info("保存上传附件：f_id：" + f_id + "文件名：" + imgFile.getOriginalFilename() + "成功!");
		result.state = "SUCCESS";
		result.url = domain + "/gridfs-download?f_id=" + f_id;
		return result;
	}
	
	@Override
	public void downloadFile(String f_id, HttpServletResponse response) {
		dao.downloadFile(f_id, response);
	}

	@Override
	public InputStream fileIo(String f_id) {
		return dao.fileIo(f_id);
	}

	@Override
	public FileResult deleteFile(String f_id) {
		dao.deleteFile(f_id);
		return FileResult.init().build(f_id, null, FileResultEnum.DELETESUCCESS);
	}

}
