package com.moviematrix;

import com.moviematrix.dao.UserDAO;
import com.moviematrix.model.User;
import com.moviematrix.service.UserService;
import com.moviematrix.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Exercises UserService business rules against an in-memory fake DAO (no live DB needed). */
class UserServiceTest {

    /** Simple in-memory stand-in for the JDBC-backed UserDAO, used only for testing. */
    static class InMemoryUserDAO implements UserDAO {
        private final Map<String, User> byEmail = new HashMap<>();
        private int nextId = 1;

        @Override
        public User findByEmail(String email) {
            return byEmail.get(email);
        }

        @Override
        public User findById(int id) {
            return byEmail.values().stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        }

        @Override
        public int insert(User user) {
            int id = nextId++;
            User stored = new User(id, user.getName(), user.getEmail(), user.getPasswordHash());
            byEmail.put(user.getEmail(), stored);
            return id;
        }

        @Override
        public boolean existsByEmail(String email) {
            return byEmail.containsKey(email);
        }
    }

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new InMemoryUserDAO());
    }

    @Test
    void registerCreatesUserWithHashedPassword() throws SQLException {
        User user = userService.register("Alice", "alice@test.com", "Password123");
        assertNotNull(user);
        assertEquals("alice@test.com", user.getEmail());
        assertEquals(PasswordUtil.hash("Password123"), user.getPasswordHash());
    }

    @Test
    void registerRejectsDuplicateEmail() throws SQLException {
        userService.register("Alice", "alice@test.com", "Password123");
        assertThrows(IllegalArgumentException.class,
                () -> userService.register("Alice2", "alice@test.com", "AnotherPass1"));
    }

    @Test
    void registerRejectsShortPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.register("Bob", "bob@test.com", "123"));
    }

    @Test
    void authenticateSucceedsWithCorrectPassword() throws SQLException {
        userService.register("Carol", "carol@test.com", "SecurePass1");
        User user = userService.authenticate("carol@test.com", "SecurePass1");
        assertNotNull(user);
        assertEquals("carol@test.com", user.getEmail());
    }

    @Test
    void authenticateFailsWithWrongPassword() throws SQLException {
        userService.register("Dave", "dave@test.com", "SecurePass1");
        User user = userService.authenticate("dave@test.com", "WrongPassword");
        assertNull(user);
    }

    @Test
    void authenticateFailsForUnknownEmail() throws SQLException {
        User user = userService.authenticate("ghost@test.com", "whatever1");
        assertNull(user);
    }
}
