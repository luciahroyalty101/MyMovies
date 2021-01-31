package com.moringaschool.mymovie.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.mymovie.R;
import com.moringaschool.mymovie.models.MovieApiResponse;

import java.util.ArrayList;

public class FirebaseMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    View mView;
    Context mContext;


    public FirebaseMovieViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bind(MovieApiResponse movieResponse) {

        TextView nameTextView = (TextView) mView.findViewById(R.id.listOfReviews);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.listOfTrailers);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.titleOfMovie);

    }

    @Override
    public void onClick(View view) {
        final ArrayList<MovieApiResponse> countriesResponses = new ArrayList<>();

    }

}