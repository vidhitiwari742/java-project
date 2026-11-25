package com.moviematrix;

import com.moviematrix.model.Genre;
import com.moviematrix.model.Movie;
import com.moviematrix.model.User;
import com.moviematrix.service.GenreBasedRecommendationStrategy;
import com.moviematrix.service.HybridRecommendationStrategy;
import com.moviematrix.service.RatingBasedRecommendationStrategy;
import com.moviematrix.service.RecommendationStrategy;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Pure in-memory tests for the recommendation algorithms (no DB required). */
class RecommendationStrategyTest {

    private final Movie scifi1 = new Movie(1, "Sci-Fi Low Rating", Genre.SCI_FI, 2010, "Dir", 6.0, "", "");
    private final Movie scifi2 = new Movie(2, "Sci-Fi High Rating", Genre.SCI_FI, 2015, "Dir", 9.0, "", "");
    private final Movie comedy1 = new Movie(3, "Comedy Movie", Genre.COMEDY, 2012, "Dir", 8.5, "", "");
    private final List<Movie> catalog = List.of(scifi1, scifi2, comedy1);

    @Test
    void ratingBasedStrategyRanksByRatingOnly() {
        RecommendationStrategy strategy = new RatingBasedRecommendationStrategy();
        User user = new User(1, "Test", "test@test.com", "hash");

        List<Movie> result = strategy.recommend(user, catalog, Collections.emptyList(), 3);

        assertEquals(scifi2, result.get(0)); // 9.0 rating, highest
        assertEquals(comedy1, result.get(1)); // 8.5 rating
        assertEquals(scifi1, result.get(2)); // 6.0 rating
    }

    @Test
    void genreBasedStrategyPrefersUsersFavoriteGenre() {
        RecommendationStrategy strategy = new GenreBasedRecommendationStrategy();
        User user = new User(1, "Test", "test@test.com", "hash");
        user.addGenrePreference(Genre.COMEDY, 10); // strongly prefers comedy

        List<Movie> result = strategy.recommend(user, catalog, Collections.emptyList(), 3);

        assertEquals(comedy1, result.get(0));
    }

    @Test
    void hybridStrategyExcludesAlreadyWatchedMovies() {
        RecommendationStrategy strategy = new HybridRecommendationStrategy();
        User user = new User(1, "Test", "test@test.com", "hash");
        user.addGenrePreference(Genre.SCI_FI, 5);

        List<Movie> result = strategy.recommend(user, catalog, List.of(scifi2), 3);

        assertFalse(result.contains(scifi2));
        assertTrue(result.contains(scifi1));
        assertTrue(result.contains(comedy1));
    }

    @Test
    void hybridStrategyRespectsLimit() {
        RecommendationStrategy strategy = new HybridRecommendationStrategy();
        User user = new User(1, "Test", "test@test.com", "hash");

        List<Movie> result = strategy.recommend(user, catalog, Collections.emptyList(), 2);

        assertEquals(2, result.size());
    }
}
