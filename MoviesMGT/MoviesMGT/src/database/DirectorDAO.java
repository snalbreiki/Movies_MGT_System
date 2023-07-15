/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Director;
import model.Movie;
import model.MovieStudio;

public class DirectorDAO {

    public static void createDirector(Director director) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "INSERT INTO director (director_id, name, date_of_birth) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, director.getId());
            statement.setString(2, director.getName());
            statement.setString(3, director.getDateOfBirth());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Director created successfully!");
            } else {
                System.out.println("Failed to create director.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating director: " + e.getMessage());
        }
    }

    public static void deleteDirector(int directorId) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "DELETE FROM director WHERE director_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, directorId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Director deleted successfully!");
            } else {
                System.out.println("Director not found or already deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting director: " + e.getMessage());
        }
    }

    public static void updateDirector(Director director) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "UPDATE director SET name = ?, date_of_birth = ? WHERE director_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, director.getName());
            statement.setString(2, director.getDateOfBirth());
            statement.setInt(3, director.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Director updated successfully!");
            } else {
                System.out.println("Director not found or unable to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating director: " + e.getMessage());
        }
    }

    public static ArrayList<Director> getAllDirectors() {
        ArrayList<Director> directors = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT director_id, name, date_of_birth FROM director";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int directorId = resultSet.getInt("director_id");
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");

                Director director = new Director(directorId, name, dateOfBirth);
                directors.add(director);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving directors: " + e.getMessage());
        }

        return directors;
    }

    public static Director getDirectorById(int directorId) {
        Director director = null;

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT name, date_of_birth FROM director WHERE director_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, directorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");

                director = new Director(directorId, name, dateOfBirth);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving director: " + e.getMessage());
        }

        return director;
    }

    public static ArrayList<Movie> getMoviesByDirectorId(int directorId) {
        ArrayList<Movie> movies = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT movie.movie_id, movie.title, movie.year_of_release, movie.genre, movie.running_time, movie.director_id, movie.movie_studio_id, movie.price FROM movie WHERE movie.director_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, directorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int yearOfRelease = resultSet.getInt("year_of_release");
                String genre = resultSet.getString("genre");
                int runningTime = resultSet.getInt("running_time");
                int movieStudioId = resultSet.getInt("movie_studio_id");
                double price = resultSet.getDouble("price");

                // Retrieve director object
                Director director = getDirectorById(directorId);

                // Retrieve movie studio object
                MovieStudio movieStudio = MovieStudioDAO.getMovieStudioById(movieStudioId);

                // Create Movie object
                Movie movie = new Movie(movieId, title, yearOfRelease, genre, runningTime, director, movieStudio, price);

                // Add movie to the list
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies by director ID: " + e.getMessage());
        }

        return movies;
    }
}
