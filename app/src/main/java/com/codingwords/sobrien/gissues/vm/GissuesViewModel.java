package com.codingwords.sobrien.gissues.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.codingwords.sobrien.gissues.GissuesScreen;
import com.codingwords.sobrien.gissues.entity.GissuesResponse;
import com.codingwords.sobrien.gissues.model.SearchIssuesModel;
import com.codingwords.sobrien.gissues.repo.IssueRepo;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

public class GissuesViewModel extends ViewModel {

    private IssueRepo issueRepository;
    private MediatorLiveData<GissuesResponse> gapiResponse;
    private SearchIssuesModel searchModel;
    private GissuesScreen gissuesScreen;


    @Inject
    public GissuesViewModel(IssueRepo repository) {
        this.issueRepository = repository;
        this.gapiResponse = new MediatorLiveData<GissuesResponse>();

    }

    public MediatorLiveData<GissuesResponse> getGapiResponse() {
        return gapiResponse;
    }

    public void pullIssues(){
        if (getSearchModel() != null){
            if ((getSearchModel().getOwner() == null) || (getSearchModel().getOwner().length() < 2)){
                gissuesScreen.showOwnerError();
            } else if  ((getSearchModel().getRepo() == null) || (getSearchModel().getRepo().length() < 2)) {
                gissuesScreen.showRepoError();
            } else {
                pullIssues(getSearchModel().getOwner(), getSearchModel().getRepo());
            }
        }
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

    public SearchIssuesModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(SearchIssuesModel searchModel) {
        this.searchModel = searchModel;
    }

    public GissuesScreen getGissuesScreen() {
        return gissuesScreen;
    }

    public void setGissuesScreen(GissuesScreen gissuesScreen) {
        this.gissuesScreen = gissuesScreen;
    }
}

