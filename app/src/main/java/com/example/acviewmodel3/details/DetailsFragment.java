package com.example.acviewmodel3.details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.acviewmodel3.R;
import com.example.acviewmodel3.base.GithubApp;
import com.example.acviewmodel3.home.SelectedRepoViewModel;
import com.example.acviewmodel3.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsFragment extends Fragment {

    @BindView(R.id.tv_repo_name)
    TextView tvRepoName;
    @BindView(R.id.tv_repo_description)
    TextView tvRepoDescription;
    @BindView(R.id.tv_repo_forks)
    TextView tvRepoForks;
    @BindView(R.id.tv_repo_stars)
    TextView tvRepoStars;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.repo_containter)
    LinearLayout repoContainer;

    private Unbinder unbinder;
    private SelectedRepoViewModel viewModel;
    @Inject
    ViewModelFactory factory;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        GithubApp.gitApplicationCompenent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_details_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(SelectedRepoViewModel.class);
        viewModel.restoreFromBundle(savedInstanceState);
        showRepo();
        observe();
    }

    private void observe() {
        viewModel.getLoading().observe(this,
                isLoading -> loading.setVisibility(isLoading ? View.VISIBLE : View.GONE));
        viewModel.getError().observe(this, isError -> {
            error.setVisibility(isError ? View.VISIBLE : View.GONE);
            repoContainer.setVisibility(isError ? View.GONE : View.VISIBLE);
        });
    }

    private void showRepo() {
        viewModel.getRepo().observe(this, repo -> {
            if (repo != null) {
                tvRepoName.setText(repo.name);
                tvRepoDescription.setText(repo.description);
                tvRepoForks.setText(String.valueOf(repo.forks));
                tvRepoStars.setText(String.valueOf(repo.stars));
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.saveToBundle(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
