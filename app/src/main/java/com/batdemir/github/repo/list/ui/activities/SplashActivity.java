package com.batdemir.github.repo.list.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.batdemir.mydialog.listeners.MyAlertDialogButtonListener;
import com.android.batdemir.mydialog.ui.MyAlertDialog;
import com.android.batdemir.mydialog.ui.MyDialogStyle;
import com.android.batdemir.mylibrary.tools.Tool;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.ui.activities.home.HomeActivity;

import java.util.Arrays;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity implements
        MyAlertDialogButtonListener {

    private String[] permissions = {
            //Manifest.permission.CAMERA,
            //Manifest.permission.READ_EXTERNAL_STORAGE,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            //Manifest.permission.READ_PHONE_STATE,
            //Manifest.permission.BLUETOOTH_ADMIN,
            //Manifest.permission.BLUETOOTH,
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (checkPermissions() && checkException())
            move();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length != 0) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 100) {
                if (Arrays.stream(grantResults).allMatch(x -> x == PackageManager.PERMISSION_GRANTED)) {
                    move();
                } else {
                    boolean status = false;
                    for (String permission : permissions) {
                        if (!shouldShowRequestPermissionRationale(permission)) {
                            status = true;
                            break;
                        }
                    }
                    if (status) {
                        new MyAlertDialog
                                .Builder()
                                .setMessage(getString(R.string.message_please_activate_the_permissions_in_the_Applications_app_name_permissions_section, getString(R.string.app_name)))
                                .setStyle(MyDialogStyle.INFO)
                                .build()
                                .show(getSupportFragmentManager(), getString(R.string.alert_dialog_key_permission));
                    } else {
                        try {
                            Thread.sleep(2000);
                            move();
                        } catch (Exception e) {
                            Log.e(SplashActivity.class.getSimpleName(), Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void dialogOk(MyAlertDialog myAlertDialog) {
        if (myAlertDialog.getTag() == null)
            return;
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_permission))) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            return;
        }
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_exception))) {
            move();
        }
    }

    @Override
    public void dialogCancel(MyAlertDialog myAlertDialog) {
        if (myAlertDialog.getTag() == null)
            return;
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_permission))) {
            Log.d(myAlertDialog.getTag(), "");
            return;
        }
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_exception))) {
            Log.d(myAlertDialog.getTag(), "");
        }
    }

    // FUNCTIONS

    private boolean checkPermissions() {
        boolean status = false;
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                status = true;
                break;
            }
        }

        if (status) {
            requestPermissions(permissions, 100);
            return false;
        }
        return true;
    }

    private boolean checkException() {
        String result = getIntent().getStringExtra("CRASH_REPORT");
        if (result != null && !result.isEmpty()) {
            new MyAlertDialog
                    .Builder()
                    .setMessage(result)
                    .setStyle(MyDialogStyle.FAILED)
                    .build()
                    .show(getSupportFragmentManager(), getString(R.string.alert_dialog_key_exception));
            return false;
        }
        return true;
    }

    private void move() {
        Tool.getInstance(this).move(HomeActivity.class, true, false, null);
    }
}
