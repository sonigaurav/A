/*
SQLyog Ultimate v9.02 
MySQL - 5.0.24-community-nt : Database - javarefwebcrawler
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`javarefwebcrawler` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `javarefwebcrawler`;

/*Table structure for table `crawled_data` */

DROP TABLE IF EXISTS `crawled_data`;

CREATE TABLE `crawled_data` (
  `ID` int(15) NOT NULL,
  `TITLE` varchar(1000) default NULL,
  `KEYWORD` varchar(1000) default NULL,
  `DESCRIPTION` varchar(5000) default NULL,
  `URL_ID` int(15) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_crawled_data` (`URL_ID`),
  CONSTRAINT `FK_crawled_data` FOREIGN KEY (`URL_ID`) REFERENCES `url_table` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `crawled_data` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ID` int(11) NOT NULL,
  `ROLE` varchar(50) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`ID`,`ROLE`) values (1,'Admin'),(2,'Member'),(3,'Guest');

/*Table structure for table `url_table` */

DROP TABLE IF EXISTS `url_table`;

CREATE TABLE `url_table` (
  `ID` int(15) NOT NULL,
  `CRAWLING_WORD` varchar(55) default NULL,
  `URL` varchar(2000) default NULL,
  `PAGERANK` int(15) default NULL,
  `USER_ID` int(15) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_url_table` (`USER_ID`),
  CONSTRAINT `FK_url_table` FOREIGN KEY (`USER_ID`) REFERENCES `user_table` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `url_table` */

/*Table structure for table `user_table` */

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
  `ID` int(15) NOT NULL,
  `FIRST_NAME` varchar(55) default NULL,
  `LAST_NAME` varchar(55) default NULL,
  `LOGIN` varchar(55) default NULL,
  `PASSWORD` varchar(55) default NULL,
  `DATE_OF_BIRTH` datetime default NULL,
  `ROLE_ID` int(11) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FK_user_table` (`ROLE_ID`),
  CONSTRAINT `FK_user_table` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_table` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
