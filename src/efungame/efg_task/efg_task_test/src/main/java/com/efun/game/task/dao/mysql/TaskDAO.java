package com.efun.game.task.dao.mysql;

import com.efun.game.task.entity.po.mysql.TaskPO;

public interface TaskDAO {
    int deleteByPrimaryKey(Long taskId);

    int insert(TaskPO record);

    int insertSelective(TaskPO record);

    TaskPO selectByPrimaryKey(Long taskId);

    int updateByPrimaryKeySelective(TaskPO record);

    int updateByPrimaryKey(TaskPO record);
}