package com.implementist.ireading;

import android.content.Context;
import android.os.Bundle;
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

public class DownloadHelper {

    /**
     * 创建文件下载任务
     *
     * @param context 程序上下文
     * @param book    绘本信息
     * @return 文件下载任务
     */
    static BaseDownloadTask createTask(final Context context, final Book book) {
        //获取完整文件存储绝对路径
        final String path = getStoragePath(book.getFileName());

        //创建并返回文件下载器对象
        return FileDownloader.getImpl().create(book.getContentUrl())
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
                        jumpToReadingActivity(context, book);
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
    static int initTask(BaseDownloadTask task) {
        return task.start();
    }

    /**
     * 暂停文件下载任务
     *
     * @param taskID 任务ID
     */
    public static void pauseTask(int taskID) {
        FileDownloader.getImpl().pause(taskID);
    }

    /**
     * 获取文件存储绝对路径
     *
     * @param fileName 文件名(带后缀)
     */
    public static String getStoragePath(String fileName) {

        return MyApplication.EXTERNAL_CACHE_DIR +
                File.separator +
                fileName;
    }

    /**
     * 跳转到ReadingActivity
     *
     * @param context 程序上下文
     * @param book    绘本信息
     */
    static void jumpToReadingActivity(Context context, Book book) {

        Bundle bundle = new Bundle();
        bundle.putString("Title", book.getTitle());
        bundle.putString("FileName", book.getFileName());
        bundle.putString("Author", book.getAuthor());
        bundle.putInt("PageCount", book.getPageCount());
        ((MainActivity) context).startActivity(ReadingActivity.class, bundle);
    }

    /**
     * 检查文件是否存在
     *
     * @param fileName 文件名(带后缀)
     */
    static Boolean isFileExists(String fileName) {
        String path = MyApplication.EXTERNAL_CACHE_DIR +
                File.separator +
                fileName;

        return new File(path).exists();
    }
}
