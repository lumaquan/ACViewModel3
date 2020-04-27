package com.example.acviewmodel3.networking;

import com.example.acviewmodel3.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoService {

    @GET("orgs/Google/repos")
    Call<List<Repo>> getRepositories();

    @GET("repos/{owner}/{name}")
    Call<Repo> getRepo(@Path("owner") String owner, @Path("name") String repoName);
}
