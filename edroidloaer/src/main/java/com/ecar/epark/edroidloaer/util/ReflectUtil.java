package com.ecar.epark.edroidloaer.util;

import android.content.Context;


import com.ecar.epark.edroidloaer.manager.LDClassLoaderManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*************************************
 功能：反射工具类
 创建者： kim_tony
 创建日期：2017/4/12
 版权所有：深圳市亿车科技有限公司
 *************************************/

public class ReflectUtil {

    /****************************************
     方法描述： 通过反射获取方法
     @param    className 类名
                          例如: com.ecar.dymicloader.util.ReflectUtil
                 MethodeName  方法名
                 methodPramTypes  方法参数类型
     @return
     ****************************************/
    public static Method getMethodCommon(String className, String MethodeName, Class ... methodPramTypes)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = Class.forName(className);
        //第一个参数是被调用方法的名称，后面接着这个方法的形参类型
        return cls.getMethod(MethodeName, methodPramTypes);
        //取得方法后即可通过invoke方法调用，传入被调用方法所在类的对象和实参,对象可以通过cls.newInstance取得
    }
    /****************************************
     方法描述： 通过反射获取方法
     @param    className 类名
     例如: com.ecar.dymicloader.util.ReflectUtil
     MethodeName  方法名
     methodPramTypes  方法参数类型
     @return
     ****************************************/
    public static Method getMethodDex(Context context,String className, String MethodeName, Class ... methodPramTypes)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = LDClassLoaderManager.getInstance(context).getClassLoader().loadClass(className);
        //第一个参数是被调用方法的名称，后面接着这个方法的形参类型
        return cls.getMethod(MethodeName, methodPramTypes);
        //取得方法后即可通过invoke方法调用，传入被调用方法所在类的对象和实参,对象可以通过cls.newInstance取得
    }

    /****************************************
     方法描述： 通过类名获取对象
     @param
     @return
     ****************************************/
    public static Object getObject(Context context,String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> cls = LDClassLoaderManager.getInstance(context).getClassLoader().loadClass(className);
       return  cls.newInstance();
    }
    /****************************************
     方法描述： 通过类名获取对象
     @param
     @return
     ****************************************/
    public static Object getObjectByDex(Context context,String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> cls = LDClassLoaderManager.getInstance(context).getClassLoader().loadClass(className);
        return  cls.newInstance();
    }
}
