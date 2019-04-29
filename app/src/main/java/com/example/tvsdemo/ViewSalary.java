package com.example.tvsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class ViewSalary extends AppCompatActivity {

    private static final String TAG = ViewSalary.class.getSimpleName();
    private ArrayList<EmpDetails> empDetails = new ArrayList<>();
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salary);

        barChart = findViewById(R.id.salary_bargraph);

        Intent salaryDetails = getIntent();
        empDetails = salaryDetails.getExtras().getParcelableArrayList("showSalary");
        Log.v(TAG, String.valueOf(empDetails.size()));

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String temp = empDetails.get(i).getEmpSalary().replace("$", "");
            temp = temp.replace(",", "");
            float val = Float.valueOf(temp);
            barEntries.add(new BarEntry(i, val));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Salary");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        barChart.setTouchEnabled(false);
        barChart.setData(data);
    }
}
