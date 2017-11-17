package com.ecar.dymicloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.ecar.epark.edroidloaer.manager.helper.PluginDirHelper;
import com.ecar.epark.edroidloaer.manager.PluginManager;
import com.ecar.epark.edroidloaer.util.DLFileUtils;
import com.ecar.epark.edroidloaer.util.retain.ReflectUtil;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
//        testData();
//        testData2();
        testData3();
        findViewById(R.id.content).setOnClickListener(v -> {
            onclic();
        });
    }

    //    public static final String TEST_PLUGIN_NAME = "calcumoney.dex";
    public static final String TEST_PLUGIN_NAME = "ts2.dex";
    public static final String TEST_PLUGIN_NAME2 = "pdacalctest.dex";
//    public static final String TEST_PLUGIN_NAME = "pdap1ricing.jar";

    private void testData2() {
//        PluginManager pluginManager = new PluginManager(getApplication());

        //测试数据------ 模拟jar包下载到缓存中
        String jarName = "pda_test4";
        String jarVersion = "4";
        String dexPath = PluginDirHelper.getPluginDalvikCacheDexFile(getApplication(), jarName.concat(PluginManager.DEX_TEMP_CACHE_PATH_ENDING), jarVersion);
        DLFileUtils.retrieveApkFromAssets(this, TEST_PLUGIN_NAME, dexPath);
        //测试数据------

        String url = "http://www.szkljy.com:9007/std/data?module=sys&service=File&method=downApp&type=pricing&fileName=t1.jar&t=1494471167315";
        boolean b = PluginManager.getInstance(getApplication()).initLoaderJar(jarName, jarVersion, url);
    }

    private void testData3() {
//        PluginManager pluginManager = new PluginManager(getApplication());

        //测试数据------ 模拟jar包下载到缓存中
        String jarName = "pda_test7";
        String jarVersion = "pdacalctest2";
//        String dexPath = PluginDirHelper.getPluginDalvikCacheDexFile(getApplication(), jarName.concat(PluginManager.DEX_TEMP_CACHE_PATH_ENDING), jarVersion);
//        DLFileUtils.retrieveApkFromAssets(this, TEST_PLUGIN_NAME2, dexPath);
        //测试数据------

        String url = "http://www.szkljy.com:9007/std/data?module=sys&service=File&method=downApp&type=pricing&fileName=t1.jar&t=1494471167315";
        url = "http://192.168.9.103:6082/std/data?module=sys&service=File&method=downApp&type=pricing&fileName=pdacalctest";
        boolean b = PluginManager.getInstance(getApplication()).initLoaderJar(jarName, jarVersion, url);
        if(b){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String path = "com.jh".concat(".MoneyCalcu");
        String pathLocal = "com.etest.calcumoney".concat(".MoneyCalcu");
        String method = "calcu";
        path = "com.jh".concat(".ReadXml");
        method = "testread";




//        try {
//            List<Person> persons = readXML(getResources().getAssets().open("person.xml"));
//            Toast.makeText(this, "" + persons.toString(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void onclic(){
        String path = "com.pdacalc.internal".concat(".Rule");
        String method = "getNeedPayPrice";
        TreeMap in = new TreeMap();
        SimpleDateFormat currensdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try{

            in.put("vehicletype", Integer.valueOf(1));
            in.put("startparktime", Long.valueOf(currensdf.parse("2017-06-12 08:30:00:000").getTime()));
            in.put("endparktime", Long.valueOf(currensdf.parse("2017-06-12 09:31:00:000").getTime()));

            in.put("abtime", Integer.valueOf(0));
        }catch (Exception e){
            e.printStackTrace();
        }
        TreeMap result = (TreeMap) PluginManager.getInstance(getApplication()).invokeMethod(path,method,in);
        Toast.makeText(this, "" + result, Toast.LENGTH_SHORT).show();
    }


    public static List<Person> readXML(InputStream inStream) {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inStream, "UTF-8");// 设置数据源编码
            int eventType = parser.getEventType();// 获取事件类型
            Person currentPerson = null;
            List<Person> persons = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                        persons = new ArrayList<Person>();// 实例化集合类
                        break;
                    case XmlPullParser.START_TAG://开始读取某个标签
                        //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("person")) {
                            currentPerson = new Person();
                            currentPerson.setId(parser.getAttributeValue(null, "id"));
                        } else if (currentPerson != null) {
                            if (name.equalsIgnoreCase("name")) {
                                currentPerson.setName(parser.nextText());// 如果后面是Text元素,即返回它的值
                            } else if (name.equalsIgnoreCase("age")) {
                                currentPerson.setAge(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 结束元素事件
                        //读完一个Person，可以将其添加到集合类中
                        if (parser.getName().equalsIgnoreCase("person") && currentPerson != null) {
                            persons.add(currentPerson);
                            currentPerson = null;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inStream.close();
            return persons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    private void invokeMethodOriginal(PluginManager pluginManager) {
//        try {
//            Class<?> class1 = pluginManager.dexClassLoader.loadClass("com.etest.calcumoney.MoneyCalcu");
//            Object object = class1.newInstance();
//            Class[] params = new Class[1];
//            params[0] = Integer.TYPE;
////            params[1] = Integer.TYPE;
//            Method action = class1.getMethod("calcu", params);
//            Integer ret = (Integer) action.invoke(object, 12);
//            Toast.makeText(this, "" + ret, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void invokeMethod(PluginManager pluginManager) {
//        try {
//            Class<?> class1 = pluginManager.dexClassLoader.loadClass("com.etest.calcumoney.MoneyCalcu");
//            Object object = class1.newInstance();
//            Integer calcu1 = (Integer) MethodUtils.invokeMethod(object, "calcu", 11);
//            Toast.makeText(this, "" + calcu1, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    private void testData() {
//        PluginManager pluginManager = new PluginManager(getApplication());
//        String sdPatch = DLFileUtils.getSdPatch(this);
//        String concat = sdPatch.concat(File.separator).concat(PluginManager.TEST_PLUGIN_NAME).concat(PluginDirHelper.DEX_File_Suff);
////        String concat = sdPatch.concat(File.separator).concat("asdf").concat(PluginDirHelper.File_Suff);
//
////        DLFileUtils.retrieveApkFromAssets(this,PluginManager.TEST_PLUGIN_NAME,concat);
//        try {
//            pluginManager.initPlugin(concat, PluginManager.TEST_PLUGIN_NAME);
//            Class<?> class1 = pluginManager.dexClassLoader.loadClass("com.etest.calcumoney.MoneyCalcu");
//            Object object = class1.newInstance();
////            Class[] params = new Class[1];
////            params[0] = Integer.TYPE;
//////            params[1] = Integer.TYPE;
////            Method action = class1.getMethod("calcu", params);
////            Integer ret = (Integer) action.invoke(object, 12);
//
//
//            Class[] params = new Class[1];
//            params[0] = Integer.TYPE;
//            Method calcu = MethodUtils.getAccessibleMethod(class1, "calcu", params);
//            Integer ret = (Integer) calcu.invoke(object, 12);
//            Toast.makeText(this, "" + ret, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


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
