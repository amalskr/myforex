package com.ceylonapz.myforex.util.services;

import com.ceylonapz.myforex.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ClientService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    static <S> S create(Class<S> service) {
        return retrofit.create(service);
    }
}
