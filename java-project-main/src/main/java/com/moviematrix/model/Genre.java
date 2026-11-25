package com.moviematrix.model;

/** Fixed set of supported movie genres. */
public enum Genre {
    ACTION, DRAMA, COMEDY, HORROR, ROMANCE, SCI_FI, THRILLER, ANIMATION, DOCUMENTARY, FANTASY;

    /** Parses free-form genre text (e.g. "Sci-Fi" from the DB) into an enum constant. */
    public static Genre fromString(String text) {
        if (text == null) {
            return DRAMA;
        }
        String normalized = text.trim().toUpperCase().replace("-", "_").replace(" ", "_");
        try {
            return Genre.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return DRAMA;
        }
    }

    public String display() {
        return switch (this) {
            case SCI_FI -> "Sci-Fi";
            default -> name().charAt(0) + name().substring(1).toLowerCase();
        };
    }
}
