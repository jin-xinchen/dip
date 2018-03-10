packe com.dip.java8

import java.util.Collections;
import org.junit.Test;


public class Lambda02{
  
   List<Employee> emps = Arrays.aslist(
     new Employee(101, "li",18, 99.99),
     new Employee(102, "zhang",80, 66.99),
     new Employee(103, "wang",28, 33.99),
     new Employee(104, "da",38, 77.99),
     new Employee(105, "zero",8, 55.99)
    
   );

  @Test
  public void test1(){
    Collections.sor(emps,(e1,e2)->{
        if(e1.getAge()==e2.getAge()){
           return e1.getName().compareTo(e2.getName());
         }else {
           return Integer.compare(e1.getAge(), e2.getAge());// desc from 1 to 20
           // from 20 to 1
           return -Integer.compare(e1.getAge(), e2.getAge());


         }
      });

      for(Employee emp : emps){
        System.out.println(emp);
      }
  }


  @Test
  public void test2(){
    strHandler("\t\t\t the luck day!",(str)->str.trim());
    strHandler("\t\t\t the luck day!",(str)->str.toUpperCase());
    
    String s = 
       strHandler("\t\t\t the luck day!",(str)->str.substring(7,4));
    System.out.println(s);
  }
  public String strHandler(String str, MyFunciton mf){
    return mf.getValue(str);
  }


  @Test
  public void test3(){
    op(100L, 200L, (x,y)->x+y);
    op(100L, 200L, (x,y)->x*y);

  }
  public void op(Long l1, Long l2, MyFunction2<Long, Long> mf){
    System.out.println( mf.getValue(l1,l2)  );
  }
   

}
@FuncitonalInterface
public interface MyFunction{

  public String getValue(String str);

}

@FuncitonalInterface
public interface MyFunction2<T,R>{

  public R getValue(T t1, T t2);

}
