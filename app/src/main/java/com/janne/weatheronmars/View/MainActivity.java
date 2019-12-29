package com.janne.weatheronmars.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;
import com.janne.weatheronmars.Controller.WeatherFetcher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Sol> sols;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SolFragment solFragment = new SolFragment();
        fragmentTransaction.add(R.id.fragment_container, solFragment);

        SolListFragment solListFragment = new SolListFragment();
        fragmentTransaction.add(R.id.fragment_list_container, solListFragment);
        fragmentTransaction.commit();

        textView = findViewById(R.id.textView);
        sols = new ArrayList<>();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();


    }
    private class AsyncTaskRunner extends AsyncTask<Void, Void, List<Sol>>  {

        @Override
        protected void onPreExecute() {
            // TODO: create progress spinner
        }

        @Override
        protected List<Sol> doInBackground(Void... voids) {

            return WeatherFetcher.fetch();
        }

        @Override
        protected void onPostExecute(List<Sol> sols) {
            if(sols.size() > 0) {
                // TODO: remove progress spinner

                textView.setText(sols.get(0).getAverageTemp() + " grader C");
            } else {
                Log.i("ERRRRRORRRRRRR" , "noooow");
            }
        }
    }
}
