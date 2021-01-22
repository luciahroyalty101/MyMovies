package com.moringaschool.mymovie.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.moringaschool.mymovie.models.Trailer;

import java.util.List;

public class TrailerViewModel extends ViewModel {

    private final TrailerRepository trailerRepository;

    public TrailerViewModel() {
        trailerRepository = new TrailerRepository();
    }

    public LiveData<List<Trailer>> getAllTrailers(String idOfMovie) {
        return trailerRepository.getMutableLiveData(idOfMovie);
    }

    public LiveData<List<Trailer>> getAllTrailers() {
        return null;
    }
}
