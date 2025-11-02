-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: localhost    Database: student_management
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `username` varchar(50) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `status` enum('Present','Absent') NOT NULL,
  KEY `username` (`username`),
  KEY `course_name` (`course_name`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`username`) REFERENCES `profiles` (`username`),
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`course_name`) REFERENCES `courses` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES ('Sachethan','Computer Networks','2024-11-24','Present'),('SE22UARI067','Computer Networks','2024-12-07','Present'),('SE22UARI067','Digital Image Processing','2024-12-07','Present'),('SE22UARI067','NLP Foundations','2024-12-07','Present'),('SE22UARI150','Programming Workshop','2024-12-07','Present'),('SE22UARI150','Operating Systems','2024-12-07','Present'),('SE22UARI150','OOP','2024-12-07','Present');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_registration`
--

DROP TABLE IF EXISTS `course_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_registration` (
  `username` varchar(50) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  KEY `username` (`username`),
  KEY `course_name` (`course_name`),
  CONSTRAINT `course_registration_ibfk_1` FOREIGN KEY (`username`) REFERENCES `profiles` (`username`),
  CONSTRAINT `course_registration_ibfk_2` FOREIGN KEY (`course_name`) REFERENCES `courses` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_registration`
--

LOCK TABLES `course_registration` WRITE;
/*!40000 ALTER TABLE `course_registration` DISABLE KEYS */;
INSERT INTO `course_registration` VALUES ('Sachethan','Computer Networks'),('Sachethan','DBMS'),('Sachethan','Digital Image Processing'),('Sachethan','Operating Systems'),('SE22UARI067','Computer Networks'),('SE22UARI067','DBMS'),('SE22UARI067','Digital Image Processing'),('SE22UARI067','NLP Foundations'),('SE22UARI150','Programming Workshop'),('SE22UARI150','Operating Systems'),('SE22UARI150','OOP');
/*!40000 ALTER TABLE `course_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_name` (`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Computer Networks'),(2,'DBMS'),(3,'Digital Image Processing'),(4,'NLP Foundations'),(6,'OOP'),(5,'Operating Systems'),(7,'Programming Workshop');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fees`
--

DROP TABLE IF EXISTS `fees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fees` (
  `username` varchar(50) NOT NULL,
  `total_fee` int DEFAULT NULL,
  `paid_fee` int DEFAULT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `fees_ibfk_1` FOREIGN KEY (`username`) REFERENCES `profiles` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fees`
--

LOCK TABLES `fees` WRITE;
/*!40000 ALTER TABLE `fees` DISABLE KEYS */;
INSERT INTO `fees` VALUES ('Sachethan',650000,793500),('se22uari067',650000,300000),('se22uari150',650000,350000),('se22uari170',650000,250000),('se22uari178',650000,400000);
/*!40000 ALTER TABLE `fees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marks` (
  `username` varchar(50) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `marks` int DEFAULT NULL,
  PRIMARY KEY (`username`,`subject`),
  CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`username`) REFERENCES `profiles` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks`
--

LOCK TABLES `marks` WRITE;
/*!40000 ALTER TABLE `marks` DISABLE KEYS */;
INSERT INTO `marks` VALUES ('Sachethan','Computer Networks',85),('Sachethan','Database Management Systems',90),('Sachethan','Digital Image Processing',88),('Sachethan','Natural Language Processing Foundations',89),('Sachethan','Object-Oriented Programming',92),('Sachethan','Operating Systems',87),('Sachethan','Programming Workshop',80),('se22uari067','Computer Networks',70),('se22uari067','DBMS',71),('se22uari067','Digital Image Processing',87),('se22uari067','NLP Foundations',80),('se22uari067','OOP',67),('se22uari067','Operating Systems',81),('se22uari067','Programming Workshop',71),('se22uari150','Computer Networks',98),('se22uari150','DBMS',94),('se22uari150','Digital Image Processing',75),('se22uari150','NLP Foundations',74),('se22uari150','OOP',75),('se22uari150','Operating Systems',87),('se22uari150','Programming Workshop',91),('se22uari170','Computer Networks',93),('se22uari170','DBMS',72),('se22uari170','Digital Image Processing',61),('se22uari170','NLP Foundations',70),('se22uari170','OOP',75),('se22uari170','Operating Systems',69),('se22uari170','Programming Workshop',67),('se22uari178','Computer Networks',90),('se22uari178','DBMS',72),('se22uari178','Digital Image Processing',70),('se22uari178','NLP Foundations',75),('se22uari178','OOP',82),('se22uari178','Operating Systems',65),('se22uari178','Programming Workshop',72);
/*!40000 ALTER TABLE `marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profiles`
--

DROP TABLE IF EXISTS `profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profiles` (
  `username` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `semester` int DEFAULT NULL,
  `branch` varchar(50) DEFAULT NULL,
  `section` varchar(10) DEFAULT NULL,
  `roll_no` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `blood_group` varchar(5) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `nationality` varchar(50) DEFAULT NULL,
  `religion` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` enum('Student','Admin') NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
INSERT INTO `profiles` VALUES ('admin1','Admin User','admin1@mahindrauniversity.edu.in',5,'AI','1','SE22UARI001','Male','O+','9999999999','Indian','Hindu',30,'admin123','Admin'),('Sachethan','Sachethan','se22uari199@mahindrauniversity.edu.in',5,'AI','AI3','SE22UARI199','Male','B+','7093712121','Indian','Hindu',20,'1234','Student'),('se22uari067','KANNIKANTI CHANDANA PRIYA','se22uari067@mahindrauniversity.edu.in',5,'AI','1','SE22UARI067','Female','A','9030631527','Indian','Hindi',20,'password123','Student'),('se22uari150','Sameera Nallamothu','se22uari150@mahindrauniversity.edu.in',5,'AI','3','SE22UARI150','Female','A+','7674959393','Indian','Hindu',19,'password123','Student'),('se22uari170','Susmitha Manthena','se22uari170@mahindrauniversity.edu.in',5,'AI','3','SE22UARI170','Female','B+','9100974971','Indian','Hindu',20,'password123','Student'),('se22uari178','Raja Vanabathina','se22uari178@mahindrauniversity.edu.in',5,'AI','3','SE22UARI178','Male','A+','7989871316','Indian','Christian',19,'password123','Student');
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-07 22:24:57
