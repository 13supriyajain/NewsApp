package com.supjain.newsapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is a custom ArrayAdapter class to set and display news article data into ListView.
 */
public class NewsDataAdapter extends ArrayAdapter<News> {

    public static final String LOG_TAG = NewsDataAdapter.class.getName();

    public NewsDataAdapter(Activity context, ArrayList<News> newsDataList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsDataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_article_layout, parent, false);
        }

        News currentNewsData = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.article_title);
        titleTextView.setText(currentNewsData.getArticleTitle());

        TextView sectionTextView = listItemView.findViewById(R.id.article_section);
        sectionTextView.setText(currentNewsData.getArticleSection());

        TextView authorTextView = listItemView.findViewById(R.id.article_author);
        // Check if author-name information is present for the current news article
        if (currentNewsData.hasAuthor()) {
            // If present, set it in respective TextView
            authorTextView.setText(currentNewsData.getArticleAuthor());
        } else {
            // Else hide the whole template/container displaying Author-name information.
            LinearLayout container = listItemView.findViewById(R.id.author_name_container);
            container.setVisibility(View.GONE);
        }

        // Format and set date and time information
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String dateInString = currentNewsData.getArticlePublishDate();
        dateInString = dateInString.replaceAll("Z$", "+0000");
        String formattedDate = "", formattedTime = "";
        try {
            Date date = formatter.parse(dateInString);
            formattedDate = formatDate(date);
            formattedTime = formatTime(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Exception occurred while formatting date string", e);
        }

        TextView articleDate = listItemView.findViewById(R.id.article_date);
        articleDate.setText(formattedDate);

        TextView articleTime = listItemView.findViewById(R.id.article_time);
        articleTime.setText(formattedTime);

        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
