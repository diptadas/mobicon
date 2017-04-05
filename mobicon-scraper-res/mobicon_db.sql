-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2016 at 08:03 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mobicon_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `rating_info`
--

CREATE TABLE IF NOT EXISTS `rating_info` (
  `user_name` varchar(255) NOT NULL,
  `mobile_id` varchar(1000) NOT NULL,
  `rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `shop_info`
--

CREATE TABLE IF NOT EXISTS `shop_info` (
  `shop_name` varchar(1000) NOT NULL,
  `division` varchar(1000) NOT NULL,
  `address` varchar(5000) NOT NULL,
  `lat` varchar(1000) NOT NULL,
  `long` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shop_info`
--

INSERT INTO `shop_info` (`shop_name`, `division`, `address`, `lat`, `long`) VALUES
('Bashundhara City', 'Dhaka', 'Panthapath', '23.750776', '90.390061'),
('Jamuna Future Park', 'Dhaka', 'Pragati Ave', '23.812983', '90.422188');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
