﻿packe com.dip.java8

import java.util.concurrent.recursiveTask;
import org.junit.Test;

/**
//fork/join
//multi cpu  work-stealing
**/

public class StreamTips4ForkJoin extends RecursiveTask<Long>{
  
   private static final long serialVersionUID = 134656970988L;

   private long start;
   private long end;

   private static final long THRESHOLD = 10000;

   public StreamTips4ForkJoin(long start,long end){
 
    this.start=start;
    this.end = end;
   }

   @Override
   protected Long compute(){
     long length=end - start;

     if(length<=THRESHOLD){
        long sum=0;
        for(long i=start;i<=end;i++){
           sum+=i;
        }
        return sum;
     }else{
        long middle = (start+end)/2;
        StreamTips4ForkJoin left = new StreamTips4ForkJoin(start,middle);
        left.fork();//fork into thread sequence
        
        StreamTips4ForkJoin right = new StreamTips4ForkJoin(middle+1,end);
        right.fork();

        return left.join() + right.join();    
     }
     
     
   }

}

/////////////////////
/*
 
*/
import java.util.concurrent.ForkJoinPool;
import org.junit.Test;

public class TestForkJoin{

    /*
     Fork Join framework
    */
    @Test
    public void test1(){

       Instant start = Instant.now();

       ForkJoinPool pool = new ForkJoinPool();
       ForkJoinTask<Long> task = new StreamTips4ForkJoin(0,100000000L);
       Long sum = pool.invoke(task);

       System.out.println(sum);

       Instant end =Instant.now();

       System.out.println(Duration.between(start,end).getNano());
       System.out.println(Duration.between(start,end).getSecond()); 
       System.out.println(Duration.between(start,end).toMillis());//138  
//2016-9260-19229
       System.out.println(Duration.between(start,end).getNano());
    }

    @Test
    public void test2(){
       Instant start = Instant.now();

       long sum=0L;
        for(long i =0;i<100000000L;i++){
          sum+=i;
        }

       System.out.println(sum);

       Instant end =Instant.now();

       System.out.println(Duration.between(start,end).getNano());
       System.out.println(Duration.between(start,end).getSecond()); 
       System.out.println(Duration.between(start,end).toMillis());//34
//3135-15720-31420
     
    }

  //Java 8 

  @Test
  public void test3(){
    Instant start=Instant.now();

    LongStream.rangeClosed(0, 100000000000L)
              .reduce(0, Long::sum);
 
    LongStream.rangeClosed(0, 100000000000L)
              .parallel()  //sequential()  
              .reduce(0, Long::sum);

    Instant end =Instant.now();
    System.out.println(Duration.between(start,end).toMillis());//14146
  }
}