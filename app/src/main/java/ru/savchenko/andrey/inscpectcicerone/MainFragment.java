package ru.savchenko.andrey.inscpectcicerone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Created by Andrey on 18.11.2017.
 */

public class MainFragment extends Fragment{
    private static final int NUM_PAGES = 3;
    ViewPager mPager;
    private Navigator navigator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = view.findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    firstFragment = new FirstFragment();
                    return firstFragment;
                case 1:
                    secondFragment = new SecondFragment();
                    return secondFragment;
                case 2:
                    thirdFragment = new ThirdFragment();
                    return thirdFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        App.getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        App.getNavigatorHolder().removeNavigator();
    }

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.pager) {
                @Override
                protected Intent createActivityIntent(String screenKey, Object data) {
                    return null;
                }

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    switch (screenKey) {
                        case "first":
                            return firstFragment;
                        case "second":
                            return secondFragment;
                        case "third":
                            return thirdFragment;
                    }
                    return null;
                }

                @Override
                public void applyCommand(Command command) {
                    if(command instanceof Replace){
                        switch ( ((Replace)command).getScreenKey()){
                            case "first":
                                mPager.setCurrentItem(0);
                                break;
                            case "second":
                                mPager.setCurrentItem(1);
                                break;
                        }

                    }else {
                        super.applyCommand(command);
                    }
                }

                @Override
                protected void setupFragmentTransactionAnimation(
                        Command command,
                        Fragment currentFragment,
                        Fragment nextFragment,
                        FragmentTransaction fragmentTransaction) {
                    //setup animation
                }
            };
        }
        return navigator;
    }
}
