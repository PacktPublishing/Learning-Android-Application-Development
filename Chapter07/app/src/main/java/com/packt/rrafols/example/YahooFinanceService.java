package com.packt.rrafols.example;

import com.packt.rrafols.example.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YahooFinanceService {
    @GET("webservice/v1/symbols/{symbols}/quote?format=json")
    Call<Model> getQuote(@Path("symbols") String symbols);
}