package com.moviematrix;

import com.moviematrix.util.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordUtilTest {

    @Test
    void hashIsDeterministic() {
        String hash1 = PasswordUtil.hash("Password123");
        String hash2 = PasswordUtil.hash("Password123");
        assertEquals(hash1, hash2);
    }

    @Test
    void hashIsSixtyFourHexChars() {
        String hash = PasswordUtil.hash("anything");
        assertEquals(64, hash.length());
    }

    @Test
    void matchesReturnsTrueForCorrectPassword() {
        String hash = PasswordUtil.hash("Password123");
        assertTrue(PasswordUtil.matches("Password123", hash));
    }

    @Test
    void matchesReturnsFalseForWrongPassword() {
        String hash = PasswordUtil.hash("Password123");
        assertFalse(PasswordUtil.matches("WrongPassword", hash));
    }
}
