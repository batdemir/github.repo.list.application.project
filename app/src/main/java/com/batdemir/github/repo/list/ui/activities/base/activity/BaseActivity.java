package com.batdemir.github.repo.list.ui.activities.base.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.android.batdemir.mydialog.listeners.MyAlertDialogButtonListener;
import com.android.batdemir.mydialog.ui.MyAlertDialog;
import com.android.batdemir.mydialog.ui.MyDialogStyle;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.ui.activities.base.controller.BaseController;
import com.batdemir.github.repo.list.ui.activities.base.factory.BindingFactory;
import com.batdemir.github.repo.list.ui.activities.base.factory.ControllerFactory;

public abstract class BaseActivity<B extends ViewBinding, C extends BaseController<?>> extends AppCompatActivity implements
        BaseActions,
        MyAlertDialogButtonListener {

    private B binding;
    private C controller;
    private ActivityBuilder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreated();
        create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getObjectReferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        setListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    @Override
    public void onBackPressed() {
        if (builder.isFirstActivity()) {
            new MyAlertDialog
                    .Builder()
                    .setMessage(getString(R.string.message_are_you_sure_exit_application))
                    .setStyle(MyDialogStyle.ACTION)
                    .build()
                    .show(getSupportFragmentManager(), getString(R.string.alert_dialog_key_exit));
            return;
        } else {
            finish();
            overridePendingTransition(com.android.batdemir.myresources.R.anim.slide_in_right, com.android.batdemir.myresources.R.anim.slide_out_right);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(com.android.batdemir.myresources.R.anim.slide_in_right, com.android.batdemir.myresources.R.anim.slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dialogOk(MyAlertDialog myAlertDialog) {
        if (myAlertDialog.getTag() == null)
            return;
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_exit))) {
            finishAffinity();
            System.exit(0);
        }
    }

    @Override
    public void dialogCancel(MyAlertDialog myAlertDialog) {
        if (myAlertDialog.getTag() == null)
            return;
        if (myAlertDialog.getTag().equals(getString(R.string.alert_dialog_key_exit))) {
            Log.v(BaseActivity.class.getSimpleName(), String.valueOf(myAlertDialog.getId()));
        }
    }

    // FUNCTIONS

    private void create() {
        binding = binding == null ? BindingFactory.getInstance().getBinding(this.getClass().getSimpleName().replace("Activity", "").replace("Binding", ""), this.getLayoutInflater()) : binding;
        controller = controller == null ? ControllerFactory.getInstance().getController(this.getClass().getSimpleName().replace("Activity", ""), binding) : controller;
    }

    private void destroy() {
        binding = null;
        controller = null;
    }

    protected void init(ActivityBuilder builder) {
        this.builder = (builder == null ? new ActivityBuilder() : builder);
        setTheme(this.builder.theme);
        if (getSupportActionBar() == null)
            return;
        getSupportActionBar();
        getSupportActionBar().setTitle(this.builder.getTitle());
        getSupportActionBar().setElevation(this.builder.getElevation());
        getSupportActionBar().setDisplayHomeAsUpEnabled(this.builder.isShowHomeButton());
    }

    protected B getBinding() {
        return binding;
    }

    protected C getController() {
        return controller;
    }

    public static class ActivityBuilder {
        @StyleRes
        private int theme;
        private String title;
        private float elevation;
        private boolean showHomeButton;
        private boolean isFirstActivity;

        public ActivityBuilder() {
            this.theme = R.style.AppThemeNoActionBar;
            this.title = "";
            this.elevation = 0f;
            this.showHomeButton = false;
            this.isFirstActivity = false;
        }

        public ActivityBuilder(@StyleRes int theme, String title, float elevation, boolean showHomeButton, boolean isFirstActivity) {
            this.theme = theme;
            this.title = title;
            this.elevation = elevation;
            this.showHomeButton = showHomeButton;
            this.isFirstActivity = isFirstActivity;
        }

        public int getTheme() {
            return theme;
        }

        public ActivityBuilder setTheme(@StyleRes int theme) {
            this.theme = theme;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public ActivityBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public float getElevation() {
            return elevation;
        }

        public ActivityBuilder setElevation(float elevation) {
            this.elevation = elevation;
            return this;
        }

        public boolean isShowHomeButton() {
            return showHomeButton;
        }

        public ActivityBuilder setShowHomeButton(boolean showHomeButton) {
            this.showHomeButton = showHomeButton;
            return this;
        }

        public boolean isFirstActivity() {
            return isFirstActivity;
        }

        public ActivityBuilder setFirstActivity(boolean firstActivity) {
            isFirstActivity = firstActivity;
            return this;
        }
    }
}
