package com.batdemir.github.repo.list.ui.activities.detail;

import android.content.res.ColorStateList;
import android.view.View;

import com.batdemir.entity.model.RepoModel;
import com.batdemir.github.repo.list.R;
import com.batdemir.github.repo.list.databinding.ActivityDetailBinding;
import com.batdemir.github.repo.list.helper.GlobalVariable;
import com.batdemir.github.repo.list.ui.activities.base.controller.BaseController;
import com.batdemir.utilities.MethodHelper;
import com.bumptech.glide.Glide;

public class DetailController extends BaseController<ActivityDetailBinding> {

    public DetailController(ActivityDetailBinding binding) {
        super(binding);
    }

    public void setRepoModel(RepoModel repoModel) {
        Glide.with(getContext())
                .load(repoModel.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ic_github)
                .into(getBinding().imgOwnerAvatar);
        getBinding().txtEditOwnerName.setText(String.format(GlobalVariable.getLocale(), "%s", repoModel.getOwner().getLogin()));

        if (repoModel.getLanguage() == null)
            getBinding().txtEditLanguage.setVisibility(View.GONE);
        else
            getBinding().txtEditLanguage.setText(getContext().getString(R.string.language, String.format(GlobalVariable.getLocale(), "%s", repoModel.getLanguage())));
        if (repoModel.getLicense() == null)
            getBinding().txtEditLicense.setVisibility(View.GONE);
        else
            getBinding().txtEditLicense.setText(getContext().getString(R.string.license, String.format(GlobalVariable.getLocale(), "%s", repoModel.getLicense().getName())));
        getBinding().txtEditForkCount.setText(getContext().getString(R.string.fork_count, String.format(GlobalVariable.getLocale(), "%d", repoModel.getForksCount())));
        getBinding().txtEditStartCount.setText(getContext().getString(R.string.star_count, String.format(GlobalVariable.getLocale(), "%d", repoModel.getStargazersCount())));
        getBinding().txtEditIssueCount.setText(getContext().getString(R.string.issue_count, String.format(GlobalVariable.getLocale(), "%d", repoModel.getOpenIssuesCount())));
        getBinding().txtEditDefaultBranch.setText(getContext().getString(R.string.brach, String.format(GlobalVariable.getLocale(), "%s", repoModel.getDefaultBranch())));
        getBinding().txtEditCreatedAt.setText(getContext().getString(R.string.created_at, String.format(GlobalVariable.getLocale(), "%s", MethodHelper.getInstance().getDateStringFromCalenderString(repoModel.getCreatedAt()))));
        getBinding().txtEditUpdatedAt.setText(getContext().getString(R.string.updated_at, String.format(GlobalVariable.getLocale(), "%s", MethodHelper.getInstance().getDateStringFromCalenderString(repoModel.getUpdatedAt()))));
        getBinding().btnFavorite.setIconTint(repoModel.isFavorite() ? ColorStateList.valueOf(getContext().getResources().getColor(R.color.white, null)) : ColorStateList.valueOf(getContext().getResources().getColor(R.color.primaryColor, null)));
        getBinding().btnFavorite.setText(getContext().getString(R.string.favorite, (repoModel.isFavorite() ? getContext().getString(R.string.delete) : getContext().getString(R.string.add))));
    }
}

