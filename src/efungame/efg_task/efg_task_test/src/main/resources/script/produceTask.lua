--[[
  增加一个新任务
  @Param ARGV[1] 名称
  @Param ARGV[2] 任务流水号
  @Param ARGV[3] 任务对象string
  @Param ARGV[4] 下次执行时间
--]]

local taskQueueName = ARGV[1]
local taskKey = ARGV[2]
local handler = ARGV[3]
local param = ARGV[4]
local status = ARGV[5]
local retryStrategy = ARGV[6]
local retryInterval = ARGV[7]
local maxRetryTime = ARGV[8]
local nextTime = ARGV[9]
local lastTime = ARGV[10]
local firstTime = ARGV[11]
local createTime = ARGV[12]
local updateTime = ARGV[13]
local executeTime = ARGV[14]

local taskInfoKey = taskQueueName..":"..taskKey
local taskPendingQueue = taskQueueName..".pending"

-- 检查taskKey是否存在，已存在返回失败

-- 任务列表增加一条新任务
redis.call('hmset', taskInfoKey,"taskKey", taskKey, "handler", handler, "param", param, "status", status, "retryStrategy", retryStrategy, "retryInterval", retryInterval, "maxRetryTime", maxRetryTime, "nextTime", nextTime, "lastTime", lastTime, "firstTime", firstTime, "createTime", createTime, "updateTime", updateTime)
-- 待处理任务增加一条新任务
redis.call('zadd', taskPendingQueue, executeTime, taskKey)

return nil