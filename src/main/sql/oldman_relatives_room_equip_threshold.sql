-- 老人、紧急联系人、房间、设备

-- ----------------------------
-- Table structure for oldman
-- ----------------------------
USE warn;
DROP TABLE IF EXISTS `oldman`;
CREATE TABLE `oldman` (
  `oid` int(20) NOT NULL AUTO_INCREMENT,
  `gatewayID` varchar(100) NOT NULL DEFAULT ' 'COMMENT '网关',
  `osegment` varchar(100) NOT NULL DEFAULT '' COMMENT '网段标志',
  `oldName` varchar(100) NOT NULL DEFAULT '' COMMENT '老人姓名',
  `oldPhone` varchar(100) NOT NULL DEFAULT '' COMMENT '老人电话',
  `oldAddress` varchar(100) NOT NULL DEFAULT '' COMMENT '老人住址',
  `oldRegtime` varchar(100) NOT NULL DEFAULT '' COMMENT '注册时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老人基本信息表';




-- ----------------------------
-- Table structure for relatives
-- ----------------------------
DROP TABLE IF EXISTS `relatives`;
CREATE TABLE `relatives` (
  `relid` int(20) NOT NULL AUTO_INCREMENT,
  `rName` varchar(100) NOT NULL DEFAULT '' COMMENT '紧急联系人姓名',
  `rPhone` varchar(100) NOT NULL DEFAULT '' COMMENT '紧急联系人电话',
  `rAddress` varchar(100) NOT NULL DEFAULT '' COMMENT '紧急联系人住址',
  `oldId` int(20) NOT NULL COMMENT '紧急联系人对应的老人',
  PRIMARY KEY (`relid`),
  Foreign KEY(`oldId`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='紧急联系人基本信息表';

-- 添加级联操作
-- alter table outhistory drop foreign key outhistory_ibfk_1;
-- alter table outhistory add foreign key(oid) references oldman(oid) on delete cascade on update cascade;
-- 查看mysql所有的主键，外键约束名称
-- select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE





-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `rid` int(20) NOT NULL AUTO_INCREMENT,
  `roomName` varchar(100) NOT NULL DEFAULT '' COMMENT '房间名称',
  `collectId` varchar(100) NOT NULL DEFAULT '' COMMENT '采集点ID',
  `oldId`  int(20) NOT NULL  COMMENT '老人ID',
  `nerRoom` varchar(100) NOT NULL DEFAULT ''  COMMENT '相邻房间ID，以逗号隔开',
  `rRegtime` varchar(100) NOT NULL DEFAULT '' COMMENT '注册时间',
  PRIMARY KEY (`rid`),
  Foreign KEY(`oldId`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='房间信息表';




-- ----------------------------
-- Table structure for equip
-- ----------------------------
DROP TABLE IF EXISTS `equip`;
CREATE TABLE `equip` (
  `eid` varchar(100) NOT NULL,
  `eName` varchar(100) NOT NULL DEFAULT '' COMMENT '设备名称',
  `roomId` int(20) NOT NULL  COMMENT '设备对应房间ID',
  `oldId` int(20) NOT NULL  COMMENT '设备对应老人ID',
  `eRegtime` varchar(100) NOT NULL DEFAULT '' COMMENT '注册时间',
  PRIMARY KEY (`eid`,`oldId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='设备信息表';


-- ----------------------------
-- Table structure for threshold
-- ----------------------------
DROP TABLE IF EXISTS `threshold`;
CREATE TABLE `threshold` (
  `tid` int(20) NOT NULL AUTO_INCREMENT,
  `roomId` int(20) NOT NULL  COMMENT '阈值对应房间ID',
  `a1Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '活动时的阈值 一级 单位：分钟',
  `r1Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '休息时的阈值 一级 单位：百分比',
  `a2Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '活动时的阈值 二级 单位：分钟',
  `r2Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '休息时的阈值 二级 单位：百分比',
  `n1Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '不在房间规律时间时的阈值 一级 单位：分钟',
  `n2Threshold` int(20) NOT NULL DEFAULT 0 COMMENT '不在房间规律时间时的阈值 二级 单位：分钟',
  PRIMARY KEY (`tid`),
  Foreign KEY(`roomId`) REFERENCES room(`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='行为阈值表';





-- ----------------------------
-- Table structure for threshold_wendu
-- ----------------------------
DROP TABLE IF EXISTS `threshold_wendu`;
CREATE TABLE `threshold_wendu` (
  `wid` int(20) NOT NULL AUTO_INCREMENT,
  `roomId` int(20) NOT NULL  COMMENT '阈值对应房间ID',
  `wThreshold` int(20) NOT NULL DEFAULT 0 COMMENT '阈值  单位：摄氏度',
  PRIMARY KEY (`wid`),
  Foreign KEY(`roomId`) REFERENCES room(`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='温度阈值表';



-- ----------------------------
-- Table structure for threshold_light
-- ----------------------------
DROP TABLE IF EXISTS `threshold_light`;
CREATE TABLE `threshold_light` (
  `lid` int(20) NOT NULL AUTO_INCREMENT,
  `roomId` int(20) NOT NULL  COMMENT '阈值对应房间ID',
  `lThreshold` int(20) NOT NULL DEFAULT 0 COMMENT '阈值  单位：光强度',
  `times` varchar(100) NOT NULL DEFAULT '' COMMENT '检测的时间段',
  `continueTime` int(20)  NOT NULL DEFAULT 0 COMMENT '持续时间  单位：分钟',
  PRIMARY KEY (`lid`),
  Foreign KEY(`roomId`) REFERENCES room(`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='光强阈值表';




-- ----------------------------
-- Table structure for threshold_out
-- ----------------------------
DROP TABLE IF EXISTS `threshold_out`;
CREATE TABLE `threshold_out` (
  `outId` int(20) NOT NULL AUTO_INCREMENT,
  `oid` int(20) NOT NULL  COMMENT '阈值对应老人ID',
  `outThreshold` int(20) NOT NULL DEFAULT 0 COMMENT '阈值 多久算出门  单位：分钟',
  `noComeThreshold` int(20) NOT NULL DEFAULT 0 COMMENT '阈值 多久没回来就报警 单位：分钟',
  PRIMARY KEY (`outId`),
  Foreign KEY(`oid`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='出门阈值表';
