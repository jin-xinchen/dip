packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
map - reduce
**/

public class StreamTips3{
  


  List<Employee> emps = Arrays.asList(
     new Employee(102,"lisi",59, 6666.66,Status.BUSY),
     new Employee(101,"zhangsan",18, 9999.99,Status.FREE),
     new Employee(103,"WANGwu",28, 3333.33,Status.VOCATION),
     new Employee(104,"zhaoliu",8, 7777.77,Status.BUSY),
     new Employee(104,"zhaoliu",8, 7777.77,Status.FREE),
     new Employee(104,"zhaoliu",8, 7777.77,Status.FREE),
     new Employee(105,"tianqi",38, 5555.55,Status.BUSY)

  ); 
  @Test
  public void test2(){
    Optional<Integer> count = emps.stream()
         .map((e)->1)
         .reduce(Integer::sum);
    System.out.println(count.get());

  } 

  @Test
  public void test1(){
    Integer[] nums = new Integer[]{1,2,3,4,5};

    Arrays.stream(nums)
          .map((x)->x*x)
          .forEach(System.out::println);
  }

}
