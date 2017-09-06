package com.vicky.android.baselib.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by vicky on 2017/8/31.
 */
public class FileUtils {

    public static boolean isSdCardExist(){
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getAbsoulutePath(Context context,String directory){
        String path;
        if (isSdCardExist()){
            path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        }else {
            path = context.getFilesDir().getAbsolutePath()+"/";
        }
        return  path + directory;
    }

    public static boolean createDirectory(String directory){
        File file = new File(directory);
        if (!file.exists())
           return file.mkdir();
        return true;
    }

    public static void saveStringToFile(String str, String path){
        try{
            FileOutputStream outputStream = new FileOutputStream(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(str);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
        }catch (Exception e){
        }
    }

    public static String getStringFromFile(String path){
        try{
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader inputReader = new InputStreamReader(inputStream );
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufferedReader.readLine()) != null){
                if(line.trim().equals(""))
                    continue;
                result += line + "\r\n";
            }
            return result;
        }catch (Exception E){
        }
        return "";
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void deleteAllFiles(String root) {
        File rootFile = new File(root);
        if (!rootFile.exists())
            return;
        File files[] = rootFile.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f.getAbsolutePath());
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f.getAbsolutePath());
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    public static void coypFile(String srcFilePath,String desFilePath){

        try {
            if (!isExistsFile(srcFilePath))
                return;

            FileInputStream fileInputStream = new FileInputStream(srcFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(desFilePath);

            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer,0,len);
            }

            fileInputStream.close();
            fileOutputStream.close();

        }catch (Exception e){

        }
    }

    public static boolean isExistsFile(String path){
        File file = new File(path);
        return file.exists();
    }

}
