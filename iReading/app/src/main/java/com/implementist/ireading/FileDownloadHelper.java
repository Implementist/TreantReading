package com.implementist.ireading;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.implementist.ireading.activity.MainActivity;
import com.implementist.ireading.activity.ReadingActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class FileDownloadHelper {

    /**
     * 创建文件下载任务
     *
     * @param url     文件URL
     * @param context 程序上下文
     * @param book    绘本信息
     * @return 文件下载任务
     */
    public static BaseDownloadTask createDownloadTask(String url, final Context context, final Book book) {

        final String fileName = getFileName(url);

        //获取完整文件存储绝对路径
        final String path = getStoragePath(fileName);

        //创建并返回文件下载器对象
        return FileDownloader.getImpl().create(url)
                .setPath(path, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i("Error", e.getMessage());
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        jumpToReadingActivity(context, book, fileName);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
    }

    /**
     * 初始化文件下载任务
     *
     * @param task 文件下载任务
     * @return 任务ID
     */
    public static int initDownloadTask(BaseDownloadTask task) {
        return task.start();
    }

    /**
     * 暂停文件下载任务
     *
     * @param taskID 任务ID
     */
    public static void pauseDownloadTask(int taskID) {
        FileDownloader.getImpl().pause(taskID);
    }

    /**
     * 获取文件存储绝对路径
     *
     * @param fileName 文件名(带后缀)
     */
    public static String getStoragePath(String fileName) {

        return Environment.getExternalStorageDirectory().toString() +
                File.separator +
                "Android" +
                File.separator +
                "data" +
                File.separator +
                "com.implementist.ireading" +
                File.separator +
                "cache" +
                File.separator +
                fileName;
    }

    /**
     * 从URL中截取文件名（带后缀）
     *
     * @param url URL
     * @return 文件名
     */
    public static String getFileName(String url) {
        try {
            String[] segment = url.split("/");
            return segment[segment.length - 1];
        } catch (Exception e) {
            Log.i("getFileNameException", e.getMessage());
            return "ErrorFileName";
        }
    }

    /**
     * 跳转到ReadingActivity
     *
     * @param context  程序上下文
     * @param book     绘本信息
     * @param fileName 文件名
     */
    public static void jumpToReadingActivity(Context context, Book book, String fileName) {

        Bundle bundle = new Bundle();
        bundle.putString("Title", book.getTitle());
        bundle.putString("FileName", fileName);
        bundle.putString("Author", book.getAuthor());
        bundle.putInt("PageCount", book.getPageCount());
        ((MainActivity) context).startActivity(ReadingActivity.class, bundle);
    }

    /**
     * 检查文件是否存在
     *
     * @param url URL
     */
    public static Boolean isFileExists(String url) {
        String fileName = getFileName(url);

        String path = Environment.getExternalStorageDirectory().toString() +
                File.separator +
                "Android" +
                File.separator +
                "data" +
                File.separator +
                "com.implementist.ireading" +
                File.separator +
                "cache" +
                File.separator +
                fileName;

        File file = new File(path);

        return file.exists();
    }
}
