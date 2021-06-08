package jianqiang.com.activityhook1.defineservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jianqiang.mypluginlibrary.L;

public class StubService2 extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {        L.i("StubService2.onBind() intent:" + intent);

        return null;
    }
}