package com.batdemir.api.iservices;

import com.batdemir.entity.model.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGithubService {

    @GET("/users/{user}/repos")
    Call<List<RepoModel>> getRepoList(@Path("user") String user);
}
