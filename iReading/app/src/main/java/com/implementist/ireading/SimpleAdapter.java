package com.implementist.ireading;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.LinearProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.List;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 */

public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {

    private List<Book> books;
    private int largeCardHeight, smallCardHeight;

    public SimpleAdapter(List<Book> books, Context context) {
        this.books = books;
        largeCardHeight = Utils.dip2px(context, 150);
        smallCardHeight = Utils.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {
        final Book book = books.get(position);
        //TODO:get image from server and set to the following ImageView
        //holder.imgCover.setImageResource();

        holder.tvTitle.setText(book.getTitle());
        holder.tvTotalWords.setText(String.valueOf(book.getTotalWords()));
        holder.tvNewWords.setText(String.valueOf(book.getNewWords()));

        //TODO:set corresponding image by score
        switch (book.getEvaluationScore() * 10 / 10) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            holder.rootView.getLayoutParams().height = position % 2 != 0 ? largeCardHeight : smallCardHeight;
        }

        //Item OnClick Event
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果目标文件不存在
                if (!DownloadHelper.isFileExists(book.getFileName())) {

                    //下载对话框
                    final DialogPlus dialog;

                    //实例化下载对话框
                    Holder holder = new ViewHolder(R.layout.download_dialog_content);
                    dialog = createDialog(holder, Gravity.CENTER, view.getContext());
                    dialog.show();

                    //定义并实例化Download Button
                    final LinearProgressButton btnStartToDownload;
                    btnStartToDownload = (LinearProgressButton) holder.getInflatedView()
                            .findViewById(R.id.btnStartToDownload);

                    //初始化Download Button形状
                    morphToSquare(view.getContext(), btnStartToDownload, 0);

                    //TaskID用于将来对Task进行操作，如暂停
                    final int[] downloadTaskID = new int[1];

                    //Download Button的点击事件，暂时必须写在这里
                    btnStartToDownload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //按钮变进度条
                            simulateProgress(btnStartToDownload, v.getContext(), 500);

                            //传入Holder
                            DownloadHelper.setDialog(dialog);

                            //创建下载任务
                            BaseDownloadTask task = DownloadHelper.createTask(v.getContext(),
                                    book, btnStartToDownload);

                            //启动下载任务并获取任务ID
                            downloadTaskID[0] = DownloadHelper.initTask(task);
                        }
                    });

                } else {
                    DownloadHelper.jumpToReadingActivity(view.getContext(), book);
                }

                Log.i("Current Position", String.valueOf(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return books.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    public void setData(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_book_profile, parent, false);
        return new SimpleAdapterViewHolder(v, true);
    }

    /**
     * 插入Book到BookList中
     *
     * @param book     带插入的绘本
     * @param position 插入位置
     */
    public void insert(Book book, int position) {
        insert(books, book, position);
    }

    /**
     * 从BookList中移除指定位置的绘本
     *
     * @param position 要移除的绘本的位置
     */
    public void remove(int position) {
        remove(books, position);
    }

    /**
     * 清除BookList中的所有绘本
     */
    public void clear() {
        clear(books);
    }

    /**
     * 内部类SimpleAdapterViewHolder
     */
    class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCover;
        TextView tvTitle;
        TextView tvTotalWords;
        TextView tvNewWords;
        ImageView imgEvaluation;
        View rootView;

        /**
         * 双参构造函数
         *
         * @param itemView 条目根视图
         * @param isItem   是否为条目
         */
        SimpleAdapterViewHolder(final View itemView, final boolean isItem) {
            super(itemView);
            if (isItem) {
                imgCover = (ImageView) itemView.findViewById(R.id.imgCover);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvTotalWords = (TextView) itemView.findViewById(R.id.tvTotalWords);
                tvNewWords = (TextView) itemView.findViewById(R.id.tvNewWords);
                imgEvaluation = (ImageView) itemView.findViewById(R.id.imgEvaluation);
                rootView = itemView.findViewById(R.id.lytBookUnit);
            }
        }
    }

    /**
     * 获取指定位置的绘本信息
     *
     * @param position 指定位置
     * @return 绘本信息
     */
    public Book getItem(int position) {
        if (position < books.size())
            return books.get(position);
        else
            return null;
    }

    /**
     * 创建一个下载对话框
     *
     * @param holder  ViewHolder
     * @param gravity Layout_Gravity
     * @param context 当前上下文
     * @return 新的下载对话框
     */
    private static DialogPlus createDialog(Holder holder, int gravity, Context context) {
        return DialogPlus.newDialog(context)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setExpanded(false)
                .setCancelable(true)
                .create();
    }

    /**
     * 从进度条变为方形按钮
     *
     * @param context  当前上下文
     * @param btnMorph 要改变的Button
     * @param duration 动画间隔
     */
    private void morphToSquare(Context context, final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .color(ContextCompat.getColor(context, R.color.standardBlue))
                .colorPressed(ContextCompat.getColor(context, R.color.standardDarkBlue))
                .text(context.getResources().getString(R.string.start_to_download));
        btnMorph.morph(square);
    }

    /**
     * 进度条变为成功时的圆形按钮
     *
     * @param context  当前上下文
     * @param btnMorph 要改变的Button
     */
    static void morphToSuccess(Context context, final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .color(ContextCompat.getColor(context, R.color.standardBlue))
                .colorPressed(ContextCompat.getColor(context, R.color.standardDarkBlue))
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    /**
     * 进度条变为失败时的圆形按钮
     *
     * @param context  当前上下文
     * @param btnMorph 要改变的Button
     * @param duration 动画间隔
     */
    static void morphToFailure(Context context, final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .color(ContextCompat.getColor(context, R.color.standardBlue))
                .colorPressed(ContextCompat.getColor(context, R.color.standardDarkBlue))
                .icon(R.drawable.ic_lock);
        btnMorph.morph(circle);
    }

    /**
     * 激活按钮到进度条
     *
     * @param button   要激活的按钮
     * @param context  当前上下文
     * @param duration 动画间隔
     */
    private static void simulateProgress(final LinearProgressButton button, Context context,
                                         int duration) {
        // prevent user from clicking while button is in progress
        button.blockTouch();
        button.morphToProgress(ContextCompat.getColor(context, R.color.standardDarkBlue),
                ContextCompat.getColor(context, R.color.standardBlue),
                10, button.getWidth(), 40, duration);
    }
}