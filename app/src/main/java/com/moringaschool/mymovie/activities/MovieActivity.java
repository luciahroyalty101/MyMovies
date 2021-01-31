package com.moringaschool.mymovie.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.moringaschool.mymovie.R;
import com.moringaschool.mymovie.adapters.ReviewAdapter;
import com.moringaschool.mymovie.adapters.TrailerAdapter;
import com.moringaschool.mymovie.database.MovieRoomViewModel;
import com.moringaschool.mymovie.models.Movie;
import com.moringaschool.mymovie.models.Review;
import com.moringaschool.mymovie.models.Trailer;
import com.moringaschool.mymovie.network.ReviewViewModel;
import com.moringaschool.mymovie.network.TrailerViewModel;
import com.moringaschool.mymovie.utils.Genres;
import com.moringaschool.mymovie.utils.SharedPreferencesUtils;

import java.util.List;

import static com.moringaschool.mymovie.activities.FavoriteActivity.isFavoriteActivityRunning;
import static com.moringaschool.mymovie.utils.Constant.IMAGE_URL;
import static com.moringaschool.mymovie.utils.Constant.MOVIE;
import static com.moringaschool.mymovie.utils.Utility.formatDate;


public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private RecyclerView reviewsRecyclerView, trailersRecyclerView;

    private ReviewViewModel reviewViewModel;
    private TrailerViewModel trailerViewModel;
    private MovieRoomViewModel movieRoomViewModel;

    public static String idOfMovie;
    private String title;
    private String formattedDate;
    private String vote;
    private String description;
    private String language;
    private String poster;
    private String backDrop;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all);

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        trailerViewModel = new ViewModelProvider(this).get(TrailerViewModel.class);
        movieRoomViewModel = new ViewModelProvider(this).get(MovieRoomViewModel.class);

        receiveMovieDetails();
        getReviews();
        getTrailers();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite();
            }
        });
        binding.txtSeaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSeeAllActivity();
            }
        });
    }

    private void setupRecyclerViews() {
        // Trailers
        trailersRecyclerView = findViewById(R.id.listOfTrailers);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Reviews
        int listOfReviews = 0;
        reviewsRecyclerView = findViewById(listOfReviews);
//        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @SuppressLint("StringFormatInvalid")
    private void receiveMovieDetails() {
        // Receive the movie object
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(MOVIE);

        idOfMovie = movie.getMovieId();
        title = movie.getMovieTitle();
        vote = movie.getMovieVote();
        description = movie.getMovieDescription();
        language = movie.getMovieLanguage();
        backDrop = movie.getMovieBackdrop();
        poster = movie.getMoviePoster();

        binding.titleOfMovie.setText(title);
        binding.ratingOfMovie.setText(vote);
        binding.descriptionOfMovie.setText(description);

        if (isFavoriteActivityRunning) {
            binding.releaseDateOfMovie.setText(movie.getMovieReleaseDate());
        } else {
            formattedDate = getString(R.string.Date, formatDate(movie.getMovieReleaseDate()));
            binding.releaseDateOfMovie.setText(formattedDate);
        }

        binding.languageOfMovie.setText(language);

        Glide.with(this)
                .load(IMAGE_URL + backDrop)
                .into(binding.backdropImage);

        getGenres();

        if (!isNetworkConnected()) {
            trailersRecyclerView.setVisibility(View.GONE);
            binding.noTrailers.setVisibility(View.VISIBLE);

            reviewsRecyclerView.setVisibility(View.GONE);
            binding.noReviews.setVisibility(View.VISIBLE);
        }

        // If movie is inserted
        if (SharedPreferencesUtils.getInsertState(this, idOfMovie)) {
//            binding.fab.setImageResource(R.drawable.favorite_red);
        }
    }

    public void getTrailers() {
        trailerViewModel.getAllTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                trailerAdapter = new TrailerAdapter(getApplicationContext(), trailers);

                if (trailers != null && trailers.isEmpty()) {
                    trailersRecyclerView.setVisibility(View.GONE);
                    binding.noTrailers.setVisibility(View.VISIBLE);
                }

                trailersRecyclerView.setAdapter(trailerAdapter);
            }
        });
    }

    public void getReviews() {
        reviewViewModel.getAllReviews.observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                reviewAdapter = new ReviewAdapter(getApplicationContext(), reviews);

                if (reviews != null && reviews.isEmpty()) {
                    reviewsRecyclerView.setVisibility(View.GONE);
                    binding.noReviews.setVisibility(View.VISIBLE);
                }

                reviewsRecyclerView.setAdapter(reviewAdapter);
            }
        });
    }

    private void getGenres() {
        int genre_one = 0;
        int genre_two = 0;
        int genre_three = 0;

        try {
            // get keys
            genre_one = Integer.parseInt(String.valueOf(movie.getGenreIds().get(0)));
            genre_two = Integer.parseInt(String.valueOf(movie.getGenreIds().get(1)));
            genre_three = Integer.parseInt(String.valueOf(movie.getGenreIds().get(2)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // get values
        binding.genreOne.setText(Genres.getRealGenres().get(genre_one));
        binding.genreTwo.setText(Genres.getRealGenres().get(genre_two));
        binding.genreThree.setText(Genres.getRealGenres().get(genre_three));
    }

    private void toggleFavourite() {
        // If movie is not bookmarked
        if (!SharedPreferencesUtils.getInsertState(this, idOfMovie)) {
//            binding.fab.setImageResource(R.drawable.favorite_red);
            insertFavoriteMovie();
            SharedPreferencesUtils.setInsertState(this, idOfMovie, true);
            showSnackBar("Bookmark Added");
        } else {
//            binding.fab.setImageResource(R.drawable.favorite_border_red);
            deleteFavoriteMovieById();
            SharedPreferencesUtils.setInsertState(this, idOfMovie, false);
            showSnackBar("Bookmark Removed");
        }
    }

    private void insertFavoriteMovie() {
        movie = new Movie(idOfMovie, title, vote, description, formattedDate, language, poster, backDrop, movie.getGenreIds());
        movieRoomViewModel.insert(movie);
    }

    private void deleteFavoriteMovieById() {
        movieRoomViewModel.deleteById(Integer.parseInt(idOfMovie));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    private void goToSeeAllActivity() {
        Intent intent = new Intent(this, SeeAllActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return(super.onOptionsItemSelected(item));
    }
}
