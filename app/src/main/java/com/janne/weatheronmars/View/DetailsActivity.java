package com.janne.weatheronmars.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class DetailsActivity extends AppCompatActivity {

    private static final String UNITS_KEY = "units_key";

    private List<Unit> units;
    private TextView titleTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        Intent intent = getIntent();
        List<Unit> unsortedUnits = (List<Unit>) intent.getSerializableExtra(UNITS_KEY);
        units = new ArrayList<>();
        for (int i = unsortedUnits.size() -1; i >= 0; i--) {
            units.add(unsortedUnits.get(i));
        }


        titleTextView = findViewById(R.id.title);
        titleTextView.setText(units.get(0).getTitle() + getString(R.string._in_) + units.get(0).getSign());

        LineChartView lineChartView = findViewById(R.id.chart);

        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> avg = new ArrayList<>();
        List<PointValue> min = new ArrayList<>();
        List<PointValue> max = new ArrayList<>();

        Line avgLine = new Line(avg);
        Line minLine = new Line(min);
        Line maxLine = new Line(max);

        minLine.setColor(Color.parseColor("#0E79A1"));
        avgLine.setColor(Color.parseColor("#0E79A1"));
        maxLine.setColor(Color.parseColor("#0E79A1"));


        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
        for (int i = 0; i < units.size(); i++) {
            String date = formatter.format(units.get(i).getDate());
            axisValues.add(i, new AxisValue(i).setLabel(date));

            int minVal = (int) Math.round(units.get(i).getMin());
            int avgVal = (int) Math.round(units.get(i).getAvg());
            int maxVal = (int) Math.round(units.get(i).getMax());

            PointValue minPoint = new PointValue(i, minVal);
            PointValue avgPoint = new PointValue(i, avgVal);
            PointValue maxPoint = new PointValue(i, maxVal);

            minPoint.setLabel("");
            avgPoint.setLabel("");
            maxPoint.setLabel("");

            min.add(minPoint);
            avg.add(avgPoint);
            max.add(maxPoint);
        }

        minLine.setHasLabels(true);
        avgLine.setHasLabels(true);
        maxLine.setHasLabels(true);

        min.get(0).setLabel("Min");
        avg.get(0).setLabel("Avg");
        max.get(0).setLabel("Max");


        List<Line> lines = new ArrayList();
        lines.add(maxLine);
        lines.add(avgLine);
        lines.add(minLine);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);



        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);




    }
}
