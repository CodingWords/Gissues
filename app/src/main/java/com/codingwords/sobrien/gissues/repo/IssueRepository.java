package com.codingwords.sobrien.gissues.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.codingwords.sobrien.gissues.api.GAPIService;
import com.codingwords.sobrien.gissues.entity.GissuesResponse;
import com.codingwords.sobrien.gissues.entity.Issue;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2/19/2018.
 */

public class IssueRepository implements IssueRepo {
    @Inject
    public IssueRepository() {
    }

    @Inject
    GAPIService gapiService;


    @Override
    public LiveData<GissuesResponse> receiveIssues(String owner, String repo) {
        final MutableLiveData<GissuesResponse> lData = new MutableLiveData<>();
        Call<List<Issue>> callRequest = gapiService.getIssues(owner, repo);
        callRequest.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                lData.setValue(new GissuesResponse(response.body()));
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                lData.setValue(new GissuesResponse(t));
            }
        });
        return lData;
    }
}
