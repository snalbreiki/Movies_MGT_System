package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Actor;
import model.Director;
import model.Movie;
import model.MovieStudio;
import model.Producer;

public class MovieDAO {

    public static void addMovie(Movie movie) {
        try ( Connection connection = MysqlDB.getConnection()) {
            // Insert into the movie table
            String movieQuery = "INSERT INTO movie (title, year_of_release, genre, running_time, director_id, movie_studio_id, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement movieStatement = connection.prepareStatement(movieQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            movieStatement.setString(1, movie.getTitle());
            movieStatement.setInt(2, movie.getYearOfRelease());
            movieStatement.setString(3, movie.getGenre());
            movieStatement.setInt(4, movie.getRunningTime());
            movieStatement.setInt(5, movie.getDirector().getId());
            movieStatement.setInt(6, movie.getMovieStudio().getId());
            movieStatement.setDouble(7, movie.getPrice());

            // Execute the movie table insert query
            int movieRowsAffected = movieStatement.executeUpdate();

            if (movieRowsAffected > 0) {
                // Retrieve the generated movie ID
                ResultSet generatedKeys = movieStatement.getGeneratedKeys();
                int movieId = -1;
                if (generatedKeys.next()) {
                    movieId = generatedKeys.getInt(1);
                } else {
                    System.out.println("Failed to retrieve generated movie ID.");
                    return;
                }

                // Insert into the movie_actor table
                String movieActorQuery = "INSERT INTO movie_actor (movie_id, actor_id,role) VALUES (?, ?, ?)";
                PreparedStatement movieActorStatement = connection.prepareStatement(movieActorQuery);
                for (Actor actor : movie.getActors()) {
                    movieActorStatement.setInt(1, movieId);
                    movieActorStatement.setInt(2, actor.getId());
                    movieActorStatement.setString(3, actor.getRole());

                    movieActorStatement.addBatch();
                }

                // Execute the movie_actor table insert batch
                int[] movieActorRowsAffected = movieActorStatement.executeBatch();

                // Check if all movie_actor rows were inserted successfully
                boolean allMovieActorInserted = true;
                for (int rowsAffected : movieActorRowsAffected) {
                    if (rowsAffected <= 0) {
                        allMovieActorInserted = false;
                        break;
                    }
                }

                if (!allMovieActorInserted) {
                    System.out.println("Failed to add movie-actor relationships.");
                    return;
                }

                // Insert into the movie_producer table
                String movieProducerQuery = "INSERT INTO movie_producer (movie_id, producer_id) VALUES (?, ?)";
                PreparedStatement movieProducerStatement = connection.prepareStatement(movieProducerQuery);
                for (Producer producer : movie.getProducers()) {
                    movieProducerStatement.setInt(1, movieId);
                    movieProducerStatement.setInt(2, producer.getId());
                    movieProducerStatement.addBatch();
                }

                // Execute the movie_producer table insert batch
                int[] movieProducerRowsAffected = movieProducerStatement.executeBatch();

                // Check if all movie_producer rows were inserted successfully
                boolean allMovieProducerInserted = true;
                for (int rowsAffected : movieProducerRowsAffected) {
                    if (rowsAffected <= 0) {
                        allMovieProducerInserted = false;
                        break;
                    }
                }

                if (!allMovieProducerInserted) {
                    System.out.println("Failed to add movie-producer relationships.");
                    return;
                }

                System.out.println("Movie data added successfully!");
            } else {
                System.out.println("Failed to add movie.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding movie data: " + e.getMessage());
        }
    }

    public static ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT movie_id, title, year_of_release, genre, running_time, director_id, movie_studio_id, price FROM movie";
            PreparedStatement statement = connection.prepareStatement(query);
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

                // Retrieve actors list
                ArrayList<Actor> actors = getActorsByMovieId(movieId);
                ArrayList<Producer> producers = getProducersByMovieId(movieId);

                // Create Movie object
                Movie movie = new Movie(movieId, title, yearOfRelease, genre, runningTime, director, producers, actors, movieStudio, price);

                // Add movie to the list
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }

        return movies;
    }

    public static ArrayList<Actor> getActorsByMovieId(int movieId) {
        ArrayList<Actor> actors = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT actor.id, actor.name, actor.date_of_birth, actor.role FROM actor INNER JOIN movie_actor ON actor.id = movie_actor.actor_id WHERE movie_actor.movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int actorId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String dateOfBirth = resultSet.getString("date_of_birth");
                String role = resultSet.getString("role");
                Actor actor = new Actor(actorId, name, dateOfBirth, role);
                actors.add(actor);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving actors: " + e.getMessage());
        }

        return actors;
    }

    public static ArrayList<Producer> getProducersByMovieId(int movieId) {
        ArrayList<Producer> producers = new ArrayList<>();

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT producer.producer_id, producer.name, producer.date_of_birth FROM producer INNER JOIN movie_producer ON producer.producer_id = movie_producer.producer_id WHERE movie_producer.movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
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

    public static void deleteMovie(int movieId) {
        try ( Connection connection = MysqlDB.getConnection()) {
            // Delete from the movie_producer table
            String query = "DELETE FROM movie WHERE movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting movie-producer records: " + e.getMessage());
        }
    }

    public static Movie getMovieById(int movieId) {
        Movie movie = null;

        try ( Connection connection = MysqlDB.getConnection()) {
            String query = "SELECT title, year_of_release, genre, running_time, director_id, movie_studio_id, price FROM movie WHERE movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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

                ArrayList<Actor> actors = getActorsByMovieId(movieId);
                ArrayList<Producer> producers = getProducersByMovieId(movieId);

                // Create Movie object
                movie = new Movie(movieId, title, yearOfRelease, genre, runningTime, director, producers, actors, movieStudio, price);

            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movie: " + e.getMessage());
        }

        return movie;
    }

    public static void updateMovie(Movie movie) {
        try ( Connection connection = MysqlDB.getConnection()) {
            // Update movie details
            String updateQuery = "UPDATE movie SET title = ?, year_of_release = ?, genre = ?, running_time = ?, director_id = ?, movie_studio_id = ?, price = ? WHERE movie_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, movie.getTitle());
            updateStatement.setInt(2, movie.getYearOfRelease());
            updateStatement.setString(3, movie.getGenre());
            updateStatement.setInt(4, movie.getRunningTime());
            updateStatement.setInt(5, movie.getDirector().getId());
            updateStatement.setInt(6, movie.getMovieStudio().getId());
            updateStatement.setDouble(7, movie.getPrice());
            updateStatement.setInt(8, movie.getId());
            updateStatement.executeUpdate();

            // Delete existing movie-actor relationships
            String deleteMovieActorQuery = "DELETE FROM movie_actor WHERE movie_id = ?";
            PreparedStatement deleteMovieActorStatement = connection.prepareStatement(deleteMovieActorQuery);
            deleteMovieActorStatement.setInt(1, movie.getId());
            deleteMovieActorStatement.executeUpdate();

            // Delete existing movie-producer relationships
            String deleteMovieProducerQuery = "DELETE FROM movie_producer WHERE movie_id = ?";
            PreparedStatement deleteMovieProducerStatement = connection.prepareStatement(deleteMovieProducerQuery);
            deleteMovieProducerStatement.setInt(1, movie.getId());
            deleteMovieProducerStatement.executeUpdate();

            // Insert new movie-actor relationships
            String insertMovieActorQuery = "INSERT INTO movie_actor (movie_id, actor_id,role) VALUES (?, ?,?)";
            PreparedStatement insertMovieActorStatement = connection.prepareStatement(insertMovieActorQuery);
            for (Actor actor : movie.getActors()) {
                insertMovieActorStatement.setInt(1, movie.getId());
                insertMovieActorStatement.setInt(2, actor.getId());
                insertMovieActorStatement.setString(3, actor.getRole());
                insertMovieActorStatement.executeUpdate();
            }

            // Insert new movie-producer relationships
            String insertMovieProducerQuery = "INSERT INTO movie_producer (movie_id, producer_id) VALUES (?, ?)";
            PreparedStatement insertMovieProducerStatement = connection.prepareStatement(insertMovieProducerQuery);
            for (Producer producer : movie.getProducers()) {
                insertMovieProducerStatement.setInt(1, movie.getId());
                insertMovieProducerStatement.setInt(2, producer.getId());
                insertMovieProducerStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating movie: " + e.getMessage());
        }
    }

}

