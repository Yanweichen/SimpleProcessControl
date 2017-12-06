/*
Navicat MySQL Data Transfer

Source Server         : rsc
Source Server Version : 50711
Source Host           : 192.168.1.115:3306
Source Database       : ywc

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2017-12-06 16:07:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for PROCESS
-- ----------------------------
DROP TABLE IF EXISTS `PROCESS`;
CREATE TABLE `PROCESS` (
  `PROCESS_ID` varchar(36) DEFAULT NULL COMMENT '流程ID',
  `BPM_ID` varchar(255) DEFAULT NULL COMMENT '流程图ID',
  `PROCESS_ORDER` text COMMENT '流程顺序',
  `PROCESS_SCHEDULE` text COMMENT '流程进度',
  `PROCESS_SESSION` text COMMENT '流程Session'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
