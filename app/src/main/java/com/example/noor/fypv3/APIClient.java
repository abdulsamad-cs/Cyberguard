package com.example.noor.fypv3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String BASE_URL="https://cyberguard.000webhostapp.com/cyberguard/";
    public static final String BASE_URL_FOR_AI_MODULE="https://cyberguard1.herokuapp.com/";
    public static Retrofit retrofit = null;
    public static Retrofit retrofitForAiModule=null;

    public static Retrofit getApiClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }

    public static  Retrofit getApiClientForAiModule(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofitForAiModule==null){
            retrofitForAiModule = new Retrofit.Builder().baseUrl(BASE_URL_FOR_AI_MODULE).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofitForAiModule;
    }
}
