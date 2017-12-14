package com.smart.platform.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolMain {

	private static ForkJoinPool forkJoin = new ForkJoinPool(100);

	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		RecursiveTask<Long> recusinTask = new MyRecursiveTask(1,1000000000);
		// forkJoin.invoke(task);
		System.out.println(forkJoin.invoke(recusinTask));
		System.out.println((System.nanoTime() - startTime) / 1000000.0);
	}
}
