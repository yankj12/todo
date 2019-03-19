CREATE
    TABLE t_task
    (
        id BIGINT NOT NULL AUTO_INCREMENT,
        title VARCHAR(100) default '',
        emergencyFlag VARCHAR(2) default '0',
        content VARCHAR(255) default '',
        userCode VARCHAR(20) default '',
        assignTo VARCHAR(20) default '',
        deadLine DATETIME,
        startTime DATETIME,
        endTime DATETIME,
        finishFlag VARCHAR(2) default '0',
        remark VARCHAR(255) default '',
        validStatus VARCHAR(2) default '1',
        insertTime DATETIME,
        updateTime DATETIME,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
