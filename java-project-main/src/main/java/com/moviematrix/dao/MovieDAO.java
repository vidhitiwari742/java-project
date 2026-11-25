package com.moviematrix.dao;

import com.moviematrix.model.Movie;

import java.sql.SQLException;
import java.util.List;

/** Data-access contract for movies. Separating the interface from the JDBC impl keeps callers DB-agnostic. */
public interface MovieDAO {

    List<Movie> findAll() throws SQLException;

    Movie findById(int id) throws SQLException;

    List<Movie> findByGenre(String genre) throws SQLException;

    List<Movie> searchByTitle(String keyword) throws SQLException;

    int insert(Movie movie) throws SQLException;

    void update(Movie movie) throws SQLException;

    void delete(int id) throws SQLException;
}
