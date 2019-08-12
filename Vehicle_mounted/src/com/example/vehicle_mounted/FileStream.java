package com.example.vehicle_mounted;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class FileStream {
	
	public static void writeInternal(Context context, String filename, String content, Boolean saveold) throws IOException{
	    //获取文件在内存卡中files目录下的路径
	    File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename ;

	    //打开文件输出流
	    FileOutputStream outputStream = new FileOutputStream(filename,saveold);

	    //写数据到文件中
	    outputStream.write(content.getBytes());
	    outputStream.close();
	}
	
	public static String readInternal(Context context,String filename) throws IOException{
	    StringBuilder sb = new StringBuilder("");

	    //获取文件在内存卡中files目录下的路径
	    File file = context.getFilesDir();
	    filename = file.getAbsolutePath() + File.separator + filename;

	    //打开文件输入流
	    FileInputStream inputStream = new FileInputStream(filename);

	    byte[] buffer = new byte[1024];
	    int len = inputStream.read(buffer);
	    //读取文件内容
	    while(len > 0){
	        sb.append(new String(buffer,0,len));

	        //继续将数据放到buffer中
	        len = inputStream.read(buffer);
	    }
	    //关闭输入流
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
