package com.wardensky.mongofileserver.srv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Repository
public class MongoGridFsDaoImpl implements MongoGridFsDao {

	private static final String TYPE_FILE = "file";
	private static final String TYPE_IMAGE = "image";
	private static final String DATE_TIME = "yyyyMMddHHmmssSSS";
	private static final String F_ID = "f_id";

	@Autowired 
	protected GridFsOperations mongoGridFs;

	public String uploadFile(File file, String fileName) {
		try {
			String contentType = TYPE_FILE;
			GridFSFile gridFSFile = null;
			if (Toolkit.isFileImage(fileName)) {
				contentType = TYPE_IMAGE;
			}
			final String dateTime = Toolkit.getNowTime(DATE_TIME);
			InputStream is = new FileInputStream(file);
			gridFSFile = mongoGridFs.store(is, fileName, contentType);
			gridFSFile.put(F_ID, dateTime);
			gridFSFile.save();
			return gridFSFile.get(F_ID).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void downloadFile(String f_id, HttpServletResponse response) {
		try {
			GridFSDBFile gridFSFile = mongoGridFs.findOne(new Query(Criteria.where(F_ID).is(f_id)));
			if (gridFSFile == null) {
				return;
			}
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-Disposition",
					"inline; filename=" + new String((gridFSFile.getFilename()).getBytes("gb2312"), "ISO8859-1"));
			OutputStream os = response.getOutputStream();
			InputStream is = gridFSFile.getInputStream();
			try {
				byte[] bytes = new byte[1024];
				while (is.read(bytes) > 0) {
					os.write(bytes);
				}
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InputStream fileIo(String f_id) {
		GridFSDBFile gridFSFile = mongoGridFs.findOne(new Query(Criteria.where(F_ID).is(f_id)));
		if (gridFSFile == null) {
			return null;
		}
		return gridFSFile.getInputStream();

	}

	public void deleteFile(String f_id) {
		mongoGridFs.delete(new Query(Criteria.where(F_ID).is(f_id)));
	}

	public void deleteAll() {
		// mongoGridFs.delete(new Query());
		throw new UnsupportedOperationException();
	}

	public List<String> findAllIds() {
		throw new UnsupportedOperationException();
	}

	 
}
