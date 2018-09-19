DROP TABLE IF EXISTS `manmodel`;
CREATE TABLE `manmodel` (
  `mid` int(20) NOT NULL AUTO_INCREMENT,
  `oldId` int(20) NOT NULL COMMENT '对应老人ID',
  `live` varchar(1000) NOT NULL DEFAULT '' COMMENT '老人生活规律（格式：起始时间—截止时间@房间ID多个房间的用%分割 $表示在该房间是活动(1)还是休息(2)   %后面的0表示出门，分隔符 #）  1活动  2休息',
  PRIMARY KEY (`mid`),
  FOREIGN KEY(`oldId`) REFERENCES oldman(`oid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='老人生活规律表';
-- 33是客厅， 之前的数据经过修改 如果从新导入数据库数据  没有33 把33改为响应客厅ID
-- INSERT INTO manModel VALUES ('1','1','00:00-06:30@2#06:30-07:00@4%33#07:00-11:00@0#11:00-11:30@4#11:30-13:30@33#13:30-15:30@2#15:30-17:30@0#17:30-18:30@4#18:30-20:30@33#20:30-24:00@2');
INSERT INTO manmodel VALUES ('1','1','00:00-06:30@2$2%0#06:30-07:00@4$1%33$1#07:00-11:00@0#11:00-11:30@4$1#11:30-13:30@33$2#13:30-15:30@2$2#15:30-17:30@0#17:30-18:30@4$1#18:30-20:30@33$2#20:30-24:00@2$2');


DROP TABLE IF EXISTS `roommodel`;
CREATE TABLE `roommodel` (
  `rmid` int(20) NOT NULL AUTO_INCREMENT,
  `roomId` int(20) NOT NULL COMMENT '对应房间ID',
  `active` varchar(1000) NOT NULL DEFAULT '' COMMENT '在该房间内活动的时间段',
  `rest` varchar(1000) NOT NULL DEFAULT '' COMMENT '在该房间内休息的时间段',
  PRIMARY KEY (`rmid`),
  FOREIGN KEY(`roomId`) REFERENCES room(`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='房间规律表';

INSERT INTO roommodel VALUES ('1','2','','00:00-06:30#13:30-15:30#20:30-24:00');
INSERT INTO roommodel VALUES ('2','4','06:30-07:00#11:00-11:30#17:30-18:30','');
INSERT INTO roommodel VALUES ('3','33','06:30-07:00','11:30-13:30#18:30-20:30');