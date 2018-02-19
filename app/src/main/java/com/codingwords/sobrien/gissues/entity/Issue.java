package com.codingwords.sobrien.gissues.entity;

/**
 * Created by sobrien on 2/18/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("repository_url")
    @Expose
    private String repoUrl;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private User user;
    @Expose
    private String createdAt;
    @SerializedName("state")
    @Expose
    private String currentState;
    @SerializedName("body")
    @Expose
    private String body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String state) {
        this.currentState = state;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
