package com.batdemir.api.services;

import android.content.Context;

import com.android.batdemir.mylibrary.connection.Connect;
import com.android.batdemir.mylibrary.connection.RetrofitClient;
import com.batdemir.api.OperationTypes;
import com.batdemir.api.iservices.IGithubService;

public class GithubService extends Connect {
    private Context context;
    private IGithubService iGithubService;

    public GithubService(Context context) {
        this.context = context;
        this.iGithubService = RetrofitClient.getInstance().create(IGithubService.class);
    }

    public void getRepoList(String userName) {
        connect(context, iGithubService.getRepoList(userName), OperationTypes.GET_REPO_LIST.name());
    }
}
