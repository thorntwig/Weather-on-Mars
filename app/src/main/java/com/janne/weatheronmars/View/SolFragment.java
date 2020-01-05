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
import com.janne.weatheronmars.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SolFragment extends Fragment {



    private TextView number, wind, pressure, date;
    private Button temp;
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
        setRetainInstance(true);
        sol = (Sol) getArguments().getSerializable("solkey");
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sol,container, false);

        number = (TextView) view.findViewById(R.id.number);
        number.setText("Martian sol " + sol.getNumber());

        temp = (Button) view.findViewById(R.id.temp);
        temp.setText((int) Math.round(sol.getTemp().getAvg()) + "°C");

        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("unit", sol.getTemp());
                intent.putExtra("name", "Temperature");
                intent.putExtra("sign", "°C");
                startActivity(intent);
            }
        });

        wind = (TextView) view.findViewById(R.id.wind);
        wind.setText((int) Math.round(sol.getWind().getAvg()) + " m/s");

        pressure = (TextView) view.findViewById(R.id.pressure);
        pressure.setText((int) Math.round(sol.getPressure().getAvg()) + " hPa");

        date = (TextView) view.findViewById(R.id.date);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String startDate = formatter.format(sol.getStartTime());
        date.setText(startDate);

        return view;
    }
}
