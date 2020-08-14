package com.batdemir.github.repo.list.ui.activities.detail;

import com.batdemir.entity.model.RepoModel;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.databinding.ActivityDetailBinding;
import com.batdemir.github.repo.list.repo.MainRepo;
import com.batdemir.github.repo.list.ui.activities.base.activity.BaseActivity;
import com.google.gson.Gson;

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailController> {

    private RepoModel repoModel;

    public RepoModel getRepoModel() {
        return repoModel;
    }

    public void setRepoModel(RepoModel repoModel) {
        this.repoModel = repoModel;
    }

    @Override
    public void onCreated() {
        setRepoModel(new Gson().fromJson(getIntent().getStringExtra("repoModel"), RepoModel.class));
        init(new ActivityBuilder().setShowHomeButton(true).setTitle(getRepoModel().getName()).setElevation(16f).setTheme(R.style.AppThemeActionBar));
    }

    @Override
    public void getObjectReferences() {
        getController().setRepoModel(getRepoModel());
    }

    @Override
    public void loadData() {
        //Not implemented
    }

    @Override
    public void setListeners() {
        getBinding().btnFavorite.setOnClickListener(v -> {
            setRepoModel(MainRepo.getInstance(getBinding().getRoot().getContext()).notifyData(getRepoModel()));
            getController().setRepoModel(getRepoModel());
        });
    }
}

