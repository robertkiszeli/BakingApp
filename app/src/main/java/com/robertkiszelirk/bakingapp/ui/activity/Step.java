package com.robertkiszelirk.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.ui.fragment.StepFragment;

public class Step extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private StepFragment stepFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step);

        stepFragment = new StepFragment();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_container,stepFragment)
                .commit();

    }
}
