package com.implementist.ireading.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.implementist.ireading.R;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.xw.repo.BubbleSeekBar;

public class ReadingActivity extends BaseActivity implements OnPageChangeListener,
        CompoundButton.OnCheckedChangeListener, BubbleSeekBar.OnProgressChangedListener {

    private String title, author, fileName;
    private int currentPageNumber, pageCount, userProgress;

    private TextView txtTitle, txtAuthor, txtCurrentPageNumber, txtPageCount;
    private BubbleSeekBar skbReadingProgress;
    private PDFView pdfView;
    private CheckBox chbAddToFavorite;

    @Override
    public void initParams(Bundle params) {
        title = params.getString("Title");
        fileName = params.getString("FileName");
        author = params.getString("Author");
        pageCount = params.getInt("PageCount");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reading;
    }

    @Override
    public void initView(View view) {
        txtTitle = $(R.id.txtBookTitle);
        txtAuthor = $(R.id.txtAuthor);
        txtCurrentPageNumber = $(R.id.txtCurrentPageNumber);
        txtPageCount = $(R.id.txtPageCount);
        skbReadingProgress = $(R.id.skbReadingProgress);
        pdfView = $(R.id.pdfView);
        chbAddToFavorite = $(R.id.chbAddToFavorite);

        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtCurrentPageNumber.setText(String.valueOf(currentPageNumber));
        txtPageCount.setText(String.valueOf(pageCount));

        //配置SeekBar
        skbReadingProgress.getConfigBuilder()
                .min(0)
                .max(pageCount)
                .trackSize(4)
                .secondTrackColor(ContextCompat.getColor(this, R.color.standardWhite))
                .bubbleColor(ContextCompat.getColor(this, R.color.standardWhite))
                .bubbleTextColor(ContextCompat.getColor(this, R.color.standardBlue))
                .bubbleTextSize(16)
                .build();

        //显示PDF
        showPDF(fileName, true);
    }

    @Override
    public void setListener() {
        skbReadingProgress.setOnProgressChangedListener(this);
        chbAddToFavorite.setOnCheckedChangeListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetOnClick(View v) {

    }

    private void showPDF(String assetFileName, boolean jumpToFirstPage) {

        if (jumpToFirstPage)
            currentPageNumber = 1;

        pdfView.fromAsset(assetFileName)
                .defaultPage(currentPageNumber)
                .onPageChange(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        currentPageNumber = page;
        txtCurrentPageNumber.setText(String.valueOf(currentPageNumber));

        //设置拖动条进度
        skbReadingProgress.setProgress(page);
    }

    @Override
    public void onProgressChanged(int progress, float progressFloat) {
        //记录当前用户进度
        userProgress = progress;
    }

    @Override
    public void getProgressOnActionUp(int progress, float progressFloat) {
        pdfView.jumpTo(userProgress);
    }

    @Override
    public void getProgressOnFinally(int progress, float progressFloat) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //TODO: Add this book to favorite.
            showToast("已添加至收藏夹");
        } else {
            //TODO: Remove this book from favorite.
            showToast("已从收藏夹中移除");
        }
    }
}
