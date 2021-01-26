package com.moringaschool.mymovie.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String FIREBASE_CHILD_Email = "email";

    public static final String FIREBASE_CHILD_Password = "password";

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public MovieService getMovieService() {
        return retrofit.create(MovieService.class);
    }
}
