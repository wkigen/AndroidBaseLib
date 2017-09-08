package com.vicky.android.baselib.net;

import com.alibaba.fastjson.JSON;
import com.vicky.android.baselib.http.OkHttpUtils;
import com.vicky.android.baselib.http.annotation.FILE;
import com.vicky.android.baselib.http.annotation.HEADPARAMS;
import com.vicky.android.baselib.http.annotation.POSTENCRYPT;
import com.vicky.android.baselib.http.request.RequestCall;
import com.vicky.android.baselib.http.utils.Exceptions;
import com.vicky.android.baselib.http.annotation.GET;
import com.vicky.android.baselib.http.annotation.PARAMS;
import com.vicky.android.baselib.http.annotation.POST;
import com.vicky.android.baselib.http.annotation.POSTJSON;
import com.vicky.android.baselib.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@SuppressWarnings("all")
public class NetUtil implements InvocationHandler {

    private static IHandleHttpParams handleParams;
    private static Map<String,String> headMap;

    public NetUtil(IHandleHttpParams handle,Map<String,String> hMap) {
        handleParams = handle;
        headMap = hMap;
    }

    public static List<String> getMethodHeadParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return new ArrayList<>();
        }
        List<String> headParameteNames = new ArrayList<>();

        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof HEADPARAMS) {
                    HEADPARAMS param = (HEADPARAMS) annotation;
                    headParameteNames.add(param.value());
                }
            }
        }
        return headParameteNames;
    }

    public static List<String> getMethodParameterNamesByAnnotation(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return new ArrayList<>();
        }
        List<String> parameteNames = new ArrayList<>();

        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof PARAMS) {
                    PARAMS param = (PARAMS) annotation;
                    parameteNames.add(param.value());
                }
            }
        }
        return parameteNames;
    }

    @Override
    public RequestCall invoke(Object proxy, final Method method, final Object[] args) throws Throwable {

        RequestCall requestCall = null;

        Map<String,String> params = new HashMap<>();
        Map<String,String> headParams = new HashMap<>();
        defaultProcess(method,args,headParams,params);

        if(headMap != null)
            headParams.putAll(headMap);

        if (method.isAnnotationPresent(POST.class)) {
            POST post = method.getAnnotation(POST.class);
            requestCall = OkHttpUtils.post().url(StringUtils.addHttpPrefix(post.value())).params(params).headers(headParams).build();
        }

        if (method.isAnnotationPresent(GET.class)) {
            GET get = method.getAnnotation(GET.class);
            requestCall = OkHttpUtils.get().url(StringUtils.addHttpPrefix(get.value())).params(params).headers(headParams).build();
        }

        if (method.isAnnotationPresent(POSTJSON.class)) {
            POSTJSON postjson = method.getAnnotation(POSTJSON.class);
            requestCall = OkHttpUtils.postString().url(StringUtils.addHttpPrefix(postjson.value())).headers(headParams).content(JSON.toJSONString(params)).build();
        }

        if (method.isAnnotationPresent(POSTENCRYPT.class)) {
            POSTENCRYPT postjson = method.getAnnotation(POSTENCRYPT.class);
            if (handleParams != null)
                params = handleParams.handleParams(params);
            requestCall = OkHttpUtils.post().url(StringUtils.addHttpPrefix(postjson.value())).params(params).headers(headParams).build();
        }

        if (method.isAnnotationPresent(FILE.class)){
            FILE postjson = method.getAnnotation(FILE.class);
            String url = params.get("url");
            if (url != null){
                requestCall = OkHttpUtils.get().url(StringUtils.addHttpPrefix(url)).headers(headParams).build();
            }
        }

        if (requestCall == null)
            Exceptions.illegalArgument("requestCall is null");

        return requestCall;
    }

    private void defaultProcess(Method method, Object[] objs,Map<String,String> headParams,Map<String,String> httpParams) {

        int i = 0;

        List<String> headName = getMethodHeadParameterNamesByAnnotation(method);
        List<String> paramsName = getMethodParameterNamesByAnnotation(method);

        for (String name : headName) {
            if (objs == null || objs[i] == null) {
                headParams.put(name, "");
            } else if (List.class.isInstance(objs[i])) {
                headParams.put(name, JSON.toJSONString(objs[i]));
            } else if(String.class.isInstance(objs[i])){
                headParams.put(name, objs[i].toString() + "");
            }else{
                headParams.put(name, objs[i].toString() + "");
            }
            i++;
        }

        for (String name : paramsName) {
            if (objs == null || objs[i] == null) {
                httpParams.put(name, "");
            } else if (List.class.isInstance(objs[i])) {
                    httpParams.put(name, JSON.toJSONString(objs[i]));
            } else if(String.class.isInstance(objs[i])){
                httpParams.put(name, objs[i].toString() + "");
            }else{
                httpParams.put(name, objs[i].toString() + "");
            }
            i++;
        }


    }

    private String jsonReplace(Object object){
        return JSON.toJSONString(object, true).replace("\\\"", "\"").replace("\"[", "[").replace("]\"", "]");
    }
}