package com.janne.weatheronmars.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SolFragment extends Fragment {

    private static final String SOLS_LIST_KEY = "sols_list_key";
    private static final String SOL_KEY = "sol_key";
    private static final String UNITS_KEY = "units_key";

    private TextView number, date;
    private Button temp, wind, pressure;
    private Sol sol;
    private int position;
    private List<Sol> sols;

    private SolFragment() {

    }


    public static SolFragment newInstance(List<Sol> sols, int key) {
        Bundle args = new Bundle();
        args.putSerializable(SOLS_LIST_KEY, (ArrayList<Sol>) sols);
        args.putSerializable(SOL_KEY, key);

        SolFragment fragment = new SolFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        position = getArguments().getInt(SOL_KEY);
        sols = (List<Sol>) getArguments().getSerializable(SOLS_LIST_KEY);
        sol = sols.get(position);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sol, container, false);

        date = view.findViewById(R.id.date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String startDate = formatter.format(sol.getDate());
        date.setText(startDate);

        temp = view.findViewById(R.id.temp);
        if (sol.getTemp() != null) {
            temp.setText((int) Math.round(sol.getTemp().getAvg()) + getString(R.string.temp_sign));
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Unit> temps = new ArrayList<>();
                    for (Sol s : sols) {
                        if (s.getTemp() != null) {
                            temps.add(s.getTemp());
                        }
                    }
                    startDetailsActivity(temps);
                }
            });
        }


        wind = view.findViewById(R.id.wind);
        if (sol.getWind() != null) {
            wind.setText((int) Math.round(sol.getWind().getAvg()) + getString(R.string.wind_sign));
            wind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Unit> winds = new ArrayList<>();
                    for (Sol s : sols) {
                        winds.add(s.getWind());
                    }
                    startDetailsActivity(winds);
                }
            });
        }


        pressure = view.findViewById(R.id.pressure);
        if (sol.getPressure() != null) {
            pressure.setText((int) Math.round(sol.getPressure().getAvg()) + getString(R.string.pressure_sign));
            pressure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Unit> pressures = new ArrayList<>();
                    for (Sol s : sols) {
                        pressures.add(s.getPressure());
                    }
                    startDetailsActivity(pressures);
                }
            });
        }

        number = view.findViewById(R.id.number);
        number.setText(getString(R.string.mars_sol) + sol.getNumber());

        return view;
    }

    private void startDetailsActivity(ArrayList<Unit> units) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(UNITS_KEY, units);
        startActivity(intent);
    }
}
