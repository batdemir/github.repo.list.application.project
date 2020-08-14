package com.batdemir.github.repo.list.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.batdemir.entity.model.RepoModel;
import com.batdemir.github.repo.list.databinding.ItemRepoBinding;
import com.batdemir.github.repo.list.helper.GlobalVariable;
import com.batdemir.utilities.MethodHelper;

import java.util.List;
import java.util.Objects;

public class RepoRecyclerAdapter extends RecyclerView.Adapter<RepoRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<RepoModel> models;
    private ItemRepoBinding binding;
    private ItemListener itemListener;

    public RepoRecyclerAdapter(Context context, List<RepoModel> models, ItemListener itemListener) {
        this.context = context;
        this.models = models;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemRepoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public List<RepoModel> getModels() {
        return models;
    }

    public interface ItemListener {
        void onItemClick(RepoModel item);
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RepoModel item;

        private MyViewHolder(@NonNull ItemRepoBinding binding) {
            super(binding.getRoot());
            binding.rootItemRepo.setOnClickListener(this);
        }

        private void setData(RepoModel item) {
            this.item = item;
            binding.txtEditRepoName.setText(String.format(GlobalVariable.getLocale(), "%s", item.getName()));
            if (item.getLanguage() == null)
                binding.txtEditLanguage.setVisibility(View.GONE);
            else
                binding.txtEditLanguage.setText(String.format(GlobalVariable.getLocale(), "%s", item.getLanguage()));
            if (item.getLicense() == null)
                binding.txtEditLicense.setVisibility(View.GONE);
            else
                binding.txtEditLicense.setText(String.format(GlobalVariable.getLocale(), "%s", item.getLicense().getName()));
            binding.txtEditForkCount.setText(String.format(GlobalVariable.getLocale(), "%d", item.getForksCount()));
            binding.txtEditStartCount.setText(String.format(GlobalVariable.getLocale(), "%d", item.getStargazersCount()));
            binding.txtEditIssueCount.setText(String.format(GlobalVariable.getLocale(), "%d", item.getOpenIssuesCount()));
            binding.txtEditLastUpdatedDate.setText(String.format(GlobalVariable.getLocale(), "%s", MethodHelper.getInstance().getDateStringFromCalenderString(item.getUpdatedAt())));
            binding.imgFavorite.setVisibility(item.isFavorite() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            try {
                if (itemListener == null)
                    itemListener = (ItemListener) context;
                itemListener.onItemClick(item);
            } catch (Exception e) {
                Log.e(RepoRecyclerAdapter.class.getSimpleName(), Objects.requireNonNull(e.getMessage()));
            }
        }
    }
}
