package com.efun.game.task.dao.mysql;

import com.efun.game.task.entity.po.mysql.TaskChangePO;

public interface TaskChangeDAO {
    int deleteByPrimaryKey(Long id);

    int insert(TaskChangePO record);

    int insertSelective(TaskChangePO record);

    TaskChangePO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskChangePO record);

    int updateByPrimaryKey(TaskChangePO record);
}