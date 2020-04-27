package com.example.acviewmodel3.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acviewmodel3.R;
import com.example.acviewmodel3.base.GithubApp;
import com.example.acviewmodel3.details.DetailsFragment;
import com.example.acviewmodel3.model.Repo;
import com.example.acviewmodel3.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends Fragment implements RepoSelectedListener {

    @BindView(R.id.list_rv)
    RecyclerView listRv;
    @BindView(R.id.error_tv)
    TextView errorTv;
    @BindView(R.id.loading_pb)
    ProgressBar loadingPb;
    private Unbinder unbinder;
    private ListViewModel listViewModel;
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
        View view = inflater.inflate(R.layout.screen_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewModel = new ViewModelProvider(this, factory).get(ListViewModel.class);
        prepareRecyclerView();
        observe();
    }

    private void prepareRecyclerView() {
        listRv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        listRv.setAdapter(new ReposAdapter(listViewModel.getRepos(), this, this));
    }

    private void observe() {
        listViewModel.getLoading().observe(this,
                loading -> loadingPb.setVisibility(loading ? View.VISIBLE : View.GONE));
        listViewModel.getError().observe(this, error -> {
            errorTv.setVisibility(error ? View.VISIBLE : View.GONE);
            listRv.setVisibility(error ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void onRepoSelected(Repo repo) {
        SelectedRepoViewModel sharedViewModel = new ViewModelProvider(requireActivity(), factory).get(SelectedRepoViewModel.class);
        sharedViewModel.setRepo(repo);
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_container, new DetailsFragment())
                .addToBackStack(null)
                .commit();
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
