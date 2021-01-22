package com.moringaschool.mymovie.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.moringaschool.mymovie.models.Trailer;
import com.moringaschool.mymovie.models.TrailerApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moringaschool.mymovie.network.MovieService.API_KEY;

public class TrailerRepository {

    private List<Trailer> trailerList = new ArrayList<>();
    private final MutableLiveData<List<Trailer>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Trailer>> getMutableLiveData(String idOfMovie) {
        RetrofitClient.getInstance()
                .getMovieService().getTrailers((idOfMovie), API_KEY)
                .enqueue(new Callback<TrailerApiResponse>() {
                    @Override
                    public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                        Log.v("onResponse", "Succeeded Trailers");
                        if (response.body() != null) {
                            trailerList = response.body().getTrailers();
                            mutableLiveData.setValue(trailerList);
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                        Log.v("onFailure", "Failed to get Trailers");
                    }
                });

        return mutableLiveData;
    }
}
