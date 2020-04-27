package com.example.acviewmodel3.home;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.acviewmodel3.R;
import com.example.acviewmodel3.model.Repo;

import butterknife.BindView;
import butterknife.ButterKnife;

class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_repo_name)
    TextView tvRepoName;
    @BindView(R.id.tv_repo_description)
    TextView tvRepoDescription;
    @BindView(R.id.tv_repo_forks)
    TextView tvRepoForks;
    @BindView(R.id.tv_repo_stars)
    TextView tvRepoStars;
    private Repo repo;

    ViewHolder(View view, RepoSelectedListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        view.setOnClickListener(v -> {
            listener.onRepoSelected(repo);
        });
    }

    void bind(Repo repo) {
        this.repo = repo;
        tvRepoName.setText(repo.name);
        tvRepoDescription.setText(repo.description);
        tvRepoForks.setText(String.valueOf(repo.forks));
        tvRepoStars.setText(String.valueOf(repo.stars));
    }
}
