package com.example.hearthelper

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun clickConfirmDataButtonWhenEditTextFieldsAreFilled_navigateToResultActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.etAge)).perform(replaceText("1"))
        onView(withId(R.id.etCreatininePhosphokinase)).perform(replaceText("1"))
        onView(withId(R.id.etEjectionFraction)).perform(replaceText("1"))
        onView(withId(R.id.etPlatelets)).perform(replaceText("1"))

        onView(withId(R.id.scrollViewParameter)).perform(swipeUp()) // two other edittexts need to be seen

        onView(withId(R.id.etSerumCreatinine)).perform(replaceText("1"))
        onView(withId(R.id.etSerumSodium)).perform(replaceText("1"))


        onView(withId(R.id.btnConfirmData)).perform(click())

        intended(hasComponent(ResultActivity::class.java.name)) //check intent too

        onView(withId(R.id.tvDeathEventResult)).check(matches(isDisplayed())) //check if component from ResultActivity is displayed
    }


    @Test
    fun clickConfirmDataButtonWhenAnyEditTextFieldIsEmpty_showToastMessageAndStayInMainActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btnConfirmData)).perform(click())

        onView(withText(activityRule.activity.getString(R.string.toast_error_message))).inRoot(
            ToastMatcher()
        ).check(
            matches(isDisplayed())
        )

        onView(withId(R.id.btnConfirmData)).check(matches(isDisplayed()))
    }


    @After
    fun teardown() {
        Intents.release()
    }
}