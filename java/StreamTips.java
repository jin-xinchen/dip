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
     new Employee("zhang",18, 9999.99,Status.FREE),
     new Employee("LI",58, 5555.99,Status.BUSY),
     new Employee("WANG",26, 3333.99,Status.VOCATION),
     new Employee("SAN",36, 6666.99,Status.FREE),
     new Employee("ZH",12, 8888.99,Status.BUSY),
new Employee("ZH",12, 8888.99,Status.BUSY),
new Employee("ZH",12, 8888.99,Status.BUSY),
new Employee("ZH",12, 8888.99,Status.BUSY),
new Employee("ZH",12, 8888.99,Status.BUSY),
     new Employee("KATE",28, 999.99,Status.BUSY)
  );  
  @Test
  public void filterTest(){
//filter
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
//limit
    employees.stream()
             .filter((e)->{
                System.out.println("short cut");
                return e.getSalary()>500;
               })
               .limit(2)
               .forEach(System.out::println);
//skip
         employees.stream()
             .filter((e)->{
                System.out.println("short cut");
                return e.getSalary()>500;
               })
               .skip(2)  //remove 2 item ahead.
               .forEach(System.out::println);
//distinct
         employees.stream()
             .filter((e)->e.getSallary()>5000)
               .skip(2)  //remove 2 item ahead.
               .distinct() 
//remove same items by hashCode() and equals()
//employee must implement @override hashCode() and equals()
               .forEach(System.out::println);
        

   
  }  
  //map
  @Test
  public void testMap(){
    List<String> list = Arrays.asList("a","b","c");

 //map
    list.stream()
        .map((str)->str.toUpperCase())
        .forEach(System.out::println);
    //
    employees.stream().map(Employee::getName).forEach(System::println);
    ///
    Stream<Stream<Character>> stream = list.stream()
        .map(StreamTips:filterCharacter);
    //
    stream.forEach((sm)->{
      sm.forEach(System.out:println);
    });

 //flatMap
    Stream<Character> sm2 = list.stream()
     .flatMap(StreamTips::filterCharacter);
    sm2.forEach(Systm.out::println);

 // list.add and list.addAll
    List<String> list = Arrays.asList("aa","bb");
    List list2 = new ArrayList();
    list2.add(11); list2.add(12);
    list2.add(list);
    System.out.println(list2); // [11,12,[aa,bb]]
list2.addAll(list);
    System.out.println(list2); //[11,12,aa,bb]

           

  }

   

  public Stream<Character> filterCharacter(String str){
    List<Character> list = new ArrayList<>();
    for(Character ch :str.toCharArray()){
      list.add(ch);
    }
    return list.stream();
  }

/*
  sort
  sorted() by comparable
  sorted(Comparator com)  by Comparator
*/
  @Test
   public void test7(){
      List<String> list = Arrays.asList("ccc","aaa","bbb","ddd","eee");
      list.stream().sorted().forEach(System.out::println);//comparable 
      
      System.out.println("-------------------");
      
      employees.stream().sorted((e1,e2)->{
         if(e1.getAge().equals(e2.getAge())){
           return e1.getName().compareTo(e2.getName());
         }else{
           return e1.getAge().compareTo(e2.getAge());     //asc
           //return -e1.getAge().compareTo(e2.getAge());  //desc
         }
      }).forEach(System.out:println);
   } 

  /* Termination  like forEach
   allMatch
   anyMatch
   noneMatch
   findFirst
   findAny
   count
   max
   min
  */ 
  /*

  to   change Employee
  private Status Status;
  public enum Status{FREE,BUSY,VOCATION;}
  public Employee(String name,Integer age,Double salary,
                  com.dip.java8.Employee.Status status){
      this.name=name;this.age=age;this.salary=salary;Status=status;
   }
  @Override
  public String toString(){
    return "Employee[name="+name+", age="+age+", salary="+salary+",Status="+Status+"]";

  } 
  */
  @Test
  public void test8(){
//allMatch
   boolean b1 = employees.stream()
               .allMatch((e)->e.getStatus().equals(Status.BUSY));
    System.out.println(b1);
//anyMatch
   boolean b2 = employees.stream()
               .anyMatch((e)->e.getStatus().equals(Status.BUSY));
    System.out.println(b2);
//noneMatch
   boolean b3 = employees.stream()
               .noneMatch((e)->e.getStatus().equals(Status.BUSY));
    System.out.println(b3);
//findFirst & Optional
   Optional<Employee> op  = employees.stream()
               .sorted((e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()))
               .findFirst();
  //op.orElse(otherObject)
    System.out.println(op.get());
//findAny
   Optional<Employee> op2  = employees.stream()
               .filter((e)->e.getStatus().equals(Status.FREE))
               .findAny();
  
    System.out.println(op2.get());
//parallelStream()
   Optional<Employee> op2  = employees.parallelStream()
               .filter((e)->e.getStatus().equals(Status.FREE))
               .findAny();
//count
   Long count  = employees.stream()
               .count();
//max
   Optional<Employee> op1  = employees.stream()
               .max(
          (e1,e2)->Double.compare(e1.getSalary(),e2.getSalary()));
              
   System.out.println(op1.get());
//min
   Optional<Employee> op2  = employees.stream()
               .map(Employee::getSalary)
               .min(Double::compare);
              
   System.out.println(op2.get());



               
  }

}
