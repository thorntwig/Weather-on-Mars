package com.janne.weatheronmars.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.R;

public class SolFragment extends Fragment {



    private TextView number, temp, wind, pressure;
    private Sol sol;

    public static SolFragment newInstance(Sol sol){
        Bundle args = new Bundle();
        args.putSerializable("solkey", sol);

        SolFragment fragment = new SolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sol = (Sol) getArguments().getSerializable("solkey");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sol,container, false);

        number = (TextView) view.findViewById(R.id.number);
        number.setText("Sol " + sol.getNumber());

        temp = (TextView) view.findViewById(R.id.temp);
        temp.setText(sol.getAverageTemp() + " Grader C");

        wind = (TextView) view.findViewById(R.id.wind);
        wind.setText(sol.getAverageWind() + " m/s ?");

        pressure = (TextView) view.findViewById(R.id.pressure);
        pressure.setText(sol.getAveragePressure() + " hPa ?");

        return view;
    }
}
