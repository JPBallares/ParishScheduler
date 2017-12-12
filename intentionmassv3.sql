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
-- 
--
set foreign_key_checks=0;
CREATE DATABASE IF NOT EXISTS `intentionmass` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `intentionmass`;

-- --------------------------------------------------------

--
-- 
--

DROP TABLE IF EXISTS `intention`;
CREATE TABLE IF NOT EXISTS `intention` (
  `in_id` char(4) NOT NULL,
  `kind` varchar(15) DEFAULT NULL,
  `for_name` varchar(256) NOT NULL,
  `message` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`in_id`),
  UNIQUE KEY `kind_UNIQUE` (`kind`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `intention` (`in_id`, `kind`, `for_name`, `message`) VALUES
('I001', 'Thanksgiving Mass', 'Michael Jackson', 'Thank you for the blessings.'),
('I002', 'Prayer for the Soul', 'Jose Rizal', 'Rest in Peace.');


DROP TABLE IF EXISTS `masssched`;
CREATE TABLE IF NOT EXISTS `masssched` (
  `schedid` char(4) NOT NULL,
  `time` varchar(50) NOT NULL,
  `mass_type` varchar(50) DEFAULT NULL,
  `priest_id` char(4) NOT NULL,
  PRIMARY KEY (`schedid`),
  UNIQUE KEY `time_UNIQUE` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `masssched`
ADD CONSTRAINT `priest_fk` 
FOREIGN KEY (`priest_id`) REFERENCES `priest` (`priest_id`) 
	ON DELETE CASCADE 
    ON UPDATE CASCADE;

INSERT INTO `masssched` (`schedid`, `time`, `mass_type`, `priest_id`) VALUES
('S001', '07:00 AM - 08:00 AM','Regular', 'P001'),
('S002', '05:00 PM - 06:00 PM','Regular','P002');


DROP TABLE IF EXISTS `priest`;
CREATE TABLE IF NOT EXISTS `priest` (
  `priest_id` char(4) NOT NULL,
  `f_name` varchar(15) NOT NULL,
  `l_name` varchar(15) NOT NULL,
  PRIMARY KEY (`priest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `priest` (`priest_id`, `f_name`, `l_name`) VALUES
('P001','Antonio','Valencia'),
('P002','Christian','Reyes');

DROP TABLE IF EXISTS `mass_intentions`;
CREATE TABLE IF NOT EXISTS `mass_intentions` (
  `in_id` char(4) NOT NULL,
  `schedid` char(4) NOT NULL,
  PRIMARY KEY (`in_id`, `schedid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `mass_intentions`
ADD constraint `int_fk`
FOREIGN KEY (`in_id`) REFERENCES `intention`(`in_id`)
     ON UPDATE CASCADE	
     ON DELETE cascade;
ALTER TABLE `mass_intentions`
ADD constraint `msched_fk`     
FOREIGN KEY (`schedid`) REFERENCES `masssched` (`schedid`)
    ON UPDATE CASCADE	
	ON DELETE cascade;

INSERT INTO `mass_intentions` (`in_id`,`schedid`) values
('I001','S001'),
('I001','S002'),
('I002','S002');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
