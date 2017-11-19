package ru.savchenko.andrey.inscpectcicerone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container, new MainFragment()).commitNow();
//        mPager = findViewById(R.id.pager);
//        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(mPagerAdapter);
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
//        App.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    public void onPause() {
        super.onPause();
//        App.getNavigatorHolder().removeNavigator();
    }

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    private Navigator navigator = new SupportAppNavigator(this, R.id.pager) {
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
