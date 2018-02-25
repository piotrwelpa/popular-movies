package com.example.piotrwelpa.popularmovies.ui.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.databinding.ActivityMovieDetailBinding;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_KEY);

        setTitle("Movie details");
        populateUI(movie);

        String jsonString = "{\"id\":19404,\"results\":[{\"id\":\"5581bd68c3a3685df70000c6\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"c25GKl5VNeY\",\"name\":\"Trailer 1\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Trailer\"},{\"id\":\"58e9bfb6925141351f02fde0\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"Y9JvS2TmSvA\",\"name\":\"Mere Khwabon Mein - song by CinePlusPlus\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Clip\"},{\"id\":\"58e9bf11c3a36872ee070b9a\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"H74COj0UQ_Q\",\"name\":\"Zara Sa Jhoom Loon Main - song by CinePlusPlus\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Clip\"},{\"id\":\"58e9c00792514152ac020a34\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"OkjXMqK1G0o\",\"name\":\"Ho Gaya Hai Tujhko To Pyar Sajna - song by CinePlusPlus\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Clip\"},{\"id\":\"58e9c034c3a36872ee070c84\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"7NhoeyoR_XA\",\"name\":\"Mehndi Laga Ke Rakhna - song by CinePlusPlus\",\"site\":\"YouTube\",\"size\":720,\"type\":\"Clip\"},{\"id\":\"58e9c07f9251414b2802a16e\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"Ee-cCwP7VPQ\",\"name\":\"Tujhe dekha to  Ye Jaana Sanam - song by CinePlus\",\"site\":\"YouTube\",\"size\":480,\"type\":\"Clip\"}]}";

        try {
            TrailerList trailers = MovieMapper.parseTrailerJsonToTrailerList(jsonString);
            Trailer trailer = trailers.getResults().get(0);
            Log.d("JSON MAPPER JACKSON: ", trailer.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void populateUI(Movie movie) {
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.setMovie(movie);
    }
}
