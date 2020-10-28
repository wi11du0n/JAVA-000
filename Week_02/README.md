# Week 02 2020/10/22 & 10/24

## 课后作业
### 10/22 周四
#### 1.使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

##### 串行 GC
```text
java -Xmx512m -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:8472
Heap
 def new generation   total 157248K, used 5863K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a05b9c18, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
  to   space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
 tenured generation   total 349568K, used 345256K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 349568K,  98% used [0x00000007aaaa0000, 0x00000007bfbca0d0, 0x00000007bfbca200, 0x00000007c0000000)

java -Xmx1g -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:9240
Heap
 def new generation   total 254592K, used 9190K [0x0000000780000000, 0x0000000791440000, 0x0000000795550000)
  eden space 226304K,   4% used [0x0000000780000000, 0x00000007808f9808, 0x000000078dd00000)
  from space 28288K,   0% used [0x000000078dd00000, 0x000000078dd00000, 0x000000078f8a0000)
  to   space 28288K,   0% used [0x000000078f8a0000, 0x000000078f8a0000, 0x0000000791440000)
 tenured generation   total 565744K, used 339445K [0x0000000795550000, 0x00000007b7dcc000, 0x00000007c0000000)
   the space 565744K,  59% used [0x0000000795550000, 0x00000007aa0cd760, 0x00000007aa0cd800, 0x00000007b7dcc000)

java -Xmx2g -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:9070
Heap
 def new generation   total 245888K, used 36456K [0x0000000740000000, 0x0000000750ac0000, 0x000000076aaa0000)
  eden space 218624K,   4% used [0x0000000740000000, 0x00000007408fa060, 0x000000074d580000)
  from space 27264K, 100% used [0x000000074f020000, 0x0000000750ac0000, 0x0000000750ac0000)
  to   space 27264K,   0% used [0x000000074d580000, 0x000000074d580000, 0x000000074f020000)
 tenured generation   total 546264K, used 511804K [0x000000076aaa0000, 0x000000078c016000, 0x00000007c0000000)
   the space 546264K,  93% used [0x000000076aaa0000, 0x0000000789e6f0a8, 0x0000000789e6f200, 0x000000078c016000)

java -Xmx4g -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:9526
Heap
 def new generation   total 257920K, used 9435K [0x00000006c0000000, 0x00000006d17d0000, 0x0000000715550000)
  eden space 229312K,   4% used [0x00000006c0000000, 0x00000006c0936e50, 0x00000006cdff0000)
  from space 28608K,   0% used [0x00000006cdff0000, 0x00000006cdff0000, 0x00000006cfbe0000)
  to   space 28608K,   0% used [0x00000006cfbe0000, 0x00000006cfbe0000, 0x00000006d17d0000)
 tenured generation   total 572940K, used 343763K [0x0000000715550000, 0x00000007384d3000, 0x00000007c0000000)
   the space 572940K,  59% used [0x0000000715550000, 0x000000072a504d48, 0x000000072a504e00, 0x00000007384d3000)

```
##### 并行 GC 
```text
java -Xmx512m -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:6793
Heap
 PSYoungGen      total 116736K, used 3090K [0x00000007b5580000, 0x00000007bfe00000, 0x00000007c0000000)
  eden space 64000K, 4% used [0x00000007b5580000,0x00000007b58848f8,0x00000007b9400000)
  from space 52736K, 0% used [0x00000007b9400000,0x00000007b9400000,0x00000007bc780000)
  to   space 51200K, 0% used [0x00000007bcc00000,0x00000007bcc00000,0x00000007bfe00000)
 ParOldGen       total 349696K, used 302964K [0x00000007a0000000, 0x00000007b5580000, 0x00000007b5580000)
  object space 349696K, 86% used [0x00000007a0000000,0x00000007b27dd1a8,0x00000007b5580000)

java -Xmx1g -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:7425
Heap
 PSYoungGen      total 232960K, used 47359K [0x00000007aab00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 116736K, 4% used [0x00000007aab00000,0x00000007ab02ecb0,0x00000007b1d00000)
  from space 116224K, 36% used [0x00000007b8e80000,0x00000007bb7911f8,0x00000007c0000000)
  to   space 116224K, 0% used [0x00000007b1d00000,0x00000007b1d00000,0x00000007b8e80000)
 ParOldGen       total 456192K, used 292481K [0x0000000780000000, 0x000000079bd80000, 0x00000007aab00000)
  object space 456192K, 64% used [0x0000000780000000,0x0000000791da07c0,0x000000079bd80000)

java -Xmx2g -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:9186
Heap
 PSYoungGen      total 476672K, used 205939K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 281088K, 18% used [0x0000000795580000,0x0000000798853360,0x00000007a6800000)
  from space 195584K, 78% used [0x00000007b4100000,0x00000007bd749b18,0x00000007c0000000)
  to   space 208896K, 0% used [0x00000007a6800000,0x00000007a6800000,0x00000007b3400000)
 ParOldGen       total 519168K, used 310456K [0x0000000740000000, 0x000000075fb00000, 0x0000000795580000)
  object space 519168K, 59% used [0x0000000740000000,0x0000000752f2e278,0x000000075fb00000)

java -Xmx4g -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:9786
Heap
 PSYoungGen      total 954880K, used 887253K [0x000000076ab00000, 0x00000007b1f80000, 0x00000007c0000000)
  eden space 780800K, 96% used [0x000000076ab00000,0x0000000798dbbda0,0x000000079a580000)
  from space 174080K, 75% used [0x00000007a5b80000,0x00000007adb39960,0x00000007b0580000)
  to   space 186368K, 0% used [0x000000079a580000,0x000000079a580000,0x00000007a5b80000)
 ParOldGen       total 498176K, used 285113K [0x00000006c0000000, 0x00000006de680000, 0x000000076ab00000)
  object space 498176K, 57% used [0x00000006c0000000,0x00000006d166e420,0x00000006de680000)
```
##### CMS GC
```text
java -Xmx512m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:8546
Heap
[CMS-concurrent-mark-start]
 par new generation   total 157248K, used 5653K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a05857c0, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
  to   space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
 concurrent mark-sweep generation total 349568K, used 335920K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)

java -Xmx1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:8084
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
Heap
 par new generation   total 78656K, used 13452K [0x0000000780000000, 0x0000000785550000, 0x0000000794cc0000)
  eden space 69952K,   6% used [0x0000000780000000, 0x00000007804a35d8, 0x0000000784450000)
  from space 8704K,  99% used [0x0000000784cd0000, 0x000000078554fd20, 0x0000000785550000)
  to   space 8704K,   0% used [0x0000000784450000, 0x0000000784450000, 0x0000000784cd0000)
 concurrent mark-sweep generation total 707840K, used 698443K [0x0000000794cc0000, 0x00000007c0000000, 0x00000007c0000000)

java -Xmx2g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:8191
Heap
 par new generation   total 78656K, used 11653K [0x0000000740000000, 0x0000000745550000, 0x0000000754cc0000)
  eden space 69952K,   4% used [0x0000000740000000, 0x00000007402e1790, 0x0000000744450000)
  from space 8704K,  99% used [0x0000000744cd0000, 0x000000074554fe80, 0x0000000745550000)
  to   space 8704K,   0% used [0x0000000744450000, 0x0000000744450000, 0x0000000744cd0000)
 concurrent mark-sweep generation total 730824K, used 730045K [0x0000000754cc0000, 0x0000000781672000, 0x00000007c0000000)

java -Xmx4g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:8402
Heap
 par new generation   total 78656K, used 11567K [0x00000006c0000000, 0x00000006c5550000, 0x00000006d4cc0000)
  eden space 69952K,   4% used [0x00000006c0000000, 0x00000006c02cc190, 0x00000006c4450000)
  from space 8704K,  99% used [0x00000006c4cd0000, 0x00000006c554fba0, 0x00000006c5550000)
  to   space 8704K,   0% used [0x00000006c4450000, 0x00000006c4450000, 0x00000006c4cd0000)
 concurrent mark-sweep generation total 717152K, used 716275K [0x00000006d4cc0000, 0x0000000700918000, 0x00000007c0000000)

```

##### G1 GC
```text
java -Xmx512m -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:7944
Heap
 garbage-first heap   total 524288K, used 384314K [0x00000007a0000000, 0x00000007a0101000, 0x00000007c0000000)
  region size 1024K, 5 young (5120K), 4 survivors (4096K)

java -Xmx1g -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:7418
Heap
 garbage-first heap   total 1020928K, used 543964K [0x0000000780000000, 0x0000000780101f28, 0x00000007c0000000)
  region size 1024K, 118 young (120832K), 3 survivors (3072K)

java -Xmx2g -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:6487
Heap
 garbage-first heap   total 1835008K, used 538608K [0x0000000740000000, 0x0000000740103800, 0x00000007c0000000)
  region size 1024K, 83 young (84992K), 41 survivors (41984K)

java -Xmx4g -XX:+UseG1GC -XX:+PrintGCDetails GCLogAnalysis
执行结束!共生成对象次数:5511
Heap
 garbage-first heap   total 3535872K, used 534694K [0x00000006c0000000, 0x00000006c0106be8, 0x00000007c0000000)
  region size 1024K, 131 young (134144K), 31 survivors (31744K)

```

#### 2.使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
全部使用第二次压测数据

##### 并行 GC
```text
java -Xmx1g -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    48.18ms   67.12ms   1.24s    93.99%
    Req/Sec   338.80    176.51     1.36k    70.86%
  349078 requests in 30.20s, 41.67MB read
  Socket errors: connect 0, read 750, write 0, timeout 0
Requests/sec:  11559.07
Transfer/sec:      1.38MB

java -Xmx4g -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    40.06ms   35.67ms 785.15ms   90.08%
    Req/Sec   353.34    198.91     1.64k    70.76%
  361575 requests in 30.11s, 43.16MB read
  Socket errors: connect 0, read 695, write 0, timeout 594
Requests/sec:  12007.13
Transfer/sec:      1.43MB
```

##### CMS GC
```text
java -Xmx1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    54.91ms   66.25ms   1.02s    91.06%
    Req/Sec   313.70    144.08     1.38k    69.15%
  328222 requests in 30.10s, 39.18MB read
  Socket errors: connect 0, read 781, write 0, timeout 0
Requests/sec:  10904.23
Transfer/sec:      1.30MB

java -Xmx4g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar

wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    17.39ms   54.14ms 934.78ms   98.27%
    Req/Sec   383.72    239.52     1.64k    76.54%
  449449 requests in 30.10s, 53.66MB read
  Socket errors: connect 389, read 119, write 11, timeout 0
Requests/sec:  14932.15
Transfer/sec:      1.78MB
#
```

##### G1 GC
```text
java -Xmx1g -XX:+UseG1GC -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    62.41ms  171.40ms   1.45s    96.13%
    Req/Sec   381.42    157.58     1.42k    74.01%
  426753 requests in 30.10s, 50.95MB read
  Socket errors: connect 0, read 944, write 0, timeout 0
Requests/sec:  14178.18
Transfer/sec:      1.69MB

JDK 11
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    17.55ms   28.81ms 389.46ms   96.47%
    Req/Sec   348.59    114.79     1.06k    73.74%
  401487 requests in 30.10s, 47.93MB read
  Socket errors: connect 389, read 79, write 0, timeout 0
Requests/sec:  13338.59
Transfer/sec:      1.59MB

java -Xmx4g -XX:+UseG1GC -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
wrk -t40 -d30s -c600  http://localhost:8088/api/hello
Running 30s test @ http://localhost:8088/api/hello
  40 threads and 600 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    56.31ms  106.53ms 994.82ms   95.13%
    Req/Sec   349.15    138.39     2.51k    72.03%
  375094 requests in 30.10s, 44.78MB read
  Socket errors: connect 0, read 729, write 0, timeout 0
Requests/sec:  12462.91
Transfer/sec:      1.49MB
```

#### 3.选做
略

#### 4.根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。
配置
  MacBook Pro (13-inch, 2017, Two Thunderbolt 3 ports)
  2.3 GHz 双核Intel Core i5
  16 GB 2133 MHz LPDDR3

##### GCLogAnalysis
|GC|512M|1G|2G|4G|
|--|--|--|--|--|
|SerialGC|8472|9240|9070|9526|
|ParallelGC|6792|7425|9186|9786|
|CMSGC|8546|9084|8191|8402|
|G1GC|7944|7418|6487|5511|
|G1GC(JDK11)|8409|11000|10805|10998|

默认用的 JDK8 跑的
1. SerialGC 在小内存下表现不错，达到 1G 时，遇到瓶颈。
2. ParallelGC 在内存增长时能充分利用，小内存下表现不好。
3. CMS 在 512、1G 表现良好，大内存下不佳。
4. Mac 上的 G1 在 JDK8 表现不佳，内存越大越差，JDK 11 上表现优异。

##### Gateway API Server
接口实现简单，没有大对象和复杂引用关系，基本只用到了 Minor GC。

|GC|1G Avg|1G Stdev|1G Max|1G Total|4G Avg|4G Stdev|4G Max|4G Total|
|--|--|--|--|--|--|--|--|--|
|ParallelGC|48.2ms 338.8/s|67.1ms 176.5/s|1.24s 1.36k/s|11559|40.1ms 353.3/s|35.67ms 198.91/s|785.2ms 1.64k/s|12007|
|CMSGC|54.9ms 313.7/s|66.3ms 144.1/s|1.02s 1.38k/s|10904|17.4ms 383.7/s|51.1ms 239.52/s|934.78ms 1.64k/s|14932|
|G1GC|62.4ms 381.4/s|171.4ms 157.6/s|1.45s 1.42k/s|14178|56.3ms 349.2|106.5ms 138.4/s|994.82ms 2.51k/s|12462|
|G1GC(JDK11)|17.6ms 348.6/s|28.8ms 114.79/s|389.5ms 1.06k/s|13338|14.8ms 344/s|11.81ms 93/s|282.84ms 1.27k/s|13619|

相同 GC 下，内存从 1g 到 4g 延时和吞吐都会变好，并行和CMS差别不大。G1 在 JDK 11 下表现最优。

##### GC 总结
Mac OS 上 JDK 8 的 G1 并不完善，但是在 JDK 11 下表现优异。G1 的响应延迟更低，CMS 和 Parallel 的差异在两个事例中并不大。1G 时 Parallel 更好，4G 时 CMS 更好。

具体业务系统中，还是要根据业务延迟、吞吐要求，以及机器容量、内存对象分布来选择适合的 GC。并且配置合适的 GC 参数，形成标准。在 JDK 11 中优先使用 G1，在较小内存时使用 Parallel。

### 10/24 周六

见 [OkHttpTest.java](./src/main/java/org/example/OkHttpTest.java)