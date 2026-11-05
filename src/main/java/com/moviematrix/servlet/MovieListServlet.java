package com.moviematrix.servlet;

import com.moviematrix.model.Movie;
import com.moviematrix.service.MovieService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** Lists the movie catalog, optionally filtered by search keyword or genre (handles /movies). */
@WebServlet("/movies")
public class MovieListServlet extends HttpServlet {

    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String keyword = req.getParameter("q");
        String genre = req.getParameter("genre");

        try {
            List<Movie> movies;
            if (genre != null && !genre.isBlank()) {
                movies = movieService.getMoviesByGenre(genre);
            } else {
                movies = movieService.searchMovies(keyword);
            }
            req.setAttribute("movies", movies);
            req.setAttribute("query", keyword);
            req.getRequestDispatcher("/movies.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load movies", e);
        }
    }
}
