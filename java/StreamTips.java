packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
Stream is a data channel which generate order serious from data source

Collection is data, Stream is calculate.
Stream does not save data.
Stream does not change data source, reture a new Stream.
Stream does not instant implement. implement When need .


**/

public class StreamTips{
  

  @Test
  public void test1(){

   //1. Collection stream() or parallelStream()

   List<String> list = new ArrayList<>();
   Stream<String> stream1 = list.stream();
   
   //2. Arrary 
   Employee[] emps = new Employee[10];
   Stream<Employee> stream2 =  Arrays.stream(emps);

   //3. Stream of()
   Stream<String> streatm3 = Stream.of("a","b","c");

   //4. infinity
   //die dai
   Stream<Integer> stream4 = Stream.iterate(0, (x)->x+2);
   //Stream4.forEach(System.out::println);
   Stream4.limit(10).forEach(System.out::println);

   // generate
   Stream.generate(()->Math.random());
     .limit(4)
     .forEach(System.out::println);

 
   

    
  }

  List<Employee> employees = Arrays.asList(
     new Employee("zhang",18, 9999.99),
     new Employee("LI",58, 5555.99),
     new Employee("WANG",26, 3333.99),
     new Employee("SAN",36, 6666.99),
     new Employee("ZH",12, 8888.99),
new Employee("ZH",12, 8888.99),
new Employee("ZH",12, 8888.99),
new Employee("ZH",12, 8888.99),
new Employee("ZH",12, 8888.99),
     new Employee("KATE",28, 999.99)
  );  
  @Test
  public void filterTest(){
    Stream<Employee> stream = employees.stream()
      .filter((e>->e.getAge()>35);

    //*Terminate output result.
    stream.forEach(System.out::println);
    //forEach is inner iterate.

    Stream<Employee> stream = employees.stream()
      .filter((e>->{
             //*do not be output if there is not any terminal doing.
             System.out.println("intermedte do");
             return e.getAge()>35;
             });
     
    Iterator<Employee> it = employees.iterator();
    while(it.hasNext()){
      System.out.println(it.next());
    }
   
  }

  @Test
  public void limitTest(){
    employees.stream()
             .filter((e)->{
                System.out.println("short cut");
                return e.getSalary()>500;
               })
               .limit(2)
               .forEach(System.out::println);

         employees.stream()
             .filter((e)->{
                System.out.println("short cut");
                return e.getSalary()>500;
               })
               .skip(2)  //remove 2 item ahead.
               .forEach(System.out::println);

         employees.stream()
             .filter((e)->e.getSallary()>5000)
               .skip(2)  //remove 2 item ahead.
               .distinct() 
//remove same items by hashCode() and equals()
//employee must implement @override hashCode() and equals()
               .forEach(System.out::println);
        

   
  }  

}
