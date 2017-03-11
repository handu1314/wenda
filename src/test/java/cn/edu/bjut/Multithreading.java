package cn.edu.bjut;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:hanxiao
 * @Description:
 * @Modified By:
 * Created by Administrator on 2017/3/11.
 */
class MyThread extends Thread {
    private int tid;

    MyThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(String.format("线程：%d--i:%d", tid, i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> q;

    public Consumer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + q.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer implements Runnable {
    BlockingQueue<String> q;

    public Producer(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Multithreading {
    static void testMyThread() {
        for (int i = 0; i < 10; i++) {
            new MyThread(i).start();
        }
    }

    private static Object obj = new Object();

    static void testSynchronized1() {
        synchronized (obj) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(String.format("T1:%d", i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static void testSynchronized2() {
        synchronized (obj) {
            for (int i = 0; i < 5; i++) {
                System.out.println(String.format("T2:%d", i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void testSynchronized() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }

    static void testBlockingQueue() {
        BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q), "producer1").start();
        new Thread(new Consumer(q), "consumer1").start();
        new Thread(new Consumer(q), "consumer2").start();
    }

    static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>();
    static int counter = 0;
    static Object obj1 = new Object();

    static void testThreadLocal() {

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    integerThreadLocal.set(finalI);
                    try {
                        Thread.sleep(1000);
                        System.out.println(String.format("ThreadLoca:%d", integerThreadLocal.get()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter = finalI;
                    try {
                        Thread.sleep(1000);
                        System.out.println(String.format("counter:%d", counter));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    static void testExecutor(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(String.format("T1--%d",i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(String.format("T2--%d",i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executorService.shutdown();
    }
    static void testFuture(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Integer i = 0;
                for (int j = 0; j < 10; j++) {
                    ++i;
                }
                return i/0;
            }
        });
        executorService.shutdown();
        try {
            System.out.println(future.get());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    static int count = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static void testWithoutAtomic(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(count++);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        System.out.println(count);
    }
    static void testWithAtomic(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        System.out.println(atomicInteger.incrementAndGet());
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        System.out.println(count);
    }
    static void testAtomic(){
        //testWithoutAtomic();
        testWithAtomic();
    }

    public static void main(String[] args) {
        //testMyThread();
        //testSynchronized();
        //testBlockingQueue();
        //testThreadLocal();
        //testExecutor();
        //testFuture();
        testAtomic();
    }
}
