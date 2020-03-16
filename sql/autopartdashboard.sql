/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : autopartdashboard

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 17/03/2020 01:26:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agingap
-- ----------------------------
DROP TABLE IF EXISTS `agingap`;
CREATE TABLE `agingap`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `monthperiod` int(255) NULL DEFAULT NULL,
  `yearperiod` int(255) NULL DEFAULT NULL,
  `current` float(255, 0) NULL DEFAULT NULL,
  `range1` float(255, 0) NULL DEFAULT NULL,
  `range2` float(255, 0) NULL DEFAULT NULL,
  `range3` float(255, 0) NULL DEFAULT NULL,
  `range4` float(255, 0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agingstock
-- ----------------------------
DROP TABLE IF EXISTS `agingstock`;
CREATE TABLE `agingstock`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `monthperiod` int(255) NULL DEFAULT NULL,
  `yearperiod` int(255) NULL DEFAULT NULL,
  `range1` float(255, 0) NULL DEFAULT NULL,
  `range2` float(255, 0) NULL DEFAULT NULL,
  `range3` float(255, 0) NULL DEFAULT NULL,
  `range4` float(255, 0) NULL DEFAULT NULL,
  `range5` float(255, 0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for araging
-- ----------------------------
DROP TABLE IF EXISTS `araging`;
CREATE TABLE `araging`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `monthperiod` int(255) NULL DEFAULT NULL,
  `yearperiod` int(255) NULL DEFAULT NULL,
  `current` float(255, 0) NULL DEFAULT NULL,
  `range1` float(255, 0) NULL DEFAULT NULL,
  `range2` float(255, 0) NULL DEFAULT NULL,
  `range3` float(255, 0) NULL DEFAULT NULL,
  `range4` float(255, 0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lastcashopname
-- ----------------------------
DROP TABLE IF EXISTS `lastcashopname`;
CREATE TABLE `lastcashopname`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `monthperiod` int(255) NULL DEFAULT NULL,
  `yearperiod` int(255) NULL DEFAULT NULL,
  `lastvariant` float(255, 0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for profitloss
-- ----------------------------
DROP TABLE IF EXISTS `profitloss`;
CREATE TABLE `profitloss`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `monthperiod` int(255) NULL DEFAULT NULL,
  `yearperiod` int(255) NULL DEFAULT NULL,
  `reportname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `reportval` float(255, 0) NULL DEFAULT NULL,
  `reportgroup` int(255) NULL DEFAULT NULL,
  `groupname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `reportidx` int(255) NULL DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profitloss
-- ----------------------------
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Penjualan Kotor', 2300839168, 100, 'INCOME', 101, 53);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Retur Penjualan', -21559130, 100, 'INCOME', 102, 54);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Diskon Penjualan', -4793389, 100, 'INCOME', 103, 55);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pendapatan Jasa Service', 10428000, 100, 'INCOME', 104, 56);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Harga Pokok Penjualan', 2086252160, 200, 'COST OF GOOD SOLD', 201, 57);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'GROSS PROFIT', 198662560, 250, 'GROSS PROFIT', 251, 58);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Alat dan Bahan', 3010700, 300, 'EXPENSE', 301, 59);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'BBM', 3626000, 300, 'EXPENSE', 302, 60);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Biaya Lain-lain', 6479850, 300, 'EXPENSE', 303, 61);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pajak', 3220000, 300, 'EXPENSE', 304, 62);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pulsa', 25000, 300, 'EXPENSE', 305, 63);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Uang Makan', 90000, 300, 'EXPENSE', 306, 64);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Uang Transport', 1130500, 300, 'EXPENSE', 307, 65);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'OTHER INCOME', 0, 400, 'OTHER INCOME', 401, 66);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'STOCK ADJUSTMENT', 7816690, 450, 'STOCK ADJUSTMENT', 451, 67);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'NET PROFIT', 188897200, 500, 'NET PROFIT', 501, 68);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Penjualan Kotor', 2145026560, 100, 'INCOME', 101, 69);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Retur Penjualan', -17626350, 100, 'INCOME', 102, 70);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Diskon Penjualan', -6716491, 100, 'INCOME', 103, 71);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Pendapatan Jasa Service', 10989500, 100, 'INCOME', 104, 72);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Harga Pokok Penjualan', 1948334336, 200, 'COST OF GOOD SOLD', 201, 73);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'GROSS PROFIT', 183338960, 250, 'GROSS PROFIT', 251, 74);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Alat dan Bahan', 1410000, 300, 'EXPENSE', 301, 75);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'BBM', 3206000, 300, 'EXPENSE', 302, 76);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Beban Selisih Kas', 192048, 300, 'EXPENSE', 303, 77);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Biaya Lain-lain', 22605000, 300, 'EXPENSE', 304, 78);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Pajak', 3010000, 300, 'EXPENSE', 305, 79);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Pulsa', 127000, 300, 'EXPENSE', 306, 80);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Uang Lembur', 220000, 300, 'EXPENSE', 307, 81);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Uang Makan', 290000, 300, 'EXPENSE', 308, 82);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'Uang Transport', 1719000, 300, 'EXPENSE', 309, 83);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'OTHER INCOME', 0, 400, 'OTHER INCOME', 401, 84);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'STOCK ADJUSTMENT', 1929921, 450, 'STOCK ADJUSTMENT', 451, 85);
INSERT INTO `profitloss` VALUES ('1', 12, 2019, 'NET PROFIT', 152489840, 500, 'NET PROFIT', 501, 86);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `projectname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`projectcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'Nusukan');
INSERT INTO `project` VALUES ('12', 'Banyuanyar');
INSERT INTO `project` VALUES ('13', 'Gumpang');

SET FOREIGN_KEY_CHECKS = 1;
