package com.robertkiszelirk.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.robertkiszelirk.bakingapp.R;
import com.robertkiszelirk.bakingapp.data.model.Recipe;
import com.robertkiszelirk.bakingapp.datatoui.WidgetListViewService;
import com.robertkiszelirk.bakingapp.datatoui.WidgetService;
import com.robertkiszelirk.bakingapp.ui.activity.BakingAppMain;

public class BakingAppWidget extends AppWidgetProvider {

    public static Recipe recipe = null;

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        if (recipe != null) {
            views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
            Intent listIntent = new Intent(context, WidgetListViewService.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipe", recipe);
            listIntent.putExtra("ingredientlist", bundle);
            listIntent.setData(Uri.parse(listIntent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_ingredient_list, listIntent);

        }

        Intent intent = new Intent(context, BakingAppMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_recipe_name, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }


    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getApplicationContext(), BakingAppWidget.class));

        final String action = intent.getAction();

        if (action != null && action.equals(WidgetService.UPDATE_INGREDIENTS_LIST)) {

            if (intent.getExtras() != null) {
                recipe = intent.getExtras().getParcelable("recipeData");
            } else {
                Log.e(context.getPackageName(), "Recipe is null");
            }
            onUpdate(context, appWidgetManager, appWidgetIds);
        }else{
            Log.e(context.getPackageName(),"No passed data\n"+action);
        }

        super.onReceive(context, intent);
    }
}

