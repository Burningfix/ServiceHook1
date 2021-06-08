package jianqiang.com.activityhook1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.jianqiang.mypluginlibrary.L;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import jianqiang.com.activityhook1.ams.AMSHookHelper;
import jianqiang.com.activityhook1.classloder_hook.BaseDexClassLoaderHookHelper;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String apkName = "testservice1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName("jianqiang.com.testservice1",
                                    "jianqiang.com.testservice1.MyService1"));

                    startService(intent);
                } catch (Throwable e) {
                    L.i(e);
                }
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName("jianqiang.com.testservice1",
                                    "jianqiang.com.testservice1.MyService1"));
                    stopService(intent);
                } catch (Throwable e) {
                    L.i(e);
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, apkName);

            File dexFile = getFileStreamPath(apkName);
            File optDexFile = getFileStreamPath("testservice1.dex");
            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);

            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();

            String strJSON = Utils.readZipFileString(dexFile.getAbsolutePath(), "assets/plugin_config.json");
            L.i("MainActivity.attachBaseContext() strJSON:" + strJSON);

            if (strJSON != null && !TextUtils.isEmpty(strJSON)) {
                JSONObject jObject = new JSONObject(strJSON.replaceAll("\r|\n", ""));
                JSONArray jsonArray = jObject.getJSONArray("plugins");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    UPFApplication.pluginServices.put(
                            jsonObject.optString("PluginService"),
                            jsonObject.optString("StubService"));
                }
            }
        } catch (Throwable e) {
            L.i(e);
        }
    }
}
