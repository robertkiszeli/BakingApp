package com.robertkiszelirk.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.robertkiszelirk.bakingapp.data.model.Ingredient;
import com.robertkiszelirk.bakingapp.data.model.Recipe;
import com.robertkiszelirk.bakingapp.ui.fragment.IngredientsAndStepsFragment;
import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.ui.fragment.StepFragment;

import java.util.ArrayList;

public class IngredientsAndSteps
        extends AppCompatActivity
        implements IngredientsAndStepsFragment.OnClickListener {

    private FragmentManager fragmentManager;

    private IngredientsAndStepsFragment ingredientsAndStepsFragment;

    private StepFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingridients_and_steps);

        ingredientsAndStepsFragment = new IngredientsAndStepsFragment();

        //Recipe recipe = getIntent().getExtras().getParcelable("recipedata");

        Recipe recipe = getIntent().getExtras().getParcelable("recipedata");

        Log.d("test","class: "+recipe.getName());

        ingredientsAndStepsFragment.setArguments(getIntent().getExtras());

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.main_container,ingredientsAndStepsFragment)
                .commit();



        if(findViewById(R.id.step_detail) != null){

            stepFragment = new StepFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.step_detail,stepFragment)
                    .commit();

        }

    }

    @Override
    public void textClicked() {

        Intent intent = new Intent(this, Step.class);

        startActivity(intent);
    }

}
