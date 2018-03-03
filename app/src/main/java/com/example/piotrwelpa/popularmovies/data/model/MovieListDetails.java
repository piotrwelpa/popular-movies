package com.example.piotrwelpa.popularmovies.data.model;

import java.util.List;

public class MovieListDetails {
    private Double page;
    private Double totalPages;
    private Double totalResult;
    private List<Movie> results;

    public Double getPage() {
        return page;
    }

    public void setPage(Double page) {
        this.page = page;
    }

    public Double getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Double totalPages) {
        this.totalPages = totalPages;
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

