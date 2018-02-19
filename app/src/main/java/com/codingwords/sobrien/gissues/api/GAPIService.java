package com.codingwords.sobrien.gissues.api;

import com.codingwords.sobrien.gissues.entity.Issue;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sobrien on 2/18/2018.
 */

public interface GAPIService {
    @GET("/repos/{owner}/{repo}/issues")
    Call<List<Issue>> getIssues(@Path("owner") String owner, @Path("repo") String repo);
}

