package com.robertkiszelirk.bakingapp;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robertkiszelirk.bakingapp.ui.activity.BakingAppMain;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingAppTest {

    @Rule public ActivityTestRule<BakingAppMain> activityTestRule
            = new ActivityTestRule<>(BakingAppMain.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getSimpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

    }

    @Test
    public void checkRecipeName_RecipeRecyclerView(){
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerItemClick_OpenIngredientAndSteps(){
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(3))
                .perform(click());
        onView(withId(R.id.select_ingredients_list)).perform(click());
     }

     @Test
     public void ingredientsAndStepsStepClick_OpensStepDetail(){
         onView(withId(R.id.recipes_recycler_view))
                 .perform(RecyclerViewActions.scrollToPosition(3))
                 .perform(click());
         onView(withId(R.id.select_steps_list)).perform(click());
         onView(allOf(withId(R.id.ingredients_and_steps_list)))
                 .perform(RecyclerViewActions.scrollToPosition(10))
                 .perform(click());
         onView(withId(R.id.step_description)).check(matches(isDisplayed()));
     }

    @Test
    public void ingredientsAndStep_OpensInlLandscape(){
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(1))
                .perform(click());
        onView(withId(R.id.select_steps_list)).perform(click());
        onView(allOf(withId(R.id.ingredients_and_steps_list)))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(click());
        onView(withId(R.id.step_video)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
