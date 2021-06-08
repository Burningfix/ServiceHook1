package jianqiang.com.activityhook1.ams;

import android.content.ComponentName;
import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.example.jianqiang.mypluginlibrary.L;
import jianqiang.com.activityhook1.UPFApplication;

class MockClass1 implements InvocationHandler {

    private static final String TAG = "MockClass1";

    // 替身StubService的包名
    private static final String stubPackage = "jianqiang.com.activityhook1";

    Object mBase;

    public MockClass1(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        L.i("MockClass1.invoke method:" + method.toString());

        if ("startService".equals(method.getName())) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始StubService启动别的Service偷梁换柱

            // 找到参数里面的第一个Intent 对象
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            //get StubService form UPFApplication.pluginServices
            Intent rawIntent = (Intent) args[index];
            String rawServiceName = rawIntent.getComponent().getClassName();
            L.i("MockClass1.invoke startService rawIntent:" + rawIntent);

            String stubServiceName = UPFApplication.pluginServices.get(rawServiceName);

            // replace Plugin Service of StubService
            ComponentName componentName = new ComponentName(stubPackage, stubServiceName);
            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);
            L.i("MockClass1.invoke startService newIntent:" + newIntent);

            // Replace Intent, cheat AMS
            args[index] = newIntent;
            L.i("MockClass1.invoke hook startService success");

            return method.invoke(mBase, args);
        } else if ("stopService".equals(method.getName())) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始StubService启动别的Service偷梁换柱

            // 找到参数里面的第一个Intent 对象
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            //get StubService form UPFApplication.pluginServices
            Intent rawIntent = (Intent) args[index];
            String rawServiceName = rawIntent.getComponent().getClassName();
            String stubServiceName = UPFApplication.pluginServices.get(rawServiceName);
            L.i("MockClass1.invoke stopService rawIntent:" + rawIntent);

            // replace Plugin Service of StubService
            ComponentName componentName = new ComponentName(stubPackage, stubServiceName);
            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);
            L.i("MockClass1.invoke stopService newIntent:" + newIntent);

            // Replace Intent, cheat AMS
            args[index] = newIntent;

            L.i("MockClass1.invoke hook stopService success");
            return method.invoke(mBase, args);
        }

        return method.invoke(mBase, args);
    }
}