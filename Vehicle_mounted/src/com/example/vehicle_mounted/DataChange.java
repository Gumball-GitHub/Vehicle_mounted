package com.example.vehicle_mounted;

public class DataChange {
	/**
	 * 浮点数组转化为字符串数组
	 * @param fArr
	 * @return
	 */
	public static String[] floatToStringArr(float[] fArr) {
		String[] sArr=new String[fArr.length];
		for(int i=0;i<sArr.length;i++) {
			sArr[i]=String.valueOf(fArr[i]);
		}
		return sArr;
	}
	
	public static String[] intToStringArr(int[] fArr) {
		String[] sArr=new String[fArr.length];
		for(int i=0;i<sArr.length;i++) {
			sArr[i]=String.valueOf(fArr[i]);
		}
		return sArr;
	}
}
