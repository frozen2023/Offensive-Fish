/*
SQLyog Job Agent v12.09 (64 bit) Copyright(c) Webyog Inc. All Rights Reserved.


MySQL - 8.0.31 : Database - offensivefish
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`offensivefish` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `offensivefish`;

/*Table structure for table `ranklist` */

DROP TABLE IF EXISTS `ranklist`;

CREATE TABLE `ranklist` (
  `firstPlayerId` varchar(20) NOT NULL COMMENT '第一个玩家',
  `secondPlayerId` varchar(20) NOT NULL COMMENT '第二个玩家',
  `score` int NOT NULL COMMENT '得分',
  `rank` int NOT NULL COMMENT '排名',
  `endTime` varchar(20) NOT NULL COMMENT '生成时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `ranklist` */

insert  into `ranklist` values ('153921','12',80,1,'2023-11-13 16:22:48'),('153921','123',80,2,'2023-11-13 16:22:54'),('153921','1234',79,3,'2023-11-13 16:27:10'),('153921','12345',65,5,'2023-11-13 16:27:20'),('153921','123457',66,4,'2023-11-13 16:27:39');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userName` varchar(20) NOT NULL COMMENT '用户名',
  `passwd` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `name` varchar(10) NOT NULL COMMENT '姓名',
  `status` int NOT NULL DEFAULT '0' COMMENT '0代表未在线，1在线',
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user` values ('15392287939','D95C03EA19C5D5868A388FB8FEDBB1B6','zzz',1),('15860920147','C772E5DF5A144D0BFCE30C7C245FA131','XH懒觉大支',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
