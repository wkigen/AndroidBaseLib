package com.vicky.android.baselib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

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

}
