/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

public class Actor {

    private int id;
    private String name;
    private String dateOfBirth;
    private ArrayList<Movie> movies;
    private String role;

    public Actor(int id, String name, String dateOfBirth, String role) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        movies = new ArrayList<>();
    }

  

    // Getters and setters for all attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Actor{" + "id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", movies=" + movies + ", role=" + role + '}';
    }
    
    
    
}
