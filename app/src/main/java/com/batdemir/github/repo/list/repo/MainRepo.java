package com.batdemir.github.repo.list.repo;

import android.content.Context;

import com.android.batdemir.mylibrary.tools.ToolSharedPreferences;
import com.batdemir.entity.model.RepoModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainRepo {

    private final String LOCAL_MODEL_KEY = "LOCAL_MODEL_KEY";
    private Context context;
    private List<RepoModel> models;

    private MainRepo(Context context) {
        this.context = context;
    }

    private static MainRepo instance;

    public static MainRepo getInstance(Context context) {
        if (instance == null)
            instance = new MainRepo(context);
        instance.setModels();
        return instance;
    }

    public RepoModel notifyData(RepoModel model) {
        if (model.isFavorite())
            deleteValue(model);
        else
            addValue(model);
        model.setFavorite(!model.isFavorite());
        return model;
    }

    public void restoreModels(List<RepoModel> repoModels) {
        repoModels.forEach(element -> element.setFavorite(false));
        repoModels.stream().filter(models::contains).forEach(element -> element.setFavorite(true));
    }

    private void addValue(RepoModel model) {
        models.add(model);
        notifyRepo();
    }

    private void deleteValue(RepoModel model) {
        models.remove(model);
        notifyRepo();
    }

    private void setModels() {
        String jsonData = ToolSharedPreferences.getInstance(context).getString(LOCAL_MODEL_KEY);
        if (jsonData.isEmpty())
            models = new ArrayList<>();
        else
            models = new Gson().fromJson(jsonData, new TypeToken<List<RepoModel>>() {
            }.getType());
    }

    private void notifyRepo() {
        ToolSharedPreferences.getInstance(context).setString(LOCAL_MODEL_KEY, new Gson().toJson(new ArrayList<RepoModel>()));
        ToolSharedPreferences.getInstance(context).setString(LOCAL_MODEL_KEY, new Gson().toJson(models));
    }
}
