package com.janne.weatheronmars.View;

import android.content.Context;
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
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class DetailsActivity extends AppCompatActivity {

    private static final String UNITS_KEY = "units_key";
    private static final String UNITS_POSITION = "units_position";

    private List<Unit> units;
    private TextView titleTextView;
    private LineChartView lineChartView;
    private List<AxisValue> axisValues;
    private LineChartData data;
    private List<PointValue> avgValues, minValues, maxValues;
    private Line avgLine,minLine, maxLine;
    private List<Line> lines;

    private int position;

    public static Intent createIntent(Context context, ArrayList<Unit> units, int key) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(UNITS_KEY, units);
        intent.putExtra(UNITS_POSITION, key);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        getData();

        titleTextView = findViewById(R.id.title);
        titleTextView.setText(units.get(0).getTitle() + getString(R.string._in_) + units.get(0).getSign());

        lineChartView = findViewById(R.id.chart);

        setupLineChart();


    }

    private void getData() {
        Intent intent = getIntent();
        List<Unit> unsortedUnits = (List<Unit>) intent.getSerializableExtra(UNITS_KEY);
        position = intent.getIntExtra(UNITS_POSITION, 0);
        units = new ArrayList<>();
        for (int i = unsortedUnits.size() -1; i >= 0; i--) {
            units.add(unsortedUnits.get(i));
        }
        position = units.size() - position - 1;
    }

    private void addPoints(int i) {
        minValues.add(new PointValue(i, (int) Math.round(units.get(i).getMin())));
        avgValues.add(new PointValue(i, (int) Math.round(units.get(i).getAvg())));
        maxValues.add(new PointValue(i, (int) Math.round(units.get(i).getMax())));
    }

    private void setLineLabels() {

        for(Line line : lines) {

            line.setHasLabels(true);
            line.setColor(Color.parseColor("#FFFFFF"));
            line.setPointRadius(0);
            emptyLabels(line.getValues());
            setLineNames();
            line.setPointColor(Color.parseColor("#000000AA"));
        }
    }
    private void emptyLabels(List<PointValue> values) {

        for(int i = 0; i < values.size(); i ++) {
            if(i != position) {
                values.get(i).setLabel("");

            }
        }
    }

    private void setLineNames() {

        if(position == 0) {
            int max = units.size() -1;
            minValues.get(max).setLabel(getString(R.string.min));
            avgValues.get(max).setLabel(getString(R.string.avg));
            maxValues.get(max).setLabel(getString(R.string.max));
        } else {
            minValues.get(0).setLabel(getString(R.string.min));
            avgValues.get(0).setLabel(getString(R.string.avg));
            maxValues.get(0).setLabel(getString(R.string.max));
        }

    }

    private void setupLineChart() {

        createLists();

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");

        for (int i = 0; i < units.size(); i++) {

            String date = formatter.format(units.get(i).getDate());
            axisValues.add(i, new AxisValue(i).setLabel(date));
            addPoints(i);
        }

        setLineLabels();
        setupAxis();
        lineChartView.setLineChartData(data);
    }

    private void createLists() {
        axisValues = new ArrayList<>();
        avgValues = new ArrayList<>();
        minValues = new ArrayList<>();
        maxValues = new ArrayList<>();

        avgLine = new Line(avgValues);
        minLine = new Line(minValues);
        maxLine = new Line(maxValues);
        lines = new ArrayList();
        data = new LineChartData();

        lines.add(maxLine);
        lines.add(avgLine);
        lines.add(minLine);
    }
    private void setupAxis() {

        data.setLines(lines);
        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

    }
}
