package com.batdemir.github.repo.list.ui.activities.home;

import android.util.Log;
import android.view.inputmethod.EditorInfo;

import com.android.batdemir.mylibrary.connection.ConnectServiceListener;
import com.batdemir.api.OperationTypes;
import com.batdemir.entity.model.RepoModel;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.databinding.ActivityHomeBinding;
import com.batdemir.github.repo.list.ui.activities.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeController> implements
        ConnectServiceListener {

    private List<RepoModel> repoModels;

    private List<RepoModel> getRepoModels() {
        return repoModels;
    }

    private void setRepoModels(List<RepoModel> repoModels) {
        this.repoModels = repoModels;
    }

    @Override
    public void onCreated() {
        init(new ActivityBuilder().setFirstActivity(true).setTitle(getString(R.string.title_home)).setElevation(16f).setTheme(R.style.AppThemeActionBar));
        setRepoModels(new ArrayList<>());
    }

    @Override
    public void getObjectReferences() {
        //Not implemented
    }

    @Override
    public void loadData() {
        getController().fillList(getRepoModels());
    }

    @Override
    public void setListeners() {
        Objects.requireNonNull(getBinding().inputLayoutUser.getEditText()).setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getController().getRepoList();
                return true;
            }
            return false;
        });

        getBinding().inputLayoutUser.setEndIconOnClickListener(v -> getController().getRepoList());
    }

    @SuppressWarnings({"java:S1301","unchecked", "SwitchStatementWithTooFewBranches"})
    @Override
    public void onSuccess(String s, Response<?> response) {
        if (response == null)
            return;
        switch (OperationTypes.valueOf(s)) {
            case GET_REPO_LIST:
                setRepoModels((List<RepoModel>) response.body());
                getController().fillList(getRepoModels());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported case.");
        }
    }

    @SuppressWarnings({"java:S1301", "SwitchStatementWithTooFewBranches"})
    @Override
    public void onFailure(String s, Response<?> response) {
        if (response == null)
            return;
        try {
            switch (OperationTypes.valueOf(s)) {
                case GET_REPO_LIST:
                    setRepoModels(new ArrayList<>());
                    getController().fillList(getRepoModels());
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported case.");
            }
        } catch (Exception e) {
            Log.e(HomeActivity.class.getSimpleName(), Objects.requireNonNull(e.getMessage()));
        }
    }
}
