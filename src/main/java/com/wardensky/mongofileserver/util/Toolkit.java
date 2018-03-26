package com.wardensky.mongofileserver.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public final class Toolkit {

	public static String getNowTime(String s) {
		DateTime now = DateTime.now();
		return now.toString(s);
	}

	public static String findFileExtension(String fileName) {
		String[] aa = fileName.split(".");
		if (aa.length > 1) {
			return aa[aa.length - 1].toLowerCase();
		}
		return "";
	}

	public static boolean isFileImage(String fileName) {
		List<String> imageLists = new ArrayList<String>();
		imageLists.add("png");
		imageLists.add("jpg");
		imageLists.add("bmp");
		imageLists.add("jpeg");
		imageLists.add("gif");
		return imageLists.contains(findFileExtension(fileName));
	}

	public static List<String> findAllCsv(String folderPath) {
		List<String> fileList = new ArrayList<String>();
		recurFolder(folderPath, fileList);
		return fileList;
	}

	private static void recurFolder(String path, List<String> fileList) {
		File folder = new File(path);
		File[] folders = folder.listFiles();
		if (folders == null) {
			return;
		}
		for (File file : folders) {
			recurFolder(file.getAbsolutePath(), fileList);
			if (file.getPath().contains(".csv")) {
				fileList.add(file.getAbsolutePath());
			}
		}
	}

	/**
	 * 功能: 首字母转小写.<br/>
	 * date: 2017年2月8日 上午11:11:25 <br/>
	 *
	 * @author xushuai
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static String getFileName(String path) {
		String[] pathList = null;
		if (path.contains("\\")) {
			path = path.replace(File.separator, "/");
		}
		pathList = path.split("/");
		return pathList[pathList.length - 1];
	}

	public static String convertUpper2Camel(String s) {
		String[] entityNameList = s.split("_");
		String entityName = "";
		for (int i = 0; i < entityNameList.length; i++) {
			entityNameList[i] = Toolkit.toUpperCaseFirstOne(entityNameList[i].toLowerCase());
			entityName += entityNameList[i];
		}
		return entityName;
	}

	public static String convertCamel2Upper(String s) {
		return "";
	}

	/**
	 * 功能: 首字母转大写.<br/>
	 * date: 2017年2月8日 上午11:12:00 <br/>
	 *
	 * @author xushuai
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
