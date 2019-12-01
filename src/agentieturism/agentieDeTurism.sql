-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: agentieturism
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `agentie`
--

DROP TABLE IF EXISTS `agentie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agentie` (
  `cod_agentie` int(11) NOT NULL AUTO_INCREMENT,
  `denumire` varchar(100) DEFAULT NULL,
  `adresa` varchar(100) DEFAULT NULL,
  `telefon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cod_agentie`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agentie`
--

LOCK TABLES `agentie` WRITE;
/*!40000 ALTER TABLE `agentie` DISABLE KEYS */;
INSERT INTO `agentie` VALUES (1,'Expedia','Str. Florilor, Nr.45, Bucuresti','0214 985 475'),(2,'Priceline','Str. Dumbravei, Nr.20, Constanta','0241 485 363'),(3,'World Travel Inc.','Str. Mare, Nr. 123, Cluj-Napoca','0264 123 456'),(4,'Travizon','Str. Primaverii, Nr. 200, Iasi','0232 100 200'),(5,'Direct Travel','Str. Alba, Nr. 34, Timisoara','0256 808 502');
/*!40000 ALTER TABLE `agentie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `angajat`
--

DROP TABLE IF EXISTS `angajat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `angajat` (
  `cod_angajat` int(11) NOT NULL AUTO_INCREMENT,
  `Nume` varchar(45) NOT NULL,
  `Prenume` varchar(45) NOT NULL,
  `Telefon` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `cont` varchar(45) NOT NULL,
  `parola` varchar(45) NOT NULL,
  `cod_functie` int(11) NOT NULL,
  `cod_agentie` int(11) NOT NULL,
  PRIMARY KEY (`cod_angajat`),
  UNIQUE KEY `Telefon_UNIQUE` (`Telefon`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `cont_UNIQUE` (`cont`),
  UNIQUE KEY `parola_UNIQUE` (`parola`),
  KEY `fk_angajat_functie_idx` (`cod_functie`),
  KEY `fk_angajat_agentie` (`cod_agentie`),
  CONSTRAINT `fk_angajat_agentie` FOREIGN KEY (`cod_agentie`) REFERENCES `agentie` (`cod_agentie`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_angajat_functie` FOREIGN KEY (`cod_functie`) REFERENCES `functie` (`cod_functie`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `angajat`
--

LOCK TABLES `angajat` WRITE;
/*!40000 ALTER TABLE `angajat` DISABLE KEYS */;
INSERT INTO `angajat` VALUES (1,'Popescu','Adrian','0727808503','adi.popescu@yahoo.com','admin','1234',1,0),(2,'Ionescu','Elena','0727000113','elena_ion@gmail.com','ie100','1111',2,1),(3,'Cristescu','George','0728000111','cristigeo@gmail.com','cg100','1112',2,2),(4,'Matei','Corina','0729000111','corinamatei@gmail.com','cm100','2222',3,3),(5,'Mocanu','Alexandru','0730000111','alex_mocanu@yahoo.com','am100','2223',3,1),(6,'Vlad','Andrei','0787122322','avlad@yahoo.com','av100','3333',2,4),(12,'Bratu','Alexandra','0724896522','abratu@yahoo.com','ab109','2221',2,5);
/*!40000 ALTER TABLE `angajat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bilet`
--

DROP TABLE IF EXISTS `bilet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bilet` (
  `cod_zbor` int(11) NOT NULL,
  `cod_client` int(11) NOT NULL,
  `pret` int(11) DEFAULT NULL,
  `data_achizitionarii` date DEFAULT NULL,
  KEY `fk_zbor_has_client_client1_idx` (`cod_client`),
  KEY `fk_zbor_has_client_zbor1_idx` (`cod_zbor`),
  CONSTRAINT `fk_zbor_has_client_zbor1` FOREIGN KEY (`cod_zbor`) REFERENCES `zbor` (`cod_zbor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bilet`
--

LOCK TABLES `bilet` WRITE;
/*!40000 ALTER TABLE `bilet` DISABLE KEYS */;
INSERT INTO `bilet` VALUES (1,2,500,'2018-01-25'),(1,3,550,'2018-03-25'),(2,1,1000,'2018-08-20'),(6,5,220,'2018-08-15'),(3,4,500,'2018-12-20'),(1,6,560,'2018-12-20'),(4,1,550,'2018-12-20'),(1,1,800,'2018-12-27'),(3,3,520,'2019-01-02'),(1,1,800,'2019-01-02'),(3,2,560,'2019-01-08'),(1,1,800,'2019-01-08'),(2,1,1500,'2019-01-09'),(2,1,1500,'2019-01-10'),(1,1,800,'2019-01-10'),(1,1,800,'2019-11-14');
/*!40000 ALTER TABLE `bilet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `camera`
--

DROP TABLE IF EXISTS `camera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `camera` (
  `COD_CAM` int(11) NOT NULL AUTO_INCREMENT,
  `tip_camera` varchar(45) NOT NULL,
  `cod_cazare` int(11) NOT NULL,
  PRIMARY KEY (`COD_CAM`),
  KEY `fk_camera_cazare1_idx` (`cod_cazare`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `camera`
--

LOCK TABLES `camera` WRITE;
/*!40000 ALTER TABLE `camera` DISABLE KEYS */;
INSERT INTO `camera` VALUES (1,'single',1),(2,'single',2),(3,'single',3),(4,'single',4),(5,'single',5),(6,'single',6),(7,'single',7),(8,'twin',1),(9,'twin',2),(10,'twin',3),(11,'twin',5),(12,'twin',7),(13,'triple',2),(14,'triple',3),(15,'triple',5),(16,'triple',7),(17,'deluxe',7),(18,'deluxe',2),(19,'quad',1),(21,'quad',4),(22,'quad',6),(23,'single',8),(24,'double',8),(25,'deluxe',8);
/*!40000 ALTER TABLE `camera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cazare`
--

DROP TABLE IF EXISTS `cazare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cazare` (
  `cod_cazare` int(11) NOT NULL AUTO_INCREMENT,
  `Denumire_cazare` varchar(45) NOT NULL,
  `Tip` varchar(45) NOT NULL,
  `Nr_stele` int(11) NOT NULL,
  `cod_circuit` int(11) NOT NULL,
  `cod_sejur` int(11) NOT NULL,
  PRIMARY KEY (`cod_cazare`),
  KEY `fk_cazare_circuit1_idx` (`cod_circuit`),
  KEY `fk_cazare_sejur1_idx` (`cod_sejur`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cazare`
--

LOCK TABLES `cazare` WRITE;
/*!40000 ALTER TABLE `cazare` DISABLE KEYS */;
INSERT INTO `cazare` VALUES (1,'Hotel La Siesta','hotel',4,5,3),(2,'Hotel Belvedere','hotel',5,1,2),(3,'La Reserve Paris','hotel',5,1,1),(4,'Nayara Springs','hotel',4,5,3),(5,'Kayakapi Premium Caves','hotel',5,1,3),(6,'Hotel 41','hotel',3,1,2),(7,'The Killarney','hotel',5,3,4),(8,'Fjord','hotel',5,4,1);
/*!40000 ALTER TABLE `cazare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `circuit`
--

DROP TABLE IF EXISTS `circuit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `circuit` (
  `cod_circuit` int(11) NOT NULL AUTO_INCREMENT,
  `Denumire` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cod_circuit`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `circuit`
--

LOCK TABLES `circuit` WRITE;
/*!40000 ALTER TABLE `circuit` DISABLE KEYS */;
INSERT INTO `circuit` VALUES (1,'Europa'),(2,'Franta'),(3,'Mountain Trip'),(4,'Scandinavia Dream'),(5,'Tropical'),(9,'Belgia Tour'),(10,'Asian Experience'),(11,'CIRCUIT SUPER'),(12,'Sun Waves');
/*!40000 ALTER TABLE `circuit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `cod_client` int(11) NOT NULL AUTO_INCREMENT,
  `Nume` varchar(45) NOT NULL,
  `Prenume` varchar(45) NOT NULL,
  `Telefon` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Cont` varchar(45) DEFAULT NULL,
  `Parola` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cod_client`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Barbu','Andreea','0714000222','and.barbu@gmail.com','ab100','ab100'),(2,'Corbu','Andrei','0741555000','and.corbu@gmail.com','ac100','ac100'),(3,'Nicolae','Ion','0741525252','ionnicolae@gmail.com','in100','in100'),(4,'Vlad','Bianca','0721000111','bibivlad@yahoo.com','bv100','bv100'),(5,'Nistor','Mircea','0740000333','mircea.nistor@gmail.com','nb100','nb100'),(6,'Maicu','Marian','0756328741','marian.maicu@yahoo.com','mm100','mm100');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `cod_angajat` int(11) NOT NULL,
  `cod_furnizor` int(11) NOT NULL,
  `nrcontract` int(11) NOT NULL AUTO_INCREMENT,
  `data_incheierii` date NOT NULL,
  PRIMARY KEY (`nrcontract`),
  KEY `fk_angajat_has_furnizori_furnizori1_idx` (`cod_furnizor`),
  KEY `fk_angajat_has_furnizori_angajat1_idx` (`cod_angajat`),
  CONSTRAINT `fk_angajat_has_furnizori_furnizori1` FOREIGN KEY (`cod_furnizor`) REFERENCES `furnizori` (`cod_furnizor`),
  CONSTRAINT `fk_angajat_id` FOREIGN KEY (`cod_angajat`) REFERENCES `angajat` (`cod_angajat`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (3,4,113,'2018-12-18'),(1,2,122,'2018-01-01'),(1,2,123,'2017-03-04'),(1,2,124,'2016-03-04'),(1,1,125,'2018-01-01'),(1,1,126,'2018-01-01'),(1,1,127,'2018-01-01'),(1,3,128,'2018-01-01'),(1,3,129,'2018-01-01'),(1,1,130,'2018-01-01'),(1,4,131,'2018-01-01'),(1,1,132,'2019-01-01'),(1,3,133,'2018-08-17'),(1,2,134,'2018-11-08'),(1,2,135,'2019-01-10'),(1,1,136,'2019-01-01');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facilitati`
--

DROP TABLE IF EXISTS `facilitati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facilitati` (
  `cod_facilitate` int(11) NOT NULL AUTO_INCREMENT,
  `Denumire` varchar(45) NOT NULL,
  `cod_cam` int(11) NOT NULL,
  PRIMARY KEY (`cod_facilitate`),
  KEY `fk_facilitati_camera1_idx` (`cod_cam`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilitati`
--

LOCK TABLES `facilitati` WRITE;
/*!40000 ALTER TABLE `facilitati` DISABLE KEYS */;
INSERT INTO `facilitati` VALUES (1,'TV',1),(2,'TV',2),(3,'TV',3),(4,'TV',4),(5,'TV',5),(6,'TV',6),(7,'TV',7),(8,'TV',8),(9,'TV',9),(10,'TV',10),(11,'TV',11),(12,'TV',12),(13,'TV',13),(14,'TV',14),(15,'TV',15),(16,'TV',16),(17,'TV',17),(18,'TV',18),(19,'TV',19),(20,'TV',20),(21,'TV',21),(22,'TV',22),(23,'TV',23),(24,'TV',24),(25,'TV',25),(26,'aer conditionat',2),(27,'aer conditionat',4),(28,'aer conditionat',6),(29,'aer conditionat',8),(30,'aer conditionat',10),(31,'aer conditionat',12),(32,'aer conditionat',14),(33,'aer conditionat',16),(34,'aer conditionat',18),(35,'aer conditionat',20),(36,'aer conditionat',22),(37,'aer conditionat',24),(38,'aer conditionat',25),(39,'jacuzzi',17),(40,'jacuzzi',18),(41,'jacuzzi',25),(42,'internet',1),(43,'internet',2),(44,'internet',3),(45,'internet',4),(46,'internet',5),(47,'internet',6),(48,'internet',7),(49,'internet',8),(50,'internet',9),(51,'internet',10),(52,'internet',11),(53,'internet',12),(54,'internet',13),(55,'internet',14),(56,'internet',15),(57,'internet',16),(58,'internet',17),(59,'internet',18),(60,'internet',19),(61,'internet',20),(62,'internet',21),(63,'internet',22),(64,'internet',23),(65,'internet',24),(66,'internet',25);
/*!40000 ALTER TABLE `facilitati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `functie`
--

DROP TABLE IF EXISTS `functie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `functie` (
  `cod_functie` int(11) NOT NULL,
  `Den_functie` varchar(45) NOT NULL,
  PRIMARY KEY (`cod_functie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functie`
--

LOCK TABLES `functie` WRITE;
/*!40000 ALTER TABLE `functie` DISABLE KEYS */;
INSERT INTO `functie` VALUES (1,'Administrator'),(2,'Agent Ticketing'),(3,'Agent Turism');
/*!40000 ALTER TABLE `functie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `furnizori`
--

DROP TABLE IF EXISTS `furnizori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `furnizori` (
  `cod_furnizor` int(11) NOT NULL,
  `Firma` varchar(45) NOT NULL,
  PRIMARY KEY (`cod_furnizor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `furnizori`
--

LOCK TABLES `furnizori` WRITE;
/*!40000 ALTER TABLE `furnizori` DISABLE KEYS */;
INSERT INTO `furnizori` VALUES (1,'AirFrance'),(2,'Tarom'),(3,'Blue Air'),(4,'Wizz');
/*!40000 ALTER TABLE `furnizori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oras`
--

DROP TABLE IF EXISTS `oras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oras` (
  `cod_oras` int(11) NOT NULL AUTO_INCREMENT,
  `Nume_oras` varchar(45) NOT NULL,
  `Tara` varchar(45) NOT NULL,
  `cod_circuit` int(11) NOT NULL,
  PRIMARY KEY (`cod_oras`),
  UNIQUE KEY `Nume_oras_UNIQUE` (`Nume_oras`),
  KEY `fk_oras_circuit1_idx` (`cod_circuit`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oras`
--

LOCK TABLES `oras` WRITE;
/*!40000 ALTER TABLE `oras` DISABLE KEYS */;
INSERT INTO `oras` VALUES (1,'Budapesta','Ungaria',1),(2,'Praga','Cehia',1),(3,'Dresda','Germania',1),(4,'Bratislava','Slovacia',1),(5,'Viena','Austria',1),(6,'Strasbourg','Franta',2),(7,'Paris','Franta',2),(8,'Reims','Franta',2),(90,'Bucuresti','Romania',5),(91,'Charleroi','Belgia',9),(92,'Bruxelles','Belgia',9),(93,'Bruges','Belgia',9),(94,'Antwerp','Belgia',9),(95,'Hong Kong','China',10),(96,'Beijing','China',10),(97,'Linz','Austria',11),(98,'Freeport','Bahamas',11),(99,'Sofia','Bulgaria',11),(100,'Rio de Janeiro','Brazilia',12);
/*!40000 ALTER TABLE `oras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orase`
--

DROP TABLE IF EXISTS `orase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orase` (
  `nume` varchar(100) DEFAULT NULL,
  `tara` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orase`
--

LOCK TABLES `orase` WRITE;
/*!40000 ALTER TABLE `orase` DISABLE KEYS */;
INSERT INTO `orase` VALUES ('Viena','Austria'),('Graz','Austria'),('Linz','Austria'),('Innsbruck','Austria'),('Salzburg','Austria'),('Nassau','Bahamas'),('Freeport','Bahamas'),('West End','Bahamas'),('Antwerp','Belgia'),('Ghent','Belgia'),('Charleroi','Belgia'),('Bruxelles','Belgia'),('Bruges','Belgia'),('Sao Paulo','Brazilia'),('Rio de Janeiro','Brazilia'),('Brasilia','Brazilia'),('Salvador','Brazilia'),('Varna','Bulgaria'),('Sofia','Bulgaria'),('Hong Kong','China'),('Beijing','China'),('Shanghai','China'),('Chuzhou','China'),('Copenhaga','Danemarca'),('Santo Domingo','Republica Dominicana'),('Helsinki','Finlanda'),('Paris','Franta'),('Marseille','Franta'),('Lyon','Franta'),('Toulouse','Franta'),('Nice','Franta'),('Nantes','Franta');
/*!40000 ALTER TABLE `orase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pret_circuit`
--

DROP TABLE IF EXISTS `pret_circuit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pret_circuit` (
  `circuit_cod_circuit` int(11) NOT NULL,
  `sezon_cod_sezon` int(11) NOT NULL,
  `valoare_circuit` int(11) DEFAULT NULL,
  `cod_agentie_circuit` int(11) DEFAULT NULL,
  KEY `fk_circuit_has_sezon_sezon1_idx` (`sezon_cod_sezon`),
  KEY `fk_circuit_has_sezon_circuit1_idx` (`circuit_cod_circuit`),
  KEY `fk_cod_agentie_circuit` (`cod_agentie_circuit`),
  CONSTRAINT `fk_circuit_has_sezon_sezon1` FOREIGN KEY (`sezon_cod_sezon`) REFERENCES `sezon` (`cod_sezon`),
  CONSTRAINT `fk_cod_agentie_circuit` FOREIGN KEY (`cod_agentie_circuit`) REFERENCES `agentie` (`cod_agentie`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pret_circuit`
--

LOCK TABLES `pret_circuit` WRITE;
/*!40000 ALTER TABLE `pret_circuit` DISABLE KEYS */;
INSERT INTO `pret_circuit` VALUES (1,1,5000,2),(1,2,2500,3),(1,3,3000,3),(1,5,4000,1),(1,6,5000,1),(2,6,7500,5),(2,5,5800,4),(2,4,5000,5),(2,3,3000,1),(3,1,8000,4),(4,1,9000,2),(4,2,7000,4),(4,6,5000,3),(5,6,5000,5),(9,3,4000,3),(9,5,4400,3),(9,1,3400,3),(10,5,5400,1),(10,6,8900,1),(10,7,5900,1);
/*!40000 ALTER TABLE `pret_circuit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pret_sejur`
--

DROP TABLE IF EXISTS `pret_sejur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pret_sejur` (
  `sejur_cod_sejur` int(11) NOT NULL,
  `sezon_cod_sezon` int(11) NOT NULL,
  `valoare_sejur` int(11) DEFAULT NULL,
  `cod_agentie` int(11) DEFAULT NULL,
  KEY `fk_sejur_has_sezon_sezon1_idx` (`sezon_cod_sezon`),
  KEY `fk_sejur_has_sezon_sejur1_idx` (`sejur_cod_sejur`),
  KEY `fk_cod_agentie` (`cod_agentie`),
  CONSTRAINT `fk_cod_agentie` FOREIGN KEY (`cod_agentie`) REFERENCES `agentie` (`cod_agentie`) ON DELETE CASCADE,
  CONSTRAINT `fk_sejur_has_sezon_sezon1` FOREIGN KEY (`sezon_cod_sezon`) REFERENCES `sezon` (`cod_sezon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pret_sejur`
--

LOCK TABLES `pret_sejur` WRITE;
/*!40000 ALTER TABLE `pret_sejur` DISABLE KEYS */;
INSERT INTO `pret_sejur` VALUES (1,1,1500,1),(1,2,2000,1),(2,1,2000,4),(3,1,2200,3),(4,1,2200,5),(5,4,3000,4);
/*!40000 ALTER TABLE `pret_sejur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervare_cicuit`
--

DROP TABLE IF EXISTS `rezervare_cicuit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezervare_cicuit` (
  `circuit_cod_circuit` int(11) NOT NULL,
  `client_cod_client` int(11) NOT NULL,
  `cod_rezervare` int(11) NOT NULL AUTO_INCREMENT,
  `data_plecare` date DEFAULT NULL,
  PRIMARY KEY (`cod_rezervare`),
  KEY `fk_circuit_has_client_client1_idx` (`client_cod_client`),
  KEY `fk_circuit_has_client_circuit1_idx` (`circuit_cod_circuit`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervare_cicuit`
--

LOCK TABLES `rezervare_cicuit` WRITE;
/*!40000 ALTER TABLE `rezervare_cicuit` DISABLE KEYS */;
INSERT INTO `rezervare_cicuit` VALUES (2,3,6,'2019-05-02'),(1,1,7,'2019-03-06'),(2,11,8,'2019-01-01'),(11,1,9,'2019-01-01');
/*!40000 ALTER TABLE `rezervare_cicuit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervare_sejur`
--

DROP TABLE IF EXISTS `rezervare_sejur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezervare_sejur` (
  `cod_sejur` int(11) NOT NULL,
  `cod_client` int(11) NOT NULL,
  `cod_rezervare` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cod_rezervare`),
  KEY `fk_sejur_has_client_client1_idx` (`cod_client`),
  KEY `fk_sejur_has_client_sejur1_idx` (`cod_sejur`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervare_sejur`
--

LOCK TABLES `rezervare_sejur` WRITE;
/*!40000 ALTER TABLE `rezervare_sejur` DISABLE KEYS */;
INSERT INTO `rezervare_sejur` VALUES (4,4,3),(3,1,7),(3,5,8),(4,2,9),(5,1,10),(5,2,11),(1,2,12),(1,3,13),(1,4,14),(6,4,15),(2,4,16),(1,4,17),(5,1,18);
/*!40000 ALTER TABLE `rezervare_sejur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sejur`
--

DROP TABLE IF EXISTS `sejur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sejur` (
  `cod_sejur` int(11) NOT NULL AUTO_INCREMENT,
  `Oras_plecare` varchar(45) NOT NULL,
  `Oras_sosire` varchar(45) NOT NULL,
  `data_plecare` date NOT NULL,
  `data_sosire` date NOT NULL,
  PRIMARY KEY (`cod_sejur`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sejur`
--

LOCK TABLES `sejur` WRITE;
/*!40000 ALTER TABLE `sejur` DISABLE KEYS */;
INSERT INTO `sejur` VALUES (1,'Bucuresti','Paris','2018-12-10','2018-12-12'),(2,'Bucuresti','Viena','2018-12-10','2018-12-15'),(3,'Bucuresti','Ankara','2018-12-11','2018-12-15'),(4,'Bucuresti','Berlin','2018-12-16','2018-12-20'),(5,'Bucuresti','Salzburg','2019-05-25','2019-06-02'),(6,'Cluj','Innsbruck','2019-06-01','2020-01-01');
/*!40000 ALTER TABLE `sejur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sezon`
--

DROP TABLE IF EXISTS `sezon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sezon` (
  `cod_sezon` int(11) NOT NULL,
  `denumire_sezon` varchar(100) DEFAULT NULL,
  `data_inceput` date NOT NULL,
  `data_sfarsit` date NOT NULL,
  PRIMARY KEY (`cod_sezon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sezon`
--

LOCK TABLES `sezon` WRITE;
/*!40000 ALTER TABLE `sezon` DISABLE KEYS */;
INSERT INTO `sezon` VALUES (1,'Winter Holiday','2018-12-01','2019-01-15'),(2,'Sezon schi','2019-01-16','2019-02-28'),(3,'Primavara','2019-03-01','2019-04-01'),(4,'Vacanta de Paste','2019-04-02','2019-05-31'),(5,'Vara','2019-06-01','2019-07-20'),(6,'Concedii mare/munte vara','2019-07-21','2019-08-31'),(7,'Toamna','2019-09-01','2019-11-30');
/*!40000 ALTER TABLE `sezon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transport` (
  `cod_transport` int(11) NOT NULL,
  `Den_avion` varchar(45) NOT NULL,
  `Capacitate` int(11) NOT NULL,
  `cod_furnizor` int(11) NOT NULL,
  PRIMARY KEY (`cod_transport`),
  KEY `fk_transport_furnizori1_idx` (`cod_furnizor`),
  CONSTRAINT `fk_transport_furnizori1` FOREIGN KEY (`cod_furnizor`) REFERENCES `furnizori` (`cod_furnizor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` VALUES (1,'Airbus A300',260,1),(2,'Boeing 737',120,1),(3,'Boeing 737',120,4),(4,'Airbus A320',150,1),(5,'Airbus A320',150,2),(6,'Airbus A320',150,3);
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zbor`
--

DROP TABLE IF EXISTS `zbor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zbor` (
  `cod_zbor` int(11) NOT NULL,
  `Destinatie` varchar(45) NOT NULL,
  `data_plecarii` date NOT NULL,
  `data_sosirii` date NOT NULL,
  `ora_plecarii` int(11) DEFAULT NULL,
  `ora_sosirii` int(11) DEFAULT NULL,
  `cod_transport` int(11) NOT NULL,
  `pret` int(11) NOT NULL,
  `cod_agentie` int(11) DEFAULT NULL,
  PRIMARY KEY (`cod_zbor`),
  KEY `fk_zbor_transport1_idx` (`cod_transport`),
  KEY `fk_cod_agentie_zbor` (`cod_agentie`),
  CONSTRAINT `fk_cod_agentie_zbor` FOREIGN KEY (`cod_agentie`) REFERENCES `agentie` (`cod_agentie`) ON DELETE CASCADE,
  CONSTRAINT `fk_zbor_transport1` FOREIGN KEY (`cod_transport`) REFERENCES `transport` (`cod_transport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zbor`
--

LOCK TABLES `zbor` WRITE;
/*!40000 ALTER TABLE `zbor` DISABLE KEYS */;
INSERT INTO `zbor` VALUES (1,'Viena','2018-12-10','2018-12-10',5,7,2,200,2),(2,'Paris','2018-12-10','2018-12-10',9,11,3,500,1),(3,'Paris','2018-12-10','2018-12-10',17,21,2,560,3),(4,'Ankara','2018-12-11','2018-12-11',10,13,4,620,4),(5,'Berlin','2018-11-15','2018-11-15',12,14,2,350,5),(6,'Berlin','2018-11-16','2018-11-16',6,9,3,300,4);
/*!40000 ALTER TABLE `zbor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'agentieturism'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-30 14:38:49
