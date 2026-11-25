package com.moviematrix;

import com.moviematrix.model.Genre;
import com.moviematrix.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieTest {

    @Test
    void moviesSortByHighestRatingFirst() {
        Movie low = new Movie(1, "Low", Genre.DRAMA, 2000, "Dir", 5.0, "", "");
        Movie high = new Movie(2, "High", Genre.DRAMA, 2001, "Dir", 9.0, "", "");
        Movie mid = new Movie(3, "Mid", Genre.DRAMA, 2002, "Dir", 7.0, "", "");

        List<Movie> movies = new ArrayList<>(List.of(low, high, mid));
        Collections.sort(movies);

        assertEquals(high, movies.get(0));
        assertEquals(mid, movies.get(1));
        assertEquals(low, movies.get(2));
    }

    @Test
    void moviesWithSameIdAreEqual() {
        Movie a = new Movie(1, "A", Genre.ACTION, 2000, "Dir", 5.0, "", "");
        Movie b = new Movie(1, "Different Title", Genre.COMEDY, 1999, "Other", 1.0, "", "");
        assertEquals(a, b);
    }

    @Test
    void genreFromStringHandlesHyphenAndSpacing() {
        assertEquals(Genre.SCI_FI, Genre.fromString("Sci-Fi"));
        assertEquals(Genre.SCI_FI, Genre.fromString("sci fi"));
        assertTrue(Genre.fromString("Unknown Genre") != null);
    }
}
