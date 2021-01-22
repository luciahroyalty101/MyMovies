package com.moringaschool.mymovie.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.moringaschool.mymovie.models.Movie;

import java.util.List;

public class MovieRoomViewModel extends AndroidViewModel {
    
    private MovieRepository mRepository;

    private LiveData<List<Movie>> mAllMovies;

    public MovieRoomViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mAllMovies;
    }

    public void insert(Movie movie) {
        mRepository.insert(movie);
    }

    public void delete(Movie movie) {
        mRepository.delete(movie);
    }

    public void deleteById(int movieId) {
        mRepository.deleteById(movieId);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
