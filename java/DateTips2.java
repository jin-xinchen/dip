packe com.dip.java8

import org.junit.Test;

/* 
1.LocalDate, LocalTime, LocalDateTime
2.Instant  ,ZoneOffset
3.Duration time
4.Period   Date
5.TemporalAdjusters    TemporalAdjuster
6.DateTimeFormatter
7.ZoneId.getAvailableZoneIds
8.ZoneDate, ZonedTime, ZoneDateTime
*/

public class DateTips2{

//8.ZoneDate, ZonedTime, ZoneDateTime
  @Test
  public void test8(){
     LocalDateTime ldt=LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
     System.out.println(ldt);//2016-12-26T10:57:30.437
     
     LocalDateTime ldt2=LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
     ZoneDateTime zdt=ldt2.atZone(ZoneId.of("Asia/Shanghai"));
     System.out.println(zdt);
      //2016-12-26T10:57:30.443+08:00[Asia/Shanghai]
         

  }
//7.ZoneId.getAvailableZoneIds
  @Test
  public void test7(){

     Set<String> set = ZoneId.getAvailableZoneIds();
     set.forEach(System.out::println);

  }
//6.DateTimeFormatter
  @Test
  public void test6(){
    DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME:
    //DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
    LocalDateTime ldt=LocalDateTime.now();

    String strDate = ldt.formate(dtf);
    System.out.println(strDate);

 //
    DateTimeFormatter dtf2 =
    DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
    String strDate2 = dtf2.format(ldt);
    System.out.println(strDate2);

    LocalDateTime newDate = ldt.parse(strDate2,dtf2);
    System.out.println(newDate);






  }

  @Test
  pubic void test5(){


//5.TemporalAdjusters    TemporalAdjuster

    LocalDate nextSunday = LocalDate.now().with(
         TemporalAdjusters.next(DayOfWeek.SUNDAY)
    );
    LocalDateTime ldt = LocalDateTime.now();
    LocalDateTime ldt2 = ldt.withDayOfMonth(10);

//implement TemporalAdjuster Interface
   LocatlDateTime nextWorkDay =  
   ldt.with((l)->{
      LocalDateTime ldt4=(LocalDateTime)l;
      DayOfWeek dow =ldt4.getDayOfWeek();
      if(dow.equals(DayOfWeek.FRIDAY)){
          return ldt4.plusDays(3);
       }else if(dow.equals(DayOfWeek.SATURDAY){
          return ldt4.plusDays(2);
       }else {
          return ldt4.plusDays(1);
       }        
   });

   System.out.println(nextWorkDay);

  }
   

//4.
//Period   Date
  @Test
  public void test4(){
     LocalDate ld1=LocalDate.of(2015,1,1);
     LocalDate ld2=LocalDate.now();
    
     Period period=Period.between(ld1,ld2);
     System.out.println(period);//P1Y11M17D   ISO format
     period.getYears();          //1
     period.getMonths();           //11
     period.getDays();                //17

  }
  

//3.
//Duration time

  @Test
  public void test3(){
    Instant ins1=Instant.now();
    Try{Thread.sleep(1000); }catch(InterruptedException e){}
    Instant ins2=Instant.now();
    
    Duration duration=Duration.between(ins1,ins2);
    System.out.println(duration);//PT15    IOS
    System.out.println(duration.toMills());//1001

    System.out.println("--------------");

    LocalTime lt1=LocalTime.now();
    Try{Thread.sleep(1000); }catch(InterruptedException e){}    
    LocalTime lt2=LocalTime.now();
 System.out.println(duration.between(lt1,lt2).toMills());//1000

    
  }
  
//2.Instant  from Unix 1970-01-01T00:00:00
//ZoneOffset
  @Test
  public void test2(){
     Instant ins1 = Instant.now();//UTC time area
     System.out.println(ins1);//2016-12-18T06:02:29.585Z
     
     OffsetDateTime odt= ins1.atOffset(ZoneOffset.ofHours(8));
     //2016-12-18T14:02:29.585+08:00
     System.out.println(odt);
     
     System.out.println(ins1.topochMilli());

    Instant ins2= Instant.ofEpochSecond(60);//60 second
    System.out.println(ins2);

   } 

//1.LocalDate, LocalTime, LocalDateTime
  @Test
  public void test1(){

     LocalDateTime ldt = LocalDateTime.now();
     System.out.println(ldt);//2016-12-18T13:55:33.247

     LocalDateTime ldt2 =LocalDateTime.of(2015, 10, 19,13, 22,33);
     System.out.println(ldt2);//2016-12-18T13:55:33.333

     LocalDateTime ldt3 =ldt.plusYears(2);
     System.out.println(ldt3);//2018-12-18T13:55:33.333
     
     LocalDateTime ldt4 =ldt.minusMonths(2);
     System.out.println(ldt4);//2016-10-18T13:55:33.333
 
     ldt.getYear();
     ldt.getMonthValue();
     ldt.getDayOfMonth();
     ldt.getHour();
     ldt.getMinute();
     ldt.getSecond();

     
   }

}