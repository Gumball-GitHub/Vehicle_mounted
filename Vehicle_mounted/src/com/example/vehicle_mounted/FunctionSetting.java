package com.example.vehicle_mounted;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.app.Dialog;
import android.text.TextDirectionHeuristic;
import android.util.Log;

public class FunctionSetting {
	public static final int DAY_COUNT = 0;
	public static final int WEEK_COUNT = 1;
	// 统计方法（周/日）
	private static int countMethod;
	// 非正常时是否报警
	private static boolean warning;
	
	public static String test;

	private static Properties properties;
	// 初始化，加载配置文件
	static {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File("src/com/example/function/setting.properties")));
			countMethod = Integer.parseInt(properties.getProperty("countMethod"));
			warning = Boolean.parseBoolean(properties.getProperty("warning"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getCountMethod() {
		return countMethod;
	}

	public static void setCountMethod(int countMethod) {
		FunctionSetting.countMethod = countMethod;
		properties.setProperty("countMethod", String.valueOf(countMethod));
		try {
			properties.save(new FileOutputStream(new File("src/com/example/function/setting.properties")),
					"has been change");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isWarning() {
		return warning;
	}

	public static void setWarning(boolean warning) {
		FunctionSetting.warning = warning;
		properties.setProperty("countMethod", String.valueOf(warning));
		try {
			properties.save(new FileOutputStream(new File("src/com/example/function/setting.properties")),
					"has been change");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
