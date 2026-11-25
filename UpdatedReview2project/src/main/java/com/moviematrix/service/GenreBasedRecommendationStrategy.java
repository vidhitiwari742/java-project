package com.moviematrix.service;

import com.moviematrix.model.Genre;
import com.moviematrix.model.Movie;
import com.moviematrix.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Ranks movies by how strongly their genre matches the user's tracked genre preferences. */
public class GenreBasedRecommendationStrategy implements RecommendationStrategy {

    @Override
    public List<Movie> recommend(User user, List<Movie> catalog, List<Movie> alreadyWatched, int limit) {
        Map<Genre, Integer> preferences = user.getGenrePreferences();

        return catalog.stream()
                .filter(m -> !alreadyWatched.contains(m))
                .sorted(Comparator
                        .comparingInt((Movie m) -> preferences.getOrDefault(m.getGenre(), 0))
                        .thenComparingDouble(Movie::getRating)
                        .reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
