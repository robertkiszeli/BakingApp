package com.robertkiszelirk.bakingapp.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.idlingresource.SimpleIdlingResource;
import com.robertkiszelirk.bakingapp.datatoui.RecipeToRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingAppMain extends AppCompatActivity {

    @BindView(R.id.recipe_load_progress)
    ProgressBar progressBar;

    @BindView(R.id.recipes_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.no_internet_connection_text)
    TextView noConnection;

    RecipeToRecyclerView recipeToRecyclerView;

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baking_app_main);

        ButterKnife.bind(this);

        setRecipes();
    }

    private void setRecipes() {

        if (checkInternetConnection()) {

            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

            noConnection.setVisibility(View.GONE);

            // Set RecyclerView layout,columns
            setRecyclerView();

            simpleIdlingResource = getSimpleIdlingResource();
            simpleIdlingResource.setIdleState(false);

            // Create Recipe list handle
            recipeToRecyclerView = new RecipeToRecyclerView(this, recyclerView, progressBar, simpleIdlingResource);

            // Load recipes to RecyclerView
            recipeToRecyclerView.loadRecipes();

        } else {

            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);

            noConnection.setVisibility(View.VISIBLE);
        }

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

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getSimpleIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setRecipes();
    }
}
