package com.example.piotrwelpa.popularmovies.data.model;

import java.util.List;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public class MovieListDetails {
    private int page;
    private int toatlPages;
    private int totalResult;
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getToatlPages() {
        return toatlPages;
    }

    public void setToatlPages(int toatlPages) {
        this.toatlPages = toatlPages;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}

