package com.example.lesson5animations;

import androidx.annotation.NonNull;

/**
 * Class MyMovie
 * ---------------------------------------------------------------------
 */
class MyMovie {
    // ----- Class members -------------------------------------------------
    /**
     * Movie title
     * <p>
     * Название фильма
     */
    public String title;

    /**
     * Movie genre
     * <p>
     * Жанр фильма
     */
    public String genre;

    /**
     * Year of movie release
     * <p>
     * Год выпуска фильма
     */
    public int year;

    // ----- Class methods -------------------------------------------------
    public MyMovie(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title : " + this.title + ", Genre : " +
                this.genre + ", Year : " + this.year;
    }
}