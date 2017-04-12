package com.ecar.dymicloader.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Log;

import dalvik.system.DexClassLoader;

//classloader管理器
public class LDClassLoaderManager {

    //dex的名称
    private static  String LD_DEX_NAME = "shenzhen".concat("ecar");



    //sd卡的路径
    static public String dexsrc = null;
    //类加载器
    private DexClassLoader classloader = null;

    //原始路径
    public  static  String dexpath = null;

    //
    public static String dexoutputpath = null;

    private static Context context;


    private static LDClassLoaderManager instance = null;

    private LDClassLoaderManager() {
        CreateClassLoader();
    }

    /**
     * 获取当前类的实例(至少需要调用一次)
     *
     * @param context 上下文环境
     * @return 实例
     */
    public static LDClassLoaderManager getInstance(Context context) {
        LDClassLoaderManager.context = context;
        if (null == instance) {
            initPath();
            instance = new LDClassLoaderManager();
        }

        return instance;
    }

    private static void initPath() {
        dexsrc= getSdPatch(context).concat(File.separator);
        dexpath = dexsrc.concat(LD_DEX_NAME);
        dexoutputpath = getSysChachePathHeader(context).concat(File.separator).concat(LD_DEX_NAME);
    }


    /**
     * 获取当前的classloader
     */
    public DexClassLoader getClassLoader() {
        return classloader;
    }

    /**
     * 创建一个新的classloader
     */
    public boolean CreateClassLoader() {
        if (!getFilePath()) {
            return false;
        }
//		ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
//		ClassLoader localClassLoader = context.getClassLoader();
        ClassLoader localClassLoader = LDClassLoaderManager.class.getClassLoader();
        //如果不存在则继续使用缓存目录的dex
        DexClassLoader localDexClassLoader = new DexClassLoader(new File(dexpath).exists()?dexpath:dexoutputpath,
                dexoutputpath, null, localClassLoader);
        classloader = localDexClassLoader;
        return true;
    }

    /**
     * 获取jar的路径信息
     *
     * @return 是否获取成功
     */
    public boolean getFilePath() {
//        String path = getSysChachePathHeader(context);
//        dexpath = path + File.separator + LD_DEX_NAME;
//		if(!file.exists())
        {
            CopyAssetsFile(dexsrc, LD_DEX_NAME, getSysChachePathHeader(context));
        }


        return true;
    }

    /**
     * 获取系统内部根目录路径（无论T卡是否存在）
     */
    public static String getSysChachePathHeader(Context context) {
        String path;
        File file = context.getApplicationContext().getCacheDir();
        if (null == file) {
            return File.separator;
        }
        path = file.getPath();

        return path;
    }

    /**
     * 只删除文件
     *
     * @param file 文件路径（也可以是文件夹）
     */
    public void deleteFileOnly(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            for (File f : childFile) {
                deleteFileOnly(f);
            }
        }
    }

    /**
     * 从assets下复制文件到指定目录
     *
     * @param fileName 需要复制的文件
     * @param dir      需要保存的目录
     * @return 是否复制成功
     */
    public boolean CopyAssetsFile(String srcFileDir, String fileName,
                                  String dir) {
        if(!new File(srcFileDir,fileName).exists()){    //判断源文件是否存在
            return false;
        } else{
            File file;
            file = new File(dexoutputpath + LD_DEX_NAME);
            if (file.exists()) {
                deleteFileOnly(file);
            }
        }
        File mWorkingPath = new File(dir);
        // 如果文件路径不存在
        if (!mWorkingPath.exists()) {
            // 创建文件夹
            if (!mWorkingPath.mkdirs()) {
                return false;
            }
        }

        File outFile = new File(mWorkingPath, fileName);
        if (outFile.exists()) {
            outFile.delete();
        }

        InputStream in = null;
        OutputStream out = null;
        try {
//            in = context.getAssets().open(fileName);
            in = new FileInputStream(srcFileDir.concat(LD_DEX_NAME));
            out = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /****************************************
     * 方法描述：获取可用的sd卡
     *
     * @return
     ****************************************/
    public static String getSdPatch(Context activity) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        } else {
            String patch = null;
            if (TextUtils.isEmpty(patch = getCanUsePatch(activity))) {
                return activity.getCacheDir().getAbsolutePath();
            } else {
                return patch;
            }
        }
    }

    //获取当前可用的sd卡路径
    private static String getCanUsePatch(Context activity) {
        StorageManager mStorageManager = (StorageManager) activity
                .getSystemService(Activity.STORAGE_SERVICE);
        Method method = null;
        try {
            method = mStorageManager.getClass()
                    .getMethod("getVolumePaths");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        String[] paths = null;
        try {
            paths = (String[]) method.invoke(mStorageManager);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (paths == null || paths.length == 0) {
            return "";
        } else {
            for (int i = 0; i < paths.length; i++) {
                if (new File(paths[i]).canRead()) {
                    return paths[i];
                }
            }
        }
        return "";
    }
}