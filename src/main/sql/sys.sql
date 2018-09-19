-- ----------------------------
-- Table structure for sysphone
-- ----------------------------
DROP TABLE IF EXISTS `sysphone`;
CREATE TABLE `sysphone` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(100) NOT NULL DEFAULT ''  COMMENT '手机号',
  `orderSms` int(20) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='接受短信的手机信息表';


-- ----------------------------
-- Table structure for phoneOrder
-- ----------------------------
DROP TABLE IF EXISTS `phoneOrder`;
CREATE TABLE `phoneOrder` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `orderSms` int(20) NOT NULL COMMENT '顺序',
  `timeSms` int(20) NOT NULL COMMENT '时间间隔 单位：分钟',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='短信接收顺序信息表';

