package com.example.vehicle_mounted;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class FileStream {
	
	public static void writeInternal(Context context, String filename, String content, Boolean saveold) throws IOException{
	    //��ȡ�ļ����ڴ濨��filesĿ¼�µ�·��
	    File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename ;

	    //���ļ������
	    FileOutputStream outputStream = new FileOutputStream(filename,saveold);

	    //д���ݵ��ļ���
	    outputStream.write(content.getBytes());
	    outputStream.close();
	}
	
	public static String readInternal(Context context,String filename) throws IOException{
	    StringBuilder sb = new StringBuilder("");

	    //��ȡ�ļ����ڴ濨��filesĿ¼�µ�·��
	    File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename;

	    //���ļ�������
	    FileInputStream inputStream = new FileInputStream(filename);

	    byte[] buffer = new byte[1024];
	    int len = inputStream.read(buffer);
	    //��ȡ�ļ�����
	    while(len > 0){
	        sb.append(new String(buffer,0,len));

	        //���������ݷŵ�buffer��
	        len = inputStream.read(buffer);
	    }
	    //�ر�������
	    inputStream.close();
	    return sb.toString();
	}
	
	public static void deleteInternal(Context context,String filename){
		File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename;
	    File dfile = new File(filename);
	    dfile.delete();
	}
	
	public static String getFileLujin(Context context,String filename){
		File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename;
	    return filename;
	}
	
	public static File[] getFiles(String path){
        File file=new File(path);
        File[] files=file.listFiles();
        return files;
    }

}
