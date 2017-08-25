package com.vicky.android.baselib.camera;


import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.vicky.android.baselib.utils.ILog;

/**
 * Created by vicky on 2017/4/2.
 */
public class CameraManager {

    enum PreviewType{
        NOMAL,
        GLES,
    }


    private static CameraManager    instance;
    private Camera                  camera;
    private CameraConfig            cameraConfig;
    private SurfaceTexture          surfaceTexture;
    private PreviewType             previewType;
    private Camera.PreviewCallback  previewCallback;

    private CameraManager() {
    }

    public static synchronized CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
            instance.init();
        }
        return instance;
    }

    private void init() {
        cameraConfig = new CameraConfig();
        cameraConfig.cameraNumber = Camera.getNumberOfCameras();

        for (int ii = 0; ii < cameraConfig.cameraNumber; ii++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(ii, cameraInfo); // get camerainfo
            cameraConfig.cameraInfoList.add(cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraConfig.frontIndex.add(ii);
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraConfig.backIndex.add(ii);
            }
        }

        previewType = PreviewType.NOMAL;
    }

    public void openFront() {
        if (cameraConfig.frontIndex.size() > 0) {
            realOpen(cameraConfig.frontIndex.get(0));
        } else {
            ILog.e("没有可用的前置相机");
        }
    }

    public void openBack() {
        if (cameraConfig.backIndex.size() > 0) {
            realOpen(cameraConfig.backIndex.get(0));
        } else {
            ILog.e("没有可用的后置相机");
        }
    }

    private void realOpen(int index) {
        //默认取第一个
        try {
            camera = Camera.open(index);
            cameraConfig.parameters = camera.getParameters();
            cameraConfig.parameters.setPictureFormat(PixelFormat.JPEG);
            cameraConfig.previewSizeList.clear();
            cameraConfig.previewSizeList = cameraConfig.parameters.getSupportedPreviewSizes();
            cameraConfig.pictureSizeList = cameraConfig.parameters.getSupportedPictureSizes();
            cameraConfig.currCameraIndex = index;
        } catch (Exception e) {
            ILog.e("打开相机出错");
        }
    }

    public void setPreviewCallBack(Camera.PreviewCallback callback){
        previewCallback = callback;
    }

    public void setDisplayOrientation(Activity activity){
        if (camera != null){
            int rotation = activity.getWindowManager ().getDefaultDisplay ().getRotation ();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            int result;
            Camera.CameraInfo info = cameraConfig.getCurrCameraInfo();
            if (info != null){
                if (info.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    result = (info.orientation + degrees) % 360;
                    result = (360 - result) % 360;   // compensate the mirror
                } else {
                    // back-facing
                    result = ( info.orientation - degrees + 360) % 360;
                }
                camera.setDisplayOrientation (result);
            }
        }
    }

    public void startPreview(SurfaceHolder holder, int width, int height) {
        if (camera != null) {
            try{
                previewType = PreviewType.NOMAL;

                //设置预览大小
                Camera.Size previewSize = CamearUtils.getPropSize(cameraConfig.previewSizeList, width, height);
                cameraConfig.parameters.setPreviewSize(previewSize.width,previewSize.height);
                //设置照片大小
                Camera.Size pictureSize = CamearUtils.getPropSize(cameraConfig.pictureSizeList,width,height);
                cameraConfig.parameters.setPictureSize(pictureSize.width, pictureSize.height);
                if (cameraConfig.parameters.getSupportedFocusModes().contains(
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    cameraConfig.parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                }
                camera.setParameters(cameraConfig.parameters);
                //camera.setDisplayOrientation(90);
                camera.setPreviewDisplay(holder);
                if(previewCallback != null)
                    camera.setPreviewCallback(previewCallback);
                camera.startPreview();

            }catch (Exception e){

            }
        }
    }

    public void startPreview(SurfaceTexture surfaceTexture, int width, int height) {
        if (camera != null) {
            try{
                previewType = PreviewType.GLES;
                //设置预览大小
                Camera.Size previewSize = CamearUtils.getPropSize(cameraConfig.previewSizeList, width, height);
                cameraConfig.parameters.setPreviewSize(previewSize.width,previewSize.height);
                //设置照片大小
                Camera.Size pictureSize = CamearUtils.getPropSize(cameraConfig.pictureSizeList,width,height);
                cameraConfig.parameters.setPictureSize(pictureSize.width, pictureSize.height);
                if (cameraConfig.parameters.getSupportedFocusModes().contains(
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    cameraConfig.parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                }
                //camera.setDisplayOrientation(90);
                camera.setParameters(cameraConfig.parameters);
                camera.setPreviewTexture(surfaceTexture);
                if(previewCallback != null)
                    camera.setPreviewCallback(previewCallback);
                camera.startPreview();

            }catch (Exception e){

            }
        }
    }

    public void pausePreview(){
        if (camera != null){
            camera.stopPreview();
            camera.setPreviewCallback(null);
        }
    }

    public void stopPreview(){
        if (camera != null){
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public  void takePicture(Camera.ShutterCallback shutterCallback, Camera.PictureCallback rawCallback,
                                   Camera.PictureCallback jpegCallback){
        if (camera != null){
            camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        }
    }

}