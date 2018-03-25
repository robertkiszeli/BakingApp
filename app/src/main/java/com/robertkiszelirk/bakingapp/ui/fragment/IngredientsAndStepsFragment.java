package com.robertkiszelirk.bakingapp.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Ingredient;
import com.robertkiszelirk.bakingapp.data.model.Recipe;

import java.util.ArrayList;

public class IngredientsAndStepsFragment extends Fragment {

    public IngredientsAndStepsFragment(){ }

    OnClickListener showStep;

    public interface OnClickListener{
        void textClicked();
    }

    public void onAttach(Context context){
        super.onAttach(context);

        try{
            showStep = (OnClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + "must implement showStep");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Recipe recipe = getArguments().getParcelable("recipedata");

        View rootView = inflater.inflate(R.layout.fragment_ingredients_and_steps, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.ingredients_and_steps);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            textView.setText("Ingredients and steps landscape");
        }else{
            textView.setText(recipe.getName());
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStep.textClicked();
            }
        });

        return rootView;
    }
}
