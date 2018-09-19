DROP TABLE IF EXISTS `marker_house`;
CREATE TABLE `marker_house` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `styleIndex` int(20) NOT NULL DEFAULT 0  COMMENT '样式索引',
  `jid` int(20) NOT NULL DEFAULT 0 COMMENT '街道ID',
  `oid` int(20) NOT NULL DEFAULT 0 COMMENT '对应老人ID',
  `x` varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  `y`  varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  `detail`  varchar(100) NOT NULL DEFAULT '' COMMENT '正在服务或预警的详情',
  PRIMARY KEY (`id`),
  Foreign KEY(`oid`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='房屋标注';

DROP TABLE IF EXISTS `marker_street`;
CREATE TABLE `marker_street` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `styleIndex` int(20) NOT NULL DEFAULT 0  COMMENT '样式索引',
  `qid` int(20) NOT NULL DEFAULT 0 COMMENT '区ID',
  `jName` varchar(100) NOT NULL DEFAULT '' COMMENT '街道名称',
  `jX` varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  `jY`  varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='街道标注';

DROP TABLE IF EXISTS `marker_district`;
CREATE TABLE `marker_district` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `styleIndex` int(20) NOT NULL DEFAULT 0  COMMENT '样式索引',
  `qName` varchar(100) NOT NULL DEFAULT '' COMMENT '区名称',
  `qX` varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  `qY`  varchar(100) NOT NULL DEFAULT '' COMMENT '坐标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='区标注';

