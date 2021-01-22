package com.moringaschool.mymovie.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.moringaschool.mymovie.models.Review;

import java.util.List;

public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;
    public LiveData<List<Review>> getAllReviews;

    public ReviewViewModel() {
        reviewRepository = new ReviewRepository();
    }

    public LiveData<List<Review>> getAllReviews(String idOfMovie) {
        return reviewRepository.getMutableLiveData(idOfMovie);
    }
}
