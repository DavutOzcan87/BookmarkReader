package com.davutozcan.bookmarkreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davutozcan.bookmarkreader.application.BookmarkReaderApplication;
import com.davutozcan.bookmarkreader.databinding.*;
import com.davutozcan.bookmarkreader.databinding.FragmentSettingsBinding;
import com.davutozcan.bookmarkreader.login.User;
import com.davutozcan.bookmarkreader.menu.INavigator;

import static com.davutozcan.bookmarkreader.application.BookmarkReaderApplication.userService;

/**
 * Created by davut on 28.02.2017.
 */

public class SettingsFragment extends Fragment {
    private INavigator navigator;
    private FragmentSettingsBinding binding;

    public SettingsFragment() {
    }

    public SettingsFragment(INavigator navigator) {
        this.navigator = navigator;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.binding == null)
        {
            this.binding =  FragmentSettingsBinding.inflate(inflater , container , false);
            binding.setModel(new SettingsFragmentModel(navigator));
        }
        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(binding != null)
        {
            binding.getModel().loadFrom(userService().getLastLoginedUser());
        }
    }

    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }
}