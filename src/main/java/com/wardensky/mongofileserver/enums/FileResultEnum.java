package com.wardensky.mongofileserver.enums;

public enum FileResultEnum {

	NOERROR(0, "上传文件成功"),
	FILEISNULL(1, "上传文件为空"),
	MAXSIZE(2, "上传文件：%s大小：%s超过限制,当前允许的最大文件大小为：%s"),
	
	DELETESUCCESS(10, "文件删除成功");
	
	public int code;
	public String msg;
	
	private FileResultEnum(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
}
