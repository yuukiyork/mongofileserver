
package com.wardensky.mongofileserver.srv;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface MongoGridFsDao {

	public String uploadFile(File file, String fileName);

	public void downloadFile(String f_id, HttpServletResponse response);

	public void deleteFile(String f_id);

	public void deleteAll();

	public List<String> findAllIds();

	public InputStream fileIo(String f_id);

	void deleteFiles(List<String> fids);
}
