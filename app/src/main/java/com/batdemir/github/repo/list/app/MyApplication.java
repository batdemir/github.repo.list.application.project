package com.batdemir.github.repo.list.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.text.HtmlCompat;

import com.android.batdemir.mylibrary.connection.RetrofitClient;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.helper.GlobalVariable;
import com.batdemir.github.repo.list.ui.activities.SplashActivity;

import java.util.Locale;

public class MyApplication extends Application {

    private ActivityLifecycleCallbacks activityLifecycleCallbacks;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        GlobalVariable.setLocale(Locale.US);
        RetrofitClient.setBaseUrl("https://api.github.com");
        RetrofitClient.setSll(true);
        activityLifecycleCallbacks = getActivityLifecycleCallbacks();
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                setKeepScreen(activity, true);
                setExceptionHandler();
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                //Not Implemented
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                //Not Implemented
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                setKeepScreen(activity, false);
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                //Not Implemented
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                //Not Implemented
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                //Not Implemented
            }
        };
    }

    private void setExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<p>");
            stringBuilder.append(getBaseContext().getString(R.string.exception_last_process_failed));
            stringBuilder.append(getBaseContext().getString(R.string.exception_system_closed_by_unexpected_error));
            stringBuilder.append("</p>");
            for (int i = 0; i < e.getStackTrace().length; i++) {
                if (!e.getStackTrace()[i].isNativeMethod()) {
                    stringBuilder.append("<p>").append("----------------------------------------").append("</p>");
                    stringBuilder.append("Exception: ");
                    stringBuilder.append("<p>").append("FileName: ").append(e.getStackTrace()[i].getFileName()).append("</p>");
                    stringBuilder.append("<pr>").append("MethodName: ").append(e.getStackTrace()[i].getMethodName()).append("</p>");
                    stringBuilder.append("<p>").append("LineNumber: ").append(e.getStackTrace()[i].getLineNumber()).append("</p>");
                }
            }

            Intent crashedIntent = new Intent(getBaseContext(), SplashActivity.class);
            crashedIntent.putExtra("CRASH_REPORT", Html.fromHtml(stringBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY).toString());
            crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, crashedIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager == null)
                return;
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

            System.exit(2);
        });
    }

    private void setKeepScreen(Activity activity, boolean isKeep) {
        if (isKeep)
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
