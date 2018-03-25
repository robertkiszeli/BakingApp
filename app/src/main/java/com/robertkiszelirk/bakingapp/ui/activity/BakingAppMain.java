package com.robertkiszelirk.bakingapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.datatoui.RecipeToRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingAppMain extends AppCompatActivity {

    @BindView(R.id.recipe_load_progress)
    ProgressBar progressBar;

    @BindView(R.id.recipes_recycler_view)
    RecyclerView recyclerView;

    RecipeToRecyclerView recipeToRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_app_main);

        ButterKnife.bind(this);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        // Set RecyclerView layout,columns
        setRecyclerView();

        // Create Recipe list handle
        recipeToRecyclerView = new RecipeToRecyclerView(this,recyclerView);

        // Load recipes to RecyclerView
        recipeToRecyclerView.loadRecipes(progressBar);

    }

    // Set RecyclerView grid layout
    private void setRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns());

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(gridLayoutManager);

    }

    // Set RecyclerView columns number
    private int numberOfColumns() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthDivider;
        widthDivider = 600;

        int width = displayMetrics.widthPixels;

        return width / widthDivider;
    }

}
