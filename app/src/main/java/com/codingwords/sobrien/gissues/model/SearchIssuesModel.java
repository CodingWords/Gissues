package com.codingwords.sobrien.gissues.model;

/**
 * Created by Administrator on 2/20/2018.
 */

public class SearchIssuesModel {
    private String owner;
    private String repo;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}
