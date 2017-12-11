-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 21, 2017 at 04:59 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `contact`
--
CREATE DATABASE IF NOT EXISTS `intentionmass` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `intentionmass`;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `intention`;
CREATE TABLE IF NOT EXISTS `intention` (
  `in_id` int(4) NOT NULL,
  `kind` varchar(15) DEFAULT NULL,
  `for_name` varchar(256) NOT NULL,
  `message` varchar(256) DEFAULT NULL,
  `time_sched` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`in_id`),
  UNIQUE KEY `kind_UNIQUE` (`kind`)
  -- CONSTRAINT `FK_TimeSched` FOREIGN KEY (`schedid`)
  -- REFERENCES `masssched`(`schedid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#INSERT INTO `intention` (`in_id`, `kind`, `for_name`, `from_name`) VALUES
#('AA', 'Thanksgiving Mass', 'Michael Jackson', 'Juan Dela Cruz','11:00 AM - 12:00 NN'),
#('AB', 'Prayer for the Soul', 'Jose Rizal', 'Baby Girl','01:00 PM - 02:00 PM');

-- --------------------------------------------------------


DROP TABLE IF EXISTS `masssched`;
CREATE TABLE IF NOT EXISTS `masssched` (
  `schedid` int(4) NOT NULL,
  `time` varchar(50) NOT NULL,
  `day` varchar(10) NOT NULL,
  `mass_type` varchar(50) DEFAULT NULL,
  `priest_id` int(4) NOT NULL,
  PRIMARY KEY (`schedid`),
  UNIQUE KEY `time_UNIQUE` (`time`)
  -- CONSTRAINT `FK_PriestID` FOREIGN KEY (`priest_id`)
  -- REFERENCES `priest`(`priest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person_number`
--

#INSERT INTO `masssched` (`schedid`, `time`,`day`, `mass_type`, `priest_id`) VALUES
#('S001', '07:00 AM - 08:00 AM','Regular', 'P001'),
#('S002', '05:00 PM - 06:00 PM','Regular','P002');


--
-- Constraints for dumped tables
--


DROP TABLE IF EXISTS `priest`;
CREATE TABLE IF NOT EXISTS `priest` (
  `priest_id` int(4) NOT NULL,
  `f_name` varchar(15) NOT NULL,
  `l_name` varchar(15) NOT NULL,
  PRIMARY KEY (`priest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for table `person_number`
--
-- ALTER TABLE `person_number`
  -- ADD CONSTRAINT `fk_name` FOREIGN KEY (`name`) REFERENCES `person` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;
-- COMMIT;

DROP TABLE IF EXISTS `mass_intentions`;
CREATE TABLE IF NOT EXISTS `mass_intentions` (
  `in_id` int(4) NOT NULL,
  `schedid` int(4) NOT NULL,
  PRIMARY KEY (`in_id`, `schedid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
