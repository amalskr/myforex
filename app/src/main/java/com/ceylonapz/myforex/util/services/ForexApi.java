package com.ceylonapz.myforex.util.services;

import com.ceylonapz.myforex.util.interfaces.ApiInterface;
import com.ceylonapz.myforex.util.interfaces.StatusInterface;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForexApi {

    private ApiInterface apiInterface;
    private static ForexApi forexApi;

    public static ForexApi getInstance() {
        if (forexApi == null) {
            forexApi = new ForexApi();
        }
        return forexApi;
    }

    private ForexApi() {
        apiInterface = ClientService.create(ApiInterface.class);
    }


    //GET LATEST FOREX
    public void getLatest(final StatusInterface statusInterface) {
        apiInterface.getLatestForex().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    statusInterface.success(response.body());
                } else {
                    statusInterface.fail("Internal Problem " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable erThrowable) {
                statusInterface.fail("Server Problem " + erThrowable.getMessage());
            }
        });
    }


    //GET HISTORICAL FOREX
    public void getHistorical(String lastDate, final StatusInterface statusInterface) {
        apiInterface.getHistoricalForex(lastDate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    statusInterface.success(response.body());
                } else {
                    statusInterface.fail("Internal Problem " + response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable erThrowable) {
                statusInterface.fail("Server Problem " + erThrowable.getMessage());
            }
        });
    }
}
