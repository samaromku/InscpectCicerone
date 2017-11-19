package ru.savchenko.andrey.inscpectcicerone;

import android.app.Application;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/**
 * Created by Andrey on 18.11.2017.
 */

public class App extends Application{
    private static Cicerone<Router>cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        cicerone = Cicerone.create();
    }

    public static Router getRouter(){
        return cicerone.getRouter();
    }

    public static NavigatorHolder getNavigatorHolder(){
        return cicerone.getNavigatorHolder();
    }
}
