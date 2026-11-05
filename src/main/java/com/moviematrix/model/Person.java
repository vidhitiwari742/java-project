package com.moviematrix.model;

import java.util.Objects;

/**
 * Abstract base type shared by all people in the system.
 * Demonstrates encapsulation (private fields + accessors) and abstraction (abstract class).
 */
public abstract class Person {

    protected int id;
    protected String name;
    protected String email;

    protected Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** Every concrete person type must describe its role. */
    public abstract String getRole();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return id == person.id && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return getRole() + "{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
