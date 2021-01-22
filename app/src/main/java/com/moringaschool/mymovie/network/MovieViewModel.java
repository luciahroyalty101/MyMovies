package com.moringaschool.mymovie.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.moringaschool.mymovie.models.Movie;

public class MovieViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Movie>> moviePagedList;
    private LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    // Constructor
    public MovieViewModel() {

        // Get our database source factory
        movieDataSourceFactory movieDataSourceFactory = new movieDataSourceFactory();

        // Get the live database source from database source factory
        liveDataSource = movieDataSourceFactory.getMovieLiveDataSource();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())

                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE).build();

        // Build the paged list
        moviePagedList = (new LivePagedListBuilder(movieDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidateDataSource() {
    }

//    public void invalidateDataSource(){
//        MovieDataSource.invalidate();
//    }
}
