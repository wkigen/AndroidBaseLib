package com.vicky.android.baselib.utils;


/**
 * 可现实方法名与行数
 * 默认tag为打印所在的类名，方便过滤
 */
public final class ILog {

    private static String className;            //所在的类名
    private static String methodName;            //所在的方法名
    private static int lineNumber;                //所在行号

    private static final int VERBOSE = 1;          //显示Verbose及以上的Log
    private static final int DEBUG = 2;            //显示Debug及以上的Log
    private static final int INFO = 3;            //显示Info及以上的Log
    private static final int WARN = 4;            //显示Warn及以上的Log
    private static final int ERROR = 5;            //显示Error及以上的Log
    private static final int NOTHING = 6;        //全部不显示

    private static final int LEVEL = VERBOSE;    //控制显示的级别

    public static boolean LogEnable = true;

    private ILog() {
    }

    public static boolean isDebuggable() {
        return LogEnable;
    }

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void v(String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= VERBOSE) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.v(className, createLog(message));
        }
    }

    public static void v(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= VERBOSE) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.v(tag, createLog(message));
        }
    }

    public static void d(String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.d(className, createLog(message));
        }
    }

    public static void d(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.d(tag, createLog(message));
        }
    }

    public static void i(String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= INFO) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.i(className, createLog(message));
        }
    }

    public static void i(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= INFO) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.i(tag, createLog(message));
        }
    }

    public static void w(String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= WARN) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.w(className, createLog(message));
        }
    }

    public static void w(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= WARN) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.w(tag, createLog(message));
        }
    }

    public static void e(String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= ERROR) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.e(className, createLog(message));
        }
    }

    public static void e(String tag, String message) {
        if (!isDebuggable()) {
            return;
        }

        if (LEVEL <= ERROR) {
            getMethodNames(new Throwable().getStackTrace());
            android.util.Log.e(tag, createLog(message));
        }
    }
}
