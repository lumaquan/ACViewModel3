package com.example.acviewmodel3.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acviewmodel3.R;
import com.example.acviewmodel3.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Repo> repos = new ArrayList<>();
    private final RepoSelectedListener listener;

    public ReposAdapter(LiveData<List<Repo>> reposLiveDate, LifecycleOwner lifecycleOwner, RepoSelectedListener listener) {
        this.listener = listener;
        reposLiveDate.observe(lifecycleOwner,
                repos -> {
                    ReposAdapter.this.repos.clear();
                    ReposAdapter.this.repos.addAll(repos);
                    notifyDataSetChanged();
                });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

}
