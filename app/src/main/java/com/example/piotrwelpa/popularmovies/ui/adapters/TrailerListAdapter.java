package com.example.piotrwelpa.popularmovies.ui.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.databinding.TrailerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TrailerListAdapter extends
        RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Trailer trailer);
    }


    private List<Trailer> trailerList = new ArrayList<>();
    private final OnItemClickListener listener;

    public TrailerListAdapter(List<Trailer> trailerList, OnItemClickListener listener) {
        this.trailerList = trailerList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TrailerItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.trailer_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);
        holder.bind(trailer, listener);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TrailerItemBinding mBinding;

        ViewHolder(TrailerItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        void bind(final Trailer trailer, final OnItemClickListener listener) {
            mBinding.setTrailer(trailer);
            mBinding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(trailer);
                }
            });
        }
    }
}
