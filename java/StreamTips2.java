packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
map - reduce
**/

public class StreamTips2{
  
/*
1.reduce(T identity, BinaryOperator)
2.reduce(BinaryOperater)
*/
  @Test
  public void test1(){
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//1.reduce(T identity, BinaryOperator)
    Integer sum = list.stream().reduce(0,(x,y)->x+y);//from 0. 0+1 +2 
    System.out.println(sum);//55
//2.reduce(BinaryOperater)
    Optional<Double> op = employees.stream()
         .map(Employee::getSalary)
         .reduce(Double::sum); // sum is a static method of Double
    System.out.println(op.get());//34444.41
   
  }

  List<Employee> employees = Arrays.asList(
     new Employee("zhang",18, 9999.99,Status.FREE),
     new Employee("LI",58, 5555.55,Status.BUSY),
     new Employee("WANG",26, 3333.33,Status.VOCATION),
     new Employee("SAN",36, 6666.66,Status.FREE),
     new Employee("ZH",12, 8888.88,Status.BUSY),
new Employee("ZH",12, 8888.88,Status.BUSY)
  );  

/*
collect( Collector c); 
Collectors.toList() .toSet() .toCollection(HashSet::new) 
Collectors.counting() Collector.joining() 
*/
  @Test
  public void test2(){
    List<String> list = employees.stream()
                     .map(Employee::getName)
             .collect(Collectors.toList()); 

     list.forEach(System.out::println); 
     
     System.out.println("------------------");
     Set<String> set = employees.stream()
                   .map(Employee::getName)
                   .collect(Collector.toSet());

     set.forEach(System.out:println);
     System.out.println("------------------");
     HashSet<String> hs = employees.stream().map(Employee::getName)
              .collect(Collectors.toCollection(HashSet:new));
     hs.forEach(System.out::println);

 
  }

  @Test
  public void test3(){
//Collectors.counting()
    Long count employees.stream()
               .collect(Collectors.counting());
    System.out.println(count);
     System.out.println("------------------");
//Collectors.averagingDouble
    Double avg = employees.stream()
      .collect(Collectors.averagingDouble(Employee::getSalary));
     System.out.println(avg);
     System.out.println("------------------");
//Collectors.summingDouble
     Double sum= employees.stream()
               .collect(Collectors.summingDouble(Employee::getSalary));
     System.out.println(sum);
     System.out.println("------------------");
//Collectors.maxBy
     Optional<Employee> max = employees.stream()
          .collect(Collectors.maxBy(
             (e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()) 
           ));
      System.out.println(max.get());//Employee[name=zh,age=18,salary=9999.99,Status=FREE]

     System.out.println("------------------");
//Collectors.minBy
     Optional<Double> min = employees.stream()
             .map(Employee::getSalary)
             .collect(Collectors.minBy(Double::compare));
     System.out.println(min.get()); //3333.33

     System.out.println("------------------");

  }
//grougpingBy
  @Test
  public void test6(){
//1.groupingBy
     Map<Status,List<Employee>> map = 
       employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));

       System.out.println(map);
//2.groupingBy  multi-level
     Map<Status,Map<String,List<Employee>>> map2 =
     employees.stream()
             .collect(Colletors.groupingBy(Employee::getStatus,
                  Collectors.groupingBy((e)->{
                      if(((Employee)e).getAge()<=35){
                         return "YOUNG";
                      }else if(((Employee)e).getAge()<=50){
                         return "Adult";
                      }else { return "Senior";}
                   })));
         System.out.println(map);

//partitioningBy
       Map<Boolean,List<Employee>> map3 = 
         employees.stream()
          .collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
       System.out.println(map);

//summarizingDouble

      DoubleSummaryStatistics dss= employees.stream()
       .collect(Collectors.summarizingDouble(Employee::getSarlary));
       System.out.println(dss.getSum());
       System.out.println(dss.getAverage());
       System.out.println(dss.getMax());

//Collector.joining()

     String str = employees.stream()
           .map(Employee::getName)
           .collect(Collector.joining());
          //.collect(Collector.joining(","));
          //.collect(Collector.joining(",","[","]"));
     System.out.println(str);

  }

}
