package jianqiang.com.activityhook1;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

public class UPFApplication extends Application {

    private static Context sContext;

    public static HashMap<String, String> pluginServices = new HashMap<String, String>();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = base;
    }

    public static Context getContext() {
        return sContext;
    }
}
