package com.janne.weatheronmars.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.janne.weatheronmars.Model.Sol;
import com.janne.weatheronmars.Model.Unit;
import com.janne.weatheronmars.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class DetailsActivity extends AppCompatActivity {

    private List<Unit> units;
    private List<Sol> sols;
    private String title;
    private String sign;
    private int position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}


        Intent intent = getIntent();

        title = intent.getStringExtra("name");
        sign = intent.getStringExtra("sign");
        position = intent.getIntExtra("position", 0);
        units = (List<Unit>) intent.getSerializableExtra("units");
        sols = (List<Sol>) intent.getSerializableExtra("sols");

        LineChartView lineChartView = findViewById(R.id.chart);


        List<AxisValue> axisValues = new ArrayList<>();
        List<PointValue> avg = new ArrayList<>();
        List<PointValue> min = new ArrayList<>();
        List<PointValue> max = new ArrayList<>();

        Line avgLine = new Line(avg);
        Line minLine = new Line(min);
        Line maxLine = new Line(max);
        minLine.setColor(Color.parseColor("#D6FFFE"));
        avgLine.setColor(Color.parseColor("#5EC2F1"));
        maxLine.setColor(Color.parseColor("#0C6FFF"));





        for(int i = 0; i < sols.size(); i++) {
            axisValues.add(i, new AxisValue(i).setLabel(String.valueOf(sols.get(i).getNumber())));
            avg.add(new PointValue(i, (float) units.get(i).getAvg()));
            min.add(new PointValue(i, (float) units.get(i).getMin()));
            max.add(new PointValue(i, (float) units.get(i).getMax()));
        }

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
        axis.setName("Martian sol");

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);



    }
}
