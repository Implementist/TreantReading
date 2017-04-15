package com.implementist.ireading;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.implementist.ireading.activity.MainActivity;
import com.implementist.ireading.activity.ReadingActivity;

import java.util.List;

public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
    private List<Book> books;
    private int largeCardHeight, smallCardHeight;

    public SimpleAdapter(List<Book> books, Context context) {
        this.books = books;
        largeCardHeight = Utils.dip2px(context, 150);
        smallCardHeight = Utils.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, final int position, boolean isItem) {
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
                //TODO: Write action when item is on click

                //TODO: the following raws of code is for testing jump function
                Bundle bundle = new Bundle();
                bundle.putString("Title", "Sample");
                bundle.putString("FileName", "sample.pdf");
                bundle.putString("Author", "Implementist");
                bundle.putInt("PageCount", 84);
                ((MainActivity) holder.rootView.getContext())
                        .startActivity(ReadingActivity.class, bundle);

                Log.i("Test", String.valueOf(position));
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
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    public void insert(Book book, int position) {
        insert(books, book, position);
    }

    public void remove(int position) {
        remove(books, position);
    }

    public void clear() {
        clear(books);
    }

    class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCover;
        TextView tvTitle;
        TextView tvTotalWords;
        TextView tvNewWords;
        ImageView imgEvaluation;
        View rootView;
        public int position;

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

    public Book getItem(int position) {
        if (position < books.size())
            return books.get(position);
        else
            return null;
    }

}