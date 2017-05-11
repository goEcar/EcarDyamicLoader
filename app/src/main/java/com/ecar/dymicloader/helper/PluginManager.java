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

package com.ecar.dymicloader.helper;

import android.content.Context;

import com.ecar.dymicloader.core.PluginDirHelper;
import com.ecar.dymicloader.util.DLFileUtils;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * 此服务模仿系统的PackageManagerService，提供对插件简单的管理服务。
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2015/2/12.
 */
public class PluginManager {

    private final String TAG = PluginManager.class.getSimpleName();
    private Context mContext;
    public DexClassLoader dexClassLoader;

    public PluginManager(Context context) {
        mContext = context;
    }

    public static final String TEST_PLUGIN_NAME = "calcumoney";

    public synchronized void initPlugin(final String externalPath, final String pluginName) throws Exception {
//        new Thread() {
//            @Override
//            public void run() {
        try {
            //1.判断sd 有新包，源文件路径
            //2.sd没有新包，资产文件路径
            File externalFile = new File(externalPath);
            if (!externalFile.exists()) {
                //缓存下没有新包，则拷贝资产目录下dex
                DLFileUtils.retrieveApkFromAssets(mContext, pluginName.concat(PluginDirHelper.File_Suff), externalFile.getAbsolutePath());
            }
            externalFile = new File(externalPath);
            if (externalFile.exists()) {
                String dexOutputDir = PluginDirHelper.getPluginDalvikCacheDir(mContext, pluginName);
                dexOpt(externalFile.getAbsolutePath(), dexOutputDir);
            }
        } catch (Exception e) {
            //删除缓存文件
            String cachefilePath = PluginDirHelper.getPluginDalvikCacheDexFile(mContext, pluginName);
            DLFileUtils.deleteDir(cachefilePath);
        }
//            }
//        }.start();
    }



    private void dexOpt(String dexPath, String dexOutputDir) throws Exception {
        dexClassLoader = new DexClassLoader(dexPath, dexOutputDir, null, this
                .getClass().getClassLoader());

//        ClassLoader classloader = new PluginClassLoader(apkfile, optimizedDirectory, libraryPath, hostContext.getClassLoader().getParent());
//        DexFile dexFile = DexFile.loadDex(apkfile, PluginDirHelper.getPluginDalvikCacheFile(mContext, parser.getPackageName()), 0);

//        Log.e(TAG, "dexFile=%s,1=%s,2=%s", dexFile, DexFile.isDexOptNeeded(apkfile), DexFile.isDexOptNeeded(PluginDirHelper.getPluginDalvikCacheFile(mContext, parser.getPackageName())));
    }

    public void onDestroy() {
        mContext = null;
    }


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
