/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 10.1.8-MariaDB-log : Database - nicefish
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nicefish` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nicefish`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `COMMENT_ID` varchar(64) NOT NULL,
  `P_ID` varchar(64) DEFAULT '-1',
  `USER_ID` varchar(64) NOT NULL DEFAULT '-1',
  `USER_NAME` varchar(64) DEFAULT NULL,
  `NICK_NAME` varchar(64) DEFAULT NULL,
  `CONTENT` text,
  `COMMENT_IP` varchar(64) DEFAULT NULL COMMENT '评论者的IP地址',
  `COMMENT_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `POST_ID` varchar(64) DEFAULT NULL,
  `STATUS` int(11) DEFAULT '1' COMMENT '评论状态：0：已删除；1：已发布；2:优质评论；',
  PRIMARY KEY (`COMMENT_ID`),
  KEY `index_post_id` (`POST_ID`),
  KEY `index_comment_time` (`COMMENT_TIME`),
  KEY `index_user_id` (`USER_ID`),
  KEY `index_isbn_status_time` (`COMMENT_TIME`,`STATUS`),
  KEY `index_bookid_status_time` (`COMMENT_TIME`,`STATUS`),
  KEY `index_postid_status_time` (`COMMENT_TIME`,`POST_ID`,`STATUS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`COMMENT_ID`,`P_ID`,`USER_ID`,`USER_NAME`,`NICK_NAME`,`CONTENT`,`COMMENT_IP`,`COMMENT_TIME`,`POST_ID`,`STATUS`) values ('03edabab-ea4f-4c8f-a807-21ef00680eae','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('07d1c268-98da-455c-aa2f-264d74aa9079','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('0d643d9a-ecbd-4ac9-9ccc-de8380ca182a','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('10a992e2-f59a-4566-96bd-6864d0b81085','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('19119904-80fd-4027-ac9d-6c7bfb23aeb4','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('1e55ca2f-d197-4414-8907-91cb18c12dec','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('1f1b5745-88eb-42d1-a184-729cf32a6aa7','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('247ce83a-818c-4848-8c9f-544777eae539','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:43','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('38737610-3061-4dcc-ab6d-47207902588e','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('4527354c-5753-453e-bbed-698e59f0547a','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('45781060-cada-40fa-8ba7-010f9b3b5a90','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('4c1b3a21-f7c9-483e-aecf-0e6897b59181','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:43','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('4d42c058-549f-4e0b-aca2-f19f921001d5','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:43','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('533a605e-69ab-49ca-aafc-3f4a9de38556','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('537afab1-92a6-4f80-b1bc-adcc26927aed','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('5a2e8630-37eb-4e12-b00d-442b7911e6f8','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:32','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('5c1f6122-b1f0-4bab-8221-c4d1e60064d1','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('69def43b-f601-49ee-af2d-6aae19fc6d39','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:32','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('6c8f08da-6754-41be-bd5b-13058efb21f6','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:32','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('7e88179b-3f2d-4720-8512-af282e1f4254','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('80107385-de18-4d78-a52a-15dd23653248','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('811d245d-76d0-4c5a-97ff-6e06dea14240','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('8a75e90d-ef61-4f79-89a6-aa5869c480b9','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('92621bbf-26fd-4dc1-8ea3-36b8e386b988','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('92860cad-8587-4ad9-98fa-d6fd03d6de87','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('98dc0739-06ff-45a1-bea3-2cb8c338b4de','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:32','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('a00a6b87-6173-45c5-be22-acd2acc62fce','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:33','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('a1afade8-ea27-44a3-b250-569e7ce175cd','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('a39026d3-3e48-416f-a7fa-9cdc849a8d9b','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('b2d2349c-c65e-4dfa-ae10-f12bff95a9b1','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:40','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('b4eb4e0a-dd53-4e69-a82b-cbd532541b30','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:43','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('b87d067d-c570-4da1-b7e1-8a67dc3123d2','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:42','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('c2e1f4db-2f1d-4f2e-95bb-a38d5aca0261','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:38','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('cdc9e4ff-fa8f-4586-81f4-d78fbf8c70db','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('e1ceeb55-abda-4c52-bf2d-70aac28046dd','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:42:32','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('e293b381-1f17-4fd9-a835-6c4d6d7e056b','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:39','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1),('e70569cb-bfbc-4700-9414-526b63a011e7','-1','ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋','这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。这里可以提交评论。',NULL,'2017-05-03 22:31:41','78dd5fd1-7b59-4135-9bb2-1cb0057b690a',1);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `PERMISSION_ID` varchar(64) NOT NULL,
  `PERMISSION_CODE` varchar(64) DEFAULT NULL,
  `PERMISSION_DESC` varchar(512) NOT NULL,
  PRIMARY KEY (`PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

/*Data for the table `permission` */

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `POST_ID` varchar(64) NOT NULL,
  `POST_TITLE` varchar(128) NOT NULL,
  `POST_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `POST_CONTENT` text NOT NULL,
  `ORIGINAL_URL` varchar(512) DEFAULT NULL COMMENT '原文链接，如果有这个字段，说明是翻译文章',
  `POST_TYPE` int(11) NOT NULL DEFAULT '0' COMMENT '文章的类型，0原创1翻译',
  `LAST_MODIFY_TIME` timestamp NULL DEFAULT NULL,
  `READ_TIMES` int(11) NOT NULL DEFAULT '1' COMMENT '阅读数',
  `LIKED_TIMES` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `COMMENT_TIMES` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `USER_ID` varchar(64) NOT NULL,
  `USER_NAME` varchar(64) DEFAULT NULL,
  `NICK_NAME` varchar(64) DEFAULT NULL,
  `ENABLE_COMMENT` int(11) NOT NULL DEFAULT '1' COMMENT '是否可评论\n            0不可\n            1可',
  `STATUS` int(11) NOT NULL DEFAULT '4' COMMENT '状态：\n            1、已删除\n            2、已归档，已归档的内容禁止评论，文章不可删除\n            3、草稿\n            4、已发布\n            5、精华-->精华文章不可删除\n            6、已推至首页\n            ',
  PRIMARY KEY (`POST_ID`),
  KEY `index_user_id` (`USER_ID`),
  KEY `index_post_time` (`POST_TIME`),
  KEY `index_status` (`STATUS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `post` */

insert  into `post`(`POST_ID`,`POST_TITLE`,`POST_TIME`,`POST_CONTENT`,`ORIGINAL_URL`,`POST_TYPE`,`LAST_MODIFY_TIME`,`READ_TIMES`,`LIKED_TIMES`,`COMMENT_TIMES`,`USER_ID`,`USER_NAME`,`NICK_NAME`,`ENABLE_COMMENT`,`STATUS`) values ('78dd5fd1-7b59-4135-9bb2-1cb0057b690a','你是一个疯子','2017-05-03 22:10:39','<p>你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子你是一个疯子</p>',NULL,0,NULL,1,0,0,'ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'大漠穷秋',1,4);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ROLE_ID` varchar(64) NOT NULL,
  `ROLE_NAME` varchar(64) NOT NULL,
  `ROLE_DESC` varchar(256) DEFAULT NULL,
  `TYPE` int(11) NOT NULL COMMENT '0内置角色不能修改；1用户自定义角色；',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `role` */

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `ID` varchar(64) NOT NULL,
  `PERMISSION_ID` varchar(64) NOT NULL,
  `ROLE_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和权限映射';

/*Data for the table `role_permission` */

/*Table structure for table `sys_params` */

DROP TABLE IF EXISTS `sys_params`;

CREATE TABLE `sys_params` (
  `PARAM_KEY` varchar(128) NOT NULL,
  `PARAM_VALUE` varchar(2048) NOT NULL,
  `PARAM_DESC` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`PARAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表。';

/*Data for the table `sys_params` */

insert  into `sys_params`(`PARAM_KEY`,`PARAM_VALUE`,`PARAM_DESC`) values ('COMMENT_PAGE_NUM','10','文章评论每页显示的条数'),('COMMENT_TIME_INTERVAL','3','*分钟内不允许多次发表评论'),('HEAD_IMG_FILE_SIZE_MAX','1','用户头像最大尺寸（M）'),('MAX_COMMENT_SIZE','10000','评论Bean缓存容量'),('MAX_FILE_SIZE','10','写文章上传附件时文件的最大尺寸（M）'),('POST_COMMENT_NUM','20','文章评论每页显示的条数'),('POST_CONTENT_LEN','140','文章列表显示的缩略内容字符数'),('POST_FILE_SIZE_MAX','50','文章附件单个文件最大尺寸（M）'),('POST_IMG_FILE_SIZE_MAX','1','文章内部图片最大文件尺寸（M）'),('POST_PAGE_NUM','20','文章列表每页显示文章的数量'),('UPLOAD_FILE_DIR','TOMCAT','上传文件存放的目录');

/*Table structure for table `upload` */

DROP TABLE IF EXISTS `upload`;

CREATE TABLE `upload` (
  `UP_ID` varchar(64) NOT NULL,
  `UP_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `FILE_NAME` varchar(128) DEFAULT NULL COMMENT '与物理保存的文件名一致',
  `FILE_TYPE` varchar(16) DEFAULT '1' COMMENT '1、图片；\n            2、附件；',
  `FILE_WIDTH` int(11) DEFAULT '0',
  `FILE_HEIGHT` int(11) DEFAULT '0',
  `FILE_SIZE` float DEFAULT '0',
  `DISPLAY_ORDER` int(11) DEFAULT '0',
  `USER_ID` varchar(64) DEFAULT '0',
  `FILE_MODULE` int(11) DEFAULT '1' COMMENT '1、metro相关的图片\n            2、文章相关的图片\n            3、图书相关的图片\n            4、小图标\n            5、用户头像\n            ',
  `FILE_DESC` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传的文件，会员头像、用户头像、门店图片介绍、项目图片介绍等，所有上传的文件都记录在这张表。';

/*Data for the table `upload` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `USER_ID` varchar(64) NOT NULL,
  `USER_NAME` varchar(64) DEFAULT NULL COMMENT '登录时的账号',
  `PASSWORD` varchar(128) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL COMMENT '邮件地址',
  `REAL_NAME` varchar(64) DEFAULT NULL COMMENT '中文真实姓名',
  `NICK_NAME` varchar(64) DEFAULT NULL COMMENT '中文昵称',
  `E_NAME` varchar(64) DEFAULT NULL COMMENT '英文名',
  `QQ` varchar(64) DEFAULT NULL,
  `WEIXIN` varchar(64) DEFAULT NULL,
  `CELL_PHONE` varchar(64) DEFAULT NULL,
  `USER_DESC` varchar(512) DEFAULT NULL,
  `UP_ID` varchar(128) DEFAULT NULL COMMENT '对应的头像上传文件ID',
  `STATUS` int(11) DEFAULT '1' COMMENT '1已激活\n2已禁用（员工已离职）',
  `REG_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `LASTLOGIN_TIME` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后一次登录时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户\n预计百万级的数据';

/*Data for the table `user` */

insert  into `user`(`USER_ID`,`USER_NAME`,`PASSWORD`,`EMAIL`,`REAL_NAME`,`NICK_NAME`,`E_NAME`,`QQ`,`WEIXIN`,`CELL_PHONE`,`USER_DESC`,`UP_ID`,`STATUS`,`REG_TIME`,`LASTLOGIN_TIME`) values ('ced14f57-5fec-40f6-b0b7-abe77a5d9f21',NULL,'57d6c2aa11b6a0f842e4b1297b79e906','damoqiongqiu@126.com',NULL,'大漠穷秋',NULL,NULL,NULL,NULL,NULL,NULL,1,'2017-05-03 22:10:22','0000-00-00 00:00:00');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `ID` varchar(64) NOT NULL,
  `USER_ID` varchar(64) NOT NULL,
  `ROLE_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色映射';

/*Data for the table `user_role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
