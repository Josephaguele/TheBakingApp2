package com.example.joseph.thebakingapp.ingredients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.joseph.thebakingapp.R;
import com.example.joseph.thebakingapp.main.Baking;

import static com.example.joseph.thebakingapp.main.MainActivity.FOOD_NAME;
import static com.example.joseph.thebakingapp.main.MainActivity.FOOD_SERVINGS;

public class DetailActivity extends AppCompatActivity {

    Baking mBakings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        mBakings= getIntent().getParcelableExtra(FOOD_NAME);
        mBakings = getIntent().getParcelableExtra(FOOD_SERVINGS);


        Intent intent = getIntent();
        String food = mBakings.getmName();
        String servings = mBakings.getmServings();

        TextView servingsTextView =(TextView) findViewById(R.id.servings_detail);
        TextView nameTextView = (TextView)findViewById(R.id.food_name);


        nameTextView.setText(food);
        servingsTextView.setText(servings);
    }
}
