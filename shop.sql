/*
Navicat MySQL Data Transfer

Source Server         : shop
Source Server Version : 80300
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 80300
File Encoding         : 65001

Date: 2024-06-19 17:07:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '收货人',
  `sex` tinyint NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='地址管理';

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES ('1802985772580626433', '1802985010282651649', '杨睿卿', '1', '13409815291', null, null, null, null, null, null, '中国 安徽省 合肥市 包河区 肥西路66号', '学校', '0', '2024-06-18 16:44:41', '2024-06-18 16:44:43', '1802985010282651649', '1802985010282651649', '0');
INSERT INTO `address_book` VALUES ('1803000328958885889', '1802985010282651649', '杨睿卿', '1', '13409815291', null, null, null, null, null, null, '安徽省合肥市包河区中区6号楼下', '学校', '1', '2024-06-18 17:42:31', '2024-06-18 17:42:35', '1802985010282651649', '1802985010282651649', '0');
INSERT INTO `address_book` VALUES ('1803325098355105793', '1803324906864156674', '杨睿卿', '1', '13409815291', null, null, null, null, null, null, '安徽省合肥市包河区中区6号楼下', '学校', '1', '2024-06-19 15:13:02', '2024-06-19 15:13:05', '1803324906864156674', '1803324906864156674', '0');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint NOT NULL COMMENT '主键',
  `type` int DEFAULT NULL COMMENT '类型 产品分类',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='产品分类';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1802706326648799233', '1', '生活用品', '1', '2024-06-17 22:14:16', '2024-06-18 13:40:23', '1', '1');
INSERT INTO `category` VALUES ('1802706359616028674', '1', '电子产品', '2', '2024-06-17 22:14:23', '2024-06-17 22:14:23', '1', '1');
INSERT INTO `category` VALUES ('1802706414309752834', '1', '服装衣物', '3', '2024-06-17 22:14:37', '2024-06-17 22:14:37', '1', '1');
INSERT INTO `category` VALUES ('1802706541460078594', '1', '书籍', '4', '2024-06-17 22:15:07', '2024-06-17 22:15:07', '1', '1');
INSERT INTO `category` VALUES ('1802706570597908481', '1', '其他', '7', '2024-06-17 22:15:14', '2024-06-18 17:33:17', '1', '1');
INSERT INTO `category` VALUES ('1802939463698059266', '1', '家具', '5', '2024-06-18 13:40:40', '2024-06-18 13:40:40', '1', '1');
INSERT INTO `category` VALUES ('1802997981297885186', '1', '药品', '6', '2024-06-18 17:33:11', '2024-06-18 17:33:11', '1', '1');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='管理员信息';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', '1', '2021-05-06 17:20:07', '2021-05-10 02:24:09', '1', '1');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint DEFAULT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '地址',
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '用户姓名',
  `consignee` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '收货人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='订单表';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1802985809050099714', '1802985809050099714', '4', '1802985010282651649', '1802985772580626433', '2024-06-18 16:44:49', '2024-06-18 16:44:49', '6000.00', '', '13409815291', '中国 安徽省 合肥市 包河区 肥西路66号', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1802999837860413441', '1802999837860413441', '4', '1802985010282651649', '1802985772580626433', '2024-06-18 17:40:34', '2024-06-18 17:40:34', '15000.00', '', '13409815291', '中国 安徽省 合肥市 包河区 肥西路66号', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1802999911432699906', '1802999911432699906', '4', '1802985010282651649', '1802985772580626433', '2024-06-18 17:40:52', '2024-06-18 17:40:52', '13000.00', '', '13409815291', '中国 安徽省 合肥市 包河区 肥西路66号', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803063581604880385', '1803063581604880385', '2', '1802985010282651649', '1803000328958885889', '2024-06-18 21:53:52', '2024-06-18 21:53:52', '35400.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803308904344506369', '1803308904344506369', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 14:08:41', '2024-06-19 14:08:41', '20000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803317159175184386', '1803317159175184386', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 14:41:29', '2024-06-19 14:41:29', '1000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803317195581743106', '1803317195581743106', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 14:41:38', '2024-06-19 14:41:38', '1000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803317473588629505', '1803317473588629505', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 14:42:44', '2024-06-19 14:42:44', '1000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803325157142470657', '1803325157142470657', '2', '1803324906864156674', '1803325098355105793', '2024-06-19 15:13:16', '2024-06-19 15:13:16', '1500.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803350256679714818', '1803350256679714818', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 16:53:00', '2024-06-19 16:53:00', '458000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803350755084652546', '1803350755084652546', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 16:54:59', '2024-06-19 16:54:59', '45000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');
INSERT INTO `orders` VALUES ('1803350797191270401', '1803350797191270401', '2', '1802985010282651649', '1803000328958885889', '2024-06-19 16:55:09', '2024-06-19 16:55:09', '1000.00', '', '13409815291', '安徽省合肥市包河区中区6号楼下', null, '杨睿卿');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `product_id` bigint DEFAULT NULL COMMENT '菜品id',
  `product_param` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='订单明细表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1802985809117208578', '数学分析', 'bc5bd7f2-5375-4ff9-a920-a95ee5a0093c.jpg', '1802985809050099714', '1802984298165329922', null, '1', '6000.00');
INSERT INTO `order_detail` VALUES ('1802999837927522306', '衬衫', 'd34f353a-b14b-4767-b635-4d3c42523947.jpg', '1802999837860413441', '1802998245165744129', null, '3', '5000.00');
INSERT INTO `order_detail` VALUES ('1802999911499808770', '实分析', 'cae2bd08-588c-4330-bd13-89ace8252535.jpg', '1802999911432699906', '1802987457415766018', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1802999911499808771', '数学分析', 'bc5bd7f2-5375-4ff9-a920-a95ee5a0093c.jpg', '1802999911432699906', '1802984298165329922', null, '2', '6000.00');
INSERT INTO `order_detail` VALUES ('1803063581739098114', '枕头', '528f52dd-bbd4-43a4-b553-792402e583ea.jpg', '1803063581604880385', '1803001059564699650', null, '59', '600.00');
INSERT INTO `order_detail` VALUES ('1803308904453558274', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803308904344506369', '1803006300565553153', null, '40', '500.00');
INSERT INTO `order_detail` VALUES ('1803309193881505794', 'ipad', 'a47b8387-a233-4f52-968f-1cd00855c24d.jpg', '1803309193881505793', '1803306981201580034', null, '20', '50000.00');
INSERT INTO `order_detail` VALUES ('1803310336049205249', '实分析', 'cae2bd08-588c-4330-bd13-89ace8252535.jpg', '1803310335982096385', '1802987457415766018', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1803312700198031361', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803312700109950977', '1803006300565553153', null, '3', '500.00');
INSERT INTO `order_detail` VALUES ('1803317159330373634', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803317159175184386', '1803006300565553153', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1803317195648851969', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803317195581743106', '1803006300565553153', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1803317473651544065', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803317473588629505', '1803006300565553153', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1803325157796782081', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803325157142470657', '1803006300565553153', null, '2', '500.00');
INSERT INTO `order_detail` VALUES ('1803325157796782082', '实分析', 'cae2bd08-588c-4330-bd13-89ace8252535.jpg', '1803325157142470657', '1802987457415766018', null, '1', '500.00');
INSERT INTO `order_detail` VALUES ('1803350256797155329', 'ipad', 'a47b8387-a233-4f52-968f-1cd00855c24d.jpg', '1803350256679714818', '1803306981201580034', null, '7', '50000.00');
INSERT INTO `order_detail` VALUES ('1803350256797155330', '数学分析', 'bc5bd7f2-5375-4ff9-a920-a95ee5a0093c.jpg', '1803350256679714818', '1802984298165329922', null, '18', '6000.00');
INSERT INTO `order_detail` VALUES ('1803350755151761409', '衬衫', 'd34f353a-b14b-4767-b635-4d3c42523947.jpg', '1803350755084652546', '1802998245165744129', null, '9', '5000.00');
INSERT INTO `order_detail` VALUES ('1803350797254184962', '搞怪表情包', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '1803350797191270401', '1803006300565553153', null, '2', '500.00');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '产品名称',
  `category_id` bigint NOT NULL COMMENT '产品分类id',
  `price` int DEFAULT NULL COMMENT '产品价格',
   `stock` int DEFAULT '0' COMMENT '库存',
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述信息',
  `status` int NOT NULL DEFAULT '1' COMMENT '0 停售 1 起售',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_produt_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='产品管理';

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1802984298165329922', '数学分析', '1802706541460078594', '6000', 'bc5bd7f2-5375-4ff9-a920-a95ee5a0093c.jpg', '', '1', '0', '2024-06-18 16:38:49', '2024-06-18 16:38:49', '1', '1', '0', '89');
INSERT INTO `product` VALUES ('1802987457415766018', '实分析', '1802706541460078594', '500', 'cae2bd08-588c-4330-bd13-89ace8252535.jpg', '', '1', '0', '2024-06-18 16:51:22', '2024-06-18 17:07:23', '1802985010282651649', '1', '0', '195');
INSERT INTO `product` VALUES ('1802998245165744129', '衬衫', '1802706414309752834', '5000', 'd34f353a-b14b-4767-b635-4d3c42523947.jpg', '一件衬衫', '1', '0', '2024-06-18 17:34:14', '2024-06-18 17:34:28', '1', '1', '0', '38');
INSERT INTO `product` VALUES ('1803001059564699650', '枕头', '1802706326648799233', '600', '528f52dd-bbd4-43a4-b553-792402e583ea.jpg', '', '1', '0', '2024-06-18 17:45:25', '2024-06-18 21:54:20', '1802985010282651649', '1802985010282651649', '0', '0');
INSERT INTO `product` VALUES ('1803005703439298561', '中科大', '1802706326648799233', '6000', '9813d584-8f8b-4586-8b0a-f072d142d558.png', '', '3', '0', '2024-06-18 18:03:53', '2024-06-18 18:08:19', '1802985010282651649', '1', '0', '20');
INSERT INTO `product` VALUES ('1803006300565553153', '搞怪表情包', '1802706326648799233', '500', 'c2881ca2-d5ca-4a79-8b43-432d9b6878ab.png', '', '1', '0', '2024-06-18 18:06:15', '2024-06-18 18:08:18', '1802985010282651649', '1', '0', '935');
INSERT INTO `product` VALUES ('1803306981201580034', 'ipad', '1802706359616028674', '50000', 'a47b8387-a233-4f52-968f-1cd00855c24d.jpg', '', '1', '0', '2024-06-19 14:01:03', '2024-06-19 15:04:21', '1802985010282651649', '1', '0', '-35');
INSERT INTO `product` VALUES ('1803322446959386626', 'USTC', '1802706570597908481', '500000', '7354b5bc-f1da-4026-85b9-62353cc9401f.png', '', '2', '0', '2024-06-19 15:02:30', '2024-06-19 15:02:30', '1802985010282651649', '1802985010282651649', '0', '10');
INSERT INTO `product` VALUES ('1803326110855897089', 'yijiao', '1802706359616028674', '100000', '2e3b5118-a3c8-429c-98ed-78cbf0369462.jpg', '无', '1', '0', '2024-06-19 15:17:04', '2024-06-19 15:17:14', '1803324906864156674', '1', '0', '0');

-- ----------------------------
-- Table structure for product_param
-- ----------------------------
DROP TABLE IF EXISTS `product_param`;
CREATE TABLE `product_param` (
  `id` bigint NOT NULL COMMENT '主键',
  `product_id` bigint NOT NULL COMMENT '商品id',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '参数名称',
  `value` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '参数数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='参数';

-- ----------------------------
-- Records of product_param
-- ----------------------------

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `scores` int DEFAULT '0',
  `scores_after` int DEFAULT '0',
  `create_time` datetime DEFAULT (now()),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('74', '1802985010282651649', '积分借贷(id:60)', '6000', '6000', '2024-06-18 16:43:45');
INSERT INTO `score` VALUES ('75', '1802985010282651649', '消费(订单号:1802985809050099714)', '-6000', '0', '2024-06-18 16:44:49');
INSERT INTO `score` VALUES ('76', '1802985010282651649', '积分借贷(id:64)', '600000', '600000', '2024-06-18 17:40:11');
INSERT INTO `score` VALUES ('77', '1802985010282651649', '消费(订单号:1802999837860413441)', '-15000', '585000', '2024-06-18 17:40:34');
INSERT INTO `score` VALUES ('78', '1802985010282651649', '消费(订单号:1802999911432699906)', '-13000', '572000', '2024-06-18 17:40:51');
INSERT INTO `score` VALUES ('79', '1802985010282651649', '积分借贷(id:65)', '60000', '632000', '2024-06-18 18:08:11');
INSERT INTO `score` VALUES ('80', '1802985010282651649', '消费(订单号:1803063581604880385)', '-35400', '596600', '2024-06-18 21:53:51');
INSERT INTO `score` VALUES ('81', '1802985010282651649', '完成任务-完成数据库系统概论复习', '5000', '601600', '2024-06-19 13:30:17');
INSERT INTO `score` VALUES ('82', '1802985010282651649', '完成任务-完成量子物理复习', '5000', '606600', '2024-06-19 13:33:35');
INSERT INTO `score` VALUES ('83', '1802985010282651649', '完成任务-完成随机过程的预习', '5000', '611600', '2024-06-19 13:57:09');
INSERT INTO `score` VALUES ('84', '1802985010282651649', '消费(订单号:1803308904344506369)', '-20000', '591600', '2024-06-19 14:08:41');
INSERT INTO `score` VALUES ('88', '1802985010282651649', '消费(订单号:1803317159175184386)', '-1000', '590600', '2024-06-19 14:41:29');
INSERT INTO `score` VALUES ('89', '1802985010282651649', '消费(订单号:1803317195581743106)', '-1000', '589600', '2024-06-19 14:41:38');
INSERT INTO `score` VALUES ('90', '1802985010282651649', '消费(订单号:1803317473588629505)', '-1000', '588600', '2024-06-19 14:42:44');
INSERT INTO `score` VALUES ('91', '1803324906864156674', '消费(订单号:1803325157142470657)', '-1500', '-1500', '2024-06-19 15:13:16');
INSERT INTO `score` VALUES ('92', '1803324906864156674', '完成任务-汇报', '10000', '8500', '2024-06-19 15:15:30');
INSERT INTO `score` VALUES ('93', '1802985010282651649', '消费(订单号:1803350256679714818)', '-458000', '130600', '2024-06-19 16:53:00');
INSERT INTO `score` VALUES ('94', '1802985010282651649', '消费(订单号:1803350755084652546)', '-45000', '85600', '2024-06-19 16:54:59');
INSERT INTO `score` VALUES ('95', '1802985010282651649', '消费(订单号:1803350797191270401)', '-1000', '84600', '2024-06-19 16:55:09');

-- ----------------------------
-- Table structure for score_loan
-- ----------------------------
DROP TABLE IF EXISTS `score_loan`;
CREATE TABLE `score_loan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `score` int DEFAULT NULL,
  `loan_reason` varchar(255) DEFAULT NULL,
  `status` int DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userid` (`user_id`),
  CONSTRAINT `score_loan_chk_1` CHECK ((`score` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of score_loan
-- ----------------------------
INSERT INTO `score_loan` VALUES ('60', '1802985010282651649', '6000', '想买数学分析', '1', '2024-06-18 16:42:10', '2024-06-18 16:42:10');
INSERT INTO `score_loan` VALUES ('64', '1802985010282651649', '600000', '想要测试的积分', '1', '2024-06-18 17:40:03', '2024-06-18 17:40:03');
INSERT INTO `score_loan` VALUES ('65', '1802985010282651649', '60000', '测试需要', '1', '2024-06-18 17:43:23', '2024-06-18 17:43:23');

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `product_param` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` int NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='购物车';

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------
INSERT INTO `shopping_cart` VALUES ('1663737770813784065', '2', '3e5ab468-0228-4b2d-bd7c-f4fe51fd2783.jpg', '1663737724424781825', '1663538195884699649', null, '1', '1', '2023-05-31 10:42:50');
INSERT INTO `shopping_cart` VALUES ('1800550603651407873', '4090', '2d0d1e68-a664-4435-9490-d7d777437eb8.jpg', '1800130559685955585', '1397850140982161409', null, '1', '6800', '2024-06-11 23:28:11');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '任务名称',
  `task_des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '任务描述',
  `score` int DEFAULT NULL COMMENT '悬赏',
  `status` int DEFAULT '0' COMMENT '0未接，1以接，2已完成',
  `end_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1803325521807843331 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1802986433040900097', '完成Java大作业', '完成Java大作业', '60000', '1', '2024-06-29 00:00:00', '2024-06-18 16:47:18', '2024-06-18 16:47:18');
INSERT INTO `task` VALUES ('1803006740451573762', '测试项目', '测试的任务', '5000', '2', '2024-06-19 00:00:00', '2024-06-18 18:08:00', '2024-06-19 13:32:06');
INSERT INTO `task` VALUES ('1803298744884563970', '完成数据库系统概论复习', '完成数据库系统概论复习', '5000', '2', '2024-06-28 00:00:00', '2024-06-19 13:28:19', '2024-06-19 13:28:19');
INSERT INTO `task` VALUES ('1803299935626498049', '完成量子物理复习', '完成量子物理复习', '5000', '1', '2024-06-27 00:00:00', '2024-06-19 13:33:03', '2024-06-19 13:36:10');
INSERT INTO `task` VALUES ('1803300184889790465', '完成随机过程的预习', '完成随机过程的预习', '5000', '2', '2024-06-27 00:00:00', '2024-06-19 13:34:02', '2024-06-19 13:34:02');
INSERT INTO `task` VALUES ('1803302969135894530', '完成数据库大作业报告', '测试', '8000', '2', '2024-06-27 00:00:00', '2024-06-19 13:45:06', '2024-06-19 13:58:00');
INSERT INTO `task` VALUES ('1803322091416625153', '完成数据库大作业汇报', '完成数据库大作业汇报', '50000', '1', '2024-06-26 00:00:00', '2024-06-19 15:01:05', '2024-06-19 15:01:05');
INSERT INTO `task` VALUES ('1803325521807843330', '汇报', 'hh', '10000', '2', '2024-06-27 00:00:00', '2024-06-19 15:14:43', '2024-06-19 15:14:43');

-- ----------------------------
-- Table structure for task_u
-- ----------------------------
DROP TABLE IF EXISTS `task_u`;
CREATE TABLE `task_u` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userid` bigint NOT NULL COMMENT '接单用户',
  `taskid` bigint DEFAULT NULL COMMENT '单子',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '任务的名称',
  `score` int DEFAULT '0' COMMENT '积分',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '0未完成，1完成未审核，2审核通过,3审核失败，4任务取消',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=455626775 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of task_u
-- ----------------------------
INSERT INTO `task_u` VALUES ('455626766', '1802985010282651649', '1802986433040900097', '完成Java大作业', '60000', '4', '2024-06-18 16:49:50', '2024-06-18 16:49:50');
INSERT INTO `task_u` VALUES ('455626767', '1802985010282651649', '1803006740451573762', '测试项目', '5000', '3', '2024-06-18 18:09:59', '2024-06-18 18:09:59');
INSERT INTO `task_u` VALUES ('455626768', '1802985010282651649', '1803298744884563970', '完成数据库系统概论复习', '5000', '2', '2024-06-19 13:28:29', '2024-06-19 13:28:29');
INSERT INTO `task_u` VALUES ('455626770', '1802985010282651649', '1803299935626498049', '完成量子物理复习', '5000', '2', '2024-06-19 13:33:22', '2024-06-19 13:33:22');
INSERT INTO `task_u` VALUES ('455626771', '1802985010282651649', '1803300184889790465', '完成随机过程的预习', '5000', '2', '2024-06-19 13:36:36', '2024-06-19 13:36:36');
INSERT INTO `task_u` VALUES ('455626773', '1803324906864156674', '1803325521807843330', '汇报', '10000', '2', '2024-06-19 15:14:52', '2024-06-19 15:14:52');
INSERT INTO `task_u` VALUES ('455626774', '1802985010282651649', '1803322091416625153', '完成数据库大作业汇报', '50000', '1', '2024-06-19 16:55:44', '2024-06-19 16:55:44');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '性别',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '头像',
  `status` int DEFAULT '0' COMMENT '状态 0:禁用，1:正常',
  `scores` int DEFAULT NULL COMMENT '个人积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=COMPACT COMMENT='用户信息';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1802985010282651649', null, '13409815291', null, null, '1', '84600');
INSERT INTO `user` VALUES ('1803324906864156674', null, '19966421938', null, null, '1', '8500');
