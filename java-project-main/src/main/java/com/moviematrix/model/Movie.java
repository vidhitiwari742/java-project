package com.moviematrix.model;

import java.util.Objects;

/** Core domain entity representing a movie in the catalog. Implements {@link Comparable} for rating-based sorting. */
public class Movie implements Comparable<Movie> {

    private int id;
    private String title;
    private Genre genre;
    private int releaseYear;
    private String director;
    private double rating;
    private String description;
    private String posterUrl;

    public Movie(int id, String title, Genre genre, int releaseYear, String director,
                 double rating, String description, String posterUrl) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.director = director;
        this.rating = rating;
        this.description = description;
        this.posterUrl = posterUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    /** Natural ordering: highest rated first. */
    @Override
    public int compareTo(Movie other) {
        return Double.compare(other.rating, this.rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return title + " (" + releaseYear + ") [" + genre.display() + "] - " + rating + "/10";
    }
}
