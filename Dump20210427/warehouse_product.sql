CREATE DATABASE  IF NOT EXISTS `warehouse` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `warehouse`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: warehouse
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `productName` varchar(50) DEFAULT NULL,
  `price` decimal(15,2) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Caiet A4 matematica',4.20,'papetarie si birotica','pigna',1000),(2,'Caiet A4 dictando',4.20,'papetarie si birotica','pigna',1000),(3,'Coperta caiet A4',2.10,'papetarie si birotica','herlitz',3000),(4,'Dosar cu sina A4',5.49,'papetarie si birotica','herlitz',5000),(5,'Stilou',15.00,'papetarie si birotica','pelican',2997),(6,'Nurofen forte',30.10,'medicamente','Nurofen',110),(7,'Alprazolam',39.99,'medicamente','Xanax',420),(8,'Bupropion',65.00,'medicamente','Wellbutrin',670),(9,'Fluoxetine',79.99,'medicamente','Prozac',500),(10,'Sertraline',84.99,'medicamente','Zoloft',1000),(11,'Geanta',3000.49,'accesorii vestimentare','Gucci',50),(12,'Pantofi barbati',2999.99,'incaltaminte si haine','Gucci',30),(13,'Sacou barbati',4999.49,'incaltaminte si haine','Valentino',13),(14,'Cravata',499.99,'incaltaminte si haine','Valentino',10),(15,'Ochelari de soare',50.00,'accesorii vestimentare','Converse',50),(16,'Franghie de iuta 10 mm, 50m',50.00,'utilitati','NA',1000),(17,'Scaun bucatarie',100.00,'mobilier','Radumob',1000),(18,'Diazepam',79.99,'medicamente','Valium',400);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-27 23:18:23
