package com.batdemir.github.repo.list.ui.activities.base.controller;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

public class BaseController<B extends ViewBinding> {

    private B binding;
    private Context context;
    private FragmentManager fragmentManager;

    public BaseController(B binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        this.fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
    }

    protected B getBinding() {
        return binding;
    }

    protected Context getContext() {
        return context;
    }

    protected FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
