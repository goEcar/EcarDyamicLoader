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
        reflect();

    }

    private void reflect() {
        LDClassLoaderManager.getInstance(this).CreateClassLoader();
        try {
            LDClassLoaderManager.getInstance(this).getClassLoader().loadClass("com.ecar.dymicloader.A");
            LDClassLoaderManager.getInstance(this).getClassLoader().loadClass("com.ecar.dymicloader.B");
            LDClassLoaderManager.getInstance(this).getClassLoader().loadClass("com.ecar.dymicloader.C");
            LDClassLoaderManager.getInstance(this).getClassLoader().loadClass("com.ecar.dymicloader.BaseAbc");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method baseAbc1=
                null;
        Method baseAbc2=
                null;
        Method baseAbc3=
                null;
        try {
            baseAbc1 = ReflectUtil.getMethodDex(this,"com.ecar.dymicloader.A","show",String.class);
            baseAbc2 = ReflectUtil.getMethodDex(this,"com.ecar.dymicloader.B","show",String.class);
            baseAbc3 = ReflectUtil.getMethodDex(this,"com.ecar.dymicloader.C","show",String.class);

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
            baseAbc1.invoke(ReflectUtil.getObject(this,"com.ecar.dymicloader.A"),"is A");
            baseAbc2.invoke(ReflectUtil.getObject(this,"com.ecar.dymicloader.B"),"is B");
            baseAbc3.invoke(ReflectUtil.getObject(this,"com.ecar.dymicloader.C"),"is C");

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
