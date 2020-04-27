package com.example.acviewmodel3.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.acviewmodel3.model.Repo;
import com.example.acviewmodel3.networking.RepoService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends ViewModel {

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<List<Repo>>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final RepoService repoService;
    private Call<List<Repo>> repoCall;

    @Inject
    public ListViewModel(RepoService repoService) {
        this.repoService = repoService;
        fetchRepos();
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchRepos() {
        loading.setValue(true);
        repoCall = repoService.getRepositories();
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    error.setValue(false);
                    repos.setValue(response.body());
                } else {
                    error.setValue(true);
                }
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                loading.setValue(false);
                error.setValue(true);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
