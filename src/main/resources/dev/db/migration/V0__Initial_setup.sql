-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: smarthealthc
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
--                            UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'nam2@example.com',_binary '',_binary '\0','$2a$10$VxP4das/0ncnmrbGlnNiuOwWeoCKkBpKh3UcxtyNXrta1rXZApsbe','ADMIN'),(2,'nam1@example.com',_binary '',_binary '\0','$2a$10$VxP4das/0ncnmrbGlnNiuOwWeoCKkBpKh3UcxtyNXrta1rXZApsbe','ADMIN'),(3,'nam3@example.com',_binary '',_binary '\0','$2a$10$Lsbdks04D5.AtGVrdf8iNOt84wCYxTYCL3hmJYCKowuOQfqiRMBjm','ADMIN'),(4,'user1@gmail.com',_binary '\0',_binary '\0','$2a$10$URZD3Nj/x55TTH3SOgPMWeSjvX/cEnYl4.w69ze8F46Bzwk65QnEy','USER'),(5,'user2@gmail.com',_binary '',_binary '\0','$2a$10$b/.uJ38ZoxQdPV2cWasMleiHYaMakdG5pPHjhDuIwi3XG64iPt7iq','USER'),(6,'user3@gmail.com',_binary '',_binary '\0','$2a$10$/bc4MfW68AdDedKVhbw8P.tW6vr3z6P44lzG38CNiEL4TyQMRDKiq','USER'),(7,'nam123@gmail.com',_binary '',_binary '\0','$2a$10$xQBMSCAcA1vCkvE5rp4MXuqLgzitEFk9G3mp9/JiJKSpfYXB58Q8K','USER'),(8,'nam124@gmail.com',_binary '',_binary '\0','$2a$10$FDN6j5uDSIhLZ6HEcC2.7OUBSVsJ8NcnayaY2Z6NyfpcpAa82rmXi','USER'),(9,'nam125@gmail.com',_binary '',_binary '\0','$2a$10$Xz0URXaZyraylLiLYUBb4.TuHt35NNi00e2OcDPz63rA3PqKtqMP2','USER'),(10,'nam126@gmail.com',_binary '',_binary '\0','$2a$10$LcVVpjP33Dp9XUv9yD8xwe3eDW7c4lX4XbpT58wqh2xk9jvzpAkm6','USER'),(11,'nam127@gmail.com',_binary '\0',_binary '\0','$2a$10$6LmG.yELpjG4VwHOpNGS0uoBbVwqdvV5n7.HwfiJ8h7Nu6nPWQR2m','USER'),(12,'nam128@gmail.com',_binary '\0',_binary '\0','$2a$10$dSHxbgOQndY6vcdzoiqLtOBco/4L99zDhFaaQn0KQCMqul/1rA9LK','USER'),(13,'nam129@gmail.com',_binary '\0',_binary '','$2a$10$.v1vHDtZ6959monnMzQ0wOrP1BqwvrHSbuO4YoqPzpeYsEymwbzGa','USER'),(14,'nam139@gmail.com',_binary '\0',_binary '','$2a$10$lLYs3xadQaJD28P8jEOf/u1PNu9iZ1c1rvd2dFc2dTeqbLX8mRSNy','USER'),(15,'nam132@gmail.com',_binary '\0',_binary '','$2a$10$zjNqxA.ZfGG7JHcgwyms9u/Z0SLWSOfg9JCLrSlQ2c6tZTX14U9ty','USER'),(16,'nam4@example.com',_binary '',_binary '\0','$2a$10$DBjZz2HN47Bp7387/ltdqeQek4PLLsAefHx2D3US4tJuIz3vmmI8a','ADMIN'),(17,'staffmedical@example.com',_binary '',_binary '\0','$2a$10$9So4ioxgyCi2NtQIMA8IOOqPdZzayajEFo41iB6nuMgxDblGCycuu','MEDICAL_SPECIALIST'),(18,'quang@gmail.com',_binary '\0',_binary '\0','$2a$10$48vhfcw7h0.YgizuV6isieNe7MVR4xm5cFTWFGoa6a9/wK9X/yhMW','USER');
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
INSERT INTO `activity_record` VALUES (1,'2024-05-20 14:30:00.000000','2024-05-20 07:00:00.000000',1,75,'HEAVY',75,'HEAVY'),(2,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,45,'MEDIUM',65,'MEDIUM'),(3,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,45,'MEDIUM',45,'MEDIUM'),(4,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,40,'LIGHT',35,'LIGHT'),(5,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,50,'LIGHT',40,'HEAVY'),(6,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,35,'LIGHT',45,'MEDIUM'),(7,'2024-05-26 14:30:00.000000','2024-05-20 07:00:00.000000',1,40,'MEDIUM',45,'LIGHT'),(8,'2024-05-27 07:00:00.000000','2024-05-27 07:00:00.000000',1,30,'MEDIUM',45,'LIGHT'),(9,'2024-05-28 07:00:00.000000','2024-05-27 07:00:00.000000',1,35,'LIGHT',60,'MEDIUM'),(10,'2024-05-29 07:00:00.000000','2024-05-27 07:00:00.000000',1,45,'LIGHT',50,'LIGHT'),(11,'2024-05-30 07:00:00.000000','2024-05-27 07:00:00.000000',1,0,NULL,55,'LIGHT'),(12,'2024-06-11 02:57:55.000000','2024-06-11 02:58:00.000000',1,NULL,NULL,1,'LIGHT');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,4,5,'0123567125673218'),(2,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,5,1,'0123567125673218'),(3,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Y12312312','010-1234-5678',75,6,1,'0123567125673218'),(4,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Your Generated Name','010-1234-5678',75,7,1,'0123567125673218'),(5,'1990-01-01 07:00:00.000000',_binary '',19,NULL,'Y12312312','010-1234-5678',18,8,1,'0123567125673218'),(6,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam125 Name','010-1234-5678',75,9,1,'0123567125673218'),(7,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam126 Name','010-1234-5678',75,10,1,'0123567125673218'),(8,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam127 Name','010-1234-5678',75,11,5,'0123567125673218'),(9,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam128 Name','010-1234-5678',75,12,5,'0123567125673218'),(10,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam129 Name','010-1234-5678',75,13,5,'0123567125673218'),(11,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam139 Name','010-1234-5678',75,14,5,'0123567125673218'),(12,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'nam132 Name','010-1234-5678',75,15,5,'0123567125673218'),(13,'1990-01-01 07:00:00.000000',_binary '',180.5,NULL,'Your Generated Name','010-1234-5678',75,18,NULL,'0123567125673218');
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

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'0','Initial setup','SQL','V0__Initial_setup.sql',-1544244090,'root','2024-06-12 04:49:53',1941,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_appointment`
--

LOCK TABLES `medical_appointment` WRITE;
/*!40000 ALTER TABLE `medical_appointment` DISABLE KEYS */;
INSERT INTO `medical_appointment` VALUES (1,'2024-05-28 19:34:56.789000','Hanoi','PENDING','DIAGNOSIS',1,'abcdef'),(2,'2024-05-02 18:30:00.000000','Location A','CONFIRM','DIAGNOSIS',1,'abcdef'),(3,'2024-05-02 18:30:00.000000','Location A','PENDING','DIAGNOSIS',2,'abcdef'),(4,'2024-05-02 18:30:00.000000','Location A','CONFIRM','DIAGNOSIS',1,'abcdef'),(5,'2024-05-02 18:30:00.000000','Location C','PENDING','DIAGNOSIS',4,'abcdef'),(6,'2024-05-02 18:30:00.000000','Location D','PENDING','MEDICAL_CHECKUP',1,'abcdef'),(7,'2024-05-02 18:30:00.000000','Location E','PENDING','MEDICAL_CHECKUP',2,'abcdef'),(8,'2024-05-02 18:30:00.000000','Location Z','PENDING','MEDICAL_CHECKUP',3,'abcdef'),(9,'2024-05-02 18:30:00.000000','Location N','PENDING','MEDICAL_CHECKUP',4,'abcdef'),(10,'2024-05-02 18:30:00.000000','Location N','PENDING','MEDICAL_CHECKUP',5,'abcdef'),(11,'2024-05-02 18:30:00.000000','Location M','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(12,'2024-05-02 18:30:00.000000','LocationZ','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(13,'2024-05-02 18:30:00.000000','LocationD','PENDING','MEDICAL_CHECKUP',6,'abcdef'),(14,'2024-05-02 18:30:00.000000','Location G','PENDING','DIAGNOSIS',4,'abcdef'),(15,'2024-05-02 18:30:00.000000','Location S','PENDING','DIAGNOSIS',4,'abcdef'),(16,'2024-05-02 18:30:00.000000','Location J','PENDING','DIAGNOSIS',4,'abcdef'),(17,'2024-05-02 18:30:00.000000','Location M','PENDING','DIAGNOSIS',4,'abcdef');
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
INSERT INTO `medical_history` VALUES (1,_binary '\0','Bệnh thận mãn tính','CARDINAL'),(2,_binary '\0','Cao huyết áp','CARDINAL'),(3,_binary '\0','Bệnh xơ phổi vô căn','RESPIRATORY'),(4,_binary '\0','Bệnh hen phế quản/ hen suyễn','RESPIRATORY'),(5,_binary '\0','Tổn thương phổi sau bệnh lao','RESPIRATORY'),(6,_binary '\0','Viêm khớp dạng thấp','ARTHRITIS'),(7,_binary '\0','Thoái hoá khớp (Hông, đầu gối, khuỷu tay)','ARTHRITIS'),(8,_binary '\0','Bệnh Tim','OTHERS'),(9,_binary '\0','Bệnh Gan','OTHERS'),(10,_binary '\0','Hút thuốc 1','HABIT'),(11,_binary '\0','Uống rượu','HABIT');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_record`
--

LOCK TABLES `medicine_record` WRITE;
/*!40000 ALTER TABLE `medicine_record` DISABLE KEYS */;
INSERT INTO `medicine_record` VALUES (2,'2024-05-20 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,2),(3,'2024-05-21 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,3),(4,'2024-05-22 14:30:00.000000',_binary '','2024-05-20 07:00:00.000000',1,4),(5,'2024-05-23 14:30:00.000000',_binary '\0','2024-05-20 07:00:00.000000',1,4),(6,'2024-05-28 07:00:00.000000',_binary '','2024-05-27 07:00:00.000000',1,1),(7,'2024-05-29 07:00:00.000000',_binary '\0','2024-05-27 07:00:00.000000',1,2),(8,'2024-05-30 07:00:00.000000',_binary '','2024-05-27 07:00:00.000000',1,4);
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
                                 `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
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
INSERT INTO `mental_record` VALUES (1,'2024-05-20 07:00:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(2,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,2,_binary ''),(3,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(4,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(5,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(6,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,5,_binary ''),(7,'2024-05-26 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(8,'2024-05-27 07:00:00.000000','2024-05-27 07:00:00.000000',1,4,_binary ''),(9,'2024-05-28 07:00:00.000000','2024-05-27 07:00:00.000000',1,5,_binary ''),(10,'2024-05-29 07:00:00.000000','2024-05-27 07:00:00.000000',1,1,_binary ''),(11,'2024-05-30 07:00:00.000000','2024-05-27 07:00:00.000000',1,1,_binary ''),(12,'2024-05-31 07:00:00.000000','2024-05-27 07:00:00.000000',1,4,_binary ''),(13,'2024-06-01 14:30:00.000000','2024-05-27 07:00:00.000000',1,5,_binary '\0'),(14,'2024-05-21 14:30:00.000000','2024-05-20 07:00:00.000000',1,2,_binary ''),(15,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(16,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(17,'2024-05-22 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(18,'2024-05-23 14:30:00.000000','2024-05-20 07:00:00.000000',1,1,_binary ''),(19,'2024-05-24 14:30:00.000000','2024-05-20 07:00:00.000000',1,3,_binary ''),(20,'2024-05-25 14:30:00.000000','2024-05-20 07:00:00.000000',1,5,_binary '');
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
                               `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','Microsoft Assignment Query','ASSIGN_MS',1,1,'2024-05-07 18:30:00.000000'),(2,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','Microsoft Assignment Query','ASSIGN_ADMIN',1,1,'2024-05-07 18:30:00.000000'),(3,'This is the answer vip pro 2','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123','ASSIGN_ADMIN',2,1,'2024-05-07 18:30:00.000000'),(4,'','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123123','ASSIGN_ADMIN',2,NULL,'2024-05-02 18:30:00.000000'),(5,'tra loi cau hoi','How do I assign a Microsoft license to a user?','2024-05-02 18:30:00.000000','siuuuuuuu123123','ASSIGN_ADMIN',2,1,'2024-06-02 16:23:25.519000'),(8,'','How do I assign a Microsoft license to a user?','2024-06-11 02:57:14.885000','sssssss','ASSIGN_ADMIN',2,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `web_user`
--

LOCK TABLES `web_user` WRITE;
/*!40000 ALTER TABLE `web_user` DISABLE KEYS */;
INSERT INTO `web_user` VALUES (1,'1234567890','john_doe',1,'2024-06-10 19:29:18.000000',_binary '\0'),(2,'1234567890','john_doe',2,'2024-06-10 19:29:18.000000',_binary '\0'),(3,'1234567890','john_doe',3,'2024-06-10 19:29:18.000000',_binary '\0'),(4,'1234567890','nam',16,'2024-06-10 19:29:18.000000',_binary '\0'),(5,'1234567890','staffmedic',17,'2024-06-10 19:29:18.000000',_binary '\0');
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

-- Dump completed on 2024-06-12 12:19:18
