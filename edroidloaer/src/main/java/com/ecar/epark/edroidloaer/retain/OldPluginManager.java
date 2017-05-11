/*
**        DroidPlugin Project
**
** Copyright(c) 2015 Andy Zhang <zhangyong232@gmail.com>
**
** This file is part of DroidPlugin.
**
** DroidPlugin is free software: you can redistribute it and/or
** modify it under the terms of the GNU Lesser General Public
** License as published by the Free Software Foundation, either
** version 3 of the License, or (at your option) any later version.
**
** DroidPlugin is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
** Lesser General Public License for more details.
**
** You should have received a copy of the GNU Lesser General Public
** License along with DroidPlugin.  If not, see <http://www.gnu.org/licenses/lgpl.txt>
**
**/

package com.ecar.epark.edroidloaer.retain;

import android.app.Application;
import android.text.TextUtils;

import com.ecar.epark.edroidloaer.core.PluginDirHelper;
import com.ecar.epark.edroidloaer.db.DroidSpManager;
import com.ecar.epark.edroidloaer.down.DownJarManager;
import com.ecar.epark.edroidloaer.interfaces.IPluginLoader;
import com.ecar.epark.edroidloaer.util.DLFileUtils;

import java.io.File;

import dalvik.system.DexClassLoader;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 此服务模仿系统的PackageManagerService，提供对插件简单的管理服务。
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2015/2/12.
 */
public class OldPluginManager implements IPluginLoader {
    @Override
    public boolean initLoaderJar(String jarName, String jarVersion, String downUrl) {
        return false;
    }

//    private Application mContext;
//    private DroidSpManager spManager;
//    public DexClassLoader dexClassLoader;
//
//    public OldPluginManager(Application context) {
//        mContext = context;
//        spManager = new DroidSpManager(context);
//    }
//
////    public static final String TEST_PLUGIN_NAME = "calcumoney";
//
//    public synchronized void initPlugin(final String externalPath, final String pluginName) throws Exception {
//
//    }
//
//    /**
//     * 加载jar（dex）
//     *
//     * @param jarName    jar名称，当做jar包目录名
//     * @param jarVersion jar 版本，当做jar 文件名
//     * @param downUrl    若没有对应版本则下载后加载
//     * @return 是否加载成功
//     */
//    @Override
//    public boolean initLoaderJar(String jarName, String jarVersion, String downUrl) {
//        //1.查sp jar版本 与当前版本比较。不同则更新 通则加载。
//        if (TextUtils.isEmpty(jarVersion) || TextUtils.isEmpty(jarName)) {
//            return false;
//        }
//        String jarVersionCache = spManager.getJarVersionByName(jarName);
//        //对比缓存
//        if (jarVersion.equals(jarVersionCache)) {
//            //相同:1.删除其他同类文件 2.加载
//        } else {
//            //去下载
//            boolean downResult = downJar(jarName, jarVersion, downUrl);
//        }
//
//        //        new Thread() {
////            @Override
////            public void run() {
//        try {
//            //1.判断sd 有新包，源文件路径
//            //2.sd没有新包，资产文件路径
//            String dexOutputDir = PluginDirHelper.getPluginDalvikCacheDir(mContext, pluginName);
//            File externalFile = new File(externalPath);
//            if (!externalFile.exists()) {
//                //缓存下没有新包，则拷贝资产目录下dex
//                DLFileUtils.retrieveApkFromAssets(mContext, pluginName.concat(PluginDirHelper.DEX_File_Suff), externalFile.getAbsolutePath());
//            }
//            externalFile = new File(externalPath);
//            if (externalFile.exists()) {
//
//                dexClassLoader = new DexClassLoader(null, dexOutputDir, null, this
//                        .getClass().getClassLoader());
//            }
//        } catch (Exception e) {
//            //删除缓存文件
//            String cachefilePath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, pluginName);
//            DLFileUtils.deleteDir(cachefilePath);
//        }
////            }
////        }.start();
//        return false;
//    }
//
//    /**
//     * 下载jar包
//     *
//     * @param folderName jarName 目录名称
//     * @param fileName   jarVersion 文件名称
//     * @param downUrl
//     */
//    private boolean downJar(String folderName, String fileName, final String downUrl) {
//        final String downPath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, folderName, fileName);
//        Observable<Boolean> treeMapObservable = Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                DownJarManager downJarManager = new DownJarManager(downPath, downUrl);
//                boolean downResult = downJarManager.startDownFile();
//                subscriber.onNext(downResult);
//            }
//        }).compose(this.<Boolean>rxScheduler());
//        Boolean downResult = treeMapObservable.toBlocking().first();
//        return downResult;
//    }
//
//    /**
//     * 线程转换
//     * @param <T>
//     * @return
//     */
//    public <T> Observable.Transformer<T, T> rxScheduler() {
//        return new Observable.Transformer<T, T>() {
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

//    /**
//     * 加载jar（dex）
//     * @param jarName  jar名称，当做jar包目录名
//     * @param jarVersion jar 版本，当做jar 文件名
//     * @param downUrl 若没有对应版本则下载后加载
//     * @return 是否加载成功
//     */
//    @Override
//    public boolean initLoaderJar(String jarName,String jarVersion, String downUrl) {
//        //        new Thread() {
////            @Override
////            public void run() {
//        try {
//            //1.判断sd 有新包，源文件路径
//            //2.sd没有新包，资产文件路径
//            File externalFile = new File(externalPath);
//            if (!externalFile.exists()) {
//                //缓存下没有新包，则拷贝资产目录下dex
//                DLFileUtils.retrieveApkFromAssets(mContext, pluginName.concat(PluginDirHelper.File_Suff), externalFile.getAbsolutePath());
//            }
//            externalFile = new File(externalPath);
//            if (externalFile.exists()) {
//                String dexOutputDir = PluginDirHelper.getPluginDalvikCacheDir(mContext, pluginName);
//                dexOpt(externalFile.getAbsolutePath(), dexOutputDir);
//            }
//        } catch (Exception e) {
//            //删除缓存文件
//            String cachefilePath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, pluginName);
//            DLFileUtils.deleteDir(cachefilePath);
//        }
////            }
////        }.start();
//        return false;
//    }
//
//    private void dexOpt(String dexPath, String dexOutputDir) throws Exception {
//        dexClassLoader = new DexClassLoader(dexPath, dexOutputDir, null, this
//                .getClass().getClassLoader());
//
////        ClassLoader classloader = new PluginClassLoader(apkfile, optimizedDirectory, libraryPath, hostContext.getClassLoader().getParent());
////        DexFile dexFile = DexFile.loadDex(apkfile, PluginDirHelper.getPluginDalvikCacheFile(mContext, parser.getPackageName()), 0);
//
////        Log.e(TAG, "dexFile=%s,1=%s,2=%s", dexFile, DexFile.isDexOptNeeded(apkfile), DexFile.isDexOptNeeded(PluginDirHelper.getPluginDalvikCacheFile(mContext, parser.getPackageName())));
//    }
//
//    public void onDestroy() {
//        mContext = null;
//    }


//    public synchronized void initPlugin(final String externalPath, final String pluginName) throws Exception {
////        new Thread() {
////            @Override
////            public void run() {
//        try {
//            //1.判断sd 有新包，源文件路径
//            //2.sd没有新包，资产文件路径
//            File externalFile = new File(externalPath);
//            if (!externalFile.exists()) {
//                //缓存下没有新包，则拷贝资产目录下dex
//                DLFileUtils.retrieveApkFromAssets(mContext, pluginName.concat(PluginDirHelper.File_Suff), externalFile.getAbsolutePath());
//            }
//            externalFile = new File(externalPath);
//            if (externalFile.exists()) {
//                String dexOutputDir = PluginDirHelper.getPluginDalvikCacheDir(mContext, pluginName);
//                dexOpt(externalFile.getAbsolutePath(), dexOutputDir);
//            }
//        } catch (Exception e) {
//            //删除缓存文件
//            String cachefilePath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, pluginName);
//            DLFileUtils.deleteDir(cachefilePath);
//        }
////            }
////        }.start();
//    }

//    public synchronized void installPlugin(final String externalPath, final String pluginName) throws Exception {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    //1.判断sd 有新包，有则替换缓存目录下的dex
//                    //2.sd没有新包，判断缓存目录下是否有dex，没有则拷贝资产目录dex到缓存
//                    File externalFile = new File(externalPath);
//                    String cachefilePath = null;
//                    cachefilePath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, pluginName);
//                    if (externalFile.exists()) {
//                        //sd有新包
//                        DLFileUtils.deleteDir(cachefilePath);
//                        DLFileUtils.copyFile(externalPath, cachefilePath);
//                    } else {
//                        //sd没有新包
//                        if(!new File(cachefilePath).exists()){
//                            //缓存下没有新包，则拷贝资产目录下dex
//                            DLFileUtils.retrieveApkFromAssets(mContext,pluginName.concat(PluginDirHelper.File_Suff),cachefilePath);
//                        }
//                    }
//                    dexOpt(mContext, apkfile, pluginName);
//                } catch (Exception e) {
//                    if (apkfile != null) {
//                        new File(apkfile).delete();
//                    }
//                }
//            }
//        }.start();
//    }
}
