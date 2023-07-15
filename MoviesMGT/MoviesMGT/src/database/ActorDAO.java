package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import model.Actor;
import model.Director;
import model.Movie;
import model.MovieStudio;

public class ActorDAO {

    public static void insertActor(Actor actor) {

        try {
            Connection connection = MysqlDB.getConnection();
            String query = "INSERT INTO actor (id,name, date_of_birth,role) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, actor.getId());
            statement.setString(2, actor.getName());
            statement.setString(3, actor.getDateOfBirth());
            statement.setString(4, actor.getRole());

            statement.executeUpdate();
            System.out.println("Actor inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting actor: " + e.getMessage());
        }
    }

    public static void deleteActor(int actorId) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "DELETE FROM actor WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, actorId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Actor deleted successfully!");
            } else {
                System.out.println("Actor not found or already deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting actor: " + e.getMessage());
        }
    }

    public static void updateActor(Actor actor) {
        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "UPDATE actor SET name = ?, date_of_birth = ?, role = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getDateOfBirth());
            statement.setString(3, actor.getRole());

            statement.setInt(4, actor.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Actor updated successfully!");
            } else {
                System.out.println("Actor not found or unable to update.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating actor: " + e.getMessage());
        }
    }

    public static ArrayList<Actor> getAllActors() {
        ArrayList<Actor> actors = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT id, name, date_of_birth,role FROM actor";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int actorId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String role = resultSet.getString("role");

                Actor actor = new Actor(actorId, name, dateOfBirth, role);

//                int movieId = resultSet.getInt("movie_id");
//                String movieTitle = resultSet.getString("title");
//
//                if (movieId != 0 && movieTitle != null) {
//                    Movie movie = new Movie(movieId, movieTitle);
//                    movie.setRole(role);
//                    actor.addMovie(movie);
//                }
                actors.add(actor);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving actors: " + e.getMessage());
        }
        
           HashSet<Actor> uniqueList = new HashSet<>(actors);

        // Create a new ArrayList with unique values
        ArrayList<Actor> newList = new ArrayList<>(uniqueList);

        return actors;
    }
    
     public static Actor getActorById(int actorId) {
        Actor actor = null;

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT name, date_of_birth, role FROM actor WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, actorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String role = resultSet.getString("role");

                actor = new Actor(actorId, name, dateOfBirth, role);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving actor: " + e.getMessage());
        }

        return actor;
    }
     
     
       public static ArrayList<Movie> getMoviesByActorId(int actorId) {
        ArrayList<Movie> movies = new ArrayList<>();

        try (Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT movie.movie_id, movie.title, movie.year_of_release, movie.genre, movie.running_time, movie.director_id, movie.movie_studio_id, movie.price FROM movie INNER JOIN movie_actor ON movie.movie_id = movie_actor.movie_id WHERE movie_actor.actor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, actorId);
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
            System.out.println("Error retrieving movies by actor ID: " + e.getMessage());
        }

        return movies;
    }
}

