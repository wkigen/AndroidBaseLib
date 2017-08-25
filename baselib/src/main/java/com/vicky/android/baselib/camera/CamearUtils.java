package com.vicky.android.baselib.camera;

import android.hardware.Camera;

import com.vicky.android.baselib.utils.ILog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vicky on 2017/4/2.
 */
public class CamearUtils {
    private static final String TAG = CamearUtils.class.getSimpleName();

    private static SizeComparator sizeComparator = new SizeComparator();

    static class SizeComparator implements Comparator<Camera.Size> {
        @Override
        public int compare(Camera.Size s1, Camera.Size s2) {
            if (s1.height > s2.height)
                return 1;
            else if (s1.height == s2.height)
                return 0;
            else
                return -1;
        }
    }

    static public Camera.Size getPropSize(List<Camera.Size> sizeList, int minWidth, int minHeight) {
        //先排序 然后选择最合适的
        Collections.sort(sizeList, sizeComparator);
        int i = 0;
        for (Camera.Size size : sizeList) {
            ILog.v(TAG, "width:" + size.width + ",height:" + size.height);
            if ((size.height == minWidth) && size.width >= minHeight) {
                break;
            }
            i++;
        }
        if (i == sizeList.size()) {
            i = sizeList.size() - 1;//如果没找到，就选最大的size
        }
        return sizeList.get(i);
    }
}
