package com.robertkiszelirk.bakingapp.datatoui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Step;
import com.robertkiszelirk.bakingapp.ui.activity.IngredientsAndSteps;
import com.robertkiszelirk.bakingapp.ui.activity.ShowStep;

import java.util.ArrayList;

public class StepToRecyclerViewAdapter extends RecyclerView.Adapter<StepToRecyclerViewAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<Step> stepList;

    private final String recipeName;

    private ChangeStep changeStep;

    StepToRecyclerViewAdapter(Context context, ArrayList<Step> stepList, String recipeName) {
        this.context = context;
        this.stepList = stepList;
        this.recipeName = recipeName;
    }

    @NonNull
    @Override
    public StepToRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);

        return new StepToRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepToRecyclerViewAdapter.ViewHolder holder, int position) {

        String string = String.valueOf(stepList.get(position).getId()) +
                " " +
                stepList.get(position).getShortDescription();

        holder.stepDescription.setText(string);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        try {
            changeStep = (ChangeStep) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ChangeStep");
        }
    }

    public interface ChangeStep {
        void whenTowFragment(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final AppCompatButton stepDescription;

        ViewHolder(final View itemView) {
            super(itemView);

            stepDescription = itemView.findViewById(R.id.step_description);

            stepDescription.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (IngredientsAndSteps.twoFragment) {
                        changeStep.whenTowFragment(getAdapterPosition());
                    } else {
                        Intent intent = new Intent(v.getContext(), ShowStep.class);
                        intent.putExtra(v.getContext().getString(R.string.pass_step_data_to_fragment_bundle), stepList.get(getAdapterPosition()));
                        intent.putExtra("recipeName",recipeName);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
