package jianqiang.com.testservice1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.jianqiang.mypluginlibrary.L;

public class MyService1 extends Service {
    public MyService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i("MyService1.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i("MyService1.onStartCommand intent: " + intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("MyService1.onDestroy  ");
    }
}
