package com.example.joseph.thebakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.joseph.thebakingapp.QueryUtils.BASEURL;

public class IngredientsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Ingredients>> {

    IngredientsAdapter nAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_list);

        ListView listView = (ListView)findViewById(R.id.listview2);
        nAdapter = new IngredientsAdapter(this, new ArrayList<Ingredients>());
        listView.setAdapter(nAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null, this).forceLoad();
    }


    @NonNull
    @Override
    public Loader<List<Ingredients>> onCreateLoader(int id, @Nullable Bundle args) {
        String currentFood = getIntent().getStringExtra("Baking");

        return new IngredientsLoader(this, BASEURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Ingredients>> loader, List<Ingredients> data) {

        nAdapter.clear();

        if(data != null & !data.isEmpty()){
            nAdapter.addAll(data);
        } else {
            Toast.makeText(this, "ingredients not available",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Ingredients>> loader) {
        nAdapter.setIngredients(new ArrayList<Ingredients>());
        nAdapter.clear();

    }
}
