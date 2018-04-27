package com.supjain.newsapp;

/**
 * This is a custom class for holding information about a news article.
 */
public class News {

    private String mArticleTitle;
    private String mArticleSection;
    private String mArticleAuthor = null;
    private String mArticlePublishDate;
    private String mArticleUrl;

    // Parametrized constructor to create object with specific news article information
    public News(String title, String section, String author, String date, String url) {
        mArticleTitle = title;
        mArticleSection = section;
        mArticleAuthor = author;
        mArticlePublishDate = date;
        mArticleUrl = url;
    }

    // Returns news article's title
    public String getArticleTitle() {
        return mArticleTitle;
    }

    // Returns news article's section name
    public String getArticleSection() {
        return mArticleSection;
    }

    // Returns news article's author name
    public String getArticleAuthor() {
        return mArticleAuthor;
    }

    // Returns news article's publish date
    public String getArticlePublishDate() {
        return mArticlePublishDate;
    }

    // Returns news article's web url
    public String getArticleUrl() {
        return mArticleUrl;
    }

    // Returns true if news article's author-name information is present/not null.
    public boolean hasAuthor() {
        return mArticleAuthor != null;
    }
}
