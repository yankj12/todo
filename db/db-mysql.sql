CREATE
    TABLE gtd_task
    (
        id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
        title VARCHAR(100) default '' COMMENT '标题',
        emergencyFlag VARCHAR(2) default '0' COMMENT '紧急状态',
        content VARCHAR(255) default '' COMMENT '内容',
        userCode VARCHAR(20) default '' COMMENT '用户编码',
        assignTo VARCHAR(20) default '' COMMENT '指派给',
        deadLine DATETIME COMMENT '截止日期',
        startTimeExpected DATETIME COMMENT '预期开始时间',
        endTimeExpected DATETIME COMMENT '预期完成时间',
        startTimeActual DATETIME COMMENT '实际开始时间',
        endTimeActual DATETIME COMMENT '实际完成时间',
        finishFlag VARCHAR(2) default '0' COMMENT '完成状态',
        remark VARCHAR(255) default '' COMMENT '备注',
        validStatus VARCHAR(2) default '1' COMMENT '有效状态',
        insertTime DATETIME COMMENT '插入时间',
        updateTime DATETIME COMMENT '修改时间',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

    
    
CREATE
    TABLE gtd_bill
    (
        id BIGINT NOT NULL AUTO_INCREMENT,
        name VARCHAR(100) default '',
        billCode VARCHAR(20) default '',
        userCode VARCHAR(20) default '',
        url VARCHAR(255) default '',
        systemDefaultFlag VARCHAR(2) default '0',
        remark VARCHAR(255) default '',
        orderNo int,
        validStatus VARCHAR(2) default '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
