-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: smarthealthc
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `email` varchar(255) DEFAULT NULL,
                           `is_active` bit(1) NOT NULL,
                           `is_deleted` bit(1) NOT NULL,
                           `password` varchar(255) NOT NULL,
                           `type` enum('ADMIN','CUSTOMER_SUPPORT','MEDICAL_SPECIALIST','USER') NOT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `UK_q0uja26qgu1atulenwup9rxyr` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'nam2@example.com',_binary '',_binary '\0','$2a$10$Lo8cMYKeC/RQhEYI.NDh6u81Lbov334hPcHOK6YVQzke/Rui8tyMe','ADMIN'),(2,'nam1@example.com',_binary '',_binary '\0','$2a$10$VxP4das/0ncnmrbGlnNiuOwWeoCKkBpKh3UcxtyNXrta1rXZApsbe','ADMIN'),(3,'nam3@example.com',_binary '',_binary '\0','$2a$10$Lsbdks04D5.AtGVrdf8iNOt84wCYxTYCL3hmJYCKowuOQfqiRMBjm','ADMIN'),(4,'user1@gmail.com',_binary '',_binary '\0','$2a$10$Lo8cMYKeC/RQhEYI.NDh6u81Lbov334hPcHOK6YVQzke/Rui8tyMe','USER'),(5,'user2@gmail.com',_binary '',_binary '\0','$2a$10$Lo8cMYKeC/RQhEYI.NDh6u81Lbov334hPcHOK6YVQzke/Rui8tyMe','USER'),(6,'user3@gmail.com',_binary '',_binary '\0','$2a$10$/bc4MfW68AdDedKVhbw8P.tW6vr3z6P44lzG38CNiEL4TyQMRDKiq','USER'),(7,'nam123@gmail.com',_binary '',_binary '\0','$2a$10$xQBMSCAcA1vCkvE5rp4MXuqLgzitEFk9G3mp9/JiJKSpfYXB58Q8K','USER'),(8,'nam124@gmail.com',_binary '',_binary '\0','$2a$10$FDN6j5uDSIhLZ6HEcC2.7OUBSVsJ8NcnayaY2Z6NyfpcpAa82rmXi','USER'),(9,'nam125@gmail.com',_binary '',_binary '\0','$2a$10$Xz0URXaZyraylLiLYUBb4.TuHt35NNi00e2OcDPz63rA3PqKtqMP2','USER'),(10,'nam126@gmail.com',_binary '',_binary '\0','$2a$10$LcVVpjP33Dp9XUv9yD8xwe3eDW7c4lX4XbpT58wqh2xk9jvzpAkm6','USER'),(11,'nam127@gmail.com',_binary '',_binary '\0','$2a$10$6LmG.yELpjG4VwHOpNGS0uoBbVwqdvV5n7.HwfiJ8h7Nu6nPWQR2m','USER'),(12,'nam128@gmail.com',_binary '',_binary '\0','$2a$10$dSHxbgOQndY6vcdzoiqLtOBco/4L99zDhFaaQn0KQCMqul/1rA9LK','USER'),(13,'nam129@gmail.com',_binary '',_binary '\0','$2a$10$.v1vHDtZ6959monnMzQ0wOrP1BqwvrHSbuO4YoqPzpeYsEymwbzGa','USER'),(14,'nam139@gmail.com',_binary '',_binary '\0','$2a$10$lLYs3xadQaJD28P8jEOf/u1PNu9iZ1c1rvd2dFc2dTeqbLX8mRSNy','USER'),(15,'nam132@gmail.com',_binary '',_binary '\0','$2a$10$zjNqxA.ZfGG7JHcgwyms9u/Z0SLWSOfg9JCLrSlQ2c6tZTX14U9ty','USER'),(16,'nam4@example.com',_binary '',_binary '','$2a$10$DBjZz2HN47Bp7387/ltdqeQek4PLLsAefHx2D3US4tJuIz3vmmI8a','ADMIN'),(17,'staffmedical@example.com',_binary '',_binary '\0','$2a$10$Lo8cMYKeC/RQhEYI.NDh6u81Lbov334hPcHOK6YVQzke/Rui8tyMe','MEDICAL_SPECIALIST'),(18,'quang@gmail.com',_binary '',_binary '\0','$2a$10$48vhfcw7h0.YgizuV6isieNe7MVR4xm5cFTWFGoa6a9/wK9X/yhMW','USER'),(19,'medicalvip@example.com',_binary '',_binary '\0','$2a$10$Lo8cMYKeC/RQhEYI.NDh6u81Lbov334hPcHOK6YVQzke/Rui8tyMe','MEDICAL_SPECIALIST'),(20,'usernam@gmail.com',_binary '',_binary '\0','$2a$10$XQAbhW8V/PfAZyscJeXwGewH7fjia4KfIZeYRh5q.H57soNP09sgW','USER');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_record`
--

DROP TABLE IF EXISTS `activity_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `date` datetime(6) NOT NULL,
                                   `week_start` datetime(6) NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   `actual_duration` float DEFAULT NULL,
                                   `actual_type` enum('HEAVY','LIGHT','MEDIUM') DEFAULT NULL,
                                   `plan_duration` float NOT NULL,
                                   `plan_type` enum('HEAVY','LIGHT','MEDIUM') NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKcokpi2p2wcoucs4gi3ve5r7jc` (`appuser_id`),
                                   CONSTRAINT `FKcokpi2p2wcoucs4gi3ve5r7jc` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_record`
--

LOCK TABLES `activity_record` WRITE;
/*!40000 ALTER TABLE `activity_record` DISABLE KEYS */;
INSERT INTO `activity_record` VALUES (1,'2024-05-20 14:30:00.000000','2024-05-18 04:00:00.000000',1,75,'HEAVY',75,'HEAVY'),(2,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,45,'MEDIUM',65,'MEDIUM'),(3,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,45,'MEDIUM',45,'MEDIUM'),(4,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,40,'LIGHT',35,'LIGHT'),(5,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,50,'LIGHT',40,'HEAVY'),(6,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,35,'LIGHT',45,'MEDIUM'),(7,'2024-05-26 14:30:00.000000','2024-05-20 07:00:00.000000',1,40,'MEDIUM',45,'LIGHT'),(8,'2024-05-27 07:00:00.000000','2024-05-27 07:00:00.000000',1,30,'MEDIUM',45,'LIGHT'),(9,'2024-05-28 07:00:00.000000','2024-05-27 07:00:00.000000',1,35,'LIGHT',60,'MEDIUM'),(10,'2024-05-29 07:00:00.000000','2024-05-27 07:00:00.000000',1,45,'LIGHT',50,'LIGHT'),(11,'2024-05-30 07:00:00.000000','2024-05-27 07:00:00.000000',1,0,NULL,55,'LIGHT'),(12,'2024-06-11 02:57:55.000000','2024-06-11 02:58:00.000000',1,NULL,NULL,1,'LIGHT');
/*!40000 ALTER TABLE `activity_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `dob` datetime(6) NOT NULL,
                            `gender` bit(1) NOT NULL,
                            `height` float NOT NULL,
                            `medical_specialist_note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `weight` float NOT NULL,
                            `account_id` int NOT NULL,
                            `web_user_id` int DEFAULT NULL,
                            `cic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_mc8v5krsv6n13f3sxqs3c5upu` (`account_id`),
                            KEY `FKrk1k6ks3fmxvjyhm4qwsj5x43` (`web_user_id`),
                            CONSTRAINT `FKngs581vugannc8it964ohg2pt` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
                            CONSTRAINT `FKrk1k6ks3fmxvjyhm4qwsj5x43` FOREIGN KEY (`web_user_id`) REFERENCES `web_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,4,NULL,'0123567125673218'),(2,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,5,NULL,'0123567125673218'),(3,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,6,1,'0123567125673218'),(4,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Your Generated Name','010-1234-5678',75,7,NULL,'0123567125673218'),(5,'1990-01-01 07:00:00.000000',_binary '',19,NULL,'Y12312312','010-1234-5678',18,8,1,'0123567125673218'),(6,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam125 Name','010-1234-5678',75,9,1,'0123567125673218'),(7,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam126 Name','010-1234-5678',75,10,NULL,'0123567125673218'),(8,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam127 Name','010-1234-5678',75,11,5,'0123567125673218'),(9,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam128 Name','010-1234-5678',75,12,5,'0123567125673218'),(10,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam129 Name','010-1234-5678',75,13,4,'0123567125673218'),(11,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam139 Name','010-1234-5678',75,14,5,'0123567125673218'),(12,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam132 Name','010-1234-5678',75,15,NULL,'0123567125673218'),(13,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Your Generated Name','010-1234-5678',75,18,NULL,'0123567125673218'),(14,'2022-10-11 07:00:00.000000',_binary '\0',170,NULL,'Nam','02-312-3456',50,20,NULL,'123456789');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blood_pressure_record`
--

DROP TABLE IF EXISTS `blood_pressure_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blood_pressure_record` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `date` datetime(6) NOT NULL,
                                         `diastole` float NOT NULL,
                                         `systole` float NOT NULL,
                                         `week_start` datetime(6) NOT NULL,
                                         `appuser_id` int NOT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `FK59b0ry6pr9l4c5h49pljptouf` (`appuser_id`),
                                         CONSTRAINT `FK59b0ry6pr9l4c5h49pljptouf` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blood_pressure_record`
--

LOCK TABLES `blood_pressure_record` WRITE;
/*!40000 ALTER TABLE `blood_pressure_record` DISABLE KEYS */;
INSERT INTO `blood_pressure_record` VALUES (1,'2024-05-20 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(2,'2024-05-21 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(3,'2024-05-22 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(4,'2024-05-23 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(5,'2024-05-24 14:30:00.000000',80,150,'2024-05-20 07:00:00.000000',1),(6,'2024-05-25 14:30:00.000000',87,135,'2024-05-20 07:00:00.000000',1),(7,'2024-05-26 14:30:00.000000',88,123,'2024-05-20 07:00:00.000000',1),(8,'2024-05-27 07:00:00.000000',81,123,'2024-05-27 07:00:00.000000',1),(9,'2024-05-28 07:00:00.000000',79.2,135,'2024-05-27 07:00:00.000000',1),(10,'2024-05-29 07:00:00.000000',79.2,135,'2024-05-27 07:00:00.000000',1),(11,'2024-05-30 07:00:00.000000',79.2,134,'2024-05-27 07:00:00.000000',1),(12,'2024-05-31 07:00:00.000000',77.2,143.3,'2024-05-27 07:00:00.000000',1),(13,'2024-06-01 14:30:00.000000',71.2,113.3,'2024-05-27 07:00:00.000000',1),(14,'2024-06-02 14:30:00.000000',73.2,118.3,'2024-05-27 07:00:00.000000',1),(15,'2024-05-15 14:30:00.000000',80,120,'2024-05-13 07:00:00.000000',2),(16,'2024-05-20 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(17,'2024-05-21 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(18,'2024-05-22 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(19,'2024-05-23 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(20,'2024-05-21 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',1),(21,'2024-05-22 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',2),(22,'2024-05-23 14:30:00.000000',80,120,'2024-05-20 07:00:00.000000',2);
/*!40000 ALTER TABLE `blood_pressure_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cardinal_record`
--

DROP TABLE IF EXISTS `cardinal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cardinal_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `date` datetime(6) NOT NULL,
                                   `time_measure` enum('AFTER_BREAKFAST','AFTER_DINNER','AFTER_LUNCH','BEFORE_BREAKFAST','BEFORE_DINNER','BEFORE_LUNCH') NOT NULL,
                                   `week_start` datetime(6) NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   `blood_sugar` float DEFAULT NULL,
                                   `cholesterol` float DEFAULT NULL,
                                   `hba1c` float DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKtqskw8gahtcvqxgii9m9ih80u` (`appuser_id`),
                                   CONSTRAINT `FKtqskw8gahtcvqxgii9m9ih80u` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cardinal_record`
--

LOCK TABLES `cardinal_record` WRITE;
/*!40000 ALTER TABLE `cardinal_record` DISABLE KEYS */;
INSERT INTO `cardinal_record` VALUES (59,'2024-05-21 14:30:00.000000','BEFORE_BREAKFAST','2024-05-20 07:00:00.000000',1,95.2,150.5,5.7),(60,'2024-05-21 14:30:00.000000','AFTER_BREAKFAST','2024-05-20 07:00:00.000000',1,93.2,120.5,5.9),(61,'2024-05-21 14:30:00.000000','AFTER_LUNCH','2024-05-20 07:00:00.000000',1,99.2,125.5,6.9),(62,'2024-05-21 14:30:00.000000','BEFORE_LUNCH','2024-05-20 07:00:00.000000',1,77.2,201,6.3),(63,'2024-05-21 14:30:00.000000','AFTER_DINNER','2024-05-20 07:00:00.000000',1,72.2,201,6.7),(64,'2024-05-21 14:30:00.000000','BEFORE_DINNER','2024-05-20 07:00:00.000000',1,62.2,201,6.1),(71,'2024-06-23 14:30:00.000000','BEFORE_BREAKFAST','2024-06-17 07:00:00.000000',1,72.2,117.2,7.4),(72,'2024-06-23 14:30:00.000000','AFTER_BREAKFAST','2024-06-17 07:00:00.000000',1,73.2,112.2,7.1),(73,'2024-06-23 14:30:00.000000','AFTER_LUNCH','2024-06-17 07:00:00.000000',1,83.2,122.2,8.1),(74,'2024-06-23 14:30:00.000000','BEFORE_LUNCH','2024-06-17 07:00:00.000000',1,81.1,123.2,8.4),(75,'2024-06-23 14:30:00.000000','AFTER_DINNER','2024-06-17 07:00:00.000000',1,71.9,143.2,6.4),(76,'2024-06-23 14:30:00.000000','BEFORE_DINNER','2024-06-17 07:00:00.000000',1,72.9,113.2,7.4),(77,'2024-05-26 14:30:00.000000','BEFORE_BREAKFAST','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(78,'2024-05-26 14:30:00.000000','AFTER_BREAKFAST','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(79,'2024-05-26 14:30:00.000000','AFTER_LUNCH','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(80,'2024-05-26 14:30:00.000000','BEFORE_LUNCH','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(81,'2024-05-26 14:30:00.000000','AFTER_DINNER','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(82,'2024-05-26 14:30:00.000000','BEFORE_DINNER','2024-05-20 07:00:00.000000',1,72.9,113.2,7.4),(83,'2024-05-24 14:30:00.000000','BEFORE_BREAKFAST','2024-05-20 07:00:00.000000',1,87.3,123.2,8.5),(84,'2024-05-24 14:30:00.000000','AFTER_BREAKFAST','2024-05-20 07:00:00.000000',1,87.3,123.2,8.5),(85,'2024-05-24 14:30:00.000000','AFTER_LUNCH','2024-05-20 07:00:00.000000',1,87.3,201,8.5),(86,'2024-05-24 14:30:00.000000','BEFORE_LUNCH','2024-05-20 07:00:00.000000',1,87.3,201,8.5),(87,'2024-05-24 14:30:00.000000','AFTER_DINNER','2024-05-20 07:00:00.000000',1,87.3,201,8.5),(88,'2024-05-24 14:30:00.000000','BEFORE_DINNER','2024-05-20 07:00:00.000000',1,87.3,201,8.5),(89,'2024-05-25 14:30:00.000000','BEFORE_BREAKFAST','2024-05-20 07:00:00.000000',1,85.3,201,8.3),(90,'2024-05-25 14:30:00.000000','AFTER_BREAKFAST','2024-05-20 07:00:00.000000',1,85.3,201,8.3),(91,'2024-05-25 14:30:00.000000','BEFORE_LUNCH','2024-05-20 07:00:00.000000',1,85.3,121.2,8.3),(92,'2024-05-25 14:30:00.000000','AFTER_LUNCH','2024-05-20 07:00:00.000000',1,100,121.2,8.3),(93,'2024-05-25 14:30:00.000000','BEFORE_DINNER','2024-05-20 07:00:00.000000',1,100,121.2,8.3),(94,'2024-05-25 14:30:00.000000','AFTER_DINNER','2024-05-20 07:00:00.000000',1,100,121.2,8.3);
/*!40000 ALTER TABLE `cardinal_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diet_record`
--

DROP TABLE IF EXISTS `diet_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diet_record` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `date` datetime(6) NOT NULL,
                               `dish_per_day` int NOT NULL,
                               `week_start` datetime(6) NOT NULL,
                               `appuser_id` int NOT NULL,
                               `actual_value` float NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKl8afh79idhbhnqrxp3b401oj9` (`appuser_id`),
                               CONSTRAINT `FKl8afh79idhbhnqrxp3b401oj9` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diet_record`
--

LOCK TABLES `diet_record` WRITE;
/*!40000 ALTER TABLE `diet_record` DISABLE KEYS */;
INSERT INTO `diet_record` VALUES (1,'2024-05-20 14:30:00.000000',5,'2024-05-20 07:00:00.000000',1,4),(2,'2024-05-21 14:30:00.000000',7,'2024-05-20 07:00:00.000000',1,5),(3,'2024-05-22 14:30:00.000000',8,'2024-05-20 07:00:00.000000',1,4),(4,'2024-05-23 14:30:00.000000',4,'2024-05-20 07:00:00.000000',1,3),(5,'2024-05-24 14:30:00.000000',5,'2024-05-20 07:00:00.000000',1,4),(6,'2024-05-25 14:30:00.000000',3,'2024-05-20 07:00:00.000000',1,5),(7,'2024-05-26 14:30:00.000000',2,'2024-05-20 07:00:00.000000',1,6),(8,'2024-05-27 07:00:00.000000',5,'2024-05-27 07:00:00.000000',1,4),(9,'2024-05-28 07:00:00.000000',4,'2024-05-27 07:00:00.000000',1,3),(10,'2024-05-29 07:00:00.000000',3,'2024-05-27 07:00:00.000000',1,6),(11,'2024-05-30 07:00:00.000000',6,'2024-05-27 07:00:00.000000',1,4),(12,'2024-05-31 07:00:00.000000',4,'2024-05-27 07:00:00.000000',1,2),(13,'2024-06-01 14:30:00.000000',3,'2024-05-27 07:00:00.000000',1,3),(14,'2024-06-02 14:30:00.000000',4,'2024-05-27 07:00:00.000000',1,5);
/*!40000 ALTER TABLE `diet_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faq`
--

DROP TABLE IF EXISTS `faq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faq` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                       `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faq`
--

LOCK TABLES `faq` WRITE;
/*!40000 ALTER TABLE `faq` DISABLE KEYS */;
/*!40000 ALTER TABLE `faq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
                                         `installed_rank` int NOT NULL,
                                         `version` varchar(50) DEFAULT NULL,
                                         `description` varchar(200) NOT NULL,
                                         `type` varchar(20) NOT NULL,
                                         `script` varchar(1000) NOT NULL,
                                         `checksum` int DEFAULT NULL,
                                         `installed_by` varchar(100) NOT NULL,
                                         `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         `execution_time` int NOT NULL,
                                         `success` tinyint NOT NULL,
                                         PRIMARY KEY (`installed_rank`),
                                         KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--
--
-- LOCK TABLES `flyway_schema_history` WRITE;
-- /*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
-- INSERT INTO `flyway_schema_history` VALUES (1,'0','Initial setup','SQL','V0__Initial_setup.sql',1352263579,'root','2024-06-15 02:51:09',1910,1);
-- /*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `forget_password_code`
--

DROP TABLE IF EXISTS `forget_password_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forget_password_code` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `code` varchar(255) NOT NULL,
                                        `account_id` int NOT NULL,
                                        `expired_date` datetime(6) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FKfbsvxf0mpebgj0nsxag3eeq5q` (`account_id`),
                                        CONSTRAINT `FKfbsvxf0mpebgj0nsxag3eeq5q` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forget_password_code`
--

LOCK TABLES `forget_password_code` WRITE;
/*!40000 ALTER TABLE `forget_password_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `forget_password_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_question`
--

DROP TABLE IF EXISTS `form_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_question` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                 `question_number` int NOT NULL,
                                 `type` enum('SAT_SF','SF') NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_question`
--

LOCK TABLES `form_question` WRITE;
/*!40000 ALTER TABLE `form_question` DISABLE KEYS */;
INSERT INTO `form_question` VALUES (1,'This is question SAT_SF 1',1,'SAT_SF'),(2,'This is question SAT_SF 2',2,'SAT_SF'),(3,'This is question SAT_SF 3',3,'SAT_SF'),(4,'This is question SAT_SF 4',4,'SAT_SF'),(5,'This is question SAT_SF 5',5,'SAT_SF'),(6,'This is question SAT_SF 6',6,'SAT_SF'),(7,'This is question SF 1',7,'SF'),(8,'This is question SF 2',8,'SF'),(9,'This is question SF 3',9,'SF'),(10,'This is question SF 4',10,'SF'),(11,'This is question SF 5x',11,'SF');
/*!40000 ALTER TABLE `form_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lesson` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `lesson_number` int NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `lesson_number` (`lesson_number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'video1','videovideovideo','this is video1\'s content',1),(2,'video2','videovideovideo','this is video2\'s content',2),(3,'video3','videovideovideo','this is video3\'s content',3),(4,'video4','videovideovideo','this is video4\'s content',4),(5,'video5','videovideovideo','this is video5\'s content',5),(6,'video6','videovideovideo','this is video6\'s update content',6);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_appointment`
--

DROP TABLE IF EXISTS `medical_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_appointment` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `date` datetime(6) DEFAULT NULL,
                                       `hospital` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                       `status_medical_appointment` enum('CONFIRM','DONE','PENDING') NOT NULL,
                                       `type_medical_appointment` enum('DIAGNOSIS','MEDICAL_CHECKUP') NOT NULL,
                                       `appuser_id` int NOT NULL,
                                       `note` text NOT NULL,
                                       PRIMARY KEY (`id`),
                                       KEY `FK69h8dnll1rty4bswhf21xb1mu` (`appuser_id`),
                                       CONSTRAINT `FK69h8dnll1rty4bswhf21xb1mu` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_appointment`
--

LOCK TABLES `medical_appointment` WRITE;
/*!40000 ALTER TABLE `medical_appointment` DISABLE KEYS */;
INSERT INTO `medical_appointment` VALUES (1,'2024-05-28 19:34:56.789000','Hanoi','PENDING','DIAGNOSIS',1,'abcdef'),(3,'2024-05-02 18:30:00.000000','Location A','PENDING','DIAGNOSIS',2,'abc'),(4,'2024-05-02 18:30:00.000000','Location A','CONFIRM','DIAGNOSIS',1,'abcdef'),(5,'2024-05-02 18:30:00.000000','Location C','PENDING','DIAGNOSIS',4,'abcdef'),(6,'2024-05-02 18:30:00.000000','Location D','PENDING','MEDICAL_CHECKUP',1,'abcdef'),(7,'2024-05-02 18:30:00.000000','Location E','PENDING','MEDICAL_CHECKUP',2,'abcdef'),(8,'2024-05-02 18:30:00.000000','Location Z','PENDING','MEDICAL_CHECKUP',3,'abcdef'),(9,'2024-05-02 18:30:00.000000','Location N','PENDING','MEDICAL_CHECKUP',4,'abcdef'),(10,'2024-05-02 18:30:00.000000','Location N','PENDING','MEDICAL_CHECKUP',5,'abcdef'),(11,'2024-05-02 18:30:00.000000','Location M','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(12,'2024-05-02 18:30:00.000000','LocationZ','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(13,'2024-05-02 18:30:00.000000','LocationD','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(14,'2024-05-02 18:30:00.000000','Location G','PENDING','DIAGNOSIS',4,'abcdef'),(15,'2024-05-02 18:30:00.000000','Location S','PENDING','DIAGNOSIS',4,'abcdef'),(16,'2024-05-02 18:30:00.000000','Location J','PENDING','DIAGNOSIS',4,'abcdef'),(17,'2024-05-02 18:30:00.000000','Location M','PENDING','DIAGNOSIS',4,'abcdef'),(18,'2024-05-02 18:30:00.000000','Location M','PENDING','DIAGNOSIS',14,'abc'),(19,'2024-05-02 18:30:00.000000',NULL,'PENDING','DIAGNOSIS',14,'abc');
/*!40000 ALTER TABLE `medical_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_history`
--

DROP TABLE IF EXISTS `medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_history` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `is_deleted` bit(1) NOT NULL,
                                   `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `type` enum('ARTHRITIS','CARDINAL','HABIT','OTHERS','RESPIRATORY') NOT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_history`
--

LOCK TABLES `medical_history` WRITE;
/*!40000 ALTER TABLE `medical_history` DISABLE KEYS */;
INSERT INTO `medical_history` VALUES (1,_binary '','Bệnh thận mãn tính','CARDINAL'),(2,_binary '\0','Cao huyết áp','CARDINAL'),(3,_binary '\0','Bệnh xơ phổi vô căn','RESPIRATORY'),(4,_binary '\0','Bệnh hen phế quản/ hen suyễn','RESPIRATORY'),(5,_binary '\0','Tổn thương phổi sau bệnh lao','RESPIRATORY'),(6,_binary '\0','Viêm khớp dạng thấp','ARTHRITIS'),(7,_binary '\0','Thoái hoá khớp (Hông, đầu gối, khuỷu tay)','ARTHRITIS'),(8,_binary '\0','Bệnh Tim','OTHERS'),(9,_binary '\0','Bệnh Gan','OTHERS'),(10,_binary '\0','Hút thuốc 1','HABIT'),(11,_binary '\0','Uống rượu','HABIT');
/*!40000 ALTER TABLE `medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_record`
--

DROP TABLE IF EXISTS `medicine_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `date` datetime(6) NOT NULL,
                                   `status` bit(1) NOT NULL,
                                   `week_start` datetime(6) NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   `medicine_type_id` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKebvs97hc30u0vkfbqbc1pcr88` (`appuser_id`),
                                   KEY `FKehxi7o1b90qsrnkschp6f3rm6` (`medicine_type_id`),
                                   CONSTRAINT `FKebvs97hc30u0vkfbqbc1pcr88` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`),
                                   CONSTRAINT `FKehxi7o1b90qsrnkschp6f3rm6` FOREIGN KEY (`medicine_type_id`) REFERENCES `medicine_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_record`
--

LOCK TABLES `medicine_record` WRITE;
/*!40000 ALTER TABLE `medicine_record` DISABLE KEYS */;
INSERT INTO `medicine_record` VALUES (2,'2024-05-20 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,2),(3,'2024-05-21 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,3),(4,'2024-05-22 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,4),(5,'2024-05-23 14:30:00.000000',_binary '\0','2024-05-20 07:00:00.000000',1,4),(6,'2024-05-28 07:00:00.000000',_binary '','2024-05-27 07:00:00.000000',1,1),(7,'2024-05-29 07:00:00.000000',_binary '\0','2024-05-27 07:00:00.000000',1,2),(57,'2024-06-17 07:00:00.000000',_binary '\0','2024-06-17 14:00:00.000000',14,1),(58,'2024-06-18 07:00:00.000000',_binary '\0','2024-06-17 14:00:00.000000',14,1),(59,'2024-06-19 07:00:00.000000',_binary '\0','2024-06-17 14:00:00.000000',14,1),(60,'2025-06-17 07:00:00.000000',_binary '\0','2025-06-17 14:00:00.000000',14,1),(61,'2025-06-18 07:00:00.000000',_binary '\0','2025-06-17 14:00:00.000000',14,1),(62,'2025-06-19 07:00:00.000000',_binary '\0','2025-06-17 14:00:00.000000',14,1),(63,'2026-06-17 07:00:00.000000',_binary '\0','2026-06-17 14:00:00.000000',14,1),(64,'2026-06-18 07:00:00.000000',_binary '\0','2026-06-17 14:00:00.000000',14,1),(65,'2026-06-19 07:00:00.000000',_binary '\0','2026-06-17 14:00:00.000000',14,1),(66,'2027-06-17 07:00:00.000000',_binary '\0','2027-06-17 14:00:00.000000',14,1),(67,'2027-06-18 07:00:00.000000',_binary '\0','2027-06-17 14:00:00.000000',14,1),(68,'2027-06-19 07:00:00.000000',_binary '\0','2027-06-17 14:00:00.000000',14,1);
/*!40000 ALTER TABLE `medicine_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_type`
--

DROP TABLE IF EXISTS `medicine_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine_type` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `is_deleted` bit(1) NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_type`
--

LOCK TABLES `medicine_type` WRITE;
/*!40000 ALTER TABLE `medicine_type` DISABLE KEYS */;
INSERT INTO `medicine_type` VALUES (1,'','Thuốc cao huyết áp',_binary '\0'),(2,'','Thuốc tăng lipid máu',_binary '\0'),(3,'','Thuốc tiểu đường',_binary '\0'),(4,'','Thuốc khác',_binary '\0'),(5,'','Thuốc khác1',_binary '\0');
/*!40000 ALTER TABLE `medicine_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mental_record`
--

DROP TABLE IF EXISTS `mental_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mental_record` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `date` datetime(6) NOT NULL,
                                 `week_start` datetime(6) NOT NULL,
                                 `appuser_id` int NOT NULL,
                                 `mental_rule_id` int NOT NULL,
                                 `status` bit(1) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FKhdjaqfcol4ndj2el5gcfj9u8` (`appuser_id`),
                                 KEY `FK7ch3br3em2r1rmkxk60j594dx` (`mental_rule_id`),
                                 CONSTRAINT `FK7ch3br3em2r1rmkxk60j594dx` FOREIGN KEY (`mental_rule_id`) REFERENCES `mental_rule` (`id`),
                                 CONSTRAINT `FKhdjaqfcol4ndj2el5gcfj9u8` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mental_record`
--

LOCK TABLES `mental_record` WRITE;
/*!40000 ALTER TABLE `mental_record` DISABLE KEYS */;
INSERT INTO `mental_record` VALUES (1,'2024-05-20 07:00:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(2,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,2,_binary ''),(3,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(4,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(5,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(6,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,5,_binary ''),(7,'2024-05-26 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(8,'2024-05-27 07:00:00.000000','2024-05-27 07:00:00.000000',1,4,_binary ''),(9,'2024-05-28 07:00:00.000000','2024-05-27 07:00:00.000000',1,5,_binary ''),(10,'2024-05-29 07:00:00.000000','2024-05-27 07:00:00.000000',1,1,_binary ''),(11,'2024-05-30 07:00:00.000000','2024-05-27 07:00:00.000000',1,1,_binary ''),(12,'2024-05-31 07:00:00.000000','2024-05-27 07:00:00.000000',1,4,_binary ''),(13,'2024-06-01 14:30:00.000000','2024-05-27 07:00:00.000000',1,5,_binary '\0'),(14,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(15,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,2,_binary ''),(16,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,4,_binary ''),(17,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(18,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(19,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,5,_binary ''),(20,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary '');
/*!40000 ALTER TABLE `mental_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mental_rule`
--

DROP TABLE IF EXISTS `mental_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mental_rule` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                               `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                               `is_deleted` bit(1) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mental_rule`
--

LOCK TABLES `mental_rule` WRITE;
/*!40000 ALTER TABLE `mental_rule` DISABLE KEYS */;
INSERT INTO `mental_rule` VALUES (1,'','Khi lo lắng hay sợ hãi về điều gì đó, hãy tự nhủ: “Điều này rồi cũng sẽ qua thôi”.',_binary '\0'),(2,'','Lùi lại một bước và nhìn nhận lại những cảm xúc mà bản thân cảm nhận được từ cả sự kiện tích cực và tiêu cực.',_binary '\0'),(3,'','Tạo cơ hội để chia sẻ một cách trung thực về những vấn đề và mối quan tâm của bản thân với những người xung quanh.',_binary '\0'),(4,'','Đừng hối tiếc về những điều đã xảy ra mà hãy coi chúng như một cơ hội để bắt đầu một điều gì đó mới mẻ.',_binary '\0'),(5,'1','Khi có những suy nghĩ tiêu cực, hãy nghe nhạc và ngừng suy nghĩ về nó trong 1 khoảng thời gian ngắn.',_binary '\0');
/*!40000 ALTER TABLE `mental_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monthly_record`
--

DROP TABLE IF EXISTS `monthly_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monthly_record` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `answer` int NOT NULL,
                                  `month_start` datetime(6) NOT NULL,
                                  `monthly_record_type` enum('SAT_SF_C','SAT_SF_P','SAT_SF_I','SF_Medication','SF_Diet','SF_Activity','SF_Mental') NOT NULL,
                                  `question` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `question_number` int NOT NULL,
                                  `appuser_id` int NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKjhilgi34lnbjynegvfritljh8` (`appuser_id`),
                                  CONSTRAINT `FKjhilgi34lnbjynegvfritljh8` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monthly_record`
--

LOCK TABLES `monthly_record` WRITE;
/*!40000 ALTER TABLE `monthly_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `monthly_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                            `body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `question_date` datetime(6) NOT NULL,
                            `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `type_user_question` enum('ASSIGN_ADMIN','ASSIGN_MS') NOT NULL,
                            `appuser_id` int NOT NULL,
                            `webuser_id` int DEFAULT NULL,
                            `answer_date` datetime(6) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK8jju1d5enwfvxbfi3veg5rcak` (`appuser_id`),
                            KEY `FK8wk86m3lwpbonn91355qmymjx` (`webuser_id`),
                            CONSTRAINT `FK8jju1d5enwfvxbfi3veg5rcak` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`),
                            CONSTRAINT `FK8wk86m3lwpbonn91355qmymjx` FOREIGN KEY (`webuser_id`) REFERENCES `web_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','Microsoft Assignment Query','ASSIGN_MS',1,1,'2024-05-07 18:30:00.000000'),(2,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','Microsoft Assignment Query','ASSIGN_ADMIN',1,1,'2024-05-07 18:30:00.000000'),(3,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123','ASSIGN_ADMIN',2,1,'2024-05-07 18:30:00.000000'),(4,'','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123123','ASSIGN_ADMIN',2,NULL,'2024-05-02 18:30:00.000000'),(5,'tra loi cau hoi','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123123','ASSIGN_ADMIN',2,1,'2024-06-02 16:23:25.519000'),(8,'','How do I assign a Microsoft license to a user?','2024-06-11 02:57:14.885000','sssssss','ASSIGN_ADMIN',2,NULL,NULL),(9,'','How tf can i sleep at the time that i want','2024-06-16 20:45:30.557000','test question again','ASSIGN_ADMIN',14,NULL,NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `access_token` varchar(255) NOT NULL,
                                 `access_expiry_time` datetime(6) NOT NULL,
                                 `account_id` int NOT NULL,
                                 `refresh_token` varchar(45) NOT NULL,
                                 `refresh_expiry_time` datetime(6) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FKiox3wo9jixvp9boxfheq7l99w` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (8,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6Im5hbTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTg0MjAxNDksImV4cCI6MTcxODQyMzc0OX0.LglwYzZGbJP2_ddSAQaaJwx2hM9fi-TD2dSEWTPK94I','2024-06-15 10:55:49.000000',1,'20d13366-4a3c-430e-911a-0dcf632b19c4','2024-06-15 10:00:49.000000'),(9,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6Im5hbTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTg1NDEwNTEsImV4cCI6MTcxODU0MTY1MX0.EZupuuNKFH7BZxQR_JtTMWyuiwCaqu5Zx9IkoJ1f3_A','2024-06-16 19:40:51.000000',1,'6de6f8b5-93f1-4ee7-a945-75581b2f4942','2024-06-16 19:40:51.000000'),(10,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6Im5hbTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTg1NDEwODYsImV4cCI6MTcxODU0MTY4Nn0.RJzxwxYUS30M2zNTLRDKVB5p0QFiajKyM52Sk-XI8y8','2024-06-16 19:41:26.000000',1,'5db3b0bd-884c-4058-bf73-518e505268d3','2024-06-16 19:41:26.000000'),(11,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwLCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODU0NDk0NiwiZXhwIjoxNzE4NTQ1NTQ2fQ.cszc-PjkQZQDEAahQqBbiQ6NGqiMuxztvf6OjF94zAk','2024-06-16 20:45:46.000000',20,'4bfc6a49-f2e3-47db-9ce1-e0fbb99f6d59','2024-06-16 20:45:46.000000'),(12,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwLCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODU0NTEzMSwiZXhwIjoxNzE4NTQ1NzMxfQ.Ze-CHqO68duAgHSBRuDdoq3MTjBA-LPBXu81irlwWhg','2024-06-16 20:48:51.000000',20,'6f665f2a-6293-4821-9a3f-ef72cfd2e1b6','2024-06-16 20:48:51.000000'),(13,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwLCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODU0NTIzMiwiZXhwIjoxNzE4NTQ1ODMyfQ.ZLVjkXe_xFBWC7yWF-rt9BPHG1YVCT1-F_LZjE3RUb8','2024-06-16 20:50:32.000000',20,'4f7e16a0-09e8-4140-897f-80d0afc5232e','2024-06-16 20:50:32.000000'),(14,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwLCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODU0NTI3MCwiZXhwIjoxNzE4NTQ1ODcwfQ.oL7zcGi3XDyc6zLU86hJhEx4AmrumYbddIuSGusjJ38','2024-06-16 20:51:10.000000',20,'09c3f26e-7920-4333-95d7-35fd9f1f293a','2024-06-16 20:51:10.000000'),(15,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwLCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODU0NTUyOCwiZXhwIjoxNzE4NTQ2MTI4fQ.BMl5itSxlroRU_lu5OVn1z9UMfcgcexY6sJrEsOaiyE','2024-06-16 20:55:28.000000',20,'29bbfce8-6aec-4122-a430-3e78cd4c7cf6','2024-06-16 20:55:28.000000'),(16,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODY5NjAzMCwiZXhwIjoxNzE4Njk2NjMwfQ.fdxFbVmg7-nw_5nz2kP52_bROdwSGr0sVkccg-VX6XA','2024-06-18 14:43:50.000000',20,'0c87b609-2c08-4e59-a557-1e29b610bcc1','2024-06-18 14:43:50.000000'),(17,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODY5NzU1OCwiZXhwIjoxNzE4Njk4MTU4fQ.V6GpKxkTzNsKZl49i1443gONeTZhwULtRoJBm4jQGEc','2024-06-18 15:09:18.000000',20,'e38255c8-6d40-416c-9f96-aea1656b5cb4','2024-06-18 15:09:18.000000'),(18,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODY5ODMxMCwiZXhwIjoxNzE4Njk4OTEwfQ.keb4LB15BjwwA4dLbtot8uiyTUdhcFLZDRBCv2dGMYU','2024-06-18 15:21:50.000000',20,'12889ff2-68b4-4a0e-a992-69879a342b4e','2024-06-18 15:21:50.000000'),(19,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMDM5MywiZXhwIjoxNzE4NzAwOTkzfQ.5JhswKrY9QhtRIxjJVNgGWSoH3JNSOtGaJWfNdy_FKA','2024-06-18 15:56:33.000000',20,'1253cff1-a4b7-4125-9e5b-ff069f49a513','2024-06-18 15:56:33.000000'),(20,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMDU0NywiZXhwIjoxNzE4NzAxMTQ3fQ.3uXiLA06qwiBPSi4cRpAiZFF68Cw8x_O_A_8kW0yVEc','2024-06-18 15:59:07.000000',20,'8a57df04-b5a7-42a9-9fdf-156ac459cbfe','2024-06-18 15:59:07.000000'),(21,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMDYzNSwiZXhwIjoxNzE4NzAxMjM1fQ.87VKI2rWz7ddGYL5l-ZqlOENXOy5dwV-BSMQc96nRfE','2024-06-18 16:00:35.000000',20,'90ff7bd4-3a85-4d8e-a924-f5c48d07c66b','2024-06-18 16:00:35.000000'),(22,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMjE3NiwiZXhwIjoxNzE4NzAyNzc2fQ.KsZcxztZUp2a-M4vE9P9hqRLlh2J2NVrupDL1CbDwgM','2024-06-18 16:26:16.000000',20,'708bbcdb-8a06-43a5-a330-212b5f570bd1','2024-06-18 16:26:16.000000'),(23,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMzY1NCwiZXhwIjoxNzE4NzA0MjU0fQ.wKVRncOqA9hoKd-R1_2tER9EXBvqToZycIFYAg4iFSI','2024-06-18 16:50:54.000000',20,'695fdbf8-f791-48c0-ad2c-909fef18ea9d','2024-06-18 16:50:54.000000'),(24,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwMzY4NSwiZXhwIjoxNzE4NzA0Mjg1fQ.x6GiwhX8ZqnfsiU4rxlU2X2L5sQ1tzXLahQm1uJUPgs','2024-06-18 16:51:25.000000',20,'5e9c39cf-97f9-4bdd-8430-1a121e6bd254','2024-06-18 16:51:25.000000'),(25,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJzdWIiOiJ1c2VybmFtQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwNDQyOCwiZXhwIjoxNzE4NzA1MDI4fQ.jViNowLmZiZIgFz4jMOpbZOhLN3yX5zbkqksXGUh3M8','2024-06-18 17:03:48.000000',20,'8379c184-437c-4895-9fd9-71e3554f71ee','2024-06-18 17:03:48.000000'),(26,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwNDgzNiwiZXhwIjoxNzE4NzA1NDM2fQ.dH9KheMi56Vj8yx0vcByBScm5y0HzsAgXoN0dStwJlQ','2024-06-18 17:10:36.000000',4,'4a4200c7-0e0e-47a8-88a9-97da61f3c9bb','2024-06-18 17:10:36.000000'),(27,'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6InVzZXIxQGdtYWlsLmNvbSIsImlhdCI6MTcxODcwNTQ5OCwiZXhwIjoxNzE4NzA2MDk4fQ.eeZ0gvW_MfGsU9tdjvNh2pfSw2_iIe-64L1ogYrIzZ8','2024-06-18 17:21:38.000000',4,'ff544483-f591-4975-84be-f52b8a91735e','2024-06-18 17:21:38.000000'),(28,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFmZm1lZGljYWxAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTg4MTQ3MTUsImV4cCI6MTcxODgxNTMxNX0.-SAxPvvP8qMUMkTuJ0ReCY8-dSYVNcmszKb3GMgTLho','2024-06-19 23:41:55.000000',17,'ba815854-6d92-46db-a422-ed37edf22af9','2024-06-19 23:41:55.000000'),(29,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFmZm1lZGljYWxAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTkwNjM4MjYsImV4cCI6MTcxOTA2NzQyNn0.8vLb915vxf6zNfa5vC9N3UuHx0lCsuLRRKqSndOBo4k','2024-06-22 21:43:46.000000',17,'ed342636-f919-4cf7-9499-42adbf625fd0','2024-06-22 20:53:46.000000'),(30,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFmZm1lZGljYWxAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTkwNjU5OTgsImV4cCI6MTcxOTA2OTU5OH0.SjJZmO8LwxHlYwAyab7reGaGIwAO5EEnB8LRV_SEiTs','2024-06-22 22:19:58.000000',17,'8b8a5cc4-243d-4288-882f-06a0d7138c86','2024-06-22 21:29:58.000000'),(31,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMkBnbWFpbC5jb20iLCJpYXQiOjE3MTkwNjg1MDAsImV4cCI6MTcxOTA3MjEwMH0.QAgJdc4GL8fzdY5eYFmteoLndLhYWooOxDJfVkyTPns','2024-06-22 23:01:40.000000',5,'66405be7-ee76-42c0-b6da-b10972795dd5','2024-06-22 22:11:40.000000'),(32,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdGFmZm1lZGljYWxAZXhhbXBsZS5jb20iLCJpYXQiOjE3MTkwNzI5NzMsImV4cCI6MTcxOTA3NjU3M30.s6-U8Bkk2jP8SrbSbXCEinNvkcCqGtPF1pC829TqDJk','2024-06-23 00:16:13.000000',17,'04645912-f6c7-476a-98d4-40b9a3e2215b','2024-06-22 23:26:13.000000'),(35,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYjI0ZGQ3ZGEtNDRlZi00NmE0LWFlYTktMTE2M2Y0YTQ0NThhIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MjEyNDI4LCJleHAiOjE3MTkyMTYwMjh9.mCFBuAubZEUYKpclNQa5HzAiDW4_krKcDXwhisHwLjU','2024-06-24 15:00:28.000000',17,'37864a16-1256-40cb-a489-1d198f4d10fa','2024-06-24 14:10:28.000000'),(36,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMDJhNjVlOTgtN2ZiMC00YjcwLTljNGUtNTM5NWQ2ZmNiYWM4Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MjEzNTE0LCJleHAiOjE3MTkyMTcxMTR9.ocwS6NgC8Fl8t4PhzDKCH0ygB2_J3FkblAw0gbp38LM','2024-06-24 15:18:34.000000',17,'3c44c535-f9cb-4ca7-bb89-bfbdae677f65','2024-06-24 14:28:34.000000'),(37,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMzhkYzEwOTAtYmVkMC00NzU4LWExMDctMmMzNzJmODIzOGJiIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MjEzNjM4LCJleHAiOjE3MTkyMTcyMzh9.ImqUT7inLdy1wmxm7o8WHGypUI9WrwG4Pl5UGWBwspQ','2024-06-24 15:20:38.000000',17,'93005ccf-bb3f-4a0c-8e3f-52eccbb4abdb','2024-06-24 14:30:38.000000'),(38,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiN2JkZjE0OWItYzk5OS00YTRiLWFiYWItMmE1MTIxZDg1ZjgyIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MjYwNDk4LCJleHAiOjE3MTkyNjA1NTh9.QFVySgOYoYjfFXcseWkv-uSGjW_OI3eF2Ybiw8vIvjk','2024-06-24 20:22:38.000000',17,'20a39ad9-3546-4a92-9806-ea00dce50265','2024-06-24 20:26:38.000000'),(39,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZjdkYzU0MDMtZjcyMS00M2MxLWE3ZTMtNzViZDgwZWVlZmM4Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MjYwOTgzLCJleHAiOjE3MTkyNjEwNDN9.UTzWI8mTocsceyO9ygb3TUfwlNJR_FQJEXO752b3nHU','2024-06-24 20:30:43.000000',17,'818efb66-5922-4828-971e-c57a09f615c7','2024-06-24 20:34:40.000000'),(40,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMzdiNjM5ODQtNWI4ZS00MGVlLTg0YmQtNzk3ZmY4ZDBkYzg4Iiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzYwNywiZXhwIjoxNzE5MzIzNjY3fQ.GMM2qegemjDschQ7YKs5o-L41XzOK2nTrLM-6W61JKk','2024-06-25 13:54:27.000000',1,'95872961-e26d-4a84-916a-f8b20755930b','2024-06-25 13:58:27.000000'),(41,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYWVjOWNkZTItNTljYi00ZTYwLTgwOGItNmYwYzRiYjAzOGEyIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzYwNywiZXhwIjoxNzE5MzIzNjY3fQ.6W9_CnSEt7G_-4cIMWPl8EPysGoq24UJKAn754zMuaA','2024-06-25 13:54:27.000000',1,'4cffcafa-e2e0-4912-8d03-64d7c701ee87','2024-06-25 14:03:27.000000'),(42,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMDg5YjUwMDEtYmU4ZS00NTcwLTk4Y2QtMWNiMTgyNWIyZTNkIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzY1NywiZXhwIjoxNzE5MzIzNzE3fQ.55Ujg6KGsSt0IjhfpAy-nJw93Yzf6cH41PHjB_RRRtc','2024-06-25 13:55:17.000000',1,'fd218663-c113-43e0-9c80-0ce56de19e5c','2024-06-25 13:59:17.000000'),(43,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYzA2MTg0ODMtODk4Yy00MTQ3LWJiMDAtYjliYzg3MjgxOWNkIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzc5MywiZXhwIjoxNzE5MzIzODUzfQ.Fmh0QBm2589jM3SiPPtbxAZuhaqN_YEEDlD15OKL0fI','2024-06-25 13:57:33.000000',1,'27232b65-e400-4c80-a8c4-5ec3d78bb5d6','2024-06-25 14:01:33.000000'),(44,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiOTc1YzA1YTYtMjJlNy00ZGU3LTg0YmQtOTUxYmQxZWY0YzI3Iiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzg5OCwiZXhwIjoxNzE5MzIzOTU4fQ.LkVWwW0psD6QzXhYK-lO9Mvx8z9Elt_uzvOJWbG25yc','2024-06-25 13:59:18.000000',1,'d51e1855-b932-43c1-8bd3-a1cd2c024415','2024-06-25 14:03:18.000000'),(45,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYmVkNWYxMDEtZWI3MS00NTQ4LWI2YmEtNzVjMGUxZjFiZGRkIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyMzk1NiwiZXhwIjoxNzE5MzI0MDE2fQ.1NRlEt1c1A0lGEc1y1RXFVQqHOyvgS97QBCwlMz2KVU','2024-06-25 14:00:16.000000',1,'7271b4ab-8bd0-4aba-9ecb-f0cc1d9bce61','2024-06-25 14:04:16.000000'),(46,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMWE1ZWVlYTEtMDFjYy00YmNiLTkzYWQtODhjYzJiZTNlMTNjIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyNDAwMSwiZXhwIjoxNzE5MzI0MDYxfQ.-VKUSJp_c7z6eA6-WOTcSQj_bJ0F26s7e-odyKs1Y4Q','2024-06-25 14:01:01.000000',1,'f6ea9e5e-bc27-4b64-bf72-c1306f197308','2024-06-25 14:05:01.000000'),(47,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZjU4NjdhY2YtNGZhMi00ZWNlLWFhMjgtNDFjYmNjNGY5YzdkIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyNDA5MywiZXhwIjoxNzE5MzI0MTUzfQ.g7vR9DUQy5I4NOcZEnhGsXZ6FIblFdSmIc_AwB1FFx8','2024-06-25 14:02:33.000000',1,'5cd0147e-f8b8-441c-ac28-7b76f1468e8a','2024-06-25 14:06:33.000000'),(48,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZGU1MjI3ZDItMmVlYy00NTc2LThjNjMtZGNmZWQ0ZWFjNjQ0Iiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyNDAwNCwiZXhwIjoxNzE5MzI0MDY0fQ.bL0PmNJTz_oUIJv9-baJUPRq02WfdWatla53xycDc8U','2024-06-25 14:01:04.000000',1,'81cc3f70-9698-4fc7-944d-002bc9ce39dd','2024-06-25 14:10:04.000000'),(49,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZTFiNDY4YTctODgzNi00NDRiLWIzOWMtOTZiNDc4MmJiZDBkIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyNDIxMCwiZXhwIjoxNzE5MzI0MjcwfQ.uihNsVKRRSMP2Cz_kpuhB2NaTR0ciLLdTH41cuEaMS8','2024-06-25 14:04:30.000000',1,'55cc2a55-0c86-42d2-b5c0-62922e7d8a6c','2024-06-25 14:08:30.000000'),(50,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiNzRjYWU5NjMtODI0MS00ZjJhLTkzZTEtNzFkZGE0MmZhODRhIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMyNDU2MywiZXhwIjoxNzE5MzI0NjIzfQ.wP_dZlCYgJH0TwnREFuC15URnCUpOR13gbboeSKfZ3c','2024-06-25 14:10:23.000000',1,'a693a433-a99a-44c0-a389-00003e82912e','2024-06-25 14:14:23.000000'),(51,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiOTNiMjFhOWMtZGIzMy00NTA0LTg0YjAtZmMzYjMxOWZmOTZlIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MzM1MjEzLCJleHAiOjE3MTkzMzg4MTN9.ktTglt5k1aqJd9lsFl9-WEYbmJNVIqaehJjEGtQRnt4','2024-06-25 18:06:53.000000',17,'dd72fae2-fae8-4373-8e92-093b2c4a8c8f','2024-06-25 17:16:53.000000'),(52,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiODRmNTg3YWYtOGNjZC00NjQ5LWJlZTQtYWI3OWE2ZmExNzQxIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MzM1NjI1LCJleHAiOjE3MTkzMzkyMjV9.NSkT8DFP57x9k6lHSBlUgtpFrjdod1g_cknv_W8TVnQ','2024-06-25 18:13:45.000000',17,'87e78365-dae8-4790-ab08-13163821c979','2024-06-25 17:23:45.000000'),(53,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiM2IwNTM3ZWItNjk2OC00YjFkLTllMzMtODU0OTg5NmNlMGFhIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MzM1NzgwLCJleHAiOjE3MTkzMzU4NDB9.aM03UKO1tXjBKzR64e9EgDXSST2bDaPk4J5ZX9VZf64','2024-06-25 17:17:20.000000',17,'87d4d318-fe3a-47ad-a44c-76f59e31088f','2024-06-25 17:26:20.000000'),(54,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiY2VjZTI3OGEtZDA1NS00OWU4LWI2YzItMjQwNTZlZDYxMjBjIiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNjM4NiwiZXhwIjoxNzE5MzM2NDQ2fQ.0vzJS65g8vIXOiZ9b7MVqYfZFLBqOPYj2UL_7FbvqMo','2024-06-25 17:27:26.000000',1,'59f4a671-7579-4ff6-bcff-dae793e4a023','2024-06-25 17:31:25.000000'),(55,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMDA1OTM4ZTUtOGQ5ZC00N2E4LTkzZmYtYzE4MDk4NTk5ZGQ3Iiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNjc3NCwiZXhwIjoxNzE5MzM2ODM0fQ.99ezwK4h5Na5A5jGeZQANlOby9oRny67BWhDQrjKqs8','2024-06-25 17:33:54.000000',1,'7e84522c-ccf3-4707-8c7b-03ca02d3cc98','2024-06-25 17:37:53.000000'),(56,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZjkyNGUzZDQtOTgyOC00MzhkLWI5ODgtMzBlYTU3MTA0N2VmIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MzM2OTA3LCJleHAiOjE3MTkzMzY5Njd9.2FIiUTP23AToB9H4WG2qw5dFStypXO8vK0taHfmTbXM','2024-06-25 17:36:07.000000',17,'262f7566-a666-4eab-901a-15aeac46d2c8','2024-06-25 17:45:07.000000'),(57,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiNGRlMzYxZWUtN2NkYy00YTI5LWFjZTctNDYzNGFlM2Q0ZjM3Iiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNjk4NSwiZXhwIjoxNzE5MzM3MDQ1fQ.5Dj2jkM-55NwQ2eylabkNBTKM56NY5Wv_8x34zagx5w','2024-06-25 17:37:25.000000',1,'6f2ac697-6b53-4d8a-bf85-8f3f219f104b','2024-06-25 17:46:25.000000'),(58,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMjMxN2FlYzAtNzIzNS00NDVlLWJhOWUtNWMyODAxZjEzNTc2Iiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNzA0MywiZXhwIjoxNzE5MzM3MTAzfQ.OS5hXq24MKQqmlVXskhmmqpBrq3chZ28_EJRfYWFlik','2024-06-25 17:38:23.000000',1,'d5d5adcd-a897-4033-b856-538dfb5d57f6','2024-06-25 17:47:23.000000'),(59,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYzVjZTQyN2YtOTRiMC00YmQwLTkxYjItM2UxYzAwMzFmMDc3Iiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNzE5MCwiZXhwIjoxNzE5MzM3MjUwfQ.nEovIAGRhY6dReNLL9YyEO8BIbIIMjj8PyC3a6Ctc4I','2024-06-25 17:40:50.000000',1,'bc7b01aa-08d0-436c-950b-c2955e67be53','2024-06-25 17:44:50.000000'),(60,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMWE4YjA5NmYtNjVkOS00YzRjLTg0ZjktNzA3ZjY1OWNjOWU3Iiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNzMxMiwiZXhwIjoxNzE5MzM3MzcyfQ.bazO1NRQyuozm_mxFY1vPZq3uVifesA5TEz4HA-UbpM','2024-06-25 17:42:52.000000',1,'d5961092-8a41-487f-a436-00ad4876cd32','2024-06-25 17:46:52.000000'),(61,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYzMxMWM2NzMtY2Q3OC00NzAzLWJlOTYtMGJjMThkMjQ0ODAyIiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNzQ3NiwiZXhwIjoxNzE5MzM3NTM2fQ.U07c7PxW-k_0GMJeVFuxTDc9nhVJ2-K7VOo61GKdztw','2024-06-25 17:45:36.000000',1,'8b1a2113-684d-4bf7-93ba-2e303b22d717','2024-06-25 17:49:36.000000'),(62,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiOGU2Y2QzOTAtMzRjOS00OTU3LThiYzMtNWNjMTdjNTRjZGU3Iiwicm9sZSI6IkFETUlOIiwic3ViIjoibmFtMkBleGFtcGxlLmNvbSIsImlhdCI6MTcxOTMzNzg0MSwiZXhwIjoxNzE5MzM3OTAxfQ.NlFRu88OCzvCGFugFZ0LO796MxWaLPxDdho1jSuRjtk','2024-06-25 17:51:41.000000',1,'b9eae906-defa-44c9-9786-6bc1df54fd86','2024-06-25 17:55:41.000000'),(63,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiY2RjYjI5MzAtNjZkZC00Njc4LWE1ODAtOGNlNTQ5NmQ3MTA4Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5MzM4MDAzLCJleHAiOjE3MTkzMzgwNjN9.silOAV2m2CvF3XQiw6O-42w6Kd2Z3j4OhengRD19fD8','2024-06-25 17:54:23.000000',17,'37c8f19e-c14d-4f31-8cc8-e6b62419e454','2024-06-25 18:03:23.000000'),(64,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiZjlmZDYwYWEtNzIzMC00YjExLTllN2ItYTA2MDQ4ODMzYzgxIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDEyNDMxLCJleHAiOjE3MTk0MTI0OTF9.9krPbLtbb1yGDO3_V9RgHc3gvWmawBrVHTD3waaF98s','2024-06-26 14:34:51.000000',17,'0876f5c9-4db5-4149-95a8-71eaa3fe7a3b','2024-06-26 14:43:51.000000'),(65,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiOWJhNmY0MWUtMjdlMy00ZDVlLWJkNzYtNzJkZDZlNTk3YzA3Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDEyNTE4LCJleHAiOjE3MTk0MTI1Nzh9.50AJo30D2iMcispv5O9o_GQHTW0H7IzMPTdoO28jKnI','2024-06-26 14:36:18.000000',17,'857f9d3d-13bb-4a0a-a36a-0d876a70b6c9','2024-06-26 14:45:18.000000'),(66,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiN2I2OTdmNTAtNWNmYS00OTlhLThjYjUtOTYyZTVmZWU4MmM5Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDEyNjUwLCJleHAiOjE3MTk0MTI3MTB9.tC9AntRkiJq1RuVcoiCHGGXklLKYRE1__cJHSVB6Y3c','2024-06-26 14:38:30.000000',17,'3e32dabc-4418-43d1-9ae6-2a9d31ee5e1c','2024-06-26 14:47:30.000000'),(67,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiNjY2YzU0MWMtYWQ1Yi00Yjk0LWFhYzgtNDI1ZmI2ZWI2M2M0Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDEyODA5LCJleHAiOjE3MTk0MTI4Njl9.WPJZGg77t2xH2Jj2h6QcCESQYpE8wtIZk875T5rWgVA','2024-06-26 14:41:09.000000',17,'bd495e87-6a88-48e5-9184-4906c56b679f','2024-06-26 14:50:09.000000'),(68,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYmEwYzAyMDEtMWQ1ZS00MGZjLTg1YWEtODVjNWFlOTdlMGFiIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDEzMDA2LCJleHAiOjE3MTk0MTYwNjZ9.pCO0yOxXM0K1Bvi1Je9KvONi8fb54vnH_tRWVpHwy-A','2024-06-26 15:34:26.000000',17,'94c21f6c-9b5e-4084-8dfa-2b79b7542769','2024-06-26 14:53:26.000000'),(69,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiNTYwYWRhN2EtYjgyNS00MmE0LWFhZTAtNGIwZWNmZDI4OTI0Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDE2MTkzLCJleHAiOjE3MTk0MTkyNTN9.jM8otTJ542UDuO1Wdq9kENrjMnuIixFP02PZrN7WzZc','2024-06-26 16:27:33.000000',17,'0efdab9b-5a11-4cd3-81d8-ce3464185d95','2024-06-26 15:46:33.000000'),(70,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiNmUzM2I2OWQtOTEyOS00ZTA0LWI4NDAtNzQ2NTBlYjVjZDg3Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDIwNzE3LCJleHAiOjE3MTk0MjM3Nzd9.ScmtSvXqHwAG520OOGNDNh3ik0xaxCIV7qYEQfPc3-I','2024-06-26 17:42:57.000000',17,'c750a00a-b659-4d01-828d-b29a5f025ddd','2024-06-26 17:01:57.000000'),(71,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiODIzMWRkMmUtNjQ4MC00MDFhLTk5ZTktZTc5MjgzNzM1MWIwIiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDI1Mzk2LCJleHAiOjE3MTk0Mjg0NTZ9.CQ-qd9y2s4LdPDRntwtTDAyuszwpcioXAlh7vcJZRuo','2024-06-26 19:00:56.000000',17,'894c36ba-3cda-479a-be15-d116a297b73f','2024-06-26 18:19:56.000000'),(72,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiMzJiOGQ5ZGItZWE3Yy00OTdhLTkzYjEtNjYyNDBhODg3ZGI4Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDY0MTI3LCJleHAiOjE3MTk0NjcxODd9.D_nGzKug3hCCq1rlW3c0OCM6hnFDfxo2TagrNkAs128','2024-06-27 05:46:27.000000',17,'0714a86f-f72c-4434-ba93-b13c1d47b00a','2024-06-27 05:05:27.000000'),(73,'eyJhbGciOiJIUzI1NiJ9.eyJVbmlxdWVJZGVudGlmaWVyIjoiYTUxM2UwYjMtYTNlMC00MzFjLWE0YTQtZWMxM2M4YWUxNDk4Iiwic3ViIjoic3RhZmZtZWRpY2FsQGV4YW1wbGUuY29tIiwiaWF0IjoxNzE5NDk2NjYxLCJleHAiOjE3MTk0OTk3MjF9.wPTCiTTmrxbaNQvs6JVmTMhkCL0xoh7z9PtuoNE1qv4','2024-06-27 14:48:41.000000',17,'f7a2f08d-d217-40b4-adc6-9b2f61119d01','2024-06-27 14:07:41.000000');
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sat_sf_c_record`
--

DROP TABLE IF EXISTS `sat_sf_c_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sat_sf_c_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `independence` int NOT NULL,
                                   `month_start` datetime(6) NOT NULL,
                                   `optimistic` int NOT NULL,
                                   `overall_point` int NOT NULL,
                                   `relationship` int NOT NULL,
                                   `shared_story` int NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKbukb1m90m4v6lxjgbrxtxowtm` (`appuser_id`),
                                   CONSTRAINT `FKbukb1m90m4v6lxjgbrxtxowtm` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sat_sf_c_record`
--

LOCK TABLES `sat_sf_c_record` WRITE;
/*!40000 ALTER TABLE `sat_sf_c_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sat_sf_c_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sat_sf_i_record`
--

DROP TABLE IF EXISTS `sat_sf_i_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sat_sf_i_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `consistency` int NOT NULL,
                                   `energy_conservation` int NOT NULL,
                                   `month_start` datetime(6) NOT NULL,
                                   `motivation` int NOT NULL,
                                   `overall_point` int NOT NULL,
                                   `revision` int NOT NULL,
                                   `self_control` int NOT NULL,
                                   `stress_facing` int NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKpa2dk4rvwok9ljug3hnybv7dt` (`appuser_id`),
                                   CONSTRAINT `FKpa2dk4rvwok9ljug3hnybv7dt` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sat_sf_i_record`
--

LOCK TABLES `sat_sf_i_record` WRITE;
/*!40000 ALTER TABLE `sat_sf_i_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sat_sf_i_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sat_sf_p_record`
--

DROP TABLE IF EXISTS `sat_sf_p_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sat_sf_p_record` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `healthy_environment` int NOT NULL,
                                   `life_pursuit` int NOT NULL,
                                   `month_start` datetime(6) NOT NULL,
                                   `overall_point` int NOT NULL,
                                   `planning` int NOT NULL,
                                   `priority_focus` int NOT NULL,
                                   `right_decision` int NOT NULL,
                                   `appuser_id` int NOT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `FKhbrmpdew742giv33xsyr5kbsh` (`appuser_id`),
                                   CONSTRAINT `FKhbrmpdew742giv33xsyr5kbsh` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sat_sf_p_record`
--

LOCK TABLES `sat_sf_p_record` WRITE;
/*!40000 ALTER TABLE `sat_sf_p_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sat_sf_p_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sf_record`
--

DROP TABLE IF EXISTS `sf_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sf_record` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `activity` int NOT NULL,
                             `activity_habit` int NOT NULL,
                             `activity_planning` int NOT NULL,
                             `diet` int NOT NULL,
                             `diet_habit` int NOT NULL,
                             `healthy_diet` int NOT NULL,
                             `medication` int NOT NULL,
                             `medication_habit` int NOT NULL,
                             `month_start` datetime(6) NOT NULL,
                             `plan_compliance` int NOT NULL,
                             `positivity` int NOT NULL,
                             `vegetable_prioritization` int NOT NULL,
                             `appuser_id` int NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKhlien69e58c092npvreltnxqn` (`appuser_id`),
                             CONSTRAINT `FKhlien69e58c092npvreltnxqn` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sf_record`
--

LOCK TABLES `sf_record` WRITE;
/*!40000 ALTER TABLE `sf_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sf_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `step_record`
--

DROP TABLE IF EXISTS `step_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `step_record` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `date` datetime(6) NOT NULL,
                               `planned_step_per_day` int NOT NULL,
                               `week_start` datetime(6) NOT NULL,
                               `appuser_id` int NOT NULL,
                               `actual_value` float NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK2ajbmalg3nrs71i3r6hk4tp0b` (`appuser_id`),
                               CONSTRAINT `FK2ajbmalg3nrs71i3r6hk4tp0b` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step_record`
--

LOCK TABLES `step_record` WRITE;
/*!40000 ALTER TABLE `step_record` DISABLE KEYS */;
INSERT INTO `step_record` VALUES (1,'2024-05-22 07:00:00.000000',5000,'2024-05-22 07:00:00.000000',1,5000),(2,'2024-05-23 07:00:00.000000',4500,'2024-05-22 07:00:00.000000',1,4500),(3,'2024-05-24 07:00:00.000000',4000,'2024-05-22 07:00:00.000000',1,4000),(4,'2024-05-25 07:00:00.000000',4500,'2024-05-22 07:00:00.000000',1,4500),(5,'2024-05-26 07:00:00.000000',4700,'2024-05-22 07:00:00.000000',1,4700),(6,'2024-05-27 07:00:00.000000',5000,'2024-05-22 07:00:00.000000',1,5000),(7,'2024-05-28 07:00:00.000000',3000,'2024-05-28 07:00:00.000000',1,3000),(8,'2024-05-29 07:00:00.000000',3500,'2024-05-28 07:00:00.000000',1,3500),(9,'2024-05-30 07:00:00.000000',3500,'2024-05-28 07:00:00.000000',1,3500),(10,'2024-05-31 07:00:00.000000',4500,'2024-05-28 07:00:00.000000',1,4500),(11,'2024-06-01 07:00:00.000000',5500,'2024-05-28 07:00:00.000000',1,3500),(12,'2024-05-20 14:30:00.000000',5000,'2024-05-20 07:00:00.000000',1,5000),(13,'2024-05-21 14:30:00.000000',4500,'2024-05-20 07:00:00.000000',1,4500),(14,'2024-05-22 14:30:00.000000',4000,'2024-05-20 07:00:00.000000',1,4000),(15,'2024-05-23 14:30:00.000000',4500,'2024-05-20 07:00:00.000000',1,4500),(16,'2024-05-28 07:00:00.000000',4700,'2024-05-20 07:00:00.000000',1,4700),(17,'2024-05-29 07:00:00.000000',5000,'2024-05-20 07:00:00.000000',1,5000),(18,'2024-05-27 07:00:00.000000',3000,'2024-05-27 07:00:00.000000',1,3000),(19,'2024-05-28 07:00:00.000000',3500,'2024-05-27 07:00:00.000000',1,3500),(20,'2024-05-29 07:00:00.000000',3500,'2024-05-27 07:00:00.000000',1,3500),(21,'2024-05-30 07:00:00.000000',4500,'2024-05-27 07:00:00.000000',1,4500),(22,'2024-05-31 07:00:00.000000',5500,'2024-05-27 07:00:00.000000',1,3500);
/*!40000 ALTER TABLE `step_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_lesson`
--

DROP TABLE IF EXISTS `user_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_lesson` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `lesson_date` datetime(6) NOT NULL,
                               `appuser_id` int NOT NULL,
                               `lesson_id` int NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK61d2bd1482jnp2810xfthq7jt` (`appuser_id`),
                               KEY `FKaoad4p1ijn3vbv7yyn1k52uk8` (`lesson_id`),
                               CONSTRAINT `FK61d2bd1482jnp2810xfthq7jt` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`),
                               CONSTRAINT `FKaoad4p1ijn3vbv7yyn1k52uk8` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_lesson`
--

LOCK TABLES `user_lesson` WRITE;
/*!40000 ALTER TABLE `user_lesson` DISABLE KEYS */;
INSERT INTO `user_lesson` VALUES (1,'2023-05-31 07:00:00.000000',1,1);
/*!40000 ALTER TABLE `user_lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_medical_history`
--

DROP TABLE IF EXISTS `user_medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_medical_history` (
                                        `id` int NOT NULL AUTO_INCREMENT,
                                        `appuser_id` int NOT NULL,
                                        `condition_id` int NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FKiatsukhbvajtlfixf7vocc1sc` (`appuser_id`),
                                        KEY `FKsd6bhal1j62iiq8w2xgfvuyua` (`condition_id`),
                                        CONSTRAINT `FKiatsukhbvajtlfixf7vocc1sc` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`),
                                        CONSTRAINT `FKsd6bhal1j62iiq8w2xgfvuyua` FOREIGN KEY (`condition_id`) REFERENCES `medical_history` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_medical_history`
--

LOCK TABLES `user_medical_history` WRITE;
/*!40000 ALTER TABLE `user_medical_history` DISABLE KEYS */;
INSERT INTO `user_medical_history` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,5),(5,1,7),(6,1,9);
/*!40000 ALTER TABLE `user_medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `web_user`
--

DROP TABLE IF EXISTS `web_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `web_user` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                            `account_id` int NOT NULL,
                            `dob` datetime(6) NOT NULL,
                            `gender` bit(1) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_30lhb0i5t6m3037ytkpswsxcu` (`account_id`),
                            CONSTRAINT `FKh1i9vc0mjqu60o2cgv0rq4ds1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_user`
--

LOCK TABLES `web_user` WRITE;
/*!40000 ALTER TABLE `web_user` DISABLE KEYS */;
INSERT INTO `web_user` VALUES (1,'1234567890','john_doe',1,'2024-06-10 19:29:18.000000',_binary '\0'),(2,'1234567890','john_doe',2,'2024-06-10 19:29:18.000000',_binary '\0'),(3,'1234567890','john_doe',3,'2024-06-10 19:29:18.000000',_binary '\0'),(4,'1234567890','nam',16,'2024-06-10 19:29:18.000000',_binary '\0'),(5,'1234567890','staffmedic',17,'2024-06-10 19:29:18.000000',_binary '\0'),(6,'1234567890','vipmedic',19,'2002-12-02 07:00:00.000000',_binary '');
/*!40000 ALTER TABLE `web_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weight_record`
--

DROP TABLE IF EXISTS `weight_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weight_record` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `date` datetime(6) NOT NULL,
                                 `week_start` datetime(6) NOT NULL,
                                 `weight` float NOT NULL,
                                 `appuser_id` int NOT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FK9gxkbwuisy35naa5nismto1n0` (`appuser_id`),
                                 CONSTRAINT `FK9gxkbwuisy35naa5nismto1n0` FOREIGN KEY (`appuser_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weight_record`
--

LOCK TABLES `weight_record` WRITE;
/*!40000 ALTER TABLE `weight_record` DISABLE KEYS */;
INSERT INTO `weight_record` VALUES (1,'2024-05-20 14:30:00.000000','2024-05-20 07:00:00.000000',70.5,1),(2,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',70.3,1),(3,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',70.4,1),(4,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',70.5,1),(5,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',70.6,1),(6,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',70.7,1),(7,'2024-05-26 14:30:00.000000','2024-05-20 07:00:00.000000',70.8,1),(8,'2024-05-27 07:00:00.000000','2024-05-27 07:00:00.000000',70.9,1),(9,'2024-05-28 07:00:00.000000','2024-05-27 07:00:00.000000',70.9,1),(10,'2024-05-29 07:00:00.000000','2024-05-27 07:00:00.000000',70.8,1),(11,'2024-05-30 07:00:00.000000','2024-05-27 07:00:00.000000',70.7,1),(12,'2024-05-31 07:00:00.000000','2024-05-27 07:00:00.000000',70.6,1),(13,'2024-06-01 14:30:00.000000','2024-05-27 07:00:00.000000',70.4,1),(14,'2024-06-02 14:30:00.000000','2024-05-27 07:00:00.000000',70.1,1);
/*!40000 ALTER TABLE `weight_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-27 20:58:45
