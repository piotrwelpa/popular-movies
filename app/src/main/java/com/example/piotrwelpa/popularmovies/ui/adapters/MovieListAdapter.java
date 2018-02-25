package com.example.piotrwelpa.popularmovies.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    private final List<Movie> movieList;
    private final OnItemClickListener listener;

    public MovieListAdapter(List<Movie> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
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
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.default_image)
                .into(holder.imageView);

        holder.bind(movie, listener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster_iv);
        }

        public void bind(final Movie movie, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(movie);
                }
            });
        }
    }

}


