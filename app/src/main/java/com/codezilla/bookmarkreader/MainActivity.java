package com.codezilla.bookmarkreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.codezilla.bookmarkreader.domainmodel.WebUnitContentUpdater;
import com.codezilla.bookmarkreader.history.HistoryView;
import com.codezilla.bookmarkreader.menu.INavigator;
import com.codezilla.bookmarkreader.sync.BackgroundUpdaterTask;
import com.codezilla.bookmarkreader.sync.MyJopScheduler;
import com.codezilla.bookmarkreader.weblist.WebListView;


public class MainActivity extends FragmentActivity {
    public static final String NAVIGATION_CONTENT_DESCRIPTION = "Open Settings View";
    public static final String TAG_WEBLIST_FRAGMENT = "WebListFragment";
    private static final String TAG_HISTORY_VIEW_FRAGMENT = "historyViewFragment";
    private static final String TAG_SETTINGS_FRAGMENT = "settingsFragment";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    INavigator navigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle("Bookmark Reader");
        this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        this.navigator = new Navigator(getSupportFragmentManager() , toggle , drawerLayout , this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_container , new SettingsFragment(navigator) , TAG_SETTINGS_FRAGMENT)
                .commit();
        this.navigator.showHome();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        toggle.syncState();
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        new MyJopScheduler(getApplicationContext()).cancel();
        super.onResume();
    }

    @Override
    protected void onPause() {
        new MyJopScheduler(getApplicationContext()).schedule();
        super.onPause();
    }

    static class Navigator implements INavigator
    {

        private final ActionBarDrawerToggle toggle;
        private final DrawerLayout drawerLayout;
        private final Context context;
        FragmentManager fragmentManager;

        public Navigator(FragmentManager fragmentManager, ActionBarDrawerToggle toggle, DrawerLayout drawerLayout , Context context) {
            this.fragmentManager = fragmentManager;
            this.toggle = toggle;
            this.drawerLayout = drawerLayout;
            this.context  = context;
        }

        @Override
        public void showHistory() {

            Fragment fragment = fragmentManager.findFragmentById(R.id.main_view_content);
            if (isRequireOpen(fragment , HistoryView.class))
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_view_content, new HistoryView(), TAG_HISTORY_VIEW_FRAGMENT)
                .addToBackStack(null)
                        .commit();
            drawerLayout.closeDrawers();
        }

        private boolean isRequireOpen(Fragment currenntFragment , Class clazz) {
            return currenntFragment == null || !currenntFragment.getClass().equals(clazz);
        }

        @Override
        public void showHome() {
            Fragment fragment =  fragmentManager.findFragmentById(R.id.main_view_content);
            if(isRequireOpen(fragment , WebListView.class))
            {
                fragmentManager.beginTransaction()
                        .replace(R.id.main_view_content , new WebListView() , TAG_WEBLIST_FRAGMENT)
                        .commit();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            drawerLayout.closeDrawers();
        }

        @Override
        public void refresh()
        {
            BackgroundUpdaterTask backgroundUpdaterTask = new BackgroundUpdaterTask(context)
            {
                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    showHome();
                }
            };
            backgroundUpdaterTask.execute();
        }
    }
}
