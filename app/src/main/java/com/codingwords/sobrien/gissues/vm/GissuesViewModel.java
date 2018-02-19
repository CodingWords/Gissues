package com.codingwords.sobrien.gissues.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.codingwords.sobrien.gissues.entity.GissuesResponse;
import com.codingwords.sobrien.gissues.repo.IssueRepo;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

public class GissuesViewModel extends ViewModel {

    private IssueRepo issueRepository;
    private MediatorLiveData<GissuesResponse> gapiResponse;

    @Inject
    public GissuesViewModel(IssueRepo repository) {
        this.issueRepository = repository;
        this.gapiResponse = new MediatorLiveData<GissuesResponse>();

    }

    public MediatorLiveData<GissuesResponse> getGapiResponse() {
        return gapiResponse;
    }

    public void pullIssues(@NonNull String user, String repo) {
        LiveData<GissuesResponse> issuesSource = issueRepository.receiveIssues(user, repo);
        gapiResponse.addSource(
                                issuesSource,
                                gapiResponse -> {
                    if (this.gapiResponse.hasActiveObservers()) {
                        this.gapiResponse.removeSource(issuesSource);
                    }
                    this.gapiResponse.setValue(gapiResponse);
                }
        );
    }
}

