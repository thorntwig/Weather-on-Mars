package com.janne.weatheronmars.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

public class DetailsActivity extends AppCompatActivity {

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
        String title = intent.getStringExtra("name");
        Unit unit = (Unit) intent.getSerializableExtra("unit");

        Log.i("TEST", "test");
        textView.setText(title);

    }
}
