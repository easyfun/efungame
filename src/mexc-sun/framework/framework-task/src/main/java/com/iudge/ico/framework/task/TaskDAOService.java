package com.iudge.ico.framework.task;

import com.alibaba.fastjson.JSON;
import com.iudge.ico.framework.task.dao.TaskDAO;
import com.iudge.ico.framework.task.entity.TaskPO;
import com.iudge.ico.framework.task.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDAOService.class);

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
        if (taskPO.getParam().length() > 2048) {
            LOGGER.error("param字段太长截断, taskPO={}", JsonUtil.toJSONString(taskPO));
            taskPO.setParam(taskPO.getParam().substring(0, 2047));
        }
        taskDAO.insertSelective(taskPO);
    }

}
