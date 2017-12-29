package com.fan.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

public class FileCreate {

	public static String getString(Properties pro, String key) throws Exception {

		String value = pro.getProperty(key);
		if (value == null) {
			throw new Exception("请检查参数配置是否正确：" + key);
		}
		return value;
	}

	public static int getInt(Properties pro, String key) throws Exception {

		String value = pro.getProperty(key);
		int count = 0;
		try {
			count = Integer.valueOf(value);
		} catch (Exception ex) {
			throw new Exception("请检查参数配置是否正确：" + key, ex);
		}
		return count;
	}

	public static long getLong(Properties pro, String key) throws Exception {

		String value = pro.getProperty(key);
		long count = 0;
		try {
			count = Long.valueOf(value);
		} catch (Exception ex) {
			throw new Exception("请检查参数配置是否正确：" + key, ex);
		}
		return count;
	}

	public static void main(String[] args) throws Exception {

		File configFile = new File("createrandomfile\\src\\main\\resources\\config.properties");
		if (!configFile.exists()) {
			throw new Exception("config.properties配置文件不存在:"+configFile.getAbsolutePath());
		}

		Properties pro = new Properties();
		FileInputStream in = new FileInputStream(configFile);
		pro.load(in);
		in.close();

		// 文件路径
		String fileDir = getString(pro, "fileDir");
		File dir = new File(fileDir);
		if (!dir.exists()) {
			throw new IOException("文件生成路径不存在，工具不会自动生成目录以免错写");
		}

		// 文件数量
		int count = getInt(pro, "fileNumber");

		// 文件大小
		long fileMinSize = getFileSize(pro, "fileMinSize");
		long fileMaxSize = getFileSize(pro, "fileMaxSize");

		// 临时文件后缀
		String tempFileExtensions = pro.getProperty("tempFileExtensions");
		if (tempFileExtensions == null) {
			tempFileExtensions = "";
		}
		// 文件前缀
		String fileBenforeName = pro.getProperty("fileBenforeName");
		if (fileBenforeName == null) {
			fileBenforeName = "";
		}

		int maxDirFileSize = getInt(pro, "maxDirFileSize");
		int targetFileNum = 0;

		long startTime = System.currentTimeMillis();

		long threadCyc = getLong(pro, "threadCyc");

		for (int i = 0; i < count; i++) {
			targetFileNum = maxDirFileSize - dir.list().length;
			if (targetFileNum > 0) {
				targetFileNum--;
				NumberFormat formatter = NumberFormat.getNumberInstance();
				formatter.setMinimumIntegerDigits(String.valueOf(count).length());
				formatter.setGroupingUsed(false);
				String s = formatter.format(i);
				String fileName = dir.getAbsolutePath() + "/" + fileBenforeName + s + ".txt";
				String tempFileName = fileName + tempFileExtensions;

				if (new File(fileName).exists() || new File(tempFileName).exists()) {
					System.out.println("文件已存在不能生成：" + fileName);
					continue;

				}

				fillFile(tempFileName, RandomUtils.nextLong(fileMinSize, fileMaxSize));

				if (!"".equals(tempFileExtensions)) {
					File f = new File(tempFileName);
					f.renameTo(new File(fileName));
				}
				if (System.currentTimeMillis() - startTime > threadCyc) {
					System.out.println("正在生成文件:" + fileName);
					startTime = System.currentTimeMillis();
				}
			} else {
				i--;
				Thread.sleep(threadCyc);
				System.out.println("目录下文件已达最大数量:" + maxDirFileSize);
			}
		}
		System.out.println("文件生成完成");
	}

	public static long getFileSize(Properties pro, String key) throws Exception {

		long num = 1l;
		String value = getString(pro, key);
		if (value.indexOf("*") == -1) {
			try {
				num = Long.valueOf(value);
			} catch (Exception ex) {
				throw new Exception("请检查参数配置是否正确：" + key, ex);
			}
		} else {

			String[] values = value.split("\\*");
			for (String tempValue : values) {
				try {
					long tempNum = Long.valueOf(tempValue);
					num = num * tempNum;
				} catch (Exception ex) {
					throw new Exception("请检查参数配置是否正确：" + key, ex);
				}
			}
		}

		return num;
	}

	public static void fillFile(String fileName, long length) {
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(fileName, "rw");
			randomFile.setLength(length);

		} catch (Exception e) {

		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
