package com.moringaschool.mymovie.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.moringaschool.mymovie.models.Review;
import com.moringaschool.mymovie.models.ReviewApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moringaschool.mymovie.network.MovieService.API_KEY;

public class ReviewRepository {

    private List<Review> reviewList = new ArrayList<>();
    private final MutableLiveData<List<Review>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Review>> getMutableLiveData(String idOfMovie) {
        RetrofitClient.getInstance()
                .getMovieService().getReviews((idOfMovie), API_KEY)
                .enqueue(new Callback<ReviewApiResponse>() {
                    @Override
                    public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                        Log.v("onResponse", "Succeeded reviews");
                        if (response.body() != null) {
                            reviewList = response.body().getReviews();
                            mutableLiveData.setValue(reviewList);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                        Log.v("onFailure", "Failed to get Reviews");
                    }
                });

        return mutableLiveData;
    }
}
