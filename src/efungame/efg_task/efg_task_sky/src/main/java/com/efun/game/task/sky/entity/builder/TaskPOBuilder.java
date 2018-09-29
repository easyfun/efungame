package com.efun.game.task.sky.entity.builder;

import com.efun.game.common.utils.DateUtil;
import com.efun.game.common.utils.RedisUtil;
import com.efun.game.task.sky.Task;
import com.efun.game.task.sky.entity.ChildPO;
import com.efun.game.task.sky.entity.TaskPO;
import com.efun.game.task.sky.enums.RetryStrategy;
import com.efun.game.task.sky.enums.TaskStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Administrator on 2018/3/27.
 */
public class TaskPOBuilder {
    public static final String CANCEL = "cancel:";
    public static final String CHILD = "child:";
    public static final String CHILD_OPERATION = "operation:";

    public static Map<String, String> buildTaskPORedisMap(Task task) {
        TaskPO po = buildTaskPO(task);
        return buildTaskPORedisMap(po);
    }

    public static Map<String, String> buildTaskPORedisMap(TaskPO po) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", RedisUtil.buildRedisString(po.getKey()));
        map.put("param", RedisUtil.buildRedisString(po.getParam()));
        map.put("handler", RedisUtil.buildRedisString(po.getHandler()));
        map.put("retryStrategy", po.getRetryStrategy().name());
        map.put("retryInterval", String.valueOf(po.getRetryInterval()));
        map.put("maxRetryTimes", String.valueOf(po.getMaxRetryTimes()));
        map.put("status", po.getStatus().name());
        map.put("retriedTimes", String.valueOf(po.getRetriedTimes()));
        map.put("createdTime", DateUtil.formatDate(po.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("firstTime", DateUtil.formatDate(po.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("lastTime", DateUtil.formatDate(po.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("nextTime", DateUtil.formatDate(po.getNextTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("doneTime", DateUtil.formatDate(po.getDoneTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("updatedTime", DateUtil.formatDate(po.getUpdatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));

        if (null != po.getCancel()) {
            map.put(CANCEL+"status", po.getCancel().getStatus().name());
            map.put(CANCEL+"appliedTime", DateUtil.formatDate(po.getCancel().getAppliedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            map.put(CANCEL+"doneTime", DateUtil.formatDate(po.getCancel().getDoneTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        }

        if (!CollectionUtils.isEmpty(po.getChilds())) {
            for (int i=0; i< po.getChilds().size(); i++) {
                ChildPO child = po.getChilds().get(i);
                String key = CHILD + child.getName() + ":";
                map.put(key+"name", child.getName());
                map.put(key+"status", child.getStatus().name());
                map.put(key+"retriedTimes", String.valueOf(child.getRetriedTimes()));
                map.put(key+"createdTime", DateUtil.formatDate(child.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                map.put(key+"firstTime", DateUtil.formatDate(child.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                map.put(key+"lastTime", DateUtil.formatDate(child.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                map.put(key+"updatedTime", DateUtil.formatDate(child.getUpdatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                map.put(key+"pausedTime", DateUtil.formatDate(child.getPausedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                map.put(key+"resumeTime", DateUtil.formatDate(child.getResumeTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));

                if (null != child.getOperation()) {
                    String operationKey = key + CHILD_OPERATION;
                    map.put(operationKey+"mode", child.getOperation().getMode().name());
                    map.put(operationKey+"appliedTime", DateUtil.formatDate(child.getOperation().getAppliedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                    map.put(operationKey+"doneTime", DateUtil.formatDate(child.getOperation().getDoneTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                }
            }
        }

        map.put("currentPausePoint", RedisUtil.buildRedisString(po.getCurrentPausePoint()));
        return map;
    }

    public static TaskPO buildTaskPO(Task task) {
        TaskPO po = new TaskPO();
        po.setKey(task.getKey());
        po.setParam(task.getParam());
        po.setHandler(task.getHandler());
        po.setRetryStrategy(task.getRetryStrategy());
        po.setRetryInterval(task.getRetryInterval());
        po.setMaxRetryTimes(task.getMaxRetryTimes());
        po.setStatus(TaskStatus.ACCEPTED);
        po.setRetriedTimes(0);
        Date current = new Date();
        po.setCreatedTime(current);
        po.setFirstTime(null);
        po.setLastTime(null);
        po.setNextTime(current);
        po.setDoneTime(null);
        po.setUpdatedTime(current);
        po.setChilds(null);
        po.setCancel(null);
        po.setCurrentPausePoint(null);
        return po;
    }

    public static TaskPO buildTestTaskPO() {
        TaskPO po = new TaskPO();
        po.setKey(String.valueOf(System.nanoTime()));
        po.setParam("");
        po.setHandler("test99");
        po.setRetryStrategy(RetryStrategy.NORMAL);
        po.setRetryInterval(60*1000);
        po.setMaxRetryTimes(10);
        po.setStatus(TaskStatus.RETRYING);
        po.setRetriedTimes(0);
        Date current = new Date();
        po.setCreatedTime(current);
        po.setFirstTime(null);
        po.setLastTime(null);
        po.setNextTime(current);
        po.setDoneTime(null);
        po.setUpdatedTime(current);

        List<ChildPO> childs = new ArrayList<>();
        childs.add(ChildPOBuilder.buildTestChildPO());
        po.setChilds(childs);

        po.setCancel(CancelBuilder.buildTestCancel());
        po.setCurrentPausePoint("testChild");
        return po;
    }
}
