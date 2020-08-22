-- admin服务相关表

-- 创建数据库
create DATABASE cs_admin DEFAULT ;
Use cs_admin;

-- ----------------------------
-- 基础用户信息表
-- ----------------------------
CREATE TABLE `base_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `username` varchar(64) NOT NULL COMMENT '用户账户',
  `password` varchar(256) NOT NULL COMMENT '用户密码',
  `name` varchar(128) DEFAULT NULL COMMENT '用户姓名',
  `user_sex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '用户性别（0男|1女）',
  `birthday` datetime DEFAULT NULL COMMENT '用户生日',
  `address` varchar(256) DEFAULT NULL COMMENT '用户地址',
  `mobile_phone` varchar(16) DEFAULT NULL COMMENT '用户手机号',
  `tel_phone` varchar(16) DEFAULT NULL COMMENT '用户电话号',
  `user_email` varchar(64) DEFAULT NULL COMMENT '用户邮箱',
  `description` varchar(512) DEFAULT NULL COMMENT '用户说明',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `is_disabled` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否禁用（1是|0否）',
  `is_super_admin` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否超级管理员（1是|0否）',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)  USING BTREE COMMENT '用户账户'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础用户表';

-- ----------------------------
-- 用户角色关系表
-- ----------------------------
CREATE TABLE `base_user_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(36)  NOT NULL COMMENT '用户Id',
  `role_code` varchar(128)  NOT NULL COMMENT '角色编码',
  `tenant_id` varchar(36)  DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户角色关系表';

-- ----------------------------
-- 基础部门信息表
-- ----------------------------
CREATE TABLE `base_dept` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `parent_id` varchar(36) NOT NULL DEFAULT 'root' COMMENT '父级部门id',
  `dept_name` varchar(256) NOT NULL COMMENT '部门名称',
  `dept_code` varchar(128) NOT NULL COMMENT '部门编码',
  `description` varchar(512) DEFAULT NULL COMMENT '部门说明',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dept_code` (`dept_code`)  USING BTREE COMMENT '部门编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础部门信息表';

-- ----------------------------
-- 基础角色部门关系表
-- ----------------------------
CREATE TABLE `base_dept_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(36)  NOT NULL COMMENT '用户id',
  `dept_code` varchar(128) NOT NULL COMMENT '部门编码',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础角色部门关系表';

-- ----------------------------
-- 数据字典类型表
-- ----------------------------
CREATE TABLE `sys_dict_type` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `dict_type_code` varchar(128) NOT NULL COMMENT '类型编码',
  `dict_type_name` varchar(255) NOT NULL COMMENT '类型名称',
  `parent_id` varchar(36) NOT NULL COMMENT '父id',
  `order_num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `description` varchar(512) DEFAULT NULL COMMENT '字典类型说明',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dict_type_code` (`dict_type_code`)  USING BTREE COMMENT '类型编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典类型';

-- ----------------------------
-- 数据字典值表
-- ----------------------------
CREATE TABLE `sys_dict_value` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `type_id` varchar(36) NOT NULL COMMENT '数据字典类型id',
  `dict_code` varchar(128) NOT NULL COMMENT '字典编码',
  `dict_title` varchar(256) NOT NULL COMMENT '字典标题',
  `order_num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `description` varchar(512) DEFAULT NULL COMMENT '字典值说明',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dict_code` (`dict_code`)  USING BTREE COMMENT '字典编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典值';


-- ----------------------------
-- 系统基础角色表
-- ----------------------------
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `parent_id` varchar(36) NOT NULL DEFAULT 'root' COMMENT '父级角色id',
  `role_code` varchar(128) NOT NULL COMMENT '角色编码',
  `role_name` varchar(256) NOT NULL COMMENT '角色名称',
  `description` varchar(512) DEFAULT NULL COMMENT '角色描述',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code` (`role_code`)  USING BTREE COMMENT '角色编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统基础角色表';

-- ----------------------------
-- 系统角色授权关系表
-- -----------------------------
CREATE TABLE `sys_role_authorization` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_code` varchar(128) NOT NULL COMMENT '角色编码',
  `resource_id` varchar(64) NOT NULL COMMENT '资源id',
  `resource_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '资源类型（0菜单|1按钮）',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色授权关系表';

-- ----------------------------
-- 系统基础菜单表
-- -----------------------------
CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL DEFAULT 'root' COMMENT '主键',
  `parent_id` varchar(36) NOT NULL DEFAULT 'root' COMMENT '父级菜单id',
  `menu_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '菜单类型(0:目录|1:菜单)',
  `menu_code` varchar(128) NOT NULL COMMENT '菜单编码',
  `menu_title` varchar(64) NOT NULL COMMENT '菜单标题',
  `menu_icon` varchar(128) DEFAULT NULL COMMENT '菜单图标',
  `menu_path` varchar(256) NOT NULL DEFAULT '/' COMMENT '菜单路径（路径别名）',
  `component` varchar(255) DEFAULT NULL COMMENT '菜单组件地址(菜单url地址)',
  `redirect` varchar(256) DEFAULT NULL COMMENT '重定向地址',
  `order_num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '排序',
  `description` varchar(512) DEFAULT NULL COMMENT '菜单描述',
  `hidden` tinyint(1) unsigned DEFAULT '0' COMMENT '是否隐藏(1:隐藏|0:未隐藏)',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `menu_code` (`menu_code`)  USING BTREE COMMENT '按钮编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统基础菜单表';

-- ----------------------------
-- 系统基础菜单按钮表
-- -----------------------------
CREATE TABLE `sys_menu_button` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` varchar(36) NOT NULL COMMENT '菜单id',
  `button_code` varchar(128) NOT NULL COMMENT '按钮编码',
  `button_title` varchar(64) NOT NULL COMMENT '按钮标题',
  `url` varchar(256) NOT NULL COMMENT '请求地址',
  `method` varchar(12) NOT NULL COMMENT '请求方式（POST|GET|DELETE|PUT）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:删除|0:未删除)',
  `description` varchar(512) DEFAULT NULL COMMENT '菜单描述',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `create_user_id` varchar(36) NOT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(128) NOT NULL COMMENT '创建用户姓名',
  `update_time` datetime NOT NULL COMMENT '最后更新日期',
  `update_user_id` varchar(36) NOT NULL COMMENT '最后更新用户Id',
  `update_user_name` varchar(128) NOT NULL COMMENT '最后更新用户姓名',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `button_code` (`button_code`) USING BTREE COMMENT '按钮编码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统基础菜单按钮表';