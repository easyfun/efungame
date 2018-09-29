package com.efun.game.task.sky.entity.builder;

import com.efun.game.task.sky.entity.ChildPO;
import com.efun.game.task.sky.enums.ChildStatus;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/4.
 */
public class ChildPOBuilder {
    public static ChildPO buildTestChildPO() {
        ChildPO po = new ChildPO();
        po.setName("testChild");
        po.setStatus(ChildStatus.ACCEPTED);
        po.setRetriedTimes(3);
        Date current = new Date();
        po.setCreatedTime(current);
        po.setFirstTime(current);
        po.setLastTime(current);
        po.setUpdatedTime(current);
        po.setPausedTime(current);
        po.setResumeTime(current);
        po.setOperation(ChildOperationBuilder.buildTestChildOperation());
        return po;
    }
}
