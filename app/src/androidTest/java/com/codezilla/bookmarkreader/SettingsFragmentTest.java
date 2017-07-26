package com.codezilla.bookmarkreader;

import android.app.Application;
import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.codezilla.bookmarkreader.application.BookmarkReaderApplication;
import com.codezilla.bookmarkreader.login.IUserService;
import com.codezilla.bookmarkreader.login.User;

import net.bytebuddy.matcher.StringMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.*;

/**
 * Created by davut on 7/24/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {
    ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class , true , false);
    @Mock
    IUserService userService;
    User user = new User("testName","testSurname");
    @Before
    public void initTests()
    {
        MockitoAnnotations.initMocks(this);
        Mockito.when(userService.getLastLoginedUser()).thenReturn(user);
        BookmarkReaderApplication.getInstance().setUserService(userService);
    }

    @After
    public void destroyTests()
    {
    }

    @Test
    public void shouldDisplayUserName() throws InterruptedException {
        launch();
        onView(withContentDescription(R.string.app_name)).perform(click());
        onView(withText("testName testSurname")).check(ViewAssertions.matches(isDisplayed()));
    }

    private void launch() {
        mainActivityActivityTestRule.launchActivity(new Intent());
    }
}