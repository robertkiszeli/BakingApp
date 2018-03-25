package com.robertkiszelirk.bakingapp.datatoui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Ingredient;
import com.robertkiszelirk.bakingapp.data.model.Recipe;
import com.robertkiszelirk.bakingapp.data.model.Step;
import com.robertkiszelirk.bakingapp.ui.activity.IngredientsAndSteps;

import java.util.ArrayList;
import java.util.List;

public class RecipeToRecyclerViewAdapter extends RecyclerView.Adapter<RecipeToRecyclerViewAdapter.ViewHolder> {

    private final Context context;

    private final ArrayList<Recipe> recipesList;

    public RecipeToRecyclerViewAdapter(Context context,ArrayList<Recipe> recipesList){
        this.context = context;
        this.recipesList = recipesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeToRecyclerViewAdapter.ViewHolder holder, int position) {

        RequestOptions options = new RequestOptions().frame(2500);
        Glide.with(context).asBitmap()
                .load(recipesList.get(position).getSteps().get(recipesList.get(position).getSteps().size() - 1).getVideoURL())
                .apply(options)
                .into(holder.recipeImage);

        holder.recipeName.setText(recipesList.get(position).getName());
        holder.servingsNumber.setText(String.valueOf(recipesList.get(position).getServings()));
        holder.ingredientsNumber.setText(String.valueOf(recipesList.get(position).getIngredients().size()));
        holder.stepsNumber.setText(String.valueOf(recipesList.get(position).getSteps().size()));

    }

    @Override
    public int getItemCount() {

        return recipesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView recipeImage;

        final AppCompatTextView recipeName;
        final AppCompatTextView servingsNumber;
        final AppCompatTextView ingredientsNumber;
        final AppCompatTextView stepsNumber;

        ViewHolder(final View itemView) {
            super(itemView);

            recipeImage = itemView.findViewById(R.id.recipe_image);

            recipeName = itemView.findViewById(R.id.recipe_name);
            servingsNumber = itemView.findViewById(R.id.servings_number);
            ingredientsNumber = itemView.findViewById(R.id.ingredients_number);
            stepsNumber = itemView.findViewById(R.id.steps_number);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), IngredientsAndSteps.class);
                    //MovieData currentMovieDetails = recipesList.get(getAdapterPosition());
                    // Add actual movie data to inten
                    Recipe recipe = recipesList.get(getAdapterPosition());
                    ArrayList<Step> stepList = recipesList.get(getAdapterPosition()).getSteps();
                    Log.d("test","adapter : "+recipe.getName());
                    intent.putExtra("recipedata", recipe);
                    // Set animation between the two activity
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) view.getContext(), posterImage, "animated");
                    // Start MovieDetails activity
                    v.getContext().startActivity(intent);
                }
            });

//            posterImage = itemView.findViewById(R.id.movies_list_movie_poster);
//            // Set click listener to the images
//            posterImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Check internet connection
//                    if(checkInternetConnection()) {
//                        Intent intent = new Intent(view.getContext(), MovieDetails.class);
//                        MovieData currentMovieDetails = recipesList.get(getAdapterPosition());
//                        // Add actual movie data to intent
//                        intent.putExtra(view.getContext().getString(R.string.passing_movie_parcelable_key), currentMovieDetails);
//                        // Set animation between the two activity
//                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) view.getContext(), posterImage, "animated");
//                        // Start MovieDetails activity
//                        view.getContext().startActivity(intent, options.toBundle());
//                    }else{
//                        Toast.makeText(context,"No internet connection.",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

