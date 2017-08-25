package com.vicky.android.baselib.camera;

import android.hardware.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicky on 2017/4/2.
 */
public class CameraConfig {

    public static final int INVALID_HANDLE = -1;

    public int cameraNumber;
    public int currCameraIndex;
    public List<Integer> frontIndex;
    public List<Integer> backIndex;

    public Camera.Parameters parameters;
    public List<Camera.Size> previewSizeList;
    public List<Camera.Size> pictureSizeList;
    public List<Camera.CameraInfo> cameraInfoList;

    public CameraConfig(){
        cameraNumber = 0;
        currCameraIndex = INVALID_HANDLE;
        frontIndex = new ArrayList<>();
        backIndex = new ArrayList<>();
        previewSizeList = new ArrayList<>();
        pictureSizeList = new ArrayList<>();
        cameraInfoList = new ArrayList<>();
    }

    public Camera.CameraInfo getCurrCameraInfo(){
        if (currCameraIndex != INVALID_HANDLE){
            return cameraInfoList.get(currCameraIndex);
        }
        return null;
    }

}
