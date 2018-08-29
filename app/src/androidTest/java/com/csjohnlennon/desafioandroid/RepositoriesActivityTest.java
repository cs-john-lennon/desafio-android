package com.csjohnlennon.desafioandroid;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.csjohnlennon.desafioandroid.mocks.Mocks;
import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.view.activity.RepositoriesActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RepositoriesActivityTest {

    MockWebServer server;

    @Rule
    public ActivityTestRule<RepositoriesActivity>
            mActivityRule = new ActivityTestRule<>(RepositoriesActivity.class, false, true);

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

        APIClient.URL_BASE = server.url("/search/").toString();

    }

    @Test
    public void whenOnClickAboutTheCard_shoudDisplayAnActivity() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SINGLE_SUCCESS_SEARCH_REPOSITORY));

        Intents.init();

        Matcher<Intent> matcher = allOf(hasComponent(PullActivity.class.getName()),
                hasExtraWithKey(PullActivity.REPOSITORY_KEY));

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.noresults_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_recycler_view)).check(matches(isDisplayed()));

        Instrumentation.ActivityResult
                result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        onView(withId(R.id.list_recycler_view)).perform(actionOnItemAtPosition(0, click()));

        intended(matcher);
        Intents.release();

    }

    @Test
    public void whenResultIsAnError_shouldDisplayAnEmptyList() {
        server.enqueue(new MockResponse().setResponseCode(400));

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.list_recycler_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void whenResultIsNotOk_shouldDisplayAnEmptyList() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SINGLE_FAIL_SEARCH_REPOSITORY));

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.list_recycler_view)).check(matches(not(isDisplayed())));
    }

}
