package com.example.bskl_kotlin.fragment.attendance;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;
    //    private static final String BASE_URL = "https://beta.mobatia.in:8888/nas-dubai-development/";
    private static final String BASE_URL = "http://gama.mobatia.in:8080/BISKL2023/public/Api-V1/";
//    private static final String BASE_URL = "https://cms.nasdubai.ae/nais/";
//    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



        return retrofit;
    }

}
