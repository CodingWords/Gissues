package com.codingwords.sobrien.gissues;

/**
 * Created by Administrator on 2/21/2018.
 */

public interface GissuesScreen extends Screen {
    void showOwnerError();
    void showRepoError();
    void showIssuesNotFound();
}
