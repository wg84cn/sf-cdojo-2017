package com.smart.platform.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * ClassName: MyRecursiveTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月6日 下午6:28:44 <br/>
 * @author 01135912
 * @version 
 * @since JDK 1.6
 */
public class MyRecursiveTask extends RecursiveTask<Long>
{
    
    private static final long serialVersionUID = 1L;
    
    private long startNum = 0;
    
    private long endNum = 0;
    
    private static final int SEGMENT_NUM = 10000000;
    
    public MyRecursiveTask(long startNum, long endNum)
    {
        this.startNum = startNum;
        this.endNum = endNum;
    }
    
    @Override
    protected Long compute()
    {
        long result = 0;
        if (endNum - startNum > SEGMENT_NUM)
        {
            List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();
            for (int times = 0; times < 100; times++)
            {
                int startIndex = SEGMENT_NUM * times + 1;
                int endIndex = SEGMENT_NUM * (times + 1);
                MyRecursiveTask recursiveTask = new MyRecursiveTask(startIndex, endIndex);
                subtasks.add(recursiveTask);
                recursiveTask.fork();
            }
            
            for (MyRecursiveTask subtask : subtasks)
            {
                result += subtask.join();
            }
        }
        else
        {
            for (long num = startNum; num <= endNum; num++)
            {
                result += num;
            }
        }
        return result;
    }
}
