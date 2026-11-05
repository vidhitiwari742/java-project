package com.moviematrix.service;

import com.moviematrix.model.Genre;
import com.moviematrix.model.Movie;
import com.moviematrix.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default "AI" recommendation logic: blends genre-preference weight with the
 * movie's overall rating into a single score. This is the custom recommendation
 * logic referenced as the AI integration layer (no external API key required).
 */
public class HybridRecommendationStrategy implements RecommendationStrategy {

    private static final double GENRE_WEIGHT = 0.6;
    private static final double RATING_WEIGHT = 0.4;

    @Override
    public List<Movie> recommend(User user, List<Movie> catalog, List<Movie> alreadyWatched, int limit) {
        Map<Genre, Integer> preferences = user.getGenrePreferences();
        int maxPreference = preferences.values().stream().mapToInt(Integer::intValue).max().orElse(1);

        return catalog.stream()
                .filter(m -> !alreadyWatched.contains(m))
                .sorted(Comparator.comparingDouble((Movie m) -> score(m, preferences, maxPreference)).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private double score(Movie movie, Map<Genre, Integer> preferences, int maxPreference) {
        double genreScore = preferences.getOrDefault(movie.getGenre(), 0) / (double) Math.max(1, maxPreference);
        double ratingScore = movie.getRating() / 10.0;
        return GENRE_WEIGHT * genreScore + RATING_WEIGHT * ratingScore;
    }
}
