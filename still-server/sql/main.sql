-- ----------------------------
-- 1、摊贩档案表 (记录合规摊贩的基本信息)
-- 说明：摊主也是系统的 user，所以在 sys_user 里建账号，这里存业务信息
-- ----------------------------
DROP TABLE IF EXISTS biz_vendor;
CREATE TABLE biz_vendor (
                            vendor_id         bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '摊贩ID',
                            user_id           bigint(20)      NOT NULL                   COMMENT '关联sys_user表的账号ID',
                            vendor_name       varchar(50)     NOT NULL                   COMMENT '摊主真实姓名',
                            phone             varchar(20)     NOT NULL                   COMMENT '联系电话',
                            goods_category    varchar(50)     DEFAULT ''                 COMMENT '主营品类(如: 烧烤, 水果)',
                            health_cert_url   varchar(255)    DEFAULT ''                 COMMENT '健康证图片地址',
                            credit_score      int(4)          DEFAULT 100                COMMENT '信用积分(满分100)',
                            status            char(1)         DEFAULT '0'                COMMENT '审核状态（0待审 1通过 2驳回 3拉黑）',
                            del_flag          char(1)         DEFAULT '0'                COMMENT '删除标志（0存在 2删除）',
                            create_by         varchar(64)     DEFAULT ''                 COMMENT '创建者',
                            create_time       datetime                                   COMMENT '创建时间',
                            update_by         varchar(64)     DEFAULT ''                 COMMENT '更新者',
                            update_time       datetime                                   COMMENT '更新时间',
                            remark            varchar(500)    DEFAULT NULL               COMMENT '备注(可存拒审原因)',
                            PRIMARY KEY (vendor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='摊贩档案表';

-- ----------------------------
-- 2、电子围栏表 (存储合规摆卖区域)
-- ----------------------------
DROP TABLE IF EXISTS biz_fence;
CREATE TABLE biz_fence (
                           fence_id          bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '围栏ID',
                           fence_name        varchar(50)     NOT NULL                   COMMENT '围栏名称(如: 正门左侧50米A区)',
                           polygon_points    text            NOT NULL                   COMMENT '多边形坐标集(JSON数组格式存经纬度)',
                           status            char(1)         DEFAULT '0'                COMMENT '状态（0启用 1停用）',
                           create_by         varchar(64)     DEFAULT ''                 COMMENT '创建者',
                           create_time       datetime                                   COMMENT '创建时间',
                           update_by         varchar(64)     DEFAULT ''                 COMMENT '更新者',
                           update_time       datetime                                   COMMENT '更新时间',
                           remark            varchar(500)    DEFAULT NULL               COMMENT '备注',
                           PRIMARY KEY (fence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子围栏区域表';

-- ----------------------------
-- 3、每日出摊打卡表 (动态位置与时间记录)
-- ----------------------------
DROP TABLE IF EXISTS biz_checkin;
CREATE TABLE biz_checkin (
                             checkin_id        bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '打卡ID',
                             vendor_id         bigint(20)      NOT NULL                   COMMENT '摊贩ID',
                             fence_id          bigint(20)      DEFAULT NULL               COMMENT '所在围栏ID',
                             latitude          decimal(10, 6)  NOT NULL                   COMMENT '打卡纬度',
                             longitude         decimal(10, 6)  NOT NULL                   COMMENT '打卡经度',
                             status            char(1)         DEFAULT '0'                COMMENT '状态（0合规经营 1越界违规）',
                             create_time       datetime        NOT NULL                   COMMENT '打卡出摊时间',
                             checkout_time     datetime        DEFAULT NULL               COMMENT '收摊时间',
                             PRIMARY KEY (checkin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日出摊打卡表';

-- ----------------------------
-- 4、巡查与违规记录表 (志愿者抓现行)
-- ----------------------------
DROP TABLE IF EXISTS biz_violation;
CREATE TABLE biz_violation (
                               violation_id      bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '记录ID',
                               vendor_id         bigint(20)      NOT NULL                   COMMENT '违规摊贩ID',
                               inspector_id      bigint(20)      NOT NULL                   COMMENT '巡查员/志愿者ID(关联sys_user)',
                               violation_type    varchar(20)     NOT NULL                   COMMENT '违规类型(占道/卫生差/无证)',
                               photo_url         varchar(500)    DEFAULT ''                 COMMENT '现场照片(多张逗号分隔)',
                               deduct_score      int(4)          DEFAULT 0                  COMMENT '扣除积分值',
                               status            char(1)         DEFAULT '0'                COMMENT '处理状态（0待处理 1已扣分确认 2驳回撤销）',
                               create_by         varchar(64)     DEFAULT ''                 COMMENT '上报人',
                               create_time       datetime                                   COMMENT '上报时间',
                               update_by         varchar(64)     DEFAULT ''                 COMMENT '处理人(保卫处老师)',
                               update_time       datetime                                   COMMENT '处理时间',
                               remark            varchar(500)    DEFAULT NULL               COMMENT '违规详细描述',
                               PRIMARY KEY (violation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='巡查与违规记录表';

-- ----------------------------
-- 5、留言与反馈表 (单层评论机制)
-- ----------------------------
DROP TABLE IF EXISTS biz_feedback;
CREATE TABLE biz_feedback (
                              feedback_id       bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '留言ID',
                              vendor_id         bigint(20)      NOT NULL                   COMMENT '所属摊贩ID',
                              user_id           bigint(20)      NOT NULL                   COMMENT '留言学生ID(可为空代表匿名)',
                              rating            int(1)          NOT NULL                   COMMENT '评分(1-5星)',
                              content           varchar(500)    NOT NULL                   COMMENT '留言内容',
                              reply_content     varchar(500)    DEFAULT NULL               COMMENT '摊主回复内容',
                              reply_time        datetime        DEFAULT NULL               COMMENT '回复时间',
                              create_time       datetime                                   COMMENT '留言时间',
                              PRIMARY KEY (feedback_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='留言与反馈表';

-- ----------------------------
-- 6、志愿者申请与档案表
-- ----------------------------
DROP TABLE IF EXISTS biz_volunteer;
CREATE TABLE biz_volunteer (
                               volunteer_id      bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '志愿者ID',
                               user_id           bigint(20)      DEFAULT NULL               COMMENT '关联sys_user(审核通过后生成并绑定)',
                               student_name      varchar(50)     NOT NULL                   COMMENT '学生姓名',
                               student_id        varchar(20)     NOT NULL                   COMMENT '学号',
                               college           varchar(50)     DEFAULT ''                 COMMENT '所属学院(如: 财经学院)',
                               phone             varchar(20)     NOT NULL                   COMMENT '联系电话',
                               status            char(1)         DEFAULT '0'                COMMENT '状态（0待审 1通过 2驳回 3清退）',
                               patrol_count      int(4)          DEFAULT 0                  COMMENT '累计巡查次数(用于评优)',
                               create_time       datetime                                   COMMENT '申请时间',
                               remark            varchar(500)    DEFAULT NULL               COMMENT '备注',
                               PRIMARY KEY (volunteer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者申请与档案表';

-- ----------------------------
-- 微信小程序 C端用户表
-- ----------------------------
DROP TABLE IF EXISTS biz_wx_user;
CREATE TABLE biz_wx_user (
                             wx_user_id      bigint(20)      NOT NULL AUTO_INCREMENT    COMMENT '小程序用户主键',
                             openid          varchar(64)     NOT NULL                   COMMENT '微信OpenID(唯一标识)',
                             unionid         varchar(64)     DEFAULT ''                 COMMENT '微信UnionID(跨应用标识,备用)',
                             nickname        varchar(50)     DEFAULT '微信用户'         COMMENT '用户昵称',
                             avatar_url      varchar(255)    DEFAULT ''                 COMMENT '头像地址',
                             phone           varchar(20)     DEFAULT ''                 COMMENT '绑定的手机号',
                             user_type       char(1)         DEFAULT '0'                COMMENT '身份类型(0:普通学生 1:摊贩 2:志愿者)',
                             status          char(1)         DEFAULT '0'                COMMENT '账号状态(0正常 1封禁)',
                             create_time     datetime                                   COMMENT '首次授权时间',
                             last_login_time datetime                                   COMMENT '最后登录时间',
                             PRIMARY KEY (wx_user_id),
                             UNIQUE KEY idx_openid (openid) -- 保证OpenID唯一
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序微信用户表';

-- ----------------------------
-- 13、摊贩二维码表 (短标识到跳转目标的映射)
-- 说明：用于管理商贩的推广/扫码二维码，短标识作为外部暴露的主键
-- ----------------------------
DROP TABLE IF EXISTS biz_vendor_qrcode;
CREATE TABLE biz_vendor_qrcode (
                             short_id          varchar(64)     NOT NULL                   COMMENT '二维码短标识(主键/外部短码)',
                             target_type       int(4)          NOT NULL DEFAULT 1        COMMENT '跳转类型（1:商贩主页,2:支付页,3:投诉页,4:监管页）',
                             vendor_id         bigint(20)     DEFAULT NULL              COMMENT '对应的商贩ID(关联biz_vendor.vendor_id)',
                             status            int(4)          DEFAULT 1                 COMMENT '二维码状态（1:正常,0:失效/封禁）',
                             create_time       datetime                                   COMMENT '创建时间',
                             update_time       datetime                                   COMMENT '更新时间',
                             PRIMARY KEY (short_id),
                             KEY idx_vendor_id (vendor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='摊贩二维码管理表';
