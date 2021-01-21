package com.moringaschool.mymovie.network;

import com.moringaschool.mymovie.models.MovieApiResponse;
import com.moringaschool.mymovie.models.ReviewApiResponse;
import com.moringaschool.mymovie.models.TrailerApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    String API_KEY = "38823f2acec65e7d47bfe0cd5731eb19";

    @GET("movie/{sort}")
    Call<MovieApiResponse> getMovies(@Path("sort") String sortBy, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieApiResponse> searchForMovies(@Query("query") String query ,@Query("api_key") String apiKey);

    @GET("movie/{movieId}/videos")
    Call<TrailerApiResponse> getTrailers(@Path("movieId") String movieId , @Query("api_key") String apiKey);

    @GET("movie/{movieId}/reviews")
    Call<ReviewApiResponse> getReviews(@Path("movieId") String movieId , @Query("api_key") String apiKey);
}
