package com.ecar.dymicloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ecar.dymicloader.helper.LDClassLoaderManager;
import com.ecar.dymicloader.util.ReflectUtil;

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
        testGson();

    }

    //测试gson
    private void testGson() {

        Method gsons1=
                null;
        Method gsons2=
                null;
        try {
//            gsons1 = ReflectUtil.getMethodDex(this,"com.google.gson.Gson","toJson",String.class);
//            gsons1.invoke(ReflectUtil.getObject(this,"com.google.gson.Gson"),"{abc:xxx}");

            gsons2 = ReflectUtil.getMethodDex(this,"com.google.gson.Gson","toString");
            gsons2.invoke(ReflectUtil.getObject(this,"com.google.gson.Gson"));
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
        Method baseAbc1=
                null;
        Method baseAbc2=
                null;
        Method baseAbc3=
                null;
        try {
            baseAbc1 = ReflectUtil.getMethodDex(this,"gt.ecar.com.mylibrary.A","show",String.class);
            baseAbc2 = ReflectUtil.getMethodDex(this,"gt.ecar.com.mylibrary.B","show",String.class);
            baseAbc3 = ReflectUtil.getMethodDex(this,"gt.ecar.com.mylibrary.C","show",String.class);

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
            baseAbc1.invoke(ReflectUtil.getObject(this,"gt.ecar.com.mylibrary.A"),"is A");
            baseAbc2.invoke(ReflectUtil.getObject(this,"gt.ecar.com.mylibrary.B"),"is B");
            baseAbc3.invoke(ReflectUtil.getObject(this,"gt.ecar.com.mylibrary.C"),"is C");

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
