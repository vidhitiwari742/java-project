package com.moviematrix.model;

/** Administrative user who can manage the movie catalog. Demonstrates polymorphism via {@link #getRole()}. */
public class Admin extends Person {

    private String passwordHash;

    public Admin(int id, String name, String email, String passwordHash) {
        super(id, name, email);
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
