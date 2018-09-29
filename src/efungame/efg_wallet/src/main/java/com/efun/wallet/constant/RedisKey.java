package com.efun.wallet.constant;

/**
 * Created by Administrator on 2018/3/30.
 */
public class RedisKey {
    // 充值同步
    /** BTC同步的最新区块id */
    public static String REDIS_KEY_BTC_BLOCK_REGION_SYNC_LAST_BATCH_ID = "btc.block.region.sync.last.batch.id";
    /** BTC同步的最新区块id */
    public static String REDIS_KEY_BTC_BLOCK_SYNC_LAST_BLOCK_ID = "btc.block.sync.last.block.id";
    /** ETH同步的最新区块id */
    public static String REDIS_KEY_ETH_BLOCK_SYNC_LAST_BLOCK_NUMBER = "eth.block.sync.last.block.number";
    /** ETH同步任务id */
    public static String REDIS_KEY_ETH_BLOCK_SYNC_TASK = "task.eth.block.sync.task.list";
    /** BTC同步任务id */
    public static String REDIS_KEY_BTC_BLOCK_SYNC_TASK = "task.btc.block.sync.task.list";
    /** BTC同步任务id */
    public static String REDIS_KEY_TASK_BTC_REGION_SYNC_TASK_DETAIL = "task.btc.region.sync.task.detail.list";
    /** 同步任务批次区块个数 */
    public static String REDIS_KEY_BLOCK_SYNC_BATCH_COUNT = "block.sync.batch.count";
    /** 区间同步任务交易个数 */
    public static String REDIS_KEY_BTC_REGION_SYNC_TRANSACTION_COUNT = "btc.region.sync.transaction.count";

    /** BTC充值成功的区块确认次数 */
    public static String REDIS_KEY_BTC_RECHARGE_SUCCESSFUL_BLOCK_CONFIRM_COUNT = "btc.recharge.successful.blcok.confirm.count";


}
