-- ----------------------------
-- Table structure for authorization_file
-- ----------------------------
DROP TABLE IF EXISTS `authorization_file`;
CREATE TABLE `authorization_file`  (
  `authorization_id` bigint(0) NOT NULL,
  `file_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`authorization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorization_function
-- ----------------------------
DROP TABLE IF EXISTS `authorization_function`;
CREATE TABLE `authorization_function`  (
  `authorization_id` bigint(0) NOT NULL,
  `function_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`authorization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorization_info
-- ----------------------------
DROP TABLE IF EXISTS `authorization_info`;
CREATE TABLE `authorization_info`  (
  `id` bigint(0) NOT NULL,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorization_page_element
-- ----------------------------
DROP TABLE IF EXISTS `authorization_page_element`;
CREATE TABLE `authorization_page_element`  (
  `authorization_id` bigint(0) NOT NULL,
  `page_element_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`authorization_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件名',
  `path` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for function_info
-- ----------------------------
DROP TABLE IF EXISTS `function_info`;
CREATE TABLE `function_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作编码',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作url',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父操作id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_info
-- ----------------------------
DROP TABLE IF EXISTS `group_info`;
CREATE TABLE `group_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组名',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父组id',
  `describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单地址',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for page_element_info
-- ----------------------------
DROP TABLE IF EXISTS `page_element_info`;
CREATE TABLE `page_element_info`  (
  `id` bigint(0) NOT NULL,
  `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面元素编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '按钮资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_authorization
-- ----------------------------
DROP TABLE IF EXISTS `role_authorization`;
CREATE TABLE `role_authorization`  (
  `role_id` bigint(0) NOT NULL,
  `authorization_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `describe` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_resources
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role_id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单id',
  `menu_parent_id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单父节点id',
  `button_id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '按钮id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色资源对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `group_id` int(0) NULL DEFAULT NULL COMMENT '组id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户在组里的名称',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '用户添加组的时间',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(0) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户姓名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户电话号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
