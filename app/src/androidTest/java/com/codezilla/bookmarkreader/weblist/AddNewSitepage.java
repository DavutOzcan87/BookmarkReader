package com.codezilla.bookmarkreader.weblist;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.widget.EditText;

import com.codezilla.bookmarkreader.R;

import java.util.concurrent.CompletionService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by davut on 8/29/2017.
 */

public class AddNewSitepage {
    public AddNewSitepage enter(String url) {
        onView(ViewMatchers.withId(R.id.txtUrl)).perform(ViewActions.typeText(url));
        return this;
    }

    public AddNewSitepage submit() {
        onView(withText(R.string.submit)).perform(click());
        return this;
    }

    public void assertVisible() {
        onView( withText(R.string.add_new)).check(matches(ViewMatchers.isDisplayed()));
    }

    public AddNewSitepage cancel()
    {
        onView(withText(R.string.cancel)).perform(click());
        return this;
    }
}
