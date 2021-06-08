package jianqiang.com.testservice1;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.jianqiang.mypluginlibrary.L;

public class MyService2 extends Service {
    private int count;
    private boolean quit;

    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public int getCount() {
            return count;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.i("MyService2.onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i("MyService2.onCreate");

        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {

                    }

                    count++;
                }
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.i("MyService2.onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit = true;
        L.i("MyService2.onDestroy");
    }
}