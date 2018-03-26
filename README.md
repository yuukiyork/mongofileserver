# 说明

本项目是使用spring boot框架开发的一个基于mongodb gridfs的文件存储服务器。

由于直接js端调用非本域名下接口有跨域问题，本方案为先通过js向项目服务器(client)上传文件，再通过java后台调用接口上传文件到mongo文件服务器(server)
目前支持单文件以及百度ueditor上传文件存储到MongoDB

#### 流程图
```flow
st=>start: 用户点击页面上传按钮
js=>operation: ajaxfileupload.js
project=>operation: 项目服务器
impl=>operation: UploadUtils.java
mongoserver=>operation: mongo文件服务器
e=>end: MongoDB

st->js->project->impl->mongoserver->e
```

### 项目服务器(client)
#### jfinal Controller 代码片段
```java

	/**
	 * jQuery上传文件
	 * http://domain.cn/ueditor-upload为mongo文件服务器(server)域名或IP:PORT
	 */
	public void upload(){
		UploadFile uploadFile = getFile();
		if (null == uploadFile) {
			renderNull();
			return;
		}
		String json = null;
		try {
			json = UploadUtils.uploadFile("http://domain.cn/gridfs-upload", uploadFile.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderJson(json);
	}
	
	/**
	 * 百度ueditor上传文件
	 * http://domain.cn/ueditor-upload为mongo文件服务器(server)域名或IP:PORT
	 */
	public void ueditorUpload(){
		UploadFile uploadFile = getFile();
		if (null == uploadFile) {
			renderNull();
			return;
		}
		String json = null;
		try {
			json = UploadUtils.uploadUeditorFile("http://domain.cn/ueditor-upload", uploadFile.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderJson(json);
	}
```

src/main/webapp/client-demo文件夹下为项目服务器(client)前端代码示例
ueditor上传文件要使用js-lib目录下MongoDbUEditor的js插件
修改MongoDbUEditor/jsp/config.json文件中部分xxxActionName为上传文件Action相对路径
(例如:/ueditorUpload,调用uploadUeditorFile方法的Action相对路径)

src/main/java/com/wardensky/mongofileserver/client/UploadUtils.java为项目服务器(client)后端上传工具类

实际开发中需要将上述两部分复制到项目中，并将client-demo和UploadUtils.java从本项目中移除

通过jQuery上传文件后会返回上传结果，上传成功后请将<b>f_id</b>入库以便后续根据<b>f_id</b>查找文件

| 字段名| 类型| 说明|
| ---| ---| ---|
| f_id    | String | 文件id(非mongoId)|
| fileName| String | 文件名|
| state   | Integer| 状态码<br> 0:上传文件成功<br> 1:上传文件为空 <br> 2:上传文件：[文件名]大小：[文件大小]超过限制,当前允许的最大文件大小为：[最大支持大小] <br> 10:文件删除成功|
| message | String | 信息|

ueditor上传文件无需再存储f_id，保存ueditor内容时其中包含获取文件的url

### mongo文件服务器(server)
本项目使用spring boot框架开发，使用tomcat启动

GridfsController为上传文件访问入口
application.properties为项目配置文件，其中定义了mongo数据库连接
spring.http.multipart.maxFileSize = 1024Mb
spring.http.multipart.maxRequestSize=10240Mb
两个属性为spring boot框架支持上传文件 单个数据大小 和 总数据大小 (默认为1M)，这里我配置了1G
MongoGridFsSrvImpl中MAXSIZE_MB属性为实际项目中需要限制的最大文件,单位MB(代码中配置的100MB)

> 为什么不直接用spring boot框架限制文件大小，还要定义MAXSIZE_MB属性
因为若用框架限制文件大小为100MB，上传>100MB文件，框架底层会抛出异常信息，不会进入上传方法从而无法返回错误信息。
框架限制文件大小为1GB，上传文件，进入上传方法后再根据文件大小判断是否超过限制大小，返回上传信息。
