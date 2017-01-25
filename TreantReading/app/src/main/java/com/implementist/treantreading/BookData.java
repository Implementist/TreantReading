package com.implementist.treantreading;

/**
 * Copyright © 2017. Fishbone Inc. All rights reserved.
 */

public class BookData {
    private int bookNumber;
    private String coverUrl;
    private String title;
    private int totalWords;
    private int newWords;
    private int evaluationScore;

    public BookData(int bookNumber, String coverUrl, String title, int totalWords, int newWords,
                    int evaluationScore) {
        this.bookNumber = bookNumber;
        this.coverUrl = coverUrl;
        this.title = title;
        this.totalWords = totalWords;
        this.newWords = newWords;
        this.evaluationScore = evaluationScore;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getNewWords() {
        return newWords;
    }

    public int getEvaluationScore() {
        return evaluationScore;
    }
}
