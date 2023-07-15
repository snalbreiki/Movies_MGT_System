package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Director;
import model.Movie;
import model.MovieStudio;
import model.Producer;

public class ProducerDAO {

    public static void createProducer(Producer producer) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "INSERT INTO producer (producer_id, name, date_of_birth) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, producer.getId());
            statement.setString(2, producer.getName());
            statement.setString(3, producer.getDateOfBirth());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producer created successfully!");
            } else {
                System.out.println("Failed to create producer.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating producer: " + e.getMessage());
        }
    }

    public static void deleteProducer(int producerId) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "DELETE FROM producer WHERE producer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, producerId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producer deleted successfully!");
            } else {
                System.out.println("Producer not found or already deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting producer: " + e.getMessage());
        }
    }

    public static void updateProducer(Producer producer) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "UPDATE producer SET name = ?, date_of_birth = ? WHERE producer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, producer.getName());
            statement.setString(2, producer.getDateOfBirth());
            statement.setInt(3, producer.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Producer updated successfully!");
            } else {
                System.out.println("Producer not found or unable to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating producer: " + e.getMessage());
        }
    }

    public static ArrayList<Producer> getAllProducers() {
        ArrayList<Producer> producers = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT producer_id, name, date_of_birth FROM producer";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int producerId = resultSet.getInt("producer_id");
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");

                Producer producer = new Producer(producerId, name, dateOfBirth);
                producers.add(producer);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving producers: " + e.getMessage());
        }

        return producers;
    }
    
     public static Producer getProducerById(int producerId) {
        Producer producer = null;

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT name, date_of_birth FROM producer WHERE producer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, producerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");

                producer = new Producer(producerId, name, dateOfBirth);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving producer: " + e.getMessage());
        }

        return producer;
    }
     
     
     public static ArrayList<Movie> getMoviesByProducerId(int producerId) {
        ArrayList<Movie> movies = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT movie.movie_id, movie.title, movie.year_of_release, movie.genre, movie.running_time, movie.director_id, movie.movie_studio_id, movie.price FROM movie INNER JOIN movie_producer ON movie.movie_id = movie_producer.movie_id WHERE movie_producer.producer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, producerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int yearOfRelease = resultSet.getInt("year_of_release");
                String genre = resultSet.getString("genre");
                int runningTime = resultSet.getInt("running_time");
                int directorId = resultSet.getInt("director_id");
                int movieStudioId = resultSet.getInt("movie_studio_id");
                double price = resultSet.getDouble("price");

                // Retrieve director object
                Director director = DirectorDAO.getDirectorById(directorId);

                // Retrieve movie studio object
                MovieStudio movieStudio = MovieStudioDAO.getMovieStudioById(movieStudioId);

                // Create Movie object
                Movie movie = new Movie(movieId, title, yearOfRelease, genre, runningTime, director, movieStudio, price);

                // Add movie to the list
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies by producer ID: " + e.getMessage());
        }

        return movies;
    }
     
}
