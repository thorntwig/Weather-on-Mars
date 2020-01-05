package com.janne.weatheronmars.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private List<Unit> units;
    private String title;
    private String sign;
    private int position;

    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        textView = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();

        title = intent.getStringExtra("name");
        sign = intent.getStringExtra("sign");
        position = intent.getIntExtra("position", 0);
        units = (ArrayList<Unit>) intent.getSerializableExtra("units");

        Log.i("TEST", "test");
        textView.setText(title + " AVG: " + units.get(position).getAvg() + sign);

    }
}
