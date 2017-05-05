package com.android.charlydemo.github;

import android.content.Context;
import com.android.charly.LogcatLoggerInterceptor;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jdandrade on 4/23/2017.
 */

public class Repository {
  public void makeRequest(Context activityContext) {

    final Context context = activityContext;
    new Thread(new Runnable() {
      @Override public void run() {
        OkHttpClient defaultHttpClient =
            new OkHttpClient.Builder().addInterceptor(new LogcatLoggerInterceptor(context)).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> repos = service.listRepos("octocat");

        try {
          final Response<List<Repo>> execute = repos.execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private interface GitHubService {
    @GET("users/{user}/repos") Call<List<Repo>> listRepos(@Path("user") String user);
  }
}
