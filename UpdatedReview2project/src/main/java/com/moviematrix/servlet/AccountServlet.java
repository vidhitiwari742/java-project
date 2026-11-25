package com.moviematrix.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moviematrix.dao.ReviewDAO;
import com.moviematrix.dao.ReviewDAOImpl;
import com.moviematrix.dao.WatchHistoryDAO;
import com.moviematrix.dao.WatchHistoryDAOImpl;
import com.moviematrix.model.Movie;
import com.moviematrix.model.Review;
import com.moviematrix.model.User;
import com.moviematrix.model.WatchHistoryEntry;
import com.moviematrix.service.MovieService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/dashboard", "/watched", "/my-reviews", "/settings"})
public class AccountServlet extends HttpServlet {

    private final MovieService movieService = new MovieService();
    private final WatchHistoryDAO watchHistoryDAO = new WatchHistoryDAOImpl();
    private final ReviewDAO reviewDAO = new ReviewDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        try {
            List<WatchHistoryEntry> history = watchHistoryDAO.findByUser(user.getId());
            List<Review> myReviews = reviewDAO.findByUser(user.getId());
            List<Movie> watchedMovies = new ArrayList<>();

            for (WatchHistoryEntry entry : history) {
                Movie m = movieService.getMovie(entry.getMovieId());
                if (m != null) {
                    watchedMovies.add(m);
                }
            }

            req.setAttribute("watchedCount", watchedMovies.size());
            req.setAttribute("reviewCount", myReviews.size());
            req.setAttribute("watchedMovies", watchedMovies);
            req.setAttribute("myReviews", myReviews);

            switch (path) {
                case "/watched" -> req.getRequestDispatcher("/watched.jsp").forward(req, resp);
                case "/my-reviews" -> req.getRequestDispatcher("/myReviews.jsp").forward(req, resp);
                case "/settings" -> req.getRequestDispatcher("/settings.jsp").forward(req, resp);
                default -> req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Error loading account data", e);
        }
    }
}