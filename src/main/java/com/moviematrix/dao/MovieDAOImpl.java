package com.moviematrix.dao;

import com.moviematrix.model.Genre;
import com.moviematrix.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation of {@link MovieDAO} against the `movies` table. */
public class MovieDAOImpl implements MovieDAO {

    @Override
    public List<Movie> findAll() throws SQLException {
        String sql = "SELECT * FROM movies ORDER BY rating DESC";
        List<Movie> movies = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                movies.add(mapRow(rs));
            }
        }
        return movies;
    }

    @Override
    public Movie findById(int id) throws SQLException {
        String sql = "SELECT * FROM movies WHERE movie_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    @Override
    public List<Movie> findByGenre(String genre) throws SQLException {
        String sql = "SELECT * FROM movies WHERE genre = ? ORDER BY rating DESC";
        List<Movie> movies = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, genre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movies.add(mapRow(rs));
                }
            }
        }
        return movies;
    }

    @Override
    public List<Movie> searchByTitle(String keyword) throws SQLException {
        String sql = "SELECT * FROM movies WHERE title LIKE ? ORDER BY rating DESC";
        List<Movie> movies = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movies.add(mapRow(rs));
                }
            }
        }
        return movies;
    }

    @Override
    public int insert(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, genre, release_year, director, rating, description, poster_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, movie);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        String sql = "UPDATE movies SET title=?, genre=?, release_year=?, director=?, rating=?, "
                + "description=?, poster_url=? WHERE movie_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            bind(ps, movie);
            ps.setInt(8, movie.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void bind(PreparedStatement ps, Movie movie) throws SQLException {
        ps.setString(1, movie.getTitle());
        ps.setString(2, movie.getGenre().display());
        ps.setInt(3, movie.getReleaseYear());
        ps.setString(4, movie.getDirector());
        ps.setDouble(5, movie.getRating());
        ps.setString(6, movie.getDescription());
        ps.setString(7, movie.getPosterUrl());
    }

    private Movie mapRow(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getInt("movie_id"),
                rs.getString("title"),
                Genre.fromString(rs.getString("genre")),
                rs.getInt("release_year"),
                rs.getString("director"),
                rs.getDouble("rating"),
                rs.getString("description"),
                rs.getString("poster_url"));
    }
}
