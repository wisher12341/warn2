-- ----------------------------
-- Table structure for warnhistory
-- ----------------------------
DROP TABLE IF EXISTS `warnhistory`;
CREATE TABLE `warnhistory` (
  `wdid` int(20) NOT NULL AUTO_INCREMENT,
  `typeW` varchar(100) NOT NULL DEFAULT ''  COMMENT '预警类型',
  `oldName` varchar(100) NOT NULL DEFAULT '' COMMENT '对应老人姓名',
  `oid` int(20) NOT NULL DEFAULT 0 COMMENT '对应老人ID',
  `dataW` varchar(6555) NOT NULL DEFAULT '' COMMENT '详细信息',
  `readW`  varchar(100) NOT NULL DEFAULT '' COMMENT '是否已读 否未读 是已读',
  `timeW` varchar(100) NOT NULL DEFAULT '' COMMENT '写入时间',
  `sms` int(20) NOT NULL DEFAULT 0 COMMENT '是否发送过短信 0没有 1发送过',
  PRIMARY KEY (`wdid`),
  Foreign KEY(`oid`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='预警历史信息表';


-- ----------------------------
-- Table structure for outhistory
-- ----------------------------
DROP TABLE IF EXISTS `outhistory`;
CREATE TABLE `outhistory` (
  `odid` int(20) NOT NULL AUTO_INCREMENT,
  `oid` int(20) NOT NULL DEFAULT 0 COMMENT '对应老人ID',
  `oldName` varchar(100) NOT NULL DEFAULT '' COMMENT '对应老人姓名',
  `typeD` varchar(100) NOT NULL DEFAULT ''  COMMENT '出门类型',
  `dataD` varchar(100) NOT NULL DEFAULT '' COMMENT '详细信息',
  `readD`  varchar(100) NOT NULL DEFAULT '' COMMENT '是否已读 否未读 是已读',
  `timeD` varchar(100) NOT NULL DEFAULT '' COMMENT '写入时间',
  PRIMARY KEY (`odid`),
  Foreign KEY(`oid`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='出门历史信息表';

INSERT INTO outhistory VALUES ('1','1','刘大爷','2','否','2017-4-30');

-- ----------------------------
-- Table structure for downhistory
-- ----------------------------
DROP TABLE IF EXISTS `downhistory`;
CREATE TABLE `downhistory` (
  `downid` int(20) NOT NULL AUTO_INCREMENT,
  `oid` int(20) NOT NULL DEFAULT 0 COMMENT '对应老人ID',
  `oldName` varchar(100) NOT NULL DEFAULT '' COMMENT '对应老人姓名',
  `typeDown` varchar(100) NOT NULL DEFAULT ''  COMMENT '故障类型',
  `dataDown` varchar(100) NOT NULL DEFAULT '' COMMENT '详细信息',
  `readDown`  varchar(100) NOT NULL DEFAULT '' COMMENT '是否已读 否未读 是已读',
  `timeDown` varchar(100) NOT NULL DEFAULT '' COMMENT '写入时间',
  PRIMARY KEY (`downid`),
  Foreign KEY(`oid`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='故障历史信息表';

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logId` int(20) NOT NULL AUTO_INCREMENT,
  `userId` int(20) NOT NULL COMMENT '账号ID',
  `logData` varchar(6000) NOT NULL DEFAULT '' COMMENT '日志内容',
  `logTime` varchar(100) NOT NULL DEFAULT 0 COMMENT '添加时间',
  PRIMARY KEY (`logId`),
  Foreign KEY(`userId`) REFERENCES sysuser(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='日志信息表';