package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Director;
import model.Movie;
import model.MovieStudio;

public class MovieStudioDAO {
    public static void createMovieStudio(MovieStudio movieStudio) {
        try (Connection connection = MysqlDB.getConnection()) {
            String query = "INSERT INTO movie_studio (name, address) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movieStudio.getName());
            statement.setString(2, movieStudio.getAddress());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie studio created successfully!");
            } else {
                System.out.println("Failed to create movie studio.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating movie studio: " + e.getMessage());
        }
    }

    public static void deleteMovieStudio(int movieStudioId) {
        try (Connection connection = MysqlDB.getConnection()) {
            String query = "DELETE FROM movie_studio WHERE studio_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieStudioId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie studio deleted successfully!");
            } else {
                System.out.println("Movie studio not found or already deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting movie studio: " + e.getMessage());
        }
    }

    public static void updateMovieStudio(MovieStudio movieStudio) {
        try (Connection connection = MysqlDB.getConnection()) {
            String query = "UPDATE movie_studio SET name = ?, address = ? WHERE studio_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movieStudio.getName());
            statement.setString(2, movieStudio.getAddress());
            statement.setInt(3, movieStudio.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie studio updated successfully!");
            } else {
                System.out.println("Movie studio not found or unable to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating movie studio: " + e.getMessage());
        }
    }

    public static ArrayList<MovieStudio> getAllMovieStudios() {
        ArrayList<MovieStudio> movieStudios = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT studio_id, name, address FROM movie_studio";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int studioId = resultSet.getInt("studio_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                MovieStudio movieStudio = new MovieStudio(studioId, name, address);
                movieStudios.add(movieStudio);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movie studios: " + e.getMessage());
        }

        return movieStudios;
    }
    
     public static MovieStudio getMovieStudioById(int studioId) {
        MovieStudio movieStudio = null;

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT name, address FROM movie_studio WHERE studio_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studioId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                movieStudio = new MovieStudio(studioId, name, address);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movie studio: " + e.getMessage());
        }

        return movieStudio;
    }
     
     
     public static ArrayList<Movie> getMoviesByMovieStudioId(int movieStudioId) {
        ArrayList<Movie> movies = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT movie.movie_id, movie.title, movie.year_of_release, movie.genre, movie.running_time, movie.director_id, movie.movie_studio_id, movie.price FROM movie WHERE movie.movie_studio_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieStudioId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int yearOfRelease = resultSet.getInt("year_of_release");
                String genre = resultSet.getString("genre");
                int runningTime = resultSet.getInt("running_time");
                int directorId = resultSet.getInt("director_id");
                double price = resultSet.getDouble("price");

                // Retrieve director object
                Director director = DirectorDAO.getDirectorById(directorId);

                // Retrieve movie studio object
                MovieStudio movieStudio = getMovieStudioById(movieStudioId);

                // Create Movie object
                Movie movie = new Movie(movieId, title, yearOfRelease, genre, runningTime, director, movieStudio, price);

                // Add movie to the list
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies by movie studio ID: " + e.getMessage());
        }

        return movies;
    }
}
