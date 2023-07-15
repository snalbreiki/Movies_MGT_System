/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class MovieStudio {

    private int id;
    private String name;
    private String address;
    private ArrayList<Movie> movies;

    public MovieStudio(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.movies = new ArrayList<>();

    }

    public MovieStudio(String name, String address) {
        this.name = name;
        this.address = address;
        this.movies = new ArrayList<>();
    }

    // Getters and setters for all attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
