packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
Reference of Funciton.

object::funciton of instance.

class::static function.

Clsss::funciton of instance.

Type::new;

**/
//**


**/

public class Lambda04{
  

  @Test
  public void test7(){
    Function<Integer, String[]> fun= (x)-> new String[x];
    String[] strs = fun.apply(10);
    System.out.println(strs.length);

    Function<Integer, String[]> fun2 = String[]::new;
    String[] str2 = fun2.apply(29);
    System.out.println(str2.length);
  }
  //Consumer
  @Test
  public void test1(){
   Consumer<String> con = (x)->System.out.println(x);
   
   PrintStream ps = System.out;

   Consumer<String> con1 = ps::println;
   Consumer<String> con2 = (x)->ps.println(x);

   Consumber<String> con3=system.out::println;
   con3.accept("abcaaa");
    
   //parameters and return must be same between println and Consumer.accept

 
   
  }

  @Test
  public void test2(){

    Employee emp = new Employee();
    Supplier<String> sup = ()-> emp.getName();

   
    String str = sup.get();
    System.out.println(str);
  
    Supplier<Integer> sup2= emp::getAge;
    Integer num = sup2.get(); 
    System.out.println(num);
 
   }

   @Test
   public void test3(){
     Comparato<Integer> com = (x,y)->Integer.compare(x,y);

     Comparato<Integer> com1 = Integer::compare;
     //  parameter list and reture  must unit.
   }
  
   @Test
   public void test4(){
     BiPredicate<String, String> bp = (x,y)->x.equals.(y);
     // (x,y)  x.equals(y);  
     //The equals is x function. y is the parameter of equals().
     BiPredicate<String, String> bp2 = String::equals;
 
     
   }

   @Test
   public void test5(){
    Supplier<Employee> sup = ()->new Employee();

    sup.get();

    Supplier<Employee> sup2 = Employee::new;
    Employee emp = sup2.get();
    Sytem.out.println(emp);
    
   }

   @Test
   public void test6(){
     Function<Integer, Employee> fun = (x)-> new Employee(x);
     Function<Integer, Employee> fun2= Employee::new;
     Employee emp =      fun2.apply(101);
     System.out.println(emp);

     
     BiFunciton<Integer, Integer, Employee> bf = Employee:new;
     // 
     
  
     
   }

}
