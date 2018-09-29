package com.mexc.sun.framework.task;

import com.mexc.sun.framework.task.dao.TaskDAO;
import com.mexc.sun.framework.task.entity.TaskPO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by easyfun on 2018/5/16.
 */
@Component
public class TaskDAOService {
    @Resource
    private TaskDAO taskDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertTaskToDB(TaskPO taskPO) {
        List<TaskPO> existeds = taskDAO.selectListByTaskKey(taskPO.getTaskKey());

        for (TaskPO t : existeds) {
            if (0 == t.getUpdatedTime().compareTo(taskPO.getUpdatedTime())) {
                return;
            }
        }

        taskPO.setInsertedTime(new Date());
        taskDAO.insertSelective(taskPO);
    }

}
