package com.codewithdevesh.internshipassignment.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.codewithdevesh.internshipassignment.databinding.FragmentTab2Binding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Tab2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTab2Binding binding = FragmentTab2Binding.inflate(inflater, container, false);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i=4;i>=1;i--){
            float val = (float) (i*10.0);
            PieEntry entry = new PieEntry(val,i);
            entries.add(entry);
        }
        PieDataSet dataSet = new PieDataSet(entries,"Data");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        binding.pieChart.setData(new PieData(dataSet));
        binding.pieChart.animateXY(3000,3000);
        binding.pieChart.getDescription().setEnabled(false);
        return binding.getRoot();
    }
}