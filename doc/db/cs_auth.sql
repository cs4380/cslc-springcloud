-- admin服务相关表

-- 创建数据库
create DATABASE cs_auth DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
Use cs_auth;

-- ----------------------------
-- 客户端表
-- ----------------------------
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256)  NOT NULL COMMENT '标识客户端,类似appKey',
  `resource_ids` varchar(256)  NULL DEFAULT NULL COMMENT '客户端所能访问的资源id集合',
  `client_secret` varchar(256)  NULL DEFAULT NULL COMMENT '客户端访问密匙,类似appSecret',
  `scope` varchar(256)  NULL DEFAULT NULL COMMENT '客户端申请的权限范围,可选值包括read,write,trust',
  `authorized_grant_types` varchar(256)  NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256)  NULL DEFAULT NULL,
  `authorities` varchar(256)  NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096)  NULL DEFAULT NULL,
  `autoapprove` varchar(256)  NULL DEFAULT NULL,
  `description` varchar(512)  NULL DEFAULT NULL COMMENT '说明',
  `is_deleted` int(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_user_id` varchar(36)  NULL DEFAULT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128)  NULL DEFAULT NULL COMMENT '创建用户姓名',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `update_user_id` varchar(36)  NULL DEFAULT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128)  NULL DEFAULT NULL COMMENT '最后更新用户姓名',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- app:app
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', '', '$2a$10$5UXSc055T9SJ2ek3LvEQu.DZ/aXriFPvWjAG.PIvw8aQIeYmQ2qwK', 'read', 'password,refresh_token', '', '', 7200, NULL, '{}', '', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
