package com.robertkiszelirk.bakingapp.datatoui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.robertkiszelirk.bakingapp.data.model.Step;

import java.util.ArrayList;

public class StepToRecyclerView {

    private Context context;

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    public StepToRecyclerView(Context context, RecyclerView recyclerView, ProgressBar progressBar) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
    }

    public void loadSteps(ArrayList<Step> stepList,String recipeName) {

        StepToRecyclerViewAdapter recyclerAdapter = new StepToRecyclerViewAdapter(context, stepList, recipeName);

        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
