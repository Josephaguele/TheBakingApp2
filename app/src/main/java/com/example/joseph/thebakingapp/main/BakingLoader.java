package com.example.joseph.thebakingapp.main;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.joseph.thebakingapp.QueryUtils;

import java.util.List;

public class BakingLoader extends AsyncTaskLoader<List<Baking>> {

    /*Tag for log messages*/
    private static final String LOG_TAG = BakingLoader.class.getName();
    /*Query URL*/
    private String mUrl;

    /*Constructs a new {@link bakingslLoader}
     *@param context of the activity
     *@param url to load data from*/
    public BakingLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();

    }

    @Override
    public List<Baking> loadInBackground() {
        Log.i(LOG_TAG, "LOAD IN BACKGROUND RUNNING");
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of bakings
        List<Baking> bakings = QueryUtils.fetchBakingData(mUrl);
        Log.i(LOG_TAG,bakings.toString());
        return bakings;
        // load in background works like the AsyncTask doInBackground method.

    }

}
