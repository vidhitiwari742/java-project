package com.moviematrix.service;

import com.moviematrix.model.Movie;
import com.moviematrix.model.User;

import java.util.List;
import java.util.stream.Collectors;

/** Simple fallback strategy: highest overall rated movies not yet watched. Useful for new users with no history. */
public class RatingBasedRecommendationStrategy implements RecommendationStrategy {

    @Override
    public List<Movie> recommend(User user, List<Movie> catalog, List<Movie> alreadyWatched, int limit) {
        return catalog.stream()
                .filter(m -> !alreadyWatched.contains(m))
                .sorted() // Movie's natural ordering: highest rating first
                .limit(limit)
                .collect(Collectors.toList());
    }
}
