package model;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private int id;
    private String title;
    private int yearOfRelease;
    private String genre;
    private int runningTime;
    private Director director;
    private List<Producer> producers;
    private ArrayList<Actor> actors;
    private MovieStudio movieStudio;
    private double price;

    public Movie(String title, int yearOfRelease, String genre, int runningTime, Director director, ArrayList<Producer> producers, ArrayList<Actor> actors, MovieStudio movieStudio, double price) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.director = director;
        this.producers = producers;
        this.actors = actors;
        this.movieStudio = movieStudio;
        this.price = price;
    }

    public Movie(int id, String title, int yearOfRelease, String genre, int runningTime, Director director, ArrayList<Producer> producers, ArrayList<Actor> actors, MovieStudio movieStudio, double price) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.director = director;
        this.producers = producers;
        this.actors = actors;
        this.movieStudio = movieStudio;
        this.price = price;
        this.id = id;
    }

    public Movie(int movieId, String title, int yearOfRelease, String genre, int runningTime, Director director, MovieStudio movieStudio, double price) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.director = director;
        this.movieStudio = movieStudio;
        this.price = price;
        this.id = movieId;

    }

    // Getters and setters for all attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public MovieStudio getMovieStudio() {
        return movieStudio;
    }

    public void setMovieStudio(MovieStudio movieStudio) {
        this.movieStudio = movieStudio;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
