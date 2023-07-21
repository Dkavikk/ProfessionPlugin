-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: minecraft
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `entity_data_profession`
--

DROP TABLE IF EXISTS `entity_data_profession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entity_data_profession` (
  `entity_name` varchar(36) NOT NULL,
  `miner_profession` tinyint(1) NOT NULL DEFAULT '0',
  `hunter_profession` tinyint(1) NOT NULL DEFAULT '0',
  `c` tinyint(1) NOT NULL DEFAULT '0',
  `d` tinyint(1) NOT NULL DEFAULT '0',
  `e` tinyint(1) NOT NULL DEFAULT '0',
  `xp_kill` float DEFAULT NULL,
  `xp_breed` float DEFAULT NULL,
  `allowed_extra_experience` tinyint(1) NOT NULL DEFAULT '0',
  `allowed_cooked` tinyint(1) NOT NULL DEFAULT '0',
  `material_original` varchar(45) DEFAULT NULL,
  `material_cooked` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`entity_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_data_profession`
--

LOCK TABLES `entity_data_profession` WRITE;
/*!40000 ALTER TABLE `entity_data_profession` DISABLE KEYS */;
INSERT INTO `entity_data_profession` VALUES ('ALLAY',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('AXOLOTL',0,1,0,0,0,24,NULL,0,0,NULL,NULL),('BAT',1,1,0,0,0,2,NULL,0,0,NULL,NULL),('BEE',0,1,0,0,0,10,NULL,0,0,NULL,NULL),('BLAZE',0,1,0,0,0,20,NULL,0,0,NULL,NULL),('CAMEL',0,1,0,0,0,16,NULL,0,0,NULL,NULL),('CAT',0,1,0,0,0,5,13,0,0,NULL,NULL),('CAVE_SPIDER',1,1,0,0,0,5,NULL,0,0,NULL,NULL),('CHICKEN',0,1,0,0,0,6,4,0,0,NULL,NULL),('COD',0,1,0,0,0,4,NULL,0,0,NULL,NULL),('COW',0,1,0,0,0,9,7,0,0,'BEEF','COOKED_BEEF'),('CREEPER',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('DOLPHIN',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('DROWNED',0,1,0,0,0,23,NULL,0,0,NULL,NULL),('ELDER_GUARDIAN',0,1,0,0,0,30,NULL,0,0,NULL,NULL),('ENDER_DRAGON',0,1,0,0,0,150,NULL,0,0,NULL,NULL),('ENDERMAN',0,1,0,0,0,23,NULL,0,0,NULL,NULL),('ENDERMITE',0,1,0,0,0,2,NULL,0,0,NULL,NULL),('EVOKER',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('FOX',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('FROG',0,1,0,0,0,10,8,0,0,NULL,NULL),('GHAST',0,1,0,0,0,16,NULL,0,0,NULL,NULL),('GOAT',0,1,0,0,0,12,NULL,0,0,NULL,NULL),('GUARDIAN',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('HOGLIN',0,1,0,0,0,17,NULL,0,0,NULL,NULL),('HORSE',0,1,0,0,0,8,8,0,0,NULL,NULL),('IRON_GOLEM',0,1,0,0,0,20,NULL,0,0,NULL,NULL),('LLAMA',0,1,0,0,0,10,5,0,0,NULL,NULL),('MAGMA_CUBE',0,1,0,0,0,5,NULL,0,0,NULL,NULL),('MUSHROOM_COW',0,1,0,0,0,13,8,0,0,NULL,NULL),('OCELOT',0,1,0,0,0,17,7,0,0,NULL,NULL),('PANDA',0,1,0,0,0,8,9,0,0,NULL,NULL),('PARROT',0,1,0,0,0,4,8,0,0,NULL,NULL),('PHANTOM',0,1,0,0,0,16,NULL,0,0,NULL,NULL),('PIG',0,1,0,0,0,9,7,0,0,NULL,NULL),('PIGLIN',0,1,0,0,0,16,NULL,0,0,NULL,NULL),('PIGLIN_BRUTE',0,1,0,0,0,20,NULL,0,0,NULL,NULL),('PILLAGER',0,1,0,0,0,18,NULL,0,0,NULL,NULL),('POLAR_BEAR',0,1,0,0,0,10,NULL,0,0,NULL,NULL),('PUFFERFISH',0,1,0,0,0,8,NULL,0,0,NULL,NULL),('RABBIT',0,1,0,0,0,7,6,0,0,NULL,NULL),('RAVAGER',0,1,0,0,0,22,NULL,0,0,NULL,NULL),('SALMON',0,1,0,0,0,4,NULL,0,0,NULL,NULL),('SHEEP',0,1,0,0,0,9,7,0,0,NULL,NULL),('SHULKER',0,1,0,0,0,20,NULL,0,0,NULL,NULL),('SILVERFISH',0,1,0,0,0,2,NULL,0,0,NULL,NULL),('SKELETON',0,1,0,0,0,13,NULL,0,0,NULL,NULL),('SKELETON_HORSE',0,1,0,0,0,15,NULL,0,0,NULL,NULL),('SLIME',0,1,0,0,0,3,NULL,0,0,NULL,NULL),('SNIFFER',0,1,0,0,0,25,13,0,0,NULL,NULL),('SNOWMAN',0,1,0,0,0,1,NULL,0,0,NULL,NULL),('SPIDER',0,1,0,0,0,8,NULL,0,0,NULL,NULL),('SQUID',0,1,0,0,0,2,NULL,0,0,NULL,NULL),('STRIDER',0,1,0,0,0,12,8,0,0,NULL,NULL),('TADPOLE',0,1,0,0,0,1,NULL,0,0,NULL,NULL),('TRADER_LLAMA',0,1,0,0,0,5,NULL,0,0,NULL,NULL),('TROPICAL_FISH',0,1,0,0,0,7,NULL,0,0,NULL,NULL),('TURTLE',0,1,0,0,0,11,7,0,0,NULL,NULL),('VEX',0,1,0,0,0,8,NULL,0,0,NULL,NULL),('VINDICATOR',0,1,0,0,0,13,NULL,0,0,NULL,NULL),('WANDERING_TRADER',0,1,0,0,0,5,NULL,0,0,NULL,NULL),('WARDEN',0,1,0,0,0,90,NULL,0,0,NULL,NULL),('WITCH',0,1,0,0,0,14,NULL,0,0,NULL,NULL),('WITHER',0,1,0,0,0,90,NULL,0,0,NULL,NULL),('WITHER_SKELETON',0,1,0,0,0,18,NULL,0,0,NULL,NULL),('WOLF',0,1,0,0,0,5,5,0,0,NULL,NULL),('ZOGLIN',0,1,0,0,0,9,NULL,0,0,NULL,NULL),('ZOMBIE',0,1,0,0,0,9,NULL,0,0,NULL,NULL),('ZOMBIE_VILLAGER',0,1,0,0,0,11,NULL,0,0,NULL,NULL),('ZOMBIFIED_PIGLIN',0,1,0,0,0,12,NULL,0,0,NULL,NULL);
/*!40000 ALTER TABLE `entity_data_profession` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-20 20:28:16
