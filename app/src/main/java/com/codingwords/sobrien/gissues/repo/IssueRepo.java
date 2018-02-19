package com.codingwords.sobrien.gissues.repo;

import android.arch.lifecycle.LiveData;

import com.codingwords.sobrien.gissues.entity.GissuesResponse;

/**
 * Created by Administrator on 2/19/2018.
 */

public interface IssueRepo {
    LiveData<GissuesResponse> receiveIssues(String owner, String repo);
}
