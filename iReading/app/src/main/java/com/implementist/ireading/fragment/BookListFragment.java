package com.implementist.ireading.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.implementist.ireading.Book;
import com.implementist.ireading.EndlessRecyclerViewScrollListener;
import com.implementist.ireading.HttpRequestUtils;
import com.implementist.ireading.MyApplication;
import com.implementist.ireading.R;
import com.implementist.ireading.RefreshViewFooter;
import com.implementist.ireading.RefreshViewHeader;
import com.implementist.ireading.SimpleAdapter;
import com.implementist.ireading.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Implementist. All rights reserved.
 * Book List Fragment
 */
public class BookListFragment extends BaseFragment implements XRefreshView.XRefreshViewListener {

    RecyclerView recyclerView;
    private SimpleAdapter adapter;
    static List<Book> books = new ArrayList<>();
    XRefreshView xRefreshView;

    LinearLayoutManager layoutManager;

    //为了使第一次showBook完成之后自动跳回首条目而设置的Count值
    private static int loadTimes = 0;

    @Override
    public int bindLayout() {
        return R.layout.fragment_book_list;
    }

    @Override
    public void initView(View view) {
        xRefreshView = $(R.id.refrvBookList);
        recyclerView = $(R.id.recvBookList);
        recyclerView.setHasFixedSize(true);

        adapter = new SimpleAdapter(books, view.getContext());
        // 设置静默加载模式
        //xRefreshView1.setSilenceLoadMore();
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //静默加载模式不能设置footerView
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView iew) {
                if (MyApplication.currentItemIndex < MyApplication.books.length()) {
                    ((MainActivity) getActivity()).showToast("成功加载" +
                            Math.min(MyApplication.books.length() - MyApplication.currentItemIndex, 10) +
                            "条书目信息");

                    BookListFragment.insertItems(adapter, recyclerView);
                    xRefreshView.stopLoadMore();
                } else {
                    xRefreshView.setLoadComplete(true);
                }
            }
        });

        //设置刷新完成以后，headerView固定的时间
        xRefreshView.setPinnedTime(0);

        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(false);

        //设置自定义页头
        xRefreshView.setCustomHeaderView(new RefreshViewHeader(view.getContext()));

        //设置自定义页脚
        adapter.setCustomLoadMoreView(new RefreshViewFooter(view.getContext()));

        xRefreshView.enableReleaseToLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);

        //设置静默加载时提前加载的item个数
        //xRefreshView1.setPreLoadCount(4);

        //预读取绘本数据
        HttpRequestUtils.SearchAllBooksRequest(adapter, recyclerView);
    }

    @Override
    public void setListener() {
        xRefreshView.setXRefreshViewListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {
    }

    /**
     * 插入条目
     */
    public static void insertItems(SimpleAdapter adapter, RecyclerView view) {
        //loadTimes自增
        loadTimes++;

        try {
            int count = Math.min(MyApplication.books.length(), MyApplication.currentItemIndex + 10);
            for (int i = MyApplication.currentItemIndex; i < count; i++) {
                JSONObject jsonObject = MyApplication.books.getJSONObject(i);
                Book book = new Book();
                book.setBookID(jsonObject.getInt("BookID"));
                book.setPageCount(jsonObject.getInt("PageCount"));
                book.setScore((float) jsonObject.getDouble("Score"));
                book.setTitle(jsonObject.getString("Title"));
                book.setAuthor(jsonObject.getString("Author"));
                book.setCoverUrl(jsonObject.getString("CoverURL"));
                book.setContentUrl(jsonObject.getString("ContentURL"));
                book.setFileName(jsonObject.getString("FileName"));

                MyApplication.currentItemIndex++;

                //将书本信息插入Adapter
                adapter.insert(book, i);
            }

            if (loadTimes == 1)
                view.smoothScrollToPosition(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        //TODO: Request all of books' information from DB again
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRefreshView.stopRefresh();
            }
        }, 500);
    }

    @Override
    public void onLoadMore(boolean isSilence) {

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }
}
