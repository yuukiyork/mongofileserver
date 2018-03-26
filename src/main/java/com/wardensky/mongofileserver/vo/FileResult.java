package com.wardensky.mongofileserver.vo;

import com.wardensky.mongofileserver.enums.FileResultEnum;

public class FileResult {	
	/**
	 * 文件id
	 * (非mongo_id)
	 */
	public String  f_id;
	/**
	 * 文件名
	 */
	public String  fileName;
	/**
	 * 状态码
	 */
	public Integer state;
	/**
	 * 信息
	 */
	public String  message;
	
	protected FileResult(){}

	public static FileResult init(){
		return new FileResult();
	}

	public FileResult build(FileResultEnum e) {
		return build(e.code, e.msg);
	}

	public FileResult build(int state, String  message) {
		return build(null, null, state,  message);
	}

	public FileResult build(String f_id, String fileName) {
		FileResultEnum e = FileResultEnum.NOERROR;
		return build(f_id, fileName, e);
	}
	
	public FileResult build(String f_id, String fileName, FileResultEnum e) {
		return build(f_id, fileName, e.code, e.msg);
	}
	
	public FileResult build(String f_id, String fileName, int state, String  message) {
		this.f_id = f_id;
		this.fileName = fileName;
		this.state = state;
		this.message = message;
		return this;
	}
}
