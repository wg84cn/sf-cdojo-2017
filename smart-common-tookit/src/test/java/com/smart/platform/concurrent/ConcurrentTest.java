/**
 * Project Name:basicplatform
 * File Name:ConcurrentTest.java
 * Package Name:com.smart.platform.concurrent
 * Date:2016年9月6日下午6:12:14
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.smart.platform.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * ClassName:ConcurrentTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年9月6日 下午6:12:14 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ConcurrentTest
{
    @Test
    public void simplePlusTest()
    {
        long startTime = System.nanoTime();
        long total = 0;
        for (int i = 1; i <= 1000000000; i++)
        {
            total += i;
        }
        System.out.println(total);
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime) / 1000000.0);
    }
    
    @Test
    public void forkJoinTest()
    {
        ForkJoinPool forkJoin = new ForkJoinPool(100);
        long startTime = System.nanoTime();
        RecursiveTask<Long> recusinTask = new MyRecursiveTask(1,1000000000);
        // forkJoin.invoke(task);
        System.out.println(forkJoin.invoke(recusinTask));
        System.out.println((System.nanoTime() - startTime) / 1000000.0);
    }
    


    @Test
    public void testCompletion() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);//创建固定线程池
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool);
        for(int i = 1; i <= 10; i++)
        {
            final int task = i; //任务编号
            completionService.submit(new Callable<Integer>(){//执行任务
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));//随机产生小于5秒钟
                    System.out.println("-------");
                    return task;
                }
            });
        }
        System.out.println("等待结果中...");
        for(int i = 1; i <= 10; i++)
        {
            try {
                System.out.println(completionService.take().get());//按照执行完成的先后顺序打印返回结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();//关闭线程池
    }
}

