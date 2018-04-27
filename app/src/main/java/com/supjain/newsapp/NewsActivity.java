package com.supjain.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    // URL for fetching news articles for query = California
    private static final String NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=california&order-by=newest&show-references=author&api-key=test";
    // Constant value for the news loader ID
    private static final int News_LOADER_ID = 1;
    // Adapter for the list of news articles
    private NewsDataAdapter mAdapter;
    private TextView emptyMsgTexView;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Find a reference to the {@link ListView, ProgressBar and TextView} in the layout
        ListView newsListView = findViewById(R.id.list);
        loadingSpinner = findViewById(R.id.loading_spinner);
        emptyMsgTexView = findViewById(R.id.emaptyViewText);
        // Set default TextView as empty view for the ListView
        newsListView.setEmptyView(emptyMsgTexView);

        // Create a new adapter that takes an empty list of News articles as input
        mAdapter = new NewsDataAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        newsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news article.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news article that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getArticleUrl());

                // Create a new intent to view the news article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Call Loader class to load list of news articles from the query url
        callLoader();
    }

    /**
     * If connected to internet, then call initLoader else set 'No Internet Connection' message.
     */
    private void callLoader() {
        if (isInternetConnected()) {
            getLoaderManager().initLoader(News_LOADER_ID, null, this);
        } else {
            emptyMsgTexView.setText(R.string.no_internet);
            loadingSpinner.setVisibility(View.GONE);
        }
    }

    /**
     * Check and return true if user's device is connected to internet.
     */
    private boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsDataLoader(NewsActivity.this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        emptyMsgTexView.setText(R.string.no_news);
        loadingSpinner.setVisibility(View.GONE);
        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
