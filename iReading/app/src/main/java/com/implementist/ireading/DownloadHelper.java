package com.implementist.ireading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dd.morphingbutton.impl.LinearProgressButton;
import com.implementist.ireading.activity.MainActivity;
import com.implementist.ireading.activity.ReadingActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.dialogplus.DialogPlus;

import java.io.File;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class DownloadHelper {
    @SuppressLint("StaticFieldLeak")
    private static DialogPlus dialog;

    /**
     * 创建文件下载任务
     *
     * @param context 当前上下文
     * @param book    绘本信息
     * @param button  文件下载按钮
     * @return 文件下载任务
     */
    static BaseDownloadTask createTask(final Context context, final Book book, final LinearProgressButton button) {
        //获取完整文件存储绝对路径
        final String path = getStoragePath(book.getFileName());
        //创建并返回文件下载器对象
        return FileDownloader.getImpl().create(HttpRequestUtils.SERVER_ROOT + book.getContentUrl())
                .setPath(path, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //进度条清零
                        button.setProgress(0);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //为进度条更新进度
                        button.setProgress((int) (soFarBytes / (totalBytes + 0.0) * 100));
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Toast.makeText(context, "下载进程发生异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
                        //设置进度条满进度
                        button.setProgress(100);

                        //跳转至阅读页面
                        jumpToReadingActivity(context, book);
                        dialog.dismiss();
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

    /**
     * 设置对话框
     *
     * @param dialog 对话框
     */
    static void setDialog(DialogPlus dialog) {
        DownloadHelper.dialog = dialog;
    }
}
