/*
 Navicat Premium Data Transfer

 Source Server         : docker mysql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 120.77.176.55:3306
 Source Schema         : timeline

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 14/11/2019 20:58:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publisher` varchar(20) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `file_download_uri` varchar(100) DEFAULT NULL,
  `show_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
BEGIN;
INSERT INTO `news` VALUES (1, '互联范儿', '2019-11-02 11:18:14', '据美媒报道，11月1日的一场集会上，美国总统特朗普将自己描绘成一场酝酿多年的弹劾行动的受害者。\'弹劾是一场骗局。\'\'你不能弹劾一个没有任何过错的总统。\'特朗普说。集会在密西西比州图珀洛举行，据报道，特朗普用严厉的措辞描述了弹劾调查，并将其称为诋毁他的民主党人的一个秘密阴谋。他谴责弹劾调查是“对民主本身的攻击”，是对他在2016年大选中获胜的破坏，即使弹劾调查按美国宪法规定的程序进行。特朗普说：“昨天，民主党人投票可能使6300万美国人的投票无效，让他们自己蒙羞，让众议院蒙羞。”“自从我获胜以来，他们一直在密谋推翻选举。\'', '特朗普遭遇弹劾危机', NULL, NULL, NULL);
INSERT INTO `news` VALUES (2, '中部纵览', '2019-11-02 11:19:33', '北京时间1日晚的CBA揭幕战，辽宁本钢队客场对阵广东东莞银行队，第三节比赛辽宁男篮队员丛明晨在防守中受伤被抬下场。2日，辽宁男篮公布了丛明晨伤情初诊情况：第1-4腰椎横突骨折，治疗时间预计为2-3个月。“目前，我们正在联系广州骨科专家做进一步检查会诊。有关丛明晨伤情的最新信息，我们也会第一时间在官方渠道公布。在此，祝愿丛明晨早日康复，重返赛场！”辽宁男篮这样写道。丛明晨出生于1995年，场上司职小前锋。当晚的比赛中，丛明晨得到6分，最终辽宁98:107不敌广东队。', '丛明晨伤情公布', NULL, NULL, NULL);
INSERT INTO `news` VALUES (3, '上海热线', '2019-11-02 11:20:56', '《陈情令》国风音乐演唱会在万众期待中拉开帷幕，主演人员齐聚南京，这注定是一个不平凡的11月呀！这一部电视剧不仅带给我们一次美妙的暑期视听盛宴，也让众多优秀青年演员们在这一个夏天开始发光发热，王一博和肖战这两位更是首当其冲，两位凭借蓝忘机和魏无羡的角色收获了众多关注与喜爱，非常期待两位接下来的新作品！接下来让我们一起来欣赏一下肖战和王一博这一次的演唱会造型吧！', '肖战卷发', NULL, NULL, NULL);
INSERT INTO `news` VALUES (4, 'IT之家', '2019-11-02 11:21:57', '官方信息显示，今日的半决赛比赛将在北京时间19：00开始，半决赛为BO5的比赛。FPX战队为中国LPL赛区的一号种子；IG战队同样也来自中国LPL赛区，这支战队还是去年英雄联盟S8的冠军队伍。据了解，半决赛将在位于西班牙马德里的维斯塔阿勒格雷宫举行。除了FPX和IG战队，另一场比赛双方为SKT和G2战。', 'IG首发', NULL, NULL, NULL);
INSERT INTO `news` VALUES (5, '凤凰娱乐', '2019-11-02 11:22:34', '11月1日，《超新星全运会》正式开始，参加节目的各位人气偶像也是竭尽全力，用自身行为践行着力争上游的体育精神。在超新星直播活动中，当红的流量男团女团也是一展风采，为星运会搏了个开门红。但是在女团火箭少女101表演时，杨超越又因为“划水”的问题让网友吐槽不敬业。', '杨超越划水', NULL, NULL, NULL);
INSERT INTO `news` VALUES (6, '百家号', '2019-11-02 11:23:26', '近日，有细心的网友发现一向被媒体称为娱乐圈纪委，前首富王健林之子，名气比他爸还大的王思聪的微博竟然空空如也，据娱乐圈知名写手剩闲小师傅爆料：王思聪微博确实看不到任何内容了，曾经怼天怼地怼众多明星的王思聪，恐怕是要专心致志准备明年的脱口秀节目，暂定名《小葱秀》，而且这档节目的主持人就是王思聪本人。', '王思送清空微博', NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
