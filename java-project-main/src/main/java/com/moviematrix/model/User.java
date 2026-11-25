package com.moviematrix.model;

import java.util.HashMap;
import java.util.Map;

/** Registered platform user. Extends {@link Person} (inheritance). */
public class User extends Person {

    private String passwordHash;
    /** genre -> preference weight, built from watch history and reviews */
    private final Map<Genre, Integer> genrePreferences = new HashMap<>();

    public User(int id, String name, String email, String passwordHash) {
        super(id, name, email);
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Map<Genre, Integer> getGenrePreferences() {
        return genrePreferences;
    }

    public void addGenrePreference(Genre genre, int weight) {
        genrePreferences.merge(genre, weight, Integer::sum);
    }

    @Override
    public String getRole() {
        return "USER";
    }
}
