-- ----------------------------
-- 1、摊贩档案表 (biz_vendor)
-- 假设关联的 sys_user 账号ID为 101, 102, 103
-- ----------------------------
INSERT INTO `biz_vendor` (`vendor_id`, `user_id`, `vendor_name`, `phone`, `goods_category`, `health_cert_url`, `credit_score`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
                                                                                                                                                                                                                           (1, 101, '张建国', '13800000001', '广式烧鸭饭', '/profile/cert/v1.jpg', 100, '1', '0', 'admin', '2026-04-20 08:30:00', '', NULL, '合规摊贩，证件齐全'),
                                                                                                                                                                                                                           (2, 102, '李淑华', '13800000002', '鲜切水果捞', '/profile/cert/v2.jpg', 95, '1', '0', 'admin', '2026-04-20 09:15:00', 'admin', '2026-04-21 10:00:00', '曾有轻微卫生扣分'),
                                                                                                                                                                                                                           (3, 103, '赵铁柱', '13800000003', '东北烤冷面', '', 100, '0', '0', 'admin', '2026-04-21 11:20:00', '', NULL, '健康证待补充，目前处于待审状态');

-- ----------------------------
-- 2、电子围栏表 (biz_fence)
-- 坐标取近似校园周边经纬度结构
-- ----------------------------
INSERT INTO `biz_fence` (`fence_id`, `fence_name`, `polygon_points`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
                                                                                                                                                     (1, '主校门东侧50米A区', '[{"lat":23.085123,"lng":112.481234},{"lat":23.085456,"lng":112.481678},{"lat":23.085111,"lng":112.481888}]', '0', 'admin', '2026-04-19 14:00:00', '', NULL, '人流量最大，限制8个摊位'),
                                                                                                                                                     (2, '北苑宿舍楼下临时划线区', '[{"lat":23.089123,"lng":112.485234},{"lat":23.089456,"lng":112.485678},{"lat":23.089111,"lng":112.485888}]', '0', 'admin', '2026-04-19 14:30:00', '', NULL, '主要针对夜宵时段开放');

-- ----------------------------
-- 3、每日出摊打卡表 (biz_checkin)
-- 关联 vendor_id 和 fence_id
-- ----------------------------
INSERT INTO `biz_checkin` (`checkin_id`, `vendor_id`, `fence_id`, `latitude`, `longitude`, `status`, `create_time`, `checkout_time`) VALUES
                                                                                                                                         (1, 1, 1, 23.085200, 112.481400, '0', '2026-04-21 16:30:00', '2026-04-21 22:30:00'),
                                                                                                                                         (2, 2, 2, 23.089200, 112.485400, '0', '2026-04-21 17:00:00', NULL),
                                                                                                                                         (3, 1, NULL, 23.081000, 112.480000, '1', '2026-04-20 16:00:00', '2026-04-20 18:00:00'); -- 这是一条越界打卡的异常记录

-- ----------------------------
-- 4、巡查与违规记录表 (biz_violation)
-- 假设巡查员 user_id 为 201
-- ----------------------------
INSERT INTO `biz_violation` (`violation_id`, `vendor_id`, `inspector_id`, `violation_type`, `photo_url`, `deduct_score`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
                                                                                                                                                                                                         (1, 2, 201, '卫生不达标', '/profile/violation/v1.jpg', 5, '1', 'volunteer_chen', '2026-04-20 19:30:00', 'admin', '2026-04-21 09:00:00', '收摊时未清理地面竹签和纸巾'),
                                                                                                                                                                                                         (2, 1, 201, '占道经营', '/profile/violation/v2.jpg', 10, '0', 'volunteer_chen', '2026-04-21 18:45:00', '', NULL, '摊车超出了电子围栏A区的划线范围，阻碍学生通行');

-- ----------------------------
-- 5、留言与反馈表 (biz_feedback)
-- 假设留言学生 user_id 为 301, 302
-- ----------------------------
INSERT INTO `biz_feedback` (`feedback_id`, `vendor_id`, `user_id`, `rating`, `content`, `reply_content`, `reply_time`, `create_time`) VALUES
                                                                                                                                          (1, 1, 301, 5, '烧鸭饭分量很足，老板态度很好！', '谢谢同学支持，下次来多给你加点酸梅酱！', '2026-04-21 10:00:00', '2026-04-20 20:15:00'),
                                                                                                                                          (2, 2, 302, 3, '水果捞有点贵，而且西瓜不是很新鲜。', NULL, NULL, '2026-04-21 21:05:00');

-- ----------------------------
-- 6、志愿者申请与档案表 (biz_volunteer)
-- ----------------------------
INSERT INTO `biz_volunteer` (`volunteer_id`, `user_id`, `student_name`, `student_id`, `college`, `phone`, `status`, `patrol_count`, `create_time`, `remark`) VALUES
                                                                                                                                                                 (1, 201, '陈明', '2024001001', '计算机与软件学院', '13900000001', '1', 12, '2026-04-01 10:00:00', '责任心强，多次参与夜间巡查'),
                                                                                                                                                                 (2, 202, '林悦', '2024001002', '经济与管理学院', '13900000002', '1', 5, '2026-04-05 14:20:00', '主要负责周末下午时段的巡查'),
                                                                                                                                                                 (3, NULL, '王浩', '2024001003', '外国语学院', '13900000003', '0', 0, '2026-04-21 08:00:00', '新提交的申请，等待保卫处审核中');