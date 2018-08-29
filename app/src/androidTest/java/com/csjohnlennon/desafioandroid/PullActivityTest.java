package com.csjohnlennon.desafioandroid;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.csjohnlennon.desafioandroid.mocks.Mocks;
import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.model.Repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(JUnit4.class)
public class PullActivityTest {

    MockWebServer server;

    @Rule
    public ActivityTestRule<PullActivity>
            mActivityRule = new ActivityTestRule<>(PullActivity.class, false, false);

    @Before
    public void setUp() throws Exception {
        server         = new MockWebServer();
        server.start();

        APIClient.URL_BASE = server.url("/repos/").toString();

    }

    @Test
    public void whenResultIsOk_shouldDisplayListWithRepositories() {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(Mocks.SUCCESS_SEARCH_PULLS));

        Owner owner = new Owner();
        owner.setLogin("iluwatar");
        owner.setAvatarUrl("https://avatars1.githubusercontent.com/u/582346?v=4");

        Repository repository = new Repository();
        repository.setName("java-design-patterns");
        repository.setDescription("Design patterns implemented in Java");
        repository.setForks(10735);
        repository.setStargazers_count(33238);

        Intent intent = new Intent();
        intent.putExtra(PullActivity.REPOSITORY_KEY, repository);
        intent.putExtra(PullActivity.OWNER_KEY, owner);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.noresults_text_view)).check(matches(not(isDisplayed())));
        onView(withId(R.id.list_recycler_view)).check(matches(isDisplayed()));

    }


    @Test
    public void whenResultIsNotOk_shouldDisplayAnEmptyList() {
        server.enqueue(new MockResponse().setResponseCode(400).setBody(Mocks.FAIL_SEARCH_PULLS));

        Owner owner = new Owner();
        owner.setLogin("iluwatar");
        owner.setAvatarUrl("https://avatars1.githubusercontent.com/u/582346?v=4");

        Repository repository = new Repository();
        repository.setName("java-design-patterns");
        repository.setDescription("Design patterns implemented in Java");
        repository.setForks(10735);
        repository.setStargazers_count(33238);

        Intent intent = new Intent();
        intent.putExtra(PullActivity.REPOSITORY_KEY, repository);
        intent.putExtra(PullActivity.OWNER_KEY, owner);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.noresults_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.list_recycler_view)).check(matches(not(isDisplayed())));

    }

}
