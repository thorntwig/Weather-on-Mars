package com.janne.weatheronmars.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;
import com.janne.weatheronmars.Controller.WeatherFetcher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SolListFragment.Callbacks {

    private ArrayList<Sol> sols;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment solFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        Fragment solListFragment = fragmentManager.findFragmentById(R.id.fragment_list_container);

        if (solFragment == null || solListFragment == null) {
            sols = new ArrayList<>();
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

        }
    }


    @Override
    public void onSolSelected(List<Sol> sols, int key) {
        Fragment fragment = SolFragment.newInstance(sols, key);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, List<Sol>> {

        @Override
        protected void onPreExecute() {
            // TODO: create progress spinner
        }

        @Override
        protected List<Sol> doInBackground(Void... voids) {

            return WeatherFetcher.fetch(getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<Sol> result) {
            if (result.size() > 0) {

                sols = (ArrayList<Sol>) result;


                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment solFragmentCheck = fragmentManager.findFragmentById(R.id.fragment_container);
                Fragment solListFragmentCheck = fragmentManager.findFragmentById(R.id.fragment_list_container);

                if (solFragmentCheck == null || solListFragmentCheck == null) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SolFragment solFragment = SolFragment.newInstance(sols, 0);
                fragmentTransaction.add(R.id.fragment_container, solFragment);

                SolListFragment solListFragment = SolListFragment.newInstance(sols);
                fragmentTransaction.add(R.id.fragment_list_container, solListFragment);

                    fragmentTransaction.commit();

                } else {

                    SolFragment solFragment = SolFragment.newInstance(sols,0);
                    fragmentManager.beginTransaction().remove(solFragmentCheck).commit();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, solFragment).commit();

                    SolListFragment solListFragment = SolListFragment.newInstance(sols);
                    fragmentManager.beginTransaction().remove(solListFragmentCheck).commit();
                    fragmentManager.beginTransaction().replace(R.id.fragment_list_container, solListFragment).commit();

                }

                swipeContainer.setRefreshing(false);

            } else {
                Log.i("ERRRRRORRRRRRR", "noooow");
            }
        }
    }
}
