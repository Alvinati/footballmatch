package id.co.mine.footballclub

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v7.widget.RecyclerView
import id.co.mine.footballclub.R.id.*
import id.co.mine.footballclub.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.swipeDown
import android.view.View
import org.hamcrest.Matcher
import id.co.mine.footballclub.util.EspressoIdlingResource
import org.junit.Before






@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    var idlingResource: EspressoIdlingResource = EspressoIdlingResource()

    @Test
    fun testAppBehaviour(){

        IdlingRegistry.getInstance().register(idlingResource.getIdlingResource())

        onView(withId(bottom_navigation)).check(matches(isDisplayed()))
        onView(withId(next_match)).perform(click())
        onView(withId(favorites)).perform(click())
        onView(withId(last_match)).perform(click())


        onView(withId(list_events))
                .check(matches(isDisplayed()))

        onView(withId(list_events)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.swipe))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
        onView(withId(list_events)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click()))
        onView(withId(layout_detail)).check(matches(isDisplayed()))

        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()))
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorites)).perform(click())
        onView(withId(list_fav)).check(matches(isDisplayed()))
        onView(withId(list_fav)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withId(android.support.design.R.id.snackbar_text)).check(matches(isDisplayed()))
        onView(withText("Removed from favorite")).check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource.getIdlingResource())
    }



    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController, view: View) {
                action.perform(uiController, view)
            }
        }
    }

}

