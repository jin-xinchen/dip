packe com.dip.java8

import org.junit.Test;

/* Deprecated 
   Date(); year+1900
   Calender class
   TimeZone            //// thread safe
   SimpleDateFormat   //// thread safe

Java 8 java.time  
       LocalDate, LocalDateTime, LocalTime:  ISO-8601 for human.
       Instant:
       Duration: a time 
       Period: A date 
      OffsetTime:
java.time.Zone
*/

public class DateTips{
  


 
}

public class TestSimpleDateFormat{
//error in thread 
  public static void main(String[] args) throws Exception{
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     
     Callable<Date> task = new Callable<Date>(){
        @Override
        public Date call() throws Exception{
           return sdf.parse("20161218");// no thread safe
        }
     };
     ExecutorService pool = Executors.newFixedThreadPool(10); 
     List<Future<Date>> results = new ArrayList<>();
     for(int i=0;i<10;i++){
        results.add(pool.submit(task));
     }

     for(Future<Date> future:results){
         System.out.println(future.get());
      }          

     pool.shutdown();  
  }

//
//
/*
public class DateFormatThreadLocal{
  private static final ThreadLocal<DataFormat> df = 
       new ThreadLocal<DateFormat>(){
            protected DateFormat initialValue(){
                return new SimpleDateFormat("yyyyMMdd");
            }
       };

  public static Date conver(String source) throws ParseExcepton{
    return df.get().parse(source);
  }
}
*/
//
  @Test 
  public void main2(String[] args) throws Exception{
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     
     Callable<Date> task = new Callable<Date>(){
        @Override
        public Date call() throws Exception{
           return DateFormatThreadLocal.conver("20161218");
        }
     };
     ExecutorService pool = Executors.newFixedThreadPool(10); 
     List<Future<Date>> results = new ArrayList<>();
     for(int i=0;i<10;i++){
        results.add(pool.submit(task));
     }

     for(Future<Date> future:results){
         System.out.println(future.get());
      }          

     pool.shutdown();  
  }

//IN Java8  thread safe
  @Test 
  public void main3() throws Exception{
     //DateTimeFormatter dtf = new DateTimeFormatter.ISO_LOCAL_DATE;
     DateTimeFormatter dtf = new DateTimeFormatter.ofPattern("yyyyMMdd");
     
     Callable<LocalDate> task = new Callable<LocalDate>(){
        @Override
        public Date call() throws Exception{
           //return DateFormatThreadLocal.conver("20161218");
           return LocalDate.parse("20161218",dtf);

        }
     };
     ExecutorService pool = Executors.newFixedThreadPool(10); 
     List<Future<LocalDate>> results = new ArrayList<>();
     for(int i=0;i<10;i++){
        results.add(pool.submit(task));
     }

     for(Future<Date> future:results){
         System.out.println(future.get());
      }          

     pool.shutdown();  
  }  


}