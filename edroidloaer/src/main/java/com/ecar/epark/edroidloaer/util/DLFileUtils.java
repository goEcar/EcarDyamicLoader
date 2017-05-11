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

package com.ecar.epark.edroidloaer.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


import com.ecar.epark.edroidloaer.manager.helper.PluginDirHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Andy Zhang(zhangyong232@gmail.com) on 2015/2/26.
 */
public class DLFileUtils {

    private static final String TAG = DLFileUtils.class.getSimpleName();


    private static final String VALID_JAVA_IDENTIFIER = "(\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)*\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
    private static final Pattern ANDROID_DATA_PATTERN = Pattern.compile(VALID_JAVA_IDENTIFIER);

    public static boolean validateJavaIdentifier(String identifier) {
        return ANDROID_DATA_PATTERN.matcher(identifier).matches();
    }


    public static void copyFile(String src, String dst) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream ou = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src));
            ou = new BufferedOutputStream(new FileOutputStream(dst));
            byte[] buffer = new byte[8192];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                ou.write(buffer, 0, read);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }

            if (ou != null) {
                try {
                    ou.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void deleteDir(String file) {
        deleteFile(new File(file));
    }

    private static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    public static void deleteFileButOne(File file, String fileName) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFileButOne(files[i], fileName);
            }
        }
        if (!TextUtils.isEmpty(fileName)
                && fileName.equals(file.getName())) {
            file.delete();
        }
    }

    public static void writeToFile(File file, byte[] data) throws IOException {
        FileOutputStream fou = null;
        try {
            fou = new FileOutputStream(file);
            fou.write(data);
        } finally {
            if (fou != null) {
                try {
                    fou.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static byte[] readFromFile(File file) throws IOException {
        FileInputStream fin = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            fin = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int read = 0;
            while ((read = fin.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            byte[] data = out.toByteArray();
            out.close();
            return data;
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] digest = md.digest(data);
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
//            Log.e(TAG, "Md5 Fail");
        }
        return null;
    }

    private static String toHex(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int v = b[i];
            builder.append(HEX[(0xF0 & v) >> 4]);
            builder.append(HEX[0x0F & v]);
        }
        return builder.toString();
    }

    private static final char[] HEX = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final String ALGORITHM = "MD5";

    public static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> raps = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo rap : raps) {
            if (rap != null && rap.pid == pid) {
                return rap.processName;
            }
        }
        return null;
    }

    /**
     * 获取可用的sd卡
     */
    public static String getSdPatch(Context activity) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        } else {
            String patch = null;
            if (TextUtils.isEmpty(patch = PluginDirHelper.makePluginBaseDir(activity, "ecartemp"))) {
                return activity.getCacheDir().getAbsolutePath();
            } else {
                return patch;
            }
        }
    }


    /**
     * 将资产目录下文件拷贝到某处
     */
    public static boolean retrieveApkFromAssets(Context context, String fileName, String path) {
        boolean bRet = false;

        try {
            InputStream e = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            boolean i = false;

            int i1;
            while ((i1 = e.read(temp)) > 0) {
                fos.write(temp, 0, i1);
            }

            fos.close();
            e.close();
            bRet = true;
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return bRet;
    }
}
