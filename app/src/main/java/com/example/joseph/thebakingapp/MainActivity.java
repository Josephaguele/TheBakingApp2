package com.example.joseph.thebakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private BakingAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an ArrayList of periodicTable
        final ArrayList<String> periodicTable = new ArrayList<>();
        periodicTable.add("Hydrogen");
        periodicTable.add("Helium");
        periodicTable.add("Lithium");
        periodicTable.add("Berrylium");
        periodicTable.add("Boron");
        periodicTable.add("Carbon");
        periodicTable.add("Nitrogen");
        periodicTable.add("Oxygen");
        periodicTable.add("Flourine");
        periodicTable.add("Neon");
        periodicTable.add("Sodium");
        periodicTable.add("Magnesium");
        periodicTable.add("Aluminium");
        periodicTable.add("Silicon");
        periodicTable.add("Phosphorus");
        periodicTable.add("Sulphur");
        periodicTable.add("Chlorine");
        periodicTable.add("Argon");
        periodicTable.add("Potassium");
        periodicTable.add("Calcium");
        periodicTable.add("Scandium");
        periodicTable.add("Titanium");
        periodicTable.add("Vanadium");
        periodicTable.add("Chromium");
        periodicTable.add("Maganese");
        periodicTable.add("Iron");
        periodicTable.add("Cobalt");
        periodicTable.add("Nickel");
        periodicTable.add("Copper");
        periodicTable.add("Zinc");


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        // This is used to improve performance, since changes in content do not change the
        // layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // The recyclerView fills itself with views provided by a layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // View holder objects are managed by an adapter. The adapter creates view holders as needed.
        mAdapter = new BakingAdapter(this,R.layout.list_item,periodicTable,this);

        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
        startActivity(intent);


    }
}
