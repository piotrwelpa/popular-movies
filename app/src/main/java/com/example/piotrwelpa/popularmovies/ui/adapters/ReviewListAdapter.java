package com.example.piotrwelpa.popularmovies.ui.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Review;
import com.example.piotrwelpa.popularmovies.databinding.ReviewItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends
        RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {


    private List<Review> reviewList = new ArrayList<>();

    public ReviewListAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.review_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private ReviewItemBinding mBinding;

        ViewHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }

        void bind(final Review review) {
            mBinding.setReview(review);
            mBinding.executePendingBindings();
        }
    }
}
