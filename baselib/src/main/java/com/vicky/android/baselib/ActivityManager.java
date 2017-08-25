package com.vicky.android.baselib;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.SoftReference;
import java.util.Stack;

public class ActivityManager {

    private static Stack<SoftReference<Activity>> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        if (activityStack == null) {
            activityStack = new Stack();
        }
    }

    public static synchronized ActivityManager getAppManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(new SoftReference<Activity>(activity));
    }

    public Activity currentActivity() {
        if(activityStack.lastElement()==null)return null;
        Activity activity = (Activity) activityStack.lastElement().get();
        return activity;
    }

    public void finishActivity() {
        if(activityStack.lastElement()==null)return ;
        Activity activity = (Activity) activityStack.lastElement().get();
        finishActivity(activity);
    }

    public void popActivity(Activity activity){
        SoftReference<Activity> weakReference = null;
        if (activity != null) {
            for (SoftReference<Activity> activity1:activityStack){
                if(activity1!=null&&activity1.get()!=null){
                    if(activity1.get().equals(activity)){
                        weakReference = activity1;
                    }
                }
            }
            if(weakReference!=null)
            activityStack.remove(weakReference);
            activity = null;
        }
    }



    public void finishActivity(Activity activity) {
        if (activity != null) {
            popActivity(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class cls) {
        Stack<SoftReference<Activity>> activities = new Stack<>();
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if(activityStack.get(i)!=null&&activityStack.get(i).get()!=null){
                if (activityStack.get(i).get().getClass().equals(cls)) {
                    activityStack.get(i).get().finish();
                    activities.add(activityStack.get(i));
                }
            }
        }
        activityStack.removeAll(activities);
    }


    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)&&null!=activityStack.get(i).get()) {
                activityStack.get(i).get().finish();
            }
        }
        activityStack.clear();
    }



    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}