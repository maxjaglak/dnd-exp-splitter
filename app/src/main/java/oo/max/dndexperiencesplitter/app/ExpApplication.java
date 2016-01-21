package oo.max.dndexperiencesplitter.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import lombok.Getter;
import oo.max.dndexperiencesplitter.injecting.Injector;

public class ExpApplication extends MultiDexApplication implements Thread.UncaughtExceptionHandler {

    @Getter
    private Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger.error(ex);
    }

    public static ExpApplication getApp(Context context) {
        return (ExpApplication) context.getApplicationContext();
    }

    public void init() {
        injector = new Injector(this);
    }
}

