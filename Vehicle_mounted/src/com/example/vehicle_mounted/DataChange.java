package com.example.vehicle_mounted;

public class DataChange {
	/**
	 * ��������ת��Ϊ�ַ�������
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
