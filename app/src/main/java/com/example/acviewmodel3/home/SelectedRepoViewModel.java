package com.example.acviewmodel3.home;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.acviewmodel3.model.Repo;
import com.example.acviewmodel3.networking.RepoService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedRepoViewModel extends ViewModel {

    public static final String REPO_DETAILS = "repo_name";
    private final MutableLiveData<Repo> repo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private final RepoService repoService;
    private Call<Repo> repoCall;

    @Inject
    public SelectedRepoViewModel(RepoService repoService) {
        this.repoService = repoService;
    }

    public LiveData<Repo> getRepo() {
        return repo;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public void setRepo(Repo repo) {
        this.repo.setValue(repo);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }

    public void saveToBundle(Bundle outState) {
        Repo repo = this.repo.getValue();
        if (repo != null) {
            outState.putStringArray(REPO_DETAILS, new String[]{repo.owner.login, repo.name});
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        Repo repo = this.repo.getValue();
        if ((repo == null) && savedInstanceState.containsKey(REPO_DETAILS)) {
            loadRepo(savedInstanceState.getStringArray(REPO_DETAILS));
        }
    }

    private void loadRepo(String[] stringArray) {
        loading.setValue(true);
        repoCall = repoService.getRepo(stringArray[0], stringArray[1]);
        repoCall.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    repo.setValue(response.body());
                } else {
                    error.setValue(true);
                }
                repoCall = null;
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                loading.setValue(false);
                error.setValue(true);
            }
        });
    }


}
