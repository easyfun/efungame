package com.efun.game.task.sky.entity.builder;

import com.efun.game.task.sky.entity.Cancel;
import com.efun.game.task.sky.enums.CancelStatus;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/4.
 */
public class CancelBuilder {
    public static Cancel buildTestCancel() {
        Cancel cancel = new Cancel();
        cancel.setStatus(CancelStatus.ACCEPTED);

        Date current = new Date();
        cancel.setAppliedTime(current);
        cancel.setDoneTime(current);

        return cancel;
    }
}
