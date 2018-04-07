package com.robertkiszelirk.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.ui.fragment.StepFragment;

public class ShowStep extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if(getIntent().getExtras() != null) {
                String recipeName = getIntent().getExtras().getString("recipeName");
                getSupportActionBar().setTitle(recipeName);
            }
        }

        if(savedInstanceState == null) {
            // Create and add or replace step fragment
            StepFragment stepFragment = new StepFragment();

            stepFragment.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragmentManager.findFragmentById(R.id.step_detail_container) == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepFragment)
                        .commit();
            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
