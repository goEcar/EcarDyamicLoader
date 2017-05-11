package com.ecar.dymicloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ecar.epark.edroidloaer.core.PluginDirHelper;
import com.ecar.epark.edroidloaer.manager.PluginManager;
import com.ecar.epark.edroidloaer.reflect.MethodUtils;
import com.ecar.epark.edroidloaer.util.DLFileUtils;
import com.ecar.epark.edroidloaer.retain.ReflectUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //正常
//        common();


        //反射
//        reflect();
//        testGson();
        testData();
    }

    private void testData() {
        PluginManager pluginManager = new PluginManager(getApplication());
        String sdPatch = DLFileUtils.getSdPatch(this);
        String concat = sdPatch.concat(File.separator).concat(PluginManager.TEST_PLUGIN_NAME).concat(PluginDirHelper.File_Suff);
//        String concat = sdPatch.concat(File.separator).concat("asdf").concat(PluginDirHelper.File_Suff);

//        DLFileUtils.retrieveApkFromAssets(this,PluginManager.TEST_PLUGIN_NAME,concat);
        try {
            pluginManager.initPlugin(concat, PluginManager.TEST_PLUGIN_NAME);
            Class<?> class1 = pluginManager.dexClassLoader.loadClass("com.etest.calcumoney.MoneyCalcu");
            Object object = class1.newInstance();
//            Class[] params = new Class[1];
//            params[0] = Integer.TYPE;
////            params[1] = Integer.TYPE;
//            Method action = class1.getMethod("calcu", params);
//            Integer ret = (Integer) action.invoke(object, 12);


            Class[] params = new Class[1];
            params[0] = Integer.TYPE;
            Method calcu = MethodUtils.getAccessibleMethod(class1, "calcu", params);
            Integer ret = (Integer) calcu.invoke(object, 12);
            Toast.makeText(this, "" + ret, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //测试gson
    private void testGson() {

        Method gsons1 =
                null;
        Method gsons2 =
                null;
        try {
//            gsons1 = ReflectUtil.getMethodDex(this,"com.google.gson.Gson","toJson",String.class);
//            gsons1.invoke(ReflectUtil.getObject(this,"com.google.gson.Gson"),"{abc:xxx}");

            gsons2 = ReflectUtil.getMethodDex(this, "com.google.gson.Gson", "toString");
            gsons2.invoke(ReflectUtil.getObject(this, "com.google.gson.Gson"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void reflect() {
        Method baseAbc1 =
                null;
        Method baseAbc2 =
                null;
        Method baseAbc3 =
                null;
        try {
            baseAbc1 = ReflectUtil.getMethodDex(this, "gt.ecar.com.mylibrary.A", "show", String.class);
            baseAbc2 = ReflectUtil.getMethodDex(this, "gt.ecar.com.mylibrary.B", "show", String.class);
            baseAbc3 = ReflectUtil.getMethodDex(this, "gt.ecar.com.mylibrary.C", "show", String.class);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            baseAbc1.invoke(ReflectUtil.getObject(this, "gt.ecar.com.mylibrary.A"), "is A");
            baseAbc2.invoke(ReflectUtil.getObject(this, "gt.ecar.com.mylibrary.B"), "is B");
            baseAbc3.invoke(ReflectUtil.getObject(this, "gt.ecar.com.mylibrary.C"), "is C");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void common() {

//        BaseAbc baseAbc1 = new A();
//        BaseAbc baseAbc2 = new B();
//        BaseAbc baseAbc3 = new C();
//
//        baseAbc1.show("is A");
//        baseAbc2.show("is B");
//        baseAbc3.show("is C");
    }
}
