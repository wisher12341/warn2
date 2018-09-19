-- 账号、角色、菜单

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
USE warn;
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

INSERT INTO `sysuser` VALUES ('1', 'admin', '123');
INSERT INTO `sysuser` VALUES ('2', 'user', '123');
INSERT INTO `sysuser` VALUES ('3', 'manager', '123');


-- ----------------------------
-- Table structure for sysrole
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of sysrole
-- ----------------------------
INSERT INTO `sysrole` VALUES ('1', '管理人员');
INSERT INTO `sysrole` VALUES ('2', '前台人员');
INSERT INTO `sysrole` VALUES ('3', '超级管理人员');



-- ----------------------------
-- Table structure for sysmenu
-- ----------------------------
DROP TABLE IF EXISTS `sysmenu`;
CREATE TABLE `sysmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parentid` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单ID 0表示根节点',
  `sequence` int(6) NOT NULL DEFAULT '0' COMMENT '菜单顺序',
  `iconCls` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单图标样式',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单链接地址 总是以‘/’开头，相对于项目根路径',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否可用 1:正常，0：禁用',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentid`),
  KEY `sequence` (`sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=902 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of sysmenu
-- ----------------------------
INSERT INTO `sysmenu` VALUES ('1', '信息查询', '0', '1', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('2', '信息展示', '0', '2', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('4', '信息管理', '0', '4', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('5', '原始信息查询', '0', '5', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('6', '日志记录', '0', '6', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('7', '菜单管理', '0', '7', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('8', '系统设置', '0', '8', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('9', '阈值设置', '0', '9', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('10', '模型设置', '0', '10', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('11', '历史记录', '0', '11', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('101', '人员信息', '1', '101', 'icon-nav', '/data/user/list', '1');
INSERT INTO `sysmenu` VALUES ('102', '房间信息', '1', '102', 'icon-nav', '/room/user/list', '1');
INSERT INTO `sysmenu` VALUES ('103', '设备信息', '1', '103', 'icon-nav', '/equip/user/list', '1');
INSERT INTO `sysmenu` VALUES ('104', '汇总信息', '1', '104', 'icon-nav', '/gather/user/list', '1');
INSERT INTO `sysmenu` VALUES ('201', '生活规律', '2', '201', 'icon-nav', '/visual', '1');
INSERT INTO `sysmenu` VALUES ('401', '人员管理', '4', '401', 'icon-nav', '/data/list', '1');
INSERT INTO `sysmenu` VALUES ('402', '房间管理', '4', '402', 'icon-nav', '/room/list', '1');
INSERT INTO `sysmenu` VALUES ('403', '设备管理', '4', '403', 'icon-nav', '/equip/list', '1');
INSERT INTO `sysmenu` VALUES ('404', '汇总信息', '4', '404', 'icon-nav', '/gather/list', '1');
INSERT INTO `sysmenu` VALUES ('501', '用户信息', '5', '501', 'icon-nav', '/raw/user/list', '1');
INSERT INTO `sysmenu` VALUES ('502', '网关信息', '5', '502', 'icon-nav', '/raw/gateway/list', '1');
INSERT INTO `sysmenu` VALUES ('503', '传感器信息', '5', '503', 'icon-nav', '/raw/sensor/list', '1');
INSERT INTO `sysmenu` VALUES ('601', '日志记录', '6', '601', 'icon-nav', '/log/list', '1');
INSERT INTO `sysmenu` VALUES ('701', '资源管理', '7', '701', 'icon-nav', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('702', '菜单树', '7', '702', 'icon-nav', '/menu/listTree', '1');
INSERT INTO `sysmenu` VALUES ('801', '账号管理', '8', '801', 'icon-nav', '/account/list', '1');
INSERT INTO `sysmenu` VALUES ('802', '权限管理', '8', '802', 'icon-nav', '/authority/list', '1');
INSERT INTO `sysmenu` VALUES ('803', '系统设置', '8', '803', 'icon-nav', '/set/list', '1');
INSERT INTO `sysmenu` VALUES ('901', '阈值设置', '9', '901', 'icon-nav', '/threshold/list', '1');
INSERT INTO `sysmenu` VALUES ('1001', '生活规律模型', '10', '1001', 'icon-nav', '/model/list', '1');
INSERT INTO `sysmenu` VALUES ('1101', '报警信息', '11', '1101', 'icon-nav', '/warnHistory/warn', '1');
INSERT INTO `sysmenu` VALUES ('1102', '出门信息', '11', '1102', 'icon-nav', '/outHistory/out', '1');
INSERT INTO `sysmenu` VALUES ('902', '预警开关', '9', '902', 'icon-nav', '/threshold/switch', '1');


INSERT INTO `sysmenu` VALUES ('602', '日志记录', '6', '602', 'icon-nav', '/log/user/list', '1');
INSERT INTO `sysmenu` VALUES ('1103', '报警信息', '11', '1103', 'icon-nav', '/warnHistory/user/warn', '1');
INSERT INTO `sysmenu` VALUES ('1104', '出门信息', '11', '1104', 'icon-nav', '/outHistory/user/out', '1');
INSERT INTO `sysmenu` VALUES ('1105', '故障信息', '11', '1105', 'icon-nav', '/downHistory/down', '1');
INSERT INTO `sysmenu` VALUES ('1106', '故障信息', '11', '1106', 'icon-nav', '/downHistory/user/down', '1');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `roleId` (`roleId`),
  FOREIGN KEY(`userId`) REFERENCES sysuser(`id`),
  FOREIGN KEY(`roleId`) REFERENCES sysrole(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色用户关联表';

INSERT INTO `user_role` VALUES ('1', '1', '3');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '3', '1');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  `flagBack` VARCHAR (11) NOT NULL DEFAULT `0` COMMENT '用来区分  管理人员和超级管理员 的前台和后台权限  1后台  0前台  01表示 该父菜单又在前台又在后台',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`),
  KEY `menuId` (`menuId`),
  FOREIGN KEY(`roleId`) REFERENCES sysrole(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY(`menuId`) REFERENCES sysmenu(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色功能关联表';

-- ----------------------------
-- Records of role_function
-- ----------------------------
--超级管理人员
INSERT INTO `role_menu` VALUES ('1', '3', '1','0');
INSERT INTO `role_menu` VALUES ('2', '3', '2','0');
INSERT INTO `role_menu` VALUES ('3', '3', '6','01');
INSERT INTO `role_menu` VALUES ('4', '3', '101','0');
INSERT INTO `role_menu` VALUES ('5', '3', '102','0');
INSERT INTO `role_menu` VALUES ('6', '3', '103','0');
INSERT INTO `role_menu` VALUES ('7', '3', '1103','0');
INSERT INTO `role_menu` VALUES ('8', '3', '201','0');
INSERT INTO `role_menu` VALUES ('9', '3', '602','0');
INSERT INTO `role_menu` VALUES ('10', '3', '4','1');
INSERT INTO `role_menu` VALUES ('11', '3', '5','1');
INSERT INTO `role_menu` VALUES ('13', '3', '7','1');
INSERT INTO `role_menu` VALUES ('14', '3', '8','1');
INSERT INTO `role_menu` VALUES ('15', '3', '401','1');
INSERT INTO `role_menu` VALUES ('16', '3', '402','1');
INSERT INTO `role_menu` VALUES ('17', '3', '403','1');
INSERT INTO `role_menu` VALUES ('18', '3', '501','1');
INSERT INTO `role_menu` VALUES ('19', '3', '502','1');
INSERT INTO `role_menu` VALUES ('20', '3', '503','1');
INSERT INTO `role_menu` VALUES ('21', '3', '601','1');
INSERT INTO `role_menu` VALUES ('22', '3', '701','1');
INSERT INTO `role_menu` VALUES ('23', '3', '702','1');
INSERT INTO `role_menu` VALUES ('24', '3', '801','1');
INSERT INTO `role_menu` VALUES ('25', '3', '802','1');
INSERT INTO `role_menu` VALUES ('26', '3', '9','1');
INSERT INTO `role_menu` VALUES ('27', '3', '901','1');
INSERT INTO `role_menu` VALUES ('28', '3', '10','1');
INSERT INTO `role_menu` VALUES ('29', '3', '1001','1');
INSERT INTO `role_menu` VALUES ('30', '3', '1104','0');
INSERT INTO `role_menu` VALUES ('31', '3', '11','01');
INSERT INTO `role_menu` VALUES ('32', '3', '902','1');
INSERT INTO `role_menu` VALUES ('34', '3', '1101','1');
INSERT INTO `role_menu` VALUES ('35', '3', '1102','1');
INSERT INTO `role_menu` VALUES ('36', '3', '404','1');
INSERT INTO `role_menu` VALUES ('37', '3', '104','0');
INSERT INTO `role_menu` VALUES ('38', '3', '803','1');
INSERT INTO `role_menu` VALUES ('86', '3', '1105','1');
INSERT INTO `role_menu` VALUES ('87', '3', '1106','0');

--管理人员
INSERT INTO `role_menu` VALUES ('39', '1', '1','0');
INSERT INTO `role_menu` VALUES ('40', '1', '2','0');
INSERT INTO `role_menu` VALUES ('41', '1', '6','01');
INSERT INTO `role_menu` VALUES ('42', '1', '101','0');
INSERT INTO `role_menu` VALUES ('43', '1', '102','0');
INSERT INTO `role_menu` VALUES ('44', '1', '103','0');
INSERT INTO `role_menu` VALUES ('45', '1', '1103','0');
INSERT INTO `role_menu` VALUES ('46', '1', '201','0');
INSERT INTO `role_menu` VALUES ('47', '1', '602','0');
INSERT INTO `role_menu` VALUES ('48', '1', '104','0');
INSERT INTO `role_menu` VALUES ('49', '1', '1104','0');
INSERT INTO `role_menu` VALUES ('50', '1', '11','01');
INSERT INTO `role_menu` VALUES ('51', '1', '4','1');
INSERT INTO `role_menu` VALUES ('52', '1', '5','1');
INSERT INTO `role_menu` VALUES ('54', '1', '401','1');
INSERT INTO `role_menu` VALUES ('55', '1', '402','1');
INSERT INTO `role_menu` VALUES ('56', '1', '403','1');
INSERT INTO `role_menu` VALUES ('57', '1', '501','1');
INSERT INTO `role_menu` VALUES ('58', '1', '502','1');
INSERT INTO `role_menu` VALUES ('59', '1', '503','1');
INSERT INTO `role_menu` VALUES ('60', '1', '601','1');
INSERT INTO `role_menu` VALUES ('61', '1', '9','1');
INSERT INTO `role_menu` VALUES ('62', '1', '901','1');
INSERT INTO `role_menu` VALUES ('63', '1', '10','1');
INSERT INTO `role_menu` VALUES ('64', '1', '1001','1');
INSERT INTO `role_menu` VALUES ('65', '1', '902','1');
INSERT INTO `role_menu` VALUES ('67', '1', '1101','1');
INSERT INTO `role_menu` VALUES ('68', '1', '1102','1');
INSERT INTO `role_menu` VALUES ('69', '1', '7','1');
INSERT INTO `role_menu` VALUES ('70', '1', '702','1');
INSERT INTO `role_menu` VALUES ('71', '1', '8','1');
INSERT INTO `role_menu` VALUES ('72', '1', '803','1');
INSERT INTO `role_menu` VALUES ('85', '1', '404','1');
INSERT INTO `role_menu` VALUES ('88', '1', '1105','1');
INSERT INTO `role_menu` VALUES ('89', '1', '1106','0');

--前台人员
INSERT INTO `role_menu` VALUES ('73', '2', '1','0');
INSERT INTO `role_menu` VALUES ('74', '2', '2','0');
INSERT INTO `role_menu` VALUES ('75', '2', '6','0');
INSERT INTO `role_menu` VALUES ('76', '2', '101','0');
INSERT INTO `role_menu` VALUES ('77', '2', '102','0');
INSERT INTO `role_menu` VALUES ('78', '2', '103','0');
INSERT INTO `role_menu` VALUES ('79', '2', '1103','0');
INSERT INTO `role_menu` VALUES ('80', '2', '201','0');
INSERT INTO `role_menu` VALUES ('81', '2', '602','0');
INSERT INTO `role_menu` VALUES ('82', '2', '1104','0');
INSERT INTO `role_menu` VALUES ('83', '2', '11','0');
INSERT INTO `role_menu` VALUES ('84', '2', '104','0');
INSERT INTO `role_menu` VALUES ('90', '2', '1106','0');
