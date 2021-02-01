# 毕业总结

4 个月来跟随秦老师一起学习了 Java 和分布式的知识，对自己的知识进行了查缺补漏，形成了体系，受益匪浅。

这些庞杂的知识，知道了不一定记住了，记住了不一定能很好的应用。自己在作业和应用端还有许多需要做的事情。在后续的时间里还会时常温习，也会结合工作多加练习。把知识学会，用深，真正的融汇贯通，掌握各个技术的应用和解决技术难题。

自己也买了很多极客时间的课程，但看完的只用四分之一。今年的目标就是把已经买的课程学完，然后多加练习，进行产出，形成自己的认知和影响力。

最后谢谢秦老师、助教和班班们的辛苦付出。

## JVM

### 知识点汇总

Java 是面向对象的静态语言，基于字节码和虚拟机（VM）技术实现了二进制跨平台；并基于 JMM（Java 内存模型）和 GC（垃圾回收，动态管理内存）对内存进行管理。

#### 字节码

    一个字节（byte）指令组成。包含栈操作指令、程序流程控制指令、对象操作指令和算术运算以及类型转换指令。

    JVM 每个线程都有一个独立的线程栈，用于存储栈帧。每一次方法调用，自动创建一个栈帧。

    栈帧由操作数栈，局部变量数组以及一个Class 引用（指向运行时常量池对应的 Class）组成。

#### 类加载器
- 加载 Loading，找 Class 文件
- 验证 Verification，验证格式、依赖
- 准备 Preparation，静态字段、方法表
- 解析 Resolution，符号解析为引用
- 初始化 Initialization，构造器、静态变量赋值，静态代码块
- 使用 Using
- 卸载 Unloading
    
##### 添加引用类的几种方法
- 放到 JDK 的 lib/ext 下，或者-Djava.ext.dirs
- java –cp/classpath 或者 class 文件放到当前路径
- 自定义 ClassLoader 加载
- 拿到当前执行类的 ClassLoader，反射调用 addUrl 方法添加 Jar 或路径(JDK9 无效)。

#### JMM
- 栈 Stack
    - 线程栈（多个栈帧、方法）
        - 返回值
        - 局部变量表
        - 操作数栈
        - Class、Method 指针
    - Nativ 栈
- 堆 Heap
    - 年轻代 Young-gen
        - 新生代 Eden，存放新生对象，Young GC 时释放
        - S0、S1，新生代进阶，复制清理对象，防止内存碎片
    - 老年代 Old-gen
- 非堆 Non-Heap
    - 元数据 Metaspace
    - CSS Compressed Class Space
    - Code Cache
        - 常量池
        - 方法区

#### GC
- 标记清除算法 Mark and Sweep
    - CMS GC，老年代，并行，CPU 核心 1/4，降低暂停时间
- 整理算法 Mark Sweep Compact
    - ParNew GC 老年代，单线程，STW
    - Parallel GC 老年代，多线程
- 复制算法 Mark Copy
    - Serail GC 年轻代，单线程，STW
    - Parallel GC 年轻代，多线程
    - CMS GC，并行标记清除，年轻代，并行
- G1 实时垃圾回收，没有年轻代、老年代，2048 个小区域
- ZGC 通过着色指针和读屏障，实现几乎全部的并发执行，几毫
秒级别的延迟，线性可扩展

#### JVM 参数
- 系统属性，-Dmaven.test.skip=true
- 运行模式，server、client、Xint（解释模式，降低运行速度）、Xcomp（预编译为本地代码）、Xmixed（混合模式，默认）
- 堆内存
    - -Xmx 最大堆内存
    - -Xms 最小堆内存，服务端默认与 Xms 一样，减少扩容抖动
    - -Xmn 年轻代大小设置，G1 无效，年轻代=Eden + S0 + S1
        - -XX:NewSize 新生代初始值
        - -XX:MaxNewSize 新生代最大值
    - -Xss 线程栈占用内存大小 1024K
    - -XX:NewRatio 设置老年代和年轻代的比例（默认值 2），=8 老年代=Xmx\*8/9，年轻代=Xmx\*1/9
    - -XX:SurvivorRatio=8，新生代和存活区的比例（默认值8），S0:S1:Eden = 1:1:8，S0=S1=Xmn\*1/10，Eden=Xmn\*8/10
    - -XX:MinHeapFreeRatio=40 GC后，如果发现空闲堆内存占到整个预估上限值的40%，则增大上限值
    - -XX:MaxHeapFreeRatio=70 GC后，如果发现空闲堆内存占到整个预估上限值的70%，则收缩预估上限值
- -XX:MetaspaceSize=128m 初始元空间大小，达到该值就会触发垃圾收集进行类型卸载，同时GC会对该值进行调整：如果释放了大量的空间，就适当降低该值；如果释放了很少的空间，那么在不超过MaxMetaspaceSize时，适当提高该值
- -XX:MaxMetaspaceSize=256m 设置元空间的最大值，默认是没有上限的，也就是说你的系统内存上限是多少它就是多少。默认没有上限，在技术上，Metaspace的尺寸可以增长到交换空间
#### JVM 工具
- jps/jinfo 查看 java 进程
- jstat 查看 JVM 内部 gc 相关信息
- jmap 查看 heap 或类占用空间统计
- jstack 查看线程信息
- jcmd 整合命令

### 个人思考

- JMM 和 GC 是 JVM 管理内存的核心，理解其原理对于问题排查，性能优化起到关键的作用。需要不断的学习、思考和加深理解。
- 字节码和类加载器，以及 CPU 的执行方式决定了 Java 运行时的行为，类加载器的应用需要更加深入一些。
- JVM 参数和工具是依据原理配置、了解运行时的工具，因此需要尝尝应用，积累场景。

## NIO

### BIO

只能单线程阻塞式等待数据由内核态进入用户态，性能低下，CPU 利用率低。

### 非阻塞式 IO

轮询查询数据是否准备好了，没有准备好时，直接返回 error，CPU 可以处理其他事情。但是数据从内核态到用户态仍然需要等待。

### IO 多路复用 事件驱动IO(event driven IO)

单个线程同时监听多个套接字，通过 select、poll 来轮询。

select/poll 的几大缺点：
（1）每次调用 select，都需要把 fd 集合从用户态拷贝到
内核态，这个开销在 fd 很多时会很大
（2）同时每次调用 select 都需要在内核遍历传递进来的
所有 fd，这个开销在 fd 很多时也很大
（3）select 支持的文件描述符数量太小了，默认是1024
epoll（Linux 2.5.44内核中引入,2.6内核正式引入,可被用
于代替 POSIX select 和 poll 系统调用）：
（1）内核与用户空间共享一块内存
（2）通过回调解决遍历问题
（3）fd 没有限制，可以支撑10万连接

### 异步 IO

全流程非阻塞，数据准备和数据拷贝。

### Netty

- Channel 通道，Java NIO 中的基础概念,代表一个打开的连接,可执行读取/写入 IO 操作。
Netty 对 Channel 的所有 IO 操作都是非阻塞的。

- ChannelFuture
Java 的 Future 接口，只能查询操作的完成情况, 或者阻塞当前线程等待操作完成。
Netty 封装一个 ChannelFuture 接口。
我们可以将回调方法传给 ChannelFuture，在操作完成时自动执行。

- Event & Handler Netty 基于事件驱动，事件和处理器可以关联到入站和出站数据流。
- Encoder &
Decoder
处理网络 IO 时，需要进行序列化和反序列化, 转换 Java 对象与字节流。
对入站数据进行解码, 基类是 ByteToMessageDecoder。
对出站数据进行编码, 基类是 MessageToByteEncoder。

- ChannelPipeline 数据处理管道就是事件处理器链。
有顺序、同一 Channel 的出站处理器和入站处理器在同一个列表中。

### 高性能

- 高并发
- 高吞吐
- 低延迟
- 负面
  - 系统复杂度提升
  - 建设维护成本增加
  - 故障破坏半径增加
- 混沌工程
  - 容量预估
  - 爆炸半径
  - 工程积累与改进

### API 网关

- 请求接入
- 业务聚合
- 中介策略，安全、验证、路由、管理
- 统一管理，对 API 和策略进行统一管理


## 并发编程

### Thread



### 线程安全

### 线程池


## Spring 和 ORM

## MySQL

## 分库分表

## RPC 和微服务

## 分布式缓存

## 分布式消息队列

