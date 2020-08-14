package com.batdemir.github.repo.list.ui.activities.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.batdemir.mylibrary.tools.Tool;
import com.batdemir.api.services.GithubService;
import com.batdemir.entity.model.RepoModel;
import com.batdemir.github.repo.list.databinding.ActivityHomeBinding;
import com.batdemir.github.repo.list.repo.MainRepo;
import com.batdemir.github.repo.list.ui.activities.base.controller.BaseController;
import com.batdemir.github.repo.list.ui.activities.detail.DetailActivity;
import com.batdemir.github.repo.list.ui.adapters.RepoRecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeController extends BaseController<ActivityHomeBinding> {

    public HomeController(ActivityHomeBinding binding) {
        super(binding);
    }

    void getRepoList() {
        if (getBinding().editTextUser.getText() == null)
            return;
        if (getBinding().editTextUser.getText().toString().isEmpty()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(getBinding().editTextUser.getWindowToken(), 0);
        new GithubService(getContext()).getRepoList(getBinding().editTextUser.getText().toString());
    }

    void fillList(List<RepoModel> models) {
        MainRepo.getInstance(getContext()).restoreModels(models);
        if (models.isEmpty()) {
            getBinding().recyclerView.setVisibility(View.GONE);
            getBinding().emptyTextView.setVisibility(View.VISIBLE);
            return;
        }
        getBinding().recyclerView.setVisibility(View.VISIBLE);
        getBinding().emptyTextView.setVisibility(View.GONE);
        getBinding().recyclerView.setAdapter(new RepoRecyclerAdapter(getContext(), models, item -> {
            Bundle bundle = new Bundle();
            bundle.putString("repoModel", new Gson().toJson(item));
            Tool.getInstance(getContext()).move(DetailActivity.class, true, true, bundle);
        }));
        getBinding().recyclerView.setHasFixedSize(true);
        getBinding().recyclerView.setItemViewCacheSize(models.size());
        getBinding().recyclerView.scrollToPosition(0);
    }
}
