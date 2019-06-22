package com.ceylonapz.myforex.util.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/latest")
    Call<JsonObject> getLatestForex();

    @GET("/{lastDate}")
    Call<JsonObject> getHistoricalForex(@Path("lastDate") String lastDate);
}
