package com.robertkiszelirk.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Recipe;
import com.robertkiszelirk.bakingapp.datatoui.StepToRecyclerViewAdapter;
import com.robertkiszelirk.bakingapp.datatoui.WidgetService;
import com.robertkiszelirk.bakingapp.ui.fragment.IngredientsAndStepsFragment;
import com.robertkiszelirk.bakingapp.ui.fragment.StepFragment;

import butterknife.ButterKnife;

public class IngredientsAndSteps extends AppCompatActivity implements StepToRecyclerViewAdapter.ChangeStep {

    public static Boolean twoFragment;
    private FragmentManager fragmentManager;
    private StepFragment stepFragment;
    private Recipe recipe;
    private int currentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingridients_and_steps);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        // Check if screen is large enough for two frame
        if (findViewById(R.id.step_detail) != null) {

            // Get Recipe
            if (getIntent().getExtras() != null) {
                recipe = getIntent().getExtras().getParcelable(this.getString(R.string.pass_recipe_in_intent));
            }

            // Get saved current step
            if (savedInstanceState != null) {
                currentStep = savedInstanceState.getInt(getString(R.string.ingredient_and_steps_current_step));
            }

            // Create Step fragment
            stepFragment = new StepFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.pass_step_data_to_fragment_bundle), recipe.getSteps().get(currentStep));

            stepFragment.setArguments(bundle);

            if (fragmentManager.findFragmentByTag("step") == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail, stepFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.step_detail, stepFragment)
                        .commit();
            }

            twoFragment = true;
        } else {
            twoFragment = false;
        }

        // Handle screen orientation
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {

                recipe = getIntent().getExtras().getParcelable(this.getString(R.string.pass_recipe_in_intent));

                IngredientsAndStepsFragment ingredientsAndStepsFragment = new IngredientsAndStepsFragment();

                Bundle bundle = new Bundle();

                bundle.putParcelable(getString(R.string.pass_recipe_in_intent), recipe);

                ingredientsAndStepsFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .add(R.id.main_container, ingredientsAndStepsFragment)
                        .commit();
            } else {
                Toast.makeText(this, R.string.no_passed_data, Toast.LENGTH_SHORT).show();
            }
        } else {

            if (getIntent().getExtras() != null) {
                recipe = savedInstanceState.getParcelable(getString(R.string.instance_state_save_pass_recipe));
            }
        }

        // Change title to selected recipe name
        setTitle(recipe.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_to_widget_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_to_widget:
                WidgetService.startActionUpdateWidget(this, recipe);
                Toast.makeText(
                        this,
                        recipe.getName() + "\ningredients added to the widget.",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(getString(R.string.instance_state_save_pass_recipe), recipe);
        outState.putInt(getString(R.string.ingredient_and_steps_current_step), currentStep);

        // Remove step detail fragment on orientation change
        Fragment fragment = fragmentManager.findFragmentById(R.id.step_detail);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        super.onSaveInstanceState(outState);
    }

    // Handle the fragment change in stepFragment from steps list
    @Override
    public void whenTowFragment(int position) {

        fragmentManager = getSupportFragmentManager();

        stepFragment = new StepFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.pass_step_data_to_fragment_bundle), recipe.getSteps().get(position));

        stepFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.step_detail, stepFragment)
                .commit();
    }

    // To save current selected step
    @Override
    public void passSelectedStep(int position) {
        currentStep = position;
    }


}
