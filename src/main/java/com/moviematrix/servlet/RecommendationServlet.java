package com.moviematrix.servlet;

import com.moviematrix.model.Movie;
import com.moviematrix.model.User;
import com.moviematrix.service.RecommendationEngine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/** Serves the AI-style personalized recommendation page for the logged-in user. */
@WebServlet("/recommendations")
public class RecommendationServlet extends HttpServlet {

    private final RecommendationEngine recommendationEngine = new RecommendationEngine();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<Movie> recommendations = recommendationEngine.recommendFor(user, 8);
            req.setAttribute("recommendations", recommendations);
            req.getRequestDispatcher("/recommendations.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to build recommendations", e);
        }
    }
}
