packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
map - reduce
**/

public class StreamTips{
  
/*
1.reduce(T identity, BinaryOperator)
2.reduce(BinaryOperater)
*/
  @Test
  public void test1(){
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//1.reduce(T identity, BinaryOperator)
    Integer sum = list.stream().reduce(0,(x,y)->x+y);
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
     new Employee("ZH",12, 8888.88,Status.BUSY)
  );  

}
