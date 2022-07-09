package com.dicoding.todoapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.todoapp.R
import org.junit.Rule
import org.junit.Test

class TaskActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(TaskActivity::class.java)

    @Test
    fun taskRecyclerViewShouldDisplayed() {
        onView(withId(R.id.rv_task)).check(matches(isDisplayed()))
    }

    @Test
    fun clickFloatingActionButtonAndShouldNavigateToAddTaskActivity() {
        onView(withId(R.id.fab)).perform(click())
    }

}