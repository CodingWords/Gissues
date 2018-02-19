package com.codingwords.sobrien.gissues.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sobrien on 2/18/2018.
 */

public class User {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("login")
    @Expose
    private String login;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
