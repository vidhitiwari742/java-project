package com.moviematrix.servlet;

import com.moviematrix.model.Movie;
import com.moviematrix.model.User;
import com.moviematrix.service.MovieService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/** Shows a single movie plus its reviews, and accepts new review submissions / "mark as watched". */
@WebServlet("/movie")
public class MovieDetailsServlet extends HttpServlet {

    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int movieId = Integer.parseInt(req.getParameter("id"));
        try {
            Movie movie = movieService.getMovie(movieId);
            if (movie == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
                return;
            }
            req.setAttribute("movie", movie);
            req.setAttribute("reviews", movieService.getReviews(movieId));
            req.getRequestDispatcher("/movieDetails.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load movie details", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int movieId = Integer.parseInt(req.getParameter("movieId"));
        String action = req.getParameter("action");

        try {
            if ("watch".equals(action)) {
                movieService.markAsWatched(user.getId(), movieId);
            } else {
                double rating = Double.parseDouble(req.getParameter("rating"));
                String comment = req.getParameter("comment");
                movieService.submitReview(user.getId(), movieId, rating, comment);
            }
            resp.sendRedirect(req.getContextPath() + "/movie?id=" + movieId);
        } catch (SQLException | IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}
