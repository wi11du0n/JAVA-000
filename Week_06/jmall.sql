CREATE DATABASE `jmall`;

USE jmall;

CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` int NOT NULL COMMENT '用户 ID',
  `addr` varchar(255) NOT NULL COMMENT '地址',
  `mobile` varchar(255) NOT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) COMMENT='地址';


CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` int NOT NULL COMMENT '用户 ID',
  `addr_id` int NOT NULL COMMENT '地址 ID',
  `order_status` int NOT NULL COMMENT '订单状态',
  `total_price` int NOT NULL COMMENT '总价',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) COMMENT='订单';

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `sku` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'SKU',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='商品';

CREATE TABLE `product_snapshot` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `order_id` int NOT NULL COMMENT '订单 ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `sku` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'SKU',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_order_id` (`order_id`)
) COMMENT='商品快照';

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `nike_name` varchar(255) NOT NULL COMMENT '昵称',
  `mobile` varchar(255) NOT NULL COMMENT '手机号',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) COMMENT='用户';