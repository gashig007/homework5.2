package com.geektech.homework52;

import android.app.Application;

import com.geektech.homework52.data.remote.PostApi;
import com.geektech.homework52.data.remote.PostApiClient;
import com.geektech.homework52.data.remote.RetrofitClient;

public class App extends Application {
    private RetrofitClient retrofitClient;
    public static PostApi api;
    public static PostApiClient apiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api = retrofitClient.createApi();
        apiClient = new PostApiClient(api);
    }
}
