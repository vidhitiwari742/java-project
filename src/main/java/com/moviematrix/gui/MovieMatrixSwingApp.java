package com.moviematrix.gui;

import com.moviematrix.model.Movie;
import com.moviematrix.model.User;
import com.moviematrix.service.MovieService;
import com.moviematrix.service.RecommendationEngine;
import com.moviematrix.service.UserService;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

/**
 * Standalone Swing desktop client for Movie Matrix. Talks to MySQL directly
 * through the same service/DAO layer used by the web app (demonstrates a
 * GUI front end backed by JDBC, independent of the servlet container).
 */
public class MovieMatrixSwingApp extends JFrame {

    private final UserService userService = new UserService();
    private final MovieService movieService = new MovieService();
    private final RecommendationEngine recommendationEngine = new RecommendationEngine();

    private User currentUser;

    private final DefaultListModel<Movie> catalogModel = new DefaultListModel<>();
    private final DefaultListModel<Movie> recommendationModel = new DefaultListModel<>();
    private final JTextArea detailsArea = new JTextArea();

    public MovieMatrixSwingApp() {
        super("Movie Matrix - Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);

        if (!login()) {
            System.exit(0);
        }

        buildUI();
        loadCatalog();
    }

    private boolean login() {
        JTextField emailField = new JTextField("demo@moviematrix.com");
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 6, 6));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Login to Movie Matrix",
                JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return false;
        }

        try {
            currentUser = userService.authenticate(emailField.getText().trim(), new String(passwordField.getPassword()));
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login failed", JOptionPane.ERROR_MESSAGE);
                return login();
            }
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to database:\n" + e.getMessage(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void buildUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Catalog", buildCatalogTab());
        tabs.addTab("Recommended For You", buildRecommendationsTab());
        add(tabs, BorderLayout.CENTER);

        JLabel status = new JLabel("Logged in as " + currentUser.getName() + "  ", JLabel.RIGHT);
        status.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 10));
        add(status, BorderLayout.SOUTH);
    }

    private JPanel buildCatalogTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton watchButton = new JButton("Mark Selected as Watched");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(watchButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        JList<Movie> catalogList = new JList<>(catalogModel);
        catalogList.addListSelectionListener(e -> showDetails(catalogList.getSelectedValue()));
        panel.add(new JScrollPane(catalogList), BorderLayout.WEST);

        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setPreferredSize(new Dimension(400, 400));
        panel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> loadCatalog(searchField.getText()));
        watchButton.addActionListener(e -> {
            Movie selected = catalogList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a movie first.");
                return;
            }
            try {
                movieService.markAsWatched(currentUser.getId(), selected.getId());
                JOptionPane.showMessageDialog(this, "Marked \"" + selected.getTitle() + "\" as watched.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel buildRecommendationsTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JButton refreshButton = new JButton("Refresh Recommendations");
        panel.add(refreshButton, BorderLayout.NORTH);

        JList<Movie> recList = new JList<>(recommendationModel);
        panel.add(new JScrollPane(recList), BorderLayout.CENTER);

        refreshButton.addActionListener(e -> loadRecommendations());
        loadRecommendations();
        return panel;
    }

    private void loadCatalog() {
        loadCatalog(null);
    }

    private void loadCatalog(String keyword) {
        try {
            List<Movie> movies = movieService.searchMovies(keyword);
            catalogModel.clear();
            movies.forEach(catalogModel::addElement);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading movies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRecommendations() {
        try {
            List<Movie> recs = recommendationEngine.recommendFor(currentUser, 8);
            recommendationModel.clear();
            recs.forEach(recommendationModel::addElement);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading recommendations: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDetails(Movie movie) {
        if (movie == null) {
            detailsArea.setText("");
            return;
        }
        detailsArea.setText(
                movie.getTitle() + " (" + movie.getReleaseYear() + ")\n"
                        + "Genre: " + movie.getGenre().display() + "\n"
                        + "Director: " + movie.getDirector() + "\n"
                        + "Rating: " + movie.getRating() + "/10\n\n"
                        + movie.getDescription());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieMatrixSwingApp().setVisible(true));
    }
}
