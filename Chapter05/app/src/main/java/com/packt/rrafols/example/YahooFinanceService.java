package com.packt.rrafols.example;

import com.packt.rrafols.example.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface YahooFinanceService {
    @Headers("User-Agent: Mozilla/5.0 (Linux; Android 6.0; MotoE2(4G-LTE)  Build/MPI24.65-39) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36")
    @GET("webservice/v1/symbols/{symbols}/quote?format=json")
    Call<Model> getQuote(@Path("symbols") String symbols);
}