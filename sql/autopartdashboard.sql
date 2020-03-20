/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 100411
 Source Host           : localhost:3306
 Source Schema         : autopartdashboard

 Target Server Type    : MySQL
 Target Server Version : 100411
 File Encoding         : 65001

 Date: 20/03/2020 16:04:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for agingstock
-- ----------------------------
DROP TABLE IF EXISTS `agingstock`;
CREATE TABLE `agingstock`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `monthperiod` int(255) DEFAULT NULL,
  `yearperiod` int(255) DEFAULT NULL,
  `range1` float(255, 0) DEFAULT NULL,
  `range2` float(255, 0) DEFAULT NULL,
  `range3` float(255, 0) DEFAULT NULL,
  `range4` float(255, 0) DEFAULT NULL,
  `range5` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for apaging
-- ----------------------------
DROP TABLE IF EXISTS `apaging`;
CREATE TABLE `apaging`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `current` float(255, 0) DEFAULT NULL,
  `range1` float(255, 0) DEFAULT NULL,
  `range2` float(255, 0) DEFAULT NULL,
  `range3` float(255, 0) DEFAULT NULL,
  `range4` float(255, 0) DEFAULT NULL,
  `total` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apaging
-- ----------------------------
INSERT INTO `apaging` VALUES ('1', 3569998336, 118267352, 6831025, 1825120, 1150500, 3698072320);

-- ----------------------------
-- Table structure for araging
-- ----------------------------
DROP TABLE IF EXISTS `araging`;
CREATE TABLE `araging`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `current` float(255, 0) DEFAULT NULL,
  `range1` float(255, 0) DEFAULT NULL,
  `range2` float(255, 0) DEFAULT NULL,
  `range3` float(255, 0) DEFAULT NULL,
  `range4` float(255, 0) DEFAULT NULL,
  `total` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cashflow
-- ----------------------------
DROP TABLE IF EXISTS `cashflow`;
CREATE TABLE `cashflow`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `monthperiod` int(255) DEFAULT NULL,
  `yearperiod` int(255) DEFAULT NULL,
  `sales` float(255, 0) DEFAULT NULL,
  `purchase` float(255, 0) DEFAULT NULL,
  `otherincome` float(255, 0) DEFAULT NULL,
  `otherexpense` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cashflow
-- ----------------------------
INSERT INTO `cashflow` VALUES ('1', 3, 2020, 1499665536, 1616833536, 3486996, 17308964);
INSERT INTO `cashflow` VALUES ('1', 1, 2020, 2269106944, 2460047104, 13481972, 22500772);
INSERT INTO `cashflow` VALUES ('1', 2, 2020, 2125901312, 3513648640, 27094520, 33382400);

-- ----------------------------
-- Table structure for lastcashopname
-- ----------------------------
DROP TABLE IF EXISTS `lastcashopname`;
CREATE TABLE `lastcashopname`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `monthperiod` int(255) DEFAULT NULL,
  `yearperiod` int(255) DEFAULT NULL,
  `lastvariant` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for profitloss
-- ----------------------------
DROP TABLE IF EXISTS `profitloss`;
CREATE TABLE `profitloss`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `monthperiod` int(255) DEFAULT NULL,
  `yearperiod` int(255) DEFAULT NULL,
  `reportname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reportval` float(255, 0) DEFAULT NULL,
  `reportgroup` int(255) DEFAULT NULL,
  `groupname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reportidx` int(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profitloss
-- ----------------------------
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Penjualan Kotor', 1441426944, 100, 'INCOME', 101, 19);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Retur Penjualan', -14697115, 100, 'INCOME', 102, 20);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Diskon Penjualan', -3654085, 100, 'INCOME', 103, 21);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Pendapatan Jasa Service', 8020000, 100, 'INCOME', 104, 22);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Harga Pokok Penjualan', 1301943168, 200, 'COST OF GOOD SOLD', 201, 23);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'GROSS PROFIT', 129152544, 250, 'GROSS PROFIT', 251, 24);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Alat dan Bahan', 2305000, 300, 'EXPENSE', 301, 25);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'BBM', 2310000, 300, 'EXPENSE', 302, 26);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Beban Selisih Kas', 7108, 300, 'EXPENSE', 303, 27);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Biaya Lain-lain', 4055133, 300, 'EXPENSE', 304, 28);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Pajak', 3075000, 300, 'EXPENSE', 305, 29);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Pulsa', 75000, 300, 'EXPENSE', 306, 30);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Uang Lembur', 180000, 300, 'EXPENSE', 307, 31);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Uang Makan', 180000, 300, 'EXPENSE', 308, 32);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'Uang Transport', 712000, 300, 'EXPENSE', 309, 33);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'OTHER INCOME', 0, 400, 'OTHER INCOME', 401, 34);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'STOCK ADJUSTMENT', -39315420, 450, 'STOCK ADJUSTMENT', 451, 35);
INSERT INTO `profitloss` VALUES ('1', 3, 2020, 'NET PROFIT', 76937880, 500, 'NET PROFIT', 501, 36);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Penjualan Kotor', 2300839168, 100, 'INCOME', 101, 37);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Retur Penjualan', -21559130, 100, 'INCOME', 102, 38);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Diskon Penjualan', -4793389, 100, 'INCOME', 103, 39);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pendapatan Jasa Service', 10428000, 100, 'INCOME', 104, 40);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Harga Pokok Penjualan', 2086252160, 200, 'COST OF GOOD SOLD', 201, 41);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'GROSS PROFIT', 198662560, 250, 'GROSS PROFIT', 251, 42);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Alat dan Bahan', 3010700, 300, 'EXPENSE', 301, 43);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'BBM', 3626000, 300, 'EXPENSE', 302, 44);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Biaya Lain-lain', 6479850, 300, 'EXPENSE', 303, 45);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pajak', 3220000, 300, 'EXPENSE', 304, 46);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Pulsa', 25000, 300, 'EXPENSE', 305, 47);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Uang Makan', 90000, 300, 'EXPENSE', 306, 48);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'Uang Transport', 1130500, 300, 'EXPENSE', 307, 49);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'OTHER INCOME', 0, 400, 'OTHER INCOME', 401, 50);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'STOCK ADJUSTMENT', 7816690, 450, 'STOCK ADJUSTMENT', 451, 51);
INSERT INTO `profitloss` VALUES ('1', 1, 2020, 'NET PROFIT', 188897200, 500, 'NET PROFIT', 501, 52);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Penjualan Kotor', 2192431104, 100, 'INCOME', 101, 53);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Retur Penjualan', -10869300, 100, 'INCOME', 102, 54);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Diskon Penjualan', -9084462, 100, 'INCOME', 103, 55);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Pendapatan Jasa Service', 10830500, 100, 'INCOME', 104, 56);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Harga Pokok Penjualan', 1991864960, 200, 'COST OF GOOD SOLD', 201, 57);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'GROSS PROFIT', 191442992, 250, 'GROSS PROFIT', 251, 58);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Alat dan Bahan', 2110500, 300, 'EXPENSE', 301, 59);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'BBM', 3689000, 300, 'EXPENSE', 302, 60);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Beban Selisih Kas', 0, 300, 'EXPENSE', 303, 61);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Biaya Lain-lain', 15506900, 300, 'EXPENSE', 304, 62);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Gaji Karyawan', 2426940, 300, 'EXPENSE', 305, 63);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Pajak', 3129000, 300, 'EXPENSE', 306, 64);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Pulsa', 153000, 300, 'EXPENSE', 307, 65);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Uang Lembur', 270000, 300, 'EXPENSE', 308, 66);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Uang Makan', 120000, 300, 'EXPENSE', 309, 67);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'Uang Transport', 1519000, 300, 'EXPENSE', 310, 68);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'OTHER INCOME', 0, 400, 'OTHER INCOME', 401, 69);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'STOCK ADJUSTMENT', -5303360, 450, 'STOCK ADJUSTMENT', 451, 70);
INSERT INTO `profitloss` VALUES ('1', 2, 2020, 'NET PROFIT', 157215280, 500, 'NET PROFIT', 501, 71);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `projectcode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `projectname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`projectcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'Nusukan');
INSERT INTO `project` VALUES ('12', 'Banyuanyar');
INSERT INTO `project` VALUES ('13', 'Gumpang');

-- ----------------------------
-- Table structure for salesperiod
-- ----------------------------
DROP TABLE IF EXISTS `salesperiod`;
CREATE TABLE `salesperiod`  (
  `projectcode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `transdate` date DEFAULT NULL,
  `netsales` float(255, 0) DEFAULT NULL,
  `cogs` float(255, 0) DEFAULT NULL,
  `grossprofit` float(255, 0) DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of salesperiod
-- ----------------------------
INSERT INTO `salesperiod` VALUES ('1', '2020-01-02', 97745920, 88032312, 9713606);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-03', 87036704, 79716896, 7319804);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-04', 100099616, 91569072, 8530544);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-06', 100459128, 91639648, 8819478);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-07', 135031696, 124448304, 10583394);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-08', 89198432, 80834936, 8363495);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-09', 81611208, 73811424, 7799788);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-10', 83388128, 75990456, 7397670);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-11', 101148016, 92619880, 8528140);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-13', 107138208, 99209144, 7929068);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-14', 94367568, 85762120, 8605448);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-15', 70149440, 63780032, 6369407);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-16', 107425640, 96723056, 10702586);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-17', 74063424, 67322968, 6740455);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-18', 83206496, 76019552, 7186942);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-20', 88272464, 80481056, 7791406);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-21', 73106624, 66697272, 6409352);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-22', 90397424, 83468968, 6928453);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-23', 73306680, 66651840, 6654840);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-24', 83347424, 75634984, 7712438);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-25', 57920296, 53113084, 4807213);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-27', 97899240, 90068320, 7830919);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-28', 100295568, 93076424, 7219145);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-29', 91166328, 83646224, 7520110);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-30', 68100352, 61437632, 6662718);
INSERT INTO `salesperiod` VALUES ('1', '2020-01-31', 49032680, 44496536, 4536144);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-01', 81051136, 74271752, 6779390);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-03', 104565368, 95995408, 8569953);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-04', 112983712, 104101320, 8882389);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-05', 79022152, 71872728, 7149428);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-06', 72886256, 65806228, 7080029);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-07', 82947360, 75952768, 6994590);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-08', 72165152, 65706352, 6458802);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-10', 112025232, 102137488, 9887739);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-11', 128291048, 118603280, 9687767);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-12', 57610000, 51669660, 5940341);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-13', 71325504, 64206912, 7118586);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-14', 89649896, 82563896, 7086001);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-15', 86656160, 78585904, 8070256);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-17', 80686336, 73446208, 7240131);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-18', 114972368, 106168040, 8804333);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-19', 76436288, 69359816, 7076476);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-20', 63638600, 57122456, 6516145);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-21', 82346800, 75436400, 6910396);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-22', 95913448, 87502176, 8411270);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-24', 95221464, 86630256, 8591206);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-25', 101196304, 93122400, 8073897);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-26', 72386208, 65045848, 7340361);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-27', 68246912, 61731292, 6515617);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-28', 86025360, 78160296, 7865068);
INSERT INTO `salesperiod` VALUES ('1', '2020-02-29', 95058816, 86666000, 8392816);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-02', 83429200, 76090936, 7338267);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-03', 123356392, 113791024, 9565365);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-04', 77276248, 70209592, 7066655);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-05', 72857904, 65027120, 7830780);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-06', 79044208, 72063000, 6981209);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-07', 99409472, 90893608, 8515862);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-09', 110761000, 100416656, 10344345);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-10', 112573632, 102732264, 9841373);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-11', 87990856, 79914464, 8076391);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-12', 62848460, 55753864, 7094597);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-13', 82449296, 74917216, 7532085);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-14', 92130336, 83962320, 8168017);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-15', 874000, 801310, 72690);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-16', 85258176, 78202360, 7055814);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-17', 110999952, 101571464, 9428487);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-18', 89472528, 81599840, 7872687);
INSERT INTO `salesperiod` VALUES ('1', '2020-03-19', 60364104, 53996184, 6367917);

SET FOREIGN_KEY_CHECKS = 1;
