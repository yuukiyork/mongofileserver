package com.wardensky.mongofileserver.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 
 * Created by zlliu on 2018/03/01.
 * ueditor上传文件要使用MongoDbUEditorjs插件
 * 修改MongoDbUEditor/jsp/config.json文件中部分xxxActionName为上传文件Action相对路径
 * (例如:/ueditorUpload,调用uploadUeditorFile方法的Action相对路径)
 */
public class UploadUtils {

    private static String BOUNDARY = null;  //边界标识   随机生成
    private static String PREFIX = "--", LINE_END = "\r\n";
    private static String CONTENT_TYPE = "multipart/form-data";   //内容类型
    /**
     * 服务器端接收上传文件参数名,与GridfsController的upload方法MultipartFile upload参数对应
     */
    private static String tagname = "upload";
    private static String domain = "domain";
    
    public static int time_out = 300 * 1000;   //超时时间
    public static String charset = "utf-8"; //设置编码

    protected UploadUtils(){};

    /**
     * 上传文件至Server的方法
     * @param uploadUrl 上传路径参数
     * @param fileIs 文件io流
     * @param fileName 文件名
     * @return 服务返回json格式结果
     */
    public static String uploadFile(final String uploadUrl, String domain, String value, InputStream fileIs, String fileName) {
        BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
        String result = "";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(time_out);
            conn.setConnectTimeout(time_out);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("charset", charset);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            
            if (null != domain && !domain.trim().equals("") && null != value && !value.trim().equals("")) {
            	addText(domain, value, dos);
			}
            addFile(fileIs, fileName, dos);

            // 读取服务器返回结果
            InputStream isBack = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(isBack, charset);
            BufferedReader br = new BufferedReader(isr);
            result = br.readLine();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 上传文件至Server的方法
     * @param uploadUrl 上传路径参数
     * @param file 文件
     * @return 服务返回json格式结果
     * @throws IOException 
     */
    public static String uploadFile(final String uploadUrl, File file) throws IOException {
    	InputStream fileIs = new FileInputStream(file);
    	String json = uploadFile(uploadUrl, null, null, fileIs, file.getName());
    	fileIs.close();
		//一定要删除
		file.delete();
		return json;
    }
    
    /**
     * 上传Ueditor文件至Server的方法
     * @param uploadUrl 上传路径参数
     * @param file 文件
     * @return 服务返回json格式结果
     * @throws IOException 
     */
    public static String uploadUeditorFile(final String uploadUrl, File file) throws IOException {
    	InputStream fileIs = new FileInputStream(file);
    	int idx = 0;
    	//去除http://和https://
    	if (uploadUrl.startsWith("https://")) {
    		idx = uploadUrl.indexOf("/", 8);
		} else {
			idx = uploadUrl.indexOf("/", 7);	
		}
    	String domainVal = uploadUrl.substring(0, idx);
    	String json = uploadFile(uploadUrl, domain, domainVal, fileIs, file.getName());
    	fileIs.close();
    	//一定要删除
    	file.delete();
    	return json;
    }

    private static void addText(String name, Object value, DataOutputStream output) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX + BOUNDARY + LINE_END);
        sb.append("Content-Disposition: form-data; name=\"" + name + "\"" + LINE_END);
        sb.append("Content-Type: text/html; charset=" + charset + LINE_END);//解决文本中文乱码问题
        sb.append(LINE_END);
        sb.append(value + LINE_END);
        sb.append(PREFIX + BOUNDARY + LINE_END);
        try {
            output.write(sb.toString().getBytes());// 发送表单字段数据
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void addFile(InputStream fileIs, String fileName, DataOutputStream output) {
        if (fileIs == null) {
        	return;
        }
        try {
	        StringBuilder sb = new StringBuilder();
	        sb.append(PREFIX + BOUNDARY + LINE_END);
	        sb.append("Content-Disposition: form-data; name=\"" + tagname +  "\"; filename=\"" + fileName + "\"" + LINE_END);
	        sb.append("Content-Type: application/octet-stream; charset=" + charset + LINE_END);
	        sb.append(LINE_END);
	        output.write(sb.toString().getBytes());
	        byte[] bytes = new byte[1024];
	        int len = 0;
	        while ((len = fileIs.read(bytes)) != -1) {
	            output.write(bytes, 0, len);
	        }
	        fileIs.close();
	        output.write(LINE_END.getBytes());
	        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
	        output.write(end_data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
