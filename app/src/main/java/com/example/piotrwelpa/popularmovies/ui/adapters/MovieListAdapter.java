package com.example.piotrwelpa.popularmovies.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.ui.activities.MainActivity;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie> movieList;

    public MovieListAdapter(MainActivity mainActivity, List<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        Picasso.with(holder.imageView.getContext())
                .load(NetworkUtils.getImageUrl(movie.getPosterPath()))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.poster_iv);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

}