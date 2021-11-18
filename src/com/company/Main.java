package com.company;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        //Single Thread
        long start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 1; i <= 10_000_000; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start);


        //MultiThreading(2 Threads)
        long start1 = System.currentTimeMillis();

        AtomicLong atomicLong = new AtomicLong();
        final Object object = new Object();
        Runnable runnable1 = () -> {
            synchronized (object) {
                for (int i = 1; i <= 5_000_000; i++) {
                    atomicLong.getAndAdd(i);
                }
                System.out.println(Thread.currentThread().getName() + atomicLong);
            }
        };

        Runnable runnable2 = () -> {
            synchronized (object) {
                for (int i = 5_000_001; i <= 10_000_000; i++) {
                    atomicLong.getAndAdd(i);
                }
                System.out.println(Thread.currentThread().getName() + atomicLong);
            }
        };

        List<Thread> threadList = List.of(
                new Thread(runnable1, "Thread 1: "),
                new Thread(runnable2, "Thread 2: "));

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - start1);
    }
}
