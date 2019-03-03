package com.example.joseph.thebakingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.joseph.thebakingapp.QueryUtils.BASEURL;

public class MainActivity extends AppCompatActivity implements
        BakingAdapter.ListItemClickListener,
        LoaderCallbacks<List<Baking>> {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private BakingAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling the Baking objects
        ArrayList<Baking> bakings = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // This is used to improve performance, since changes in content do not change the
        // layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // The recyclerView fills itself with views provided by a layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // View holder objects are managed by an adapter. The adapter creates view holders as needed.
        mAdapter = new BakingAdapter(this, bakings, this);

        mRecyclerView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
        startActivity(intent);
    }

    // These methods are called automatically when you implement the LoaderCallbacks interface
    @Override
    public Loader<List<Baking>> onCreateLoader(int id, Bundle args) {
        return new BakingLoader(this, BASEURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Baking>> loader, List<Baking> data) {

        if (data != null && !data.isEmpty()) {
           /*
           i tested that the json data is generated properly and comes up by making sure that the generated data appears has a toast message
           String d = data.toString();
            Toast.makeText(this,"Data not empty",Toast.LENGTH_SHORT).show();
            Toast.makeText(this, d,Toast.LENGTH_LONG).show();*/
            mAdapter.setBaking((ArrayList<Baking>) data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Baking>> loader) {
    }


}
