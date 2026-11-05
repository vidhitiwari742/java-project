package com.moviematrix.servlet;

import com.moviematrix.model.User;
import com.moviematrix.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            User user = userService.authenticate(email, password);
            if (user == null) {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/movies");
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
