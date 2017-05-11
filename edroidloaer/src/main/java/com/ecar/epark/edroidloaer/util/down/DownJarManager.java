package com.ecar.epark.edroidloaer.util.down;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载安装apk
 */
public class DownJarManager {
    private String filePath;
    private String downUrl;

    public DownJarManager(String filePath, String downUrl) {
        this.filePath = filePath;
        this.downUrl = downUrl;
    }

    public boolean startDownFile() {
        File file = getFileFromServer(filePath, downUrl);
        if(file != null){
            return true;
        }
        return false;
    }


    /**
     * 下载文件
     *
     * @param downUrl 下载链接
     * @return 下载文件
     * @throws Exception
     */
    private File getFileFromServer(String filePath, String downUrl) {
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        try {
            // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
//            if (Environment.getExternalStorageState().equals(
//                    Environment.MEDIA_MOUNTED)) {
                Log.d("tag", "update url " + downUrl);
                URL url = new URL(downUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity"); //加上这句话解决问题
                conn.setConnectTimeout(5000);
                is = conn.getInputStream();
                File file = new File(filePath);
                fos = new FileOutputStream(file);
                bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len;
                int total = 0;
                int temp = conn.getContentLength() / 30;
                int tempstep = 0;
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    // 获取当前下载量
                    tempstep += len;
                    if (tempstep >= temp) {
                        tempstep = 0;
                    }
                }
                return file;
//            }
//            else {
//                return null;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    fos.close();
                    bis.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


//    public void startDownloadFile() {
//        File file = getFileFromServer(url, handler);
////        new Thread(new DownLoadTask()).start();
//    }
//    class DownLoadTask implements Runnable {
//        @Override
//        public void run() {
//            try {
//                File file = getFileFromServer(url, handler);
//                if (file != null) {
//                    handler.sendEmptyMessage(Download_File_Success);
//                } else
//                    handler.sendEmptyMessage(Download_File_Failed);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e("tag", e.toString());
//            }
//            return fa
//        }
//    }


}

