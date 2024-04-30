package com.xyz.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Genre {

    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    @JsonValue
    public String getGenre() {
        return genre;
    }

    @JsonCreator
    public static Genre fromValue(String value) {
        return Genre.valueOf(value);
    }
}
