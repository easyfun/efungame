package com.efun.game.task.dao.mysql;

import com.efun.game.task.entity.po.mysql.ChildTaskPO;

public interface ChildTaskDAO {
    int deleteByPrimaryKey(Long id);

    int insert(ChildTaskPO record);

    int insertSelective(ChildTaskPO record);

    ChildTaskPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChildTaskPO record);

    int updateByPrimaryKey(ChildTaskPO record);
}