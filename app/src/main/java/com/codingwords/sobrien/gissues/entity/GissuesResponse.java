package com.codingwords.sobrien.gissues.entity;

import java.util.List;

/**
 * Created by Administrator on 2/18/2018.
 */

public class GissuesResponse {

    private List<Issue> issues;
    private Throwable error;

    public GissuesResponse(List<Issue> issues, Throwable error){
        this.issues = issues;
        this.error = error;
    }

    public GissuesResponse(Throwable error) {
        this.error = error;
    }

    public GissuesResponse(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
