package com.janne.weatheronmars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Sol> sols;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        sols = new ArrayList<>();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();

    }
    private class AsyncTaskRunner extends AsyncTask<Void, Void, List<Sol>>  {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<Sol> doInBackground(Void... voids) {

            sols = WeatherFetcher.fetch();
            return sols;
        }
        @Override
        protected void onPostExecute(List<Sol> sols) {
            if(sols.size() > 0) {
                textView.setText(sols.get(0).getAverageTemp() + " grader C");
            } else {
                Log.i("ERRRRRORRRRRRR" , "noooow");
            }

        }
    }
}
