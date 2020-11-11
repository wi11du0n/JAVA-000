# Java并发

## 线程

### 进程与线程

- 进程是系统分配资源的最小单位
- 线程是CPU调度的最小单位

### 线程的实现

- 内核线程

	- 由操作系统内核实现
	- 轻量级进程与内核线程是1:1的关系
	- 系统调用需要频繁地在用户态和内核态中切换

- 用户线程

	- 使用线程库实现，系统内核无法感知
	- 进程与用户线程是1:N的关系
	- 不需要内核态的切换，进程的阻塞会导致所有线程的阻塞

- 混合模式
- Windows、Linux平台下的Sun JDK使用内核线程实现

### 线程状态

- New
- Runable
- Waiting
- Timed Waiting
- Blocked
- Terminated

### Thread、Runable、Callable

### 中断

- stop()方法可能会破坏数据的一致性
- interrupt()和InterruptedException
- 捕获底层库的中断异常或者轮询中断状态

	- public static boolean interrupted()
	- public boolean isInterrupted()

- 抛出中断异常，或者恢复中断状态交给上层代码处理

### UncaughtExceptionHandler

- 未捕获异常会导致线程停止运行
- 主线程无法捕获子线程运行时的异常

## 线程池

### 避免频繁地创建和销毁线程

### Executor

- CachedThreadPool：一个任务创建一个线程
- FixedThreadPool：所有任务只能使用固定大小的线程
- SingleThreadExecutor：相当于大小为1的FixedThreadPool

### ThreadPoolExecutor

- workQueue：提交但未执行的任务队列

	- 无界队列：LinkedBlockingQueue
	- 有界队列：ArrayBlockingQueue
	- 同步移交：SynchronousQueue

- handler：拒绝策略

	- 中止（Abort）
	- 调用者运行（Caller-Runs）

### shutdown()和shutdownNow()

- shutdown()不再接受新的任务，同时等待已提交的任务完成
- shutdownNow()中断所有执行中的任务，且不再启动队列中的任务

## 线程安全

### 安全级别

- 不可变
- 绝对线程安全
- 相对线程安全
- 线程兼容
- 线程对立

### 互斥同步

- synchronized

	- 重量级锁，操作系统Mutex实现
	- 同步代码块、静态方法、实例方法
	- wait()、notify()、notifyAll()

		- 它们都属于Object的一部分，而不属于Thread
		- 只能在synchronized中使用，否则会在运行时抛出IllegalMonitorStateExeception
		- 使用wait()挂起期间，线程会释放锁；被唤醒时重新获取锁
		- 始终使用wait循环模式，循环会在等待之前和等待之后测试条件

	-  monitorenter、monitorexit更新对象头中的锁计数器

- ReentrantLock

	- 等待可中断
	- 可实现公平锁
	- 可绑定多个条件

### 非阻塞同步

- 硬件原语支持

	- Test-and-Set
	- Fetch-and-Increment
	- Swap
	- Compare-and-Swap
	- Load-Linked/Store-Conditional

- CAS操作和ABA问题

### 无同步方案

- 栈封闭
- 实例封闭
- ThreadLocal
- 函数式编程

## 并发工具

### AQS

- state：加锁状态、重入性
- 同时记录加锁的线程
- CAS更新state为1加锁
- 等待队列存放加锁失败的线程

### ReentrantLock

- 公平锁
- tryLock()
- lockInterruptibly()
- Condition

	- await()、signal()、signalAll()
	- awaitUniterruptibly()

### ReadWriteLock

- 读写分离锁，减少锁竞争

### Semaphore

- 信号量，控制对临界资源访问的线程数

### CountDownLatch

- countDown() 倒计时
- await() 等待倒计时结束

### CyclicBarrier

- 循环栅栏，计数器可以 reset() 反复使用

## 并发容器

### ConcurrentHashMap

- 分段锁
- 额外的原子操作

### CopyOnWriteArrayList

- 读取时不加锁
- 写入时复制，并替换原有数组

### BlockingQueue

- ArrayBlockingQueue
- LinkedBlockingQueue
- SynchronousQueue

	- 不维护存储空间，生产者直接交付给消费者
	- 有足够空闲的消费者时，才适合使用同步队列

## 内存模型

### 主内存和工作内存

- 所有变量都存储在主内存中
- 每条线程还有自己的工作内存，工作内存中保存了变量的主内存副本
- 线程对变量的操作必须在工作内存中完成，而不能直接访问主内存

### volatile语义

- 可见性：变量在所有线程中一致
- 禁止指令重排序
- 保证long、double类型读写的原子性

### 原子性、可见性、有序性

- 基本类型的读写、synchronized块具有原子性
- volatile、final、synchronized可以保证变量的可见性
- 线程内表现为串行的语义，线程外会感知到指令重排序

### 先行发生原则

- 单一线程原则：在一个线程内，在程序前面的操作先行发生于后面的操作。
- 管程锁定规则：一个 unlock 操作先行发生于后面对同一个锁的 lock 操作。
- volatile 变量规则：对一个 volatile 变量的写操作先行发生于后面对这个变量的读操作。
- 线程启动规则：Thread 对象的 start() 方法调用先行发生于此线程的每一个动作。
- 线程加入规则：Thread 对象的结束先行发生于 join() 方法返回。
- 线程中断规则：对线程 interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生。
- 对象终结规则：一个对象的初始化完成（构造函数执行结束）先行发生于它的 finalize() 方法的开始。
- 传递性：如果操作 A 先行发生于操作 B，操作 B 先行发生于操作 C，那么操作 A 先行发生于操作 C。

## 锁优化

### 自旋锁

- 先执行一个忙循环，避免进入内核态
- 占用CPU时间，可能导致性能问题
- 自适应自旋锁

### 锁消除

- 不会被别的线程访问的锁进行消除

### 锁粗化

- 一系列连续操作都是对同一个对象加锁和解锁
- 将锁同步的范围粗化到操作序列的外部

### 轻量级锁

- 无锁状态、偏向锁状态、轻量级锁状态、重量级锁状态
- 使用CAS操作将对象头中的无锁状态更新为轻量级锁状态
- 如果CAS操作成功，则成功获取锁，进入轻量级锁状态
- 如果CAS操作失败且锁被其它线程获取，则膨胀为重量级锁

### 偏向锁

- 当锁对象第一次被线程获得的时候，进入偏向状态，这个线程之后获取锁不再需要同步
- Java并发

*XMind - Trial Version*