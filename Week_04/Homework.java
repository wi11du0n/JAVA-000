
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Homework {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = func7(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private static int sum() {
        System.out.println("执行任务的线程：" + Thread.currentThread().getName());
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    // FutureTask 异步
    public static int func1() {
        FutureTask futureTask = new FutureTask(() -> sum());
        try {
            Thread thread = new Thread(futureTask);
            thread.start();
            return (Integer) futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

        // Semaphore
    public static int func2() {
        AtomicInteger sum = new AtomicInteger();
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            try {
                semaphore.acquire();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sum.set(sum());
            semaphore.release();
        }).start();
        while (sum.get() <= 0) {
        }
        return sum.get();
    }

    // CountDownLatch
    public static int func3() {
        AtomicInteger sum = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            sum.set(sum());
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum.get();
    }

    // CyclicBarrier
    public static int func4() {
        AtomicInteger sum = new AtomicInteger();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> new Thread(() -> {
            sum.set(sum());
        }).start());
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (sum.get() <= 0) {
        }
        return sum.get();
    }

    // CompletableFuture
    public static int func5() {
        final Integer join = CompletableFuture.supplyAsync(() -> sum()).join();
        return join;
    }

    // join
    public static int func6() {
        AtomicInteger sum = new AtomicInteger();
        final Thread thread = new Thread(() -> {
            sum.set(sum());
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum.get();
    }

    // sleep
    public static int func7() {
        AtomicInteger sum = new AtomicInteger();
        new Thread(() -> {
            sum.set(sum());
        }).start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum.get();
    }
}