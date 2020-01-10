package com.janne.weatheronmars.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.janne.weatheronmars.Controller.WeatherRepository;
import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SolListFragment.Callbacks {

    private ArrayList<Sol> sols;
    private SwipeRefreshLayout swipeContainer;
    private ProgressBar spinner;

    private FragmentManager fragmentManager;
    private Fragment solFragment;
    private Fragment solListFragment;


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
                swipeContainer.setRefreshing(true);
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        spinner = (ProgressBar) findViewById(R.id.spinner);


        fragmentManager = getSupportFragmentManager();
        solFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        solListFragment = fragmentManager.findFragmentById(R.id.fragment_list_container);

        if (solFragment == null || solListFragment == null) {
            sols = new ArrayList<>();
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

        }
    }


    @Override
    public void onSolSelected(List<Sol> sols, int key) {
        solFragment = SolFragment.newInstance(sols, key);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, solFragment).commit();
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, List<Sol>> {

        @Override
        protected void onPreExecute() {
            if (!swipeContainer.isRefreshing()) {
                spinner.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected List<Sol> doInBackground(Void... voids) {

            return WeatherRepository.getSol(getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<Sol> result) {


            spinner.setVisibility(View.GONE);

            if (sols != null && sols.equals(result)) {

                Toast.makeText(getApplicationContext(), getString(R.string.up_to_date), Toast.LENGTH_LONG).show();

            } else {

                sols = (ArrayList<Sol>) result;

                solFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                solListFragment = fragmentManager.findFragmentById(R.id.fragment_list_container);

                if (solFragment == null || solListFragment == null) {

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    solFragment = SolFragment.newInstance(sols, 0);
                    fragmentTransaction.add(R.id.fragment_container, solFragment);

                    solListFragment = SolListFragment.newInstance(sols);
                    fragmentTransaction.add(R.id.fragment_list_container, solListFragment);

                    fragmentTransaction.commit();

                } else {

                    fragmentManager.beginTransaction().remove(solFragment).commit();
                    solFragment = SolFragment.newInstance(sols, 0);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, solFragment).commit();

                    fragmentManager.beginTransaction().remove(solListFragment).commit();
                    solListFragment = SolListFragment.newInstance(sols);
                    fragmentManager.beginTransaction().replace(R.id.fragment_list_container, solListFragment).commit();

                }
            }

            swipeContainer.setRefreshing(false);
        }
    }
}
