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

