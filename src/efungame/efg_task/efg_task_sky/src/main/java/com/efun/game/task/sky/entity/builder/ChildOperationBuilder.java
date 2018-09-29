package com.efun.game.task.sky.entity.builder;

import com.efun.game.task.sky.entity.ChildOperation;
import com.efun.game.task.sky.enums.ChildOperationMode;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/4.
 */
public class ChildOperationBuilder {
    public static ChildOperation buildTestChildOperation() {
        ChildOperation operation = new ChildOperation();
        operation.setMode(ChildOperationMode.CANCEL);

        Date current = new Date();
        operation.setAppliedTime(current);
        operation.setDoneTime(current);
        return operation;
    }
}
