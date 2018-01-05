###Mysql性能测试

####服务器配置
CPU：
内存：
带宽：


mybatis:
连接池druid,(10,100)
100线程，每个线程insert 2000次
 total=200000, success+fail=200000
 success=200000, fail=0
 elapsed time = 36169.201604 ms, 36169201604 ns

连接池druid,(100,200)
100线程，每个线程insert 2000次
 total=200000, success+fail=200000
 success=200000, fail=0
 elapsed time = 34912.668144 ms, 34912668144 ns


jdbc:
100线程，每个线程insert 2000次
 total=200000, success+fail=200000
 success=200000, fail=0
 elapsed time = 10340.233713 ms, 10340233713 ns

druid:
连接池druid,(10,100)
100线程，每个线程insert 2000次
 total=200000, success+fail=200000
 success=200000, fail=0
 elapsed time = 20474.54424 ms, 20474544240 ns

连接池druid,(100,200)
100线程，每个线程insert 2000次
 total=200000, success+fail=200000
 success=200000, fail=0
 elapsed time = 19403.864168 ms, 19403864168 ns


###redis性能测试

###get,set性能测试比较
10字节
redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -t get,set -q -d 10
SET: 54466.23 requests per second
GET: 54914.88 requests per second

100字节
redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -t get,set -q -d 100
SET: 52854.13 requests per second
GET: 51387.46 requests per second

300字节
redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -
t get,set -q -d 300
SET: 29061.32 requests per second
GET: 31766.20 requests per second

1000字节
redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -t get,set -q -d 1000
SET: 11054.61 requests per second
GET: 11460.00 requests per second

2000字节
redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -t get,set -q -d 2000
SET: 5643.34 requests per second
GET: 5754.73 requests per second

###hset,hget,hmset,hmget性能测试
hset,hmset，windows不生效，linux生效
./redis-benchmark -h 10.193.1.22 -p 6379 -a 123456 -n 500000 hset myhash rand_int rand_int

====== hmset myhash 201801051644 success 201801051645 2018-01-05 14:39:58 201801051646 2018-01-05 14:39:58 ======
  500000 requests completed in 10.07 seconds
  50 parallel clients
  3 bytes payload
  keep alive: 1

72.59% <= 1 milliseconds
99.23% <= 2 milliseconds
99.71% <= 3 milliseconds
99.88% <= 4 milliseconds
99.97% <= 5 milliseconds
99.99% <= 6 milliseconds
100.00% <= 7 milliseconds
100.00% <= 8 milliseconds
100.00% <= 8 milliseconds
49652.43 requests per second