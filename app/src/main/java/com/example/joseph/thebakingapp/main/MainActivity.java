package com.example.joseph.thebakingapp.main;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.joseph.thebakingapp.OnItemClickListener;
import com.example.joseph.thebakingapp.R;
import com.example.joseph.thebakingapp.ingredients.DetailActivity;
import com.example.joseph.thebakingapp.ingredients.IngredientsActivity;
import com.example.joseph.thebakingapp.main.Baking;
import com.example.joseph.thebakingapp.main.BakingAdapter;
import com.example.joseph.thebakingapp.main.BakingLoader;

import java.util.ArrayList;
import java.util.List;

import static com.example.joseph.thebakingapp.QueryUtils.BASEURL;

public class MainActivity extends AppCompatActivity implements
        OnItemClickListener,
        LoaderCallbacks<List<Baking>> {

    public static final String FOOD_NAME = "name";
    public static final String FOOD_SERVINGS = "servings";

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private BakingAdapter mAdapter;
    private List<Baking> mBakingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling the Baking objects
        ArrayList<Baking> bakings = new ArrayList<>();
        mBakingList = new ArrayList<>();

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




    // These methods are called automatically when you implement the LoaderCallbacks interface
    @Override
    public Loader<List<Baking>> onCreateLoader(int id, Bundle args) {
        Log.i("MainActivity","trying to parse JSON");

        return new BakingLoader(this, BASEURL);
    }


    @Override
    public void onLoadFinished(Loader<List<Baking>> loader, List<Baking> data) {
        Log.i("Check again",data.toString());
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

   @Override
    public void onItemClick(View v, int position) {
        Intent i = new Intent(this, DetailActivity.class);
      Baking b = mBakingList.get(position);
        i.putExtra(FOOD_NAME,b.getmName());
        i.putExtra(FOOD_SERVINGS, b.getmServings());
        startActivity(i);
    }


}