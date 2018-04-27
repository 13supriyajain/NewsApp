package com.supjain.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * This is a custom AsyncTaskLoader class for loading News data asynchronously on a background thread.
 */
public class NewsDataLoader extends AsyncTaskLoader<List<News>> {

    /**
     * Query URL
     */
    private String mUrl;

    public NewsDataLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news articles.
        List<News> newsDataList = NewsDataHelper.fetchNewsData(mUrl);
        return newsDataList;
    }
}
