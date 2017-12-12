CREATE DATABASE  IF NOT EXISTS `intentionmass` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `intentionmass`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: intentionmass
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `intention`
--

DROP TABLE IF EXISTS `intention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intention` (
  `in_id` char(4) NOT NULL,
  `kind` varchar(15) DEFAULT NULL,
  `for_name` varchar(256) NOT NULL,
  `message` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`in_id`),
  UNIQUE KEY `kind_UNIQUE` (`kind`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intention`
--

LOCK TABLES `intention` WRITE;
/*!40000 ALTER TABLE `intention` DISABLE KEYS */;
INSERT INTO `intention` VALUES ('I001','Thanksgiving Ma','Michael Jackson','Thank you for the blessings.'),('I002','Prayer for the ','Jose Rizal','Rest in Peace.');
/*!40000 ALTER TABLE `intention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mass_intentions`
--

DROP TABLE IF EXISTS `mass_intentions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mass_intentions` (
  `in_id` char(4) NOT NULL,
  `schedid` char(4) NOT NULL,
  PRIMARY KEY (`in_id`,`schedid`),
  KEY `msched_fk` (`schedid`),
  CONSTRAINT `int_fk` FOREIGN KEY (`in_id`) REFERENCES `intention` (`in_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `msched_fk` FOREIGN KEY (`schedid`) REFERENCES `masssched` (`schedid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mass_intentions`
--

LOCK TABLES `mass_intentions` WRITE;
/*!40000 ALTER TABLE `mass_intentions` DISABLE KEYS */;
INSERT INTO `mass_intentions` VALUES ('I001','S001'),('I001','S002'),('I002','S002');
/*!40000 ALTER TABLE `mass_intentions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masssched`
--

DROP TABLE IF EXISTS `masssched`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `masssched` (
  `schedid` char(4) NOT NULL,
  `time` time NOT NULL,
  `date` date DEFAULT NULL,
  `mass_type` varchar(50) DEFAULT NULL,
  `priest_id` char(4) NOT NULL,
  PRIMARY KEY (`schedid`),
  UNIQUE KEY `time_UNIQUE` (`time`),
  KEY `priest_fk` (`priest_id`),
  CONSTRAINT `priest_fk` FOREIGN KEY (`priest_id`) REFERENCES `priest` (`priest_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masssched`
--

LOCK TABLES `masssched` WRITE;
/*!40000 ALTER TABLE `masssched` DISABLE KEYS */;
INSERT INTO `masssched` VALUES ('S001','06:00:00','2017-12-24',NULL,'P001');
/*!40000 ALTER TABLE `masssched` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `priest`
--

DROP TABLE IF EXISTS `priest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `priest` (
  `priest_id` char(4) NOT NULL,
  `f_name` varchar(15) NOT NULL,
  `l_name` varchar(15) NOT NULL,
  PRIMARY KEY (`priest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `priest`
--

LOCK TABLES `priest` WRITE;
/*!40000 ALTER TABLE `priest` DISABLE KEYS */;
INSERT INTO `priest` VALUES ('P001','Antonio','Valencia'),('P002','Christian','Reyes');
/*!40000 ALTER TABLE `priest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-12 22:53:26
