-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 10, 2023 at 08:02 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `movieMGT_DB`
--

-- --------------------------------------------------------

--
-- Table structure for table `actor`
--

CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `actor`
--

INSERT INTO `actor` (`id`, `name`, `date_of_birth`, `role`) VALUES
(1, 'Saeed', '08-02-2000', 'Back Stage'),
(2, 'Ali Ch', '09-07-2000', 'Belly Dancer'),
(3, 'Zayed', '23-02-2022', 'Stage Dancer');

-- --------------------------------------------------------

--
-- Table structure for table `director`
--

CREATE TABLE `director` (
  `director_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `director`
--

INSERT INTO `director` (`director_id`, `name`, `date_of_birth`) VALUES
(677, 'Mohamed', '07-07-2000'),
(67721, 'Roz', '07-07-2000'),
(677211, 'Sara', '08-2-200');

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `movie_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `year_of_release` int(11) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `running_time` int(11) NOT NULL,
  `director_id` int(11) DEFAULT NULL,
  `movie_studio_id` int(11) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`movie_id`, `title`, `year_of_release`, `genre`, `running_time`, `director_id`, `movie_studio_id`, `price`) VALUES
(4, 'Full Home', 2012, 'Genreeee', 1211, 677, 2, 2.00),
(6, 'No Hope', 1019, 'jhh', 112, 67721, 1, 2.00);

-- --------------------------------------------------------

--
-- Table structure for table `movie_actor`
--

CREATE TABLE `movie_actor` (
  `movie_id` int(11) DEFAULT NULL,
  `actor_id` int(11) DEFAULT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie_actor`
--


-- --------------------------------------------------------

--
-- Table structure for table `movie_producer`
--

CREATE TABLE `movie_producer` (
  `movie_id` int(11) DEFAULT NULL,
  `producer_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie_producer`
--

INSERT INTO `movie_producer` (`movie_id`, `producer_id`) VALUES
(6, -1),
(6, 121867),
(4, -1),
(4, 121867);

-- --------------------------------------------------------

--
-- Table structure for table `movie_studio`
--

CREATE TABLE `movie_studio` (
  `studio_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie_studio`
--


-- --------------------------------------------------------

--
-- Table structure for table `producer`
--

CREATE TABLE `producer` (
  `producer_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `producer`
--


--
-- Indexes for dumped tables
--

--
-- Indexes for table `actor`
--
ALTER TABLE `actor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `director`
--
ALTER TABLE `director`
  ADD PRIMARY KEY (`director_id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`),
  ADD KEY `movie_ibfk_1` (`director_id`),
  ADD KEY `movie_ibfk_2` (`movie_studio_id`);

--
-- Indexes for table `movie_actor`
--
ALTER TABLE `movie_actor`
  ADD KEY `movie_actor_ibfk_1` (`movie_id`),
  ADD KEY `movie_actor_ibfk_2` (`actor_id`);

--
-- Indexes for table `movie_producer`
--
ALTER TABLE `movie_producer`
  ADD KEY `movie_producer_ibfk_1` (`movie_id`),
  ADD KEY `movie_producer_ibfk_2` (`producer_id`);

--
-- Indexes for table `movie_studio`
--
ALTER TABLE `movie_studio`
  ADD PRIMARY KEY (`studio_id`);

--
-- Indexes for table `producer`
--
ALTER TABLE `producer`
  ADD PRIMARY KEY (`producer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `movie_studio`
--
ALTER TABLE `movie_studio`
  MODIFY `studio_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `movie`
--
ALTER TABLE `movie`
  ADD CONSTRAINT `movie_ibfk_1` FOREIGN KEY (`director_id`) REFERENCES `director` (`director_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movie_ibfk_2` FOREIGN KEY (`movie_studio_id`) REFERENCES `movie_studio` (`studio_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `movie_actor`
--
ALTER TABLE `movie_actor`
  ADD CONSTRAINT `movie_actor_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movie_actor_ibfk_2` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `movie_producer`
--
ALTER TABLE `movie_producer`
  ADD CONSTRAINT `movie_producer_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `movie_producer_ibfk_2` FOREIGN KEY (`producer_id`) REFERENCES `producer` (`producer_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
