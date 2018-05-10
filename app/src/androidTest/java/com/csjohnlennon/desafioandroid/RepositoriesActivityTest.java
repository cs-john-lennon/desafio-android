package com.csjohnlennon.desafioandroid;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.csjohnlennon.desafioandroid.mocks.Mocks;
import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.ui.activity.PullActivity;
import com.csjohnlennon.desafioandroid.ui.activity.RepositoriesActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        server         = new MockWebServer();
        server.start();

        APIClient.URL_BASE = "http://" + server.getHostName() + ":" + server.getPort() + "";
        server.url(APIClient.URL_BASE + "search/repositories?q=language:Java&sort=stars&page=1");

    }

    @Test
    public void whenResultIsOk_shouldDisplayListWithRepositories() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SINGLE_SUCCESS_SEARCH_REPOSITORY));

        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().loadPage(0);
        onView(withId(R.id.noresults_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.repositories_list_recycler_view)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvName), withText("java-design-patterns"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvDescription), withText("Design patterns implemented in Java"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvForksCount), withText("10735"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvStargazersCount), withText("33238"))).check(matches(isDisplayed()));
    }

    @Test
    public void whenResultIsNotOk_shouldDisplayAnEmptyList() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SINGLE_FAIL_SEARCH_REPOSITORY));

        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().loadPage(0);
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.repositories_list_recycler_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void whenResultIsFail_shouldDisplayAnErrorMessage() {
        server.enqueue(new MockResponse().setResponseCode(400).setBody(Mocks.SINGLE_FAIL_SEARCH_REPOSITORY));

        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().loadPage(0);
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.repositories_list_recycler_view)).check(matches(not(isDisplayed())));
    }

    @Test
    public void whenOnClickAboutTheCard_shoudDisplayAnActivity() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SINGLE_SUCCESS_SEARCH_REPOSITORY));

        Intents.init();

        Matcher<Intent> matcher = allOf(
                hasComponent(PullActivity.class.getName())
        );

        mActivityRule.launchActivity(new Intent());
        mActivityRule.getActivity().loadPage(0);
        onView(withId(R.id.noresults_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
        onView(withId(R.id.repositories_list_recycler_view)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvName), withText("java-design-patterns"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvDescription), withText("Design patterns implemented in Java"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvForksCount), withText("10735"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.tvStargazersCount), withText("33238"))).check(matches(isDisplayed()));

        Instrumentation.ActivityResult
                result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        intending(matcher).respondWith(result);

        onView(withId(R.id.repositories_list_recycler_view)).perform(actionOnItemAtPosition(0, click()));

        intended(matcher);
        Intents.release();

    }


}
