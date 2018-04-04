package com.robertkiszelirk.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.ui.fragment.StepFragment;

public class ShowStep extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step);

        // Create and add or replace step fragment
        StepFragment stepFragment = new StepFragment();

        stepFragment.setArguments(getIntent().getExtras());

        fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.main_container) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, stepFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, stepFragment)
                    .commit();
        }

    }

    @Override
    protected void onPause() {
        // Remove fragment
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Finish activity to switch to ingredientsandsteps fragment
        finish();
        super.onSaveInstanceState(outState);

    }
}
