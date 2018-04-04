package com.robertkiszelirk.bakingapp.datatoui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.idlingresource.SimpleIdlingResource;
import com.robertkiszelirk.bakingapp.data.model.Recipe;
import com.robertkiszelirk.bakingapp.ui.activity.IngredientsAndSteps;

import java.util.ArrayList;

public class RecipeToRecyclerViewAdapter extends RecyclerView.Adapter<RecipeToRecyclerViewAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<Recipe> recipesList;

    private SimpleIdlingResource simpleIdlingResource;

    RecipeToRecyclerViewAdapter(Context context, ArrayList<Recipe> recipesList, SimpleIdlingResource simpleIdlingResource) {
        this.context = context;
        this.recipesList = recipesList;
        this.simpleIdlingResource = simpleIdlingResource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeToRecyclerViewAdapter.ViewHolder holder, int position) {

        RequestOptions options = new RequestOptions().frame(2500);
        Glide.with(context).asBitmap()
                .load(recipesList.get(position).getSteps().get(recipesList.get(position).getSteps().size() - 1).getVideoURL())
                .apply(options)
                .into(holder.recipeImage);

        holder.recipeName.setText(recipesList.get(position).getName());
        holder.servingsNumber.setText(String.valueOf(recipesList.get(position).getServings()));
        holder.ingredientsNumber.setText(String.valueOf(recipesList.get(position).getIngredients().size()));
        holder.stepsNumber.setText(String.valueOf(recipesList.get(position).getSteps().size()));

        if(recipesList.get(position).getName().equals("Brownies")){
            if(simpleIdlingResource != null){
                simpleIdlingResource.setIdleState(true);
            }
        }
    }

    @Override
    public int getItemCount() {

        return recipesList.size();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final CardView cardView;

        final ImageView recipeImage;

        final AppCompatTextView recipeName;
        final AppCompatTextView servingsNumber;
        final AppCompatTextView ingredientsNumber;
        final AppCompatTextView stepsNumber;

        ViewHolder(final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.recipe_card_view);

            recipeImage = itemView.findViewById(R.id.recipe_image);

            recipeName = itemView.findViewById(R.id.recipe_name);
            servingsNumber = itemView.findViewById(R.id.servings_number);
            ingredientsNumber = itemView.findViewById(R.id.ingredients_number);
            stepsNumber = itemView.findViewById(R.id.steps_number);

            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (checkInternetConnection()) {
                        Intent intent = new Intent(v.getContext(), IngredientsAndSteps.class);
                        Recipe recipe = recipesList.get(getAdapterPosition());
                        intent.putExtra(v.getContext().getString(R.string.pass_recipe_in_intent), recipe);
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), v.getContext().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

