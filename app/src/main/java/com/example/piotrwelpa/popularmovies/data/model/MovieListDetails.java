package com.example.piotrwelpa.popularmovies.data.model;

import java.util.List;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public class MovieListDetails {
    private Double page;
    private Double toatlPages;
    private Double totalResult;
    private List<Movie> results;

    public Double getPage() {
        return page;
    }

    public void setPage(Double page) {
        this.page = page;
    }

    public Double getToatlPages() {
        return toatlPages;
    }

    public void setToatlPages(Double toatlPages) {
        this.toatlPages = toatlPages;
    }

    public Double getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Double totalResult) {
        this.totalResult = totalResult;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}

