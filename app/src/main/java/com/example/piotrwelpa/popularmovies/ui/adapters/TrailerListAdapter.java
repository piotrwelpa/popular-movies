package com.example.piotrwelpa.popularmovies.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;

import java.util.List;

public class TrailerListAdapter extends
        RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Trailer trailer);
    }


    private final List<Trailer> trailerList;
    private final OnItemClickListener listener;

    public TrailerListAdapter(List<Trailer> trailerList, OnItemClickListener listener) {
        this.trailerList = trailerList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.textView.setText(trailer.getName());

        holder.bind(trailer, listener);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.trailer_title_tv);

        }

        void bind(final Trailer trailer, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(trailer);
                }
            });
        }
    }
}
