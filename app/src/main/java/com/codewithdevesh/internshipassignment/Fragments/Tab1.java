package com.codewithdevesh.internshipassignment.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.codewithdevesh.internshipassignment.R;
import com.codewithdevesh.internshipassignment.databinding.FragmentTab1Binding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Tab1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTab1Binding binding = FragmentTab1Binding.inflate(inflater, container, false);
        String[] months_31 = new String[]{"January","March","May","July","August","October","December"};
        String[] months_30 = new String[]{"April","June","September","November"};
        ArrayList<String>list = new ArrayList<>();
        list.add("January");
        list.add("February");
        list.add("March");
        list.add("April");
        list.add("May");
        list.add("June");
        list.add("July");
        list.add("August");
        list.add("September");
        list.add("October");
        list.add("November");
        list.add("December");
        ArrayAdapter<String>adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()   {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String month = adapterView.getItemAtPosition(i).toString();
                for(String x:months_30){
                    if(x.equals(month)){
                        LineDataSet dataSet = new LineDataSet(chartData_30(),"Graph");
                        ArrayList<LineDataSet>entries = new ArrayList<>();
                        entries.add(dataSet);
                        binding.lineChart.setData(new LineData(dataSet));
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        binding.lineChart.setData(new LineData(dataSet));
                        binding.lineChart.animateXY(3000,3000);
                        binding.lineChart.getDescription().setEnabled(false);
                        break;
                    }
                }
                for(String x:months_31){
                    if(x.equals(month)){
                        LineDataSet dataSet = new LineDataSet(chartData_31(),"Graph");
                        ArrayList<LineDataSet>entries = new ArrayList<>();
                        entries.add(dataSet);
                        binding.lineChart.setData(new LineData(dataSet));
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        binding.lineChart.setData(new LineData(dataSet));
                        binding.lineChart.animateXY(3000,3000);
                        binding.lineChart.getDescription().setEnabled(false);
                        break;
                    }
                }
                if(month.equals("February")){
                    LineDataSet dataSet = new LineDataSet(chartData_28(),"Graph");
                    ArrayList<LineDataSet>entries = new ArrayList<>();
                    entries.add(dataSet);
                    binding.lineChart.setData(new LineData(dataSet));
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    binding.lineChart.setData(new LineData(dataSet));
                    binding.lineChart.animateXY(3000,3000);
                    binding.lineChart.getDescription().setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.lineChart.setNoDataText("No data available");
            }
        });
        binding.lineChart.setNoDataText("No data available");
        return binding.getRoot();
    }

    private ArrayList<Entry> chartData_30(){
        ArrayList<Entry>dataset = new ArrayList<>();
        dataset.add(new Entry(1,20));
        dataset.add(new Entry(2,30));
        dataset.add(new Entry(3,40));
        dataset.add(new Entry(4,50));
        dataset.add(new Entry(5,20));
        dataset.add(new Entry(6,20));
        dataset.add(new Entry(7,50));
        dataset.add(new Entry(8,20));
        dataset.add(new Entry(9,70));
        dataset.add(new Entry(10,20));
        dataset.add(new Entry(11,10));
        dataset.add(new Entry(12,20));
        dataset.add(new Entry(13,40));
        dataset.add(new Entry(14,60));
        dataset.add(new Entry(15,80));
        dataset.add(new Entry(16,50));
        dataset.add(new Entry(17,20));
        dataset.add(new Entry(18,20));
        dataset.add(new Entry(19,20));
        dataset.add(new Entry(20,20));
        dataset.add(new Entry(21,20));
        dataset.add(new Entry(22,70));
        dataset.add(new Entry(23,20));
        dataset.add(new Entry(24,20));
        dataset.add(new Entry(25,20));
        dataset.add(new Entry(26,90));
        dataset.add(new Entry(27,20));
        dataset.add(new Entry(28,20));
        dataset.add(new Entry(29,100));
        dataset.add(new Entry(30,20));
        return dataset;
    }
    private ArrayList<Entry> chartData_31(){
        ArrayList<Entry>dataset = new ArrayList<>();
        dataset.add(new Entry(1,20));
        dataset.add(new Entry(2,30));
        dataset.add(new Entry(3,40));
        dataset.add(new Entry(4,50));
        dataset.add(new Entry(5,20));
        dataset.add(new Entry(6,20));
        dataset.add(new Entry(7,50));
        dataset.add(new Entry(8,20));
        dataset.add(new Entry(9,70));
        dataset.add(new Entry(10,20));
        dataset.add(new Entry(11,10));
        dataset.add(new Entry(12,20));
        dataset.add(new Entry(13,40));
        dataset.add(new Entry(14,60));
        dataset.add(new Entry(15,80));
        dataset.add(new Entry(16,50));
        dataset.add(new Entry(17,20));
        dataset.add(new Entry(18,20));
        dataset.add(new Entry(19,20));
        dataset.add(new Entry(20,20));
        dataset.add(new Entry(21,20));
        dataset.add(new Entry(22,70));
        dataset.add(new Entry(23,20));
        dataset.add(new Entry(24,20));
        dataset.add(new Entry(25,20));
        dataset.add(new Entry(26,90));
        dataset.add(new Entry(27,20));
        dataset.add(new Entry(28,20));
        dataset.add(new Entry(29,100));
        dataset.add(new Entry(30,20));
        dataset.add(new Entry(31,20));
        return dataset;
    }
    private ArrayList<Entry> chartData_28(){
        ArrayList<Entry>dataset = new ArrayList<>();
        dataset.add(new Entry(1,20));
        dataset.add(new Entry(2,30));
        dataset.add(new Entry(3,40));
        dataset.add(new Entry(4,50));
        dataset.add(new Entry(5,20));
        dataset.add(new Entry(6,20));
        dataset.add(new Entry(7,50));
        dataset.add(new Entry(8,20));
        dataset.add(new Entry(9,70));
        dataset.add(new Entry(10,20));
        dataset.add(new Entry(11,10));
        dataset.add(new Entry(12,20));
        dataset.add(new Entry(13,40));
        dataset.add(new Entry(14,60));
        dataset.add(new Entry(15,80));
        dataset.add(new Entry(16,50));
        dataset.add(new Entry(17,20));
        dataset.add(new Entry(18,20));
        dataset.add(new Entry(19,20));
        dataset.add(new Entry(20,20));
        dataset.add(new Entry(21,20));
        dataset.add(new Entry(22,70));
        dataset.add(new Entry(23,20));
        dataset.add(new Entry(24,20));
        dataset.add(new Entry(25,20));
        dataset.add(new Entry(26,90));
        dataset.add(new Entry(27,20));
        dataset.add(new Entry(28,20));
        return dataset;
    }
}