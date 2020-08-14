/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50514
Source Host           : localhost:3306
Source Database       : takeout

Target Server Type    : MYSQL
Target Server Version : 50514
File Encoding         : 65001

Date: 2020-07-15 11:34:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `address_id` varchar(30) NOT NULL,
  `cus_id` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `region` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FK_provide` (`cus_id`),
  CONSTRAINT `FK_provide` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(20) NOT NULL,
  `admin_name` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `coupon_id` varchar(20) NOT NULL,
  `shop_id` varchar(20) NOT NULL,
  `order_quantity` int(11) DEFAULT NULL,
  `order_cnt` int(11) DEFAULT NULL,
  PRIMARY KEY (`coupon_id`,`shop_id`),
  KEY `FK_collect2` (`shop_id`),
  CONSTRAINT `FK_collect2` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  CONSTRAINT `FK_collect` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `coupon_id` varchar(20) NOT NULL,
  `order_id` varchar(20) DEFAULT NULL,
  `shop_id` varchar(20) NOT NULL,
  `discount` float NOT NULL,
  `order_quantity` int(11) NOT NULL,
  `coupon_starttime` datetime NOT NULL,
  `coupon_endtime` datetime NOT NULL,
  PRIMARY KEY (`coupon_id`),
  KEY `FK_coupon_fit` (`order_id`),
  KEY `FK_give_coupon` (`shop_id`),
  CONSTRAINT `FK_give_coupon` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  CONSTRAINT `FK_coupon_fit` FOREIGN KEY (`order_id`) REFERENCES `order_all` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `cus_id` varchar(20) NOT NULL,
  `cus_name` varchar(20) NOT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `cus_pwd` varchar(20) NOT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `mailbox` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `reg_time` datetime DEFAULT NULL,
  `vip_state` varchar(5) DEFAULT NULL,
  `vip_endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`cus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for full
-- ----------------------------
DROP TABLE IF EXISTS `full`;
CREATE TABLE `full` (
  `full_id` varchar(20) NOT NULL,
  `shop_id` varchar(20) NOT NULL,
  `order_id` varchar(20) DEFAULT NULL,
  `full_demand` float NOT NULL,
  `full_reduction` float NOT NULL,
  `tag` varchar(10) NOT NULL,
  PRIMARY KEY (`full_id`),
  KEY `FK_full_fit` (`order_id`),
  KEY `FK_hold` (`shop_id`),
  CONSTRAINT `FK_hold` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  CONSTRAINT `FK_full_fit` FOREIGN KEY (`order_id`) REFERENCES `order_all` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of full
-- ----------------------------

-- ----------------------------
-- Table structure for order_all
-- ----------------------------
DROP TABLE IF EXISTS `order_all`;
CREATE TABLE `order_all` (
  `order_id` varchar(20) NOT NULL,
  `shop_id` varchar(20) DEFAULT NULL,
  `address_id` varchar(30) DEFAULT NULL,
  `cus_id` varchar(20) DEFAULT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `original_cost` float DEFAULT NULL,
  `final_cost` float DEFAULT NULL,
  `order_time` datetime DEFAULT NULL,
  `reachtime` datetime DEFAULT NULL,
  `order_state` varchar(10) DEFAULT NULL,
  `rider_id` int(11) DEFAULT NULL,
  `full_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_address` (`address_id`),
  KEY `FK_chudan` (`shop_id`),
  KEY `FK_have_a_order` (`cus_id`),
  CONSTRAINT `FK_have_a_order` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`),
  CONSTRAINT `FK_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `FK_chudan` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_all
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `pro_id` varchar(20) NOT NULL,
  `order_id` varchar(20) NOT NULL,
  `single_quantity` int(11) DEFAULT NULL,
  `single_cost` float DEFAULT NULL,
  `single_discount` float DEFAULT NULL,
  PRIMARY KEY (`pro_id`,`order_id`),
  KEY `FK_order_detail2` (`order_id`),
  CONSTRAINT `FK_order_detail2` FOREIGN KEY (`order_id`) REFERENCES `order_all` (`order_id`),
  CONSTRAINT `FK_order_detail` FOREIGN KEY (`pro_id`) REFERENCES `product` (`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for possess
-- ----------------------------
DROP TABLE IF EXISTS `possess`;
CREATE TABLE `possess` (
  `coupon_id` varchar(20) NOT NULL,
  `cus_id` varchar(20) NOT NULL,
  `shop_id` varchar(20) NOT NULL,
  `discount` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `coupon_endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`coupon_id`,`cus_id`,`shop_id`),
  KEY `FK_possess2` (`cus_id`),
  KEY `FK_possess3` (`shop_id`),
  CONSTRAINT `FK_possess3` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  CONSTRAINT `FK_possess` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`coupon_id`),
  CONSTRAINT `FK_possess2` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of possess
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pro_id` varchar(20) NOT NULL,
  `type_id` varchar(20) NOT NULL,
  `shop_id` varchar(20) NOT NULL,
  `pro_name` varchar(20) NOT NULL,
  `pro_price` float NOT NULL,
  `pro_discount_amount` float DEFAULT NULL,
  PRIMARY KEY (`pro_id`),
  KEY `FK_produce` (`shop_id`),
  KEY `FK_分类` (`type_id`),
  CONSTRAINT `FK_分类` FOREIGN KEY (`type_id`) REFERENCES `pro_type` (`type_id`),
  CONSTRAINT `FK_produce` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for pro_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `pro_evaluate`;
CREATE TABLE `pro_evaluate` (
  `shop_id` varchar(20) NOT NULL,
  `pro_id` varchar(20) NOT NULL,
  `cus_id` varchar(20) NOT NULL,
  `order_id` varchar(20) NOT NULL,
  `comment` varchar(50) DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  `pro_level` int(11) DEFAULT NULL,
  `pro_photo` longblob,
  PRIMARY KEY (`shop_id`,`pro_id`,`cus_id`,`order_id`),
  KEY `FK_pro_evaluate2` (`pro_id`),
  KEY `FK_pro_evaluate3` (`cus_id`),
  KEY `FK_pro_evaluate4` (`order_id`),
  CONSTRAINT `FK_pro_evaluate4` FOREIGN KEY (`order_id`) REFERENCES `order_all` (`order_id`),
  CONSTRAINT `FK_pro_evaluate` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`),
  CONSTRAINT `FK_pro_evaluate2` FOREIGN KEY (`pro_id`) REFERENCES `product` (`pro_id`),
  CONSTRAINT `FK_pro_evaluate3` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_evaluate
-- ----------------------------

-- ----------------------------
-- Table structure for pro_type
-- ----------------------------
DROP TABLE IF EXISTS `pro_type`;
CREATE TABLE `pro_type` (
  `type_id` varchar(20) NOT NULL,
  `type_name` varchar(20) NOT NULL,
  `pro_quantity` int(11) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pro_type
-- ----------------------------

-- ----------------------------
-- Table structure for rider
-- ----------------------------
DROP TABLE IF EXISTS `rider`;
CREATE TABLE `rider` (
  `rider_id` varchar(20) NOT NULL,
  `rider_name` varchar(20) NOT NULL,
  `entry_date` datetime DEFAULT NULL,
  `position` varchar(20) DEFAULT NULL,
  `rider_pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`rider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rider
-- ----------------------------

-- ----------------------------
-- Table structure for rider_account
-- ----------------------------
DROP TABLE IF EXISTS `rider_account`;
CREATE TABLE `rider_account` (
  `order_id` varchar(20) NOT NULL,
  `rider_id` varchar(20) NOT NULL,
  `recordtime` datetime DEFAULT NULL,
  `rider_evaluate` varchar(50) DEFAULT NULL,
  `income` float DEFAULT NULL,
  PRIMARY KEY (`order_id`,`rider_id`),
  KEY `FK_rider_account2` (`rider_id`),
  CONSTRAINT `FK_rider_account2` FOREIGN KEY (`rider_id`) REFERENCES `rider` (`rider_id`),
  CONSTRAINT `FK_rider_account` FOREIGN KEY (`order_id`) REFERENCES `order_all` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rider_account
-- ----------------------------

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `shop_id` varchar(20) NOT NULL,
  `shop_name` varchar(50) NOT NULL,
  `shop_level` int(11) DEFAULT NULL,
  `avgcost` float DEFAULT NULL,
  `total_sale` int(11) DEFAULT NULL,
  `shop_pwd` varchar(20) NOT NULL,
  `shop_photo` longblob,
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
