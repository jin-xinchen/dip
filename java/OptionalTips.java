packe com.dip.java8

import java.util.Optional;
import org.junit.Test;

/**
  
  Optional.of(T t);
  Optional.empty();
  Optional.ofNullable(T t);
  isPresent()
  orElse(T t);
  orElseGet(Supplier s);
  map( Funciton f);
  
**/

public class OptionalTips{

//map
  @Test
  public void test4(){

    Employee emp = op.ofNullable(new Employee("zz",18,888,Status.FREE));
    
    Optional<String> str = op.map((e)->e.getName());
   
    System.out.println(str.get());

    //op.flatMap((e)->e.getName());// error: must return Optional
    Optional<String> str2 = op.flatMap((e)->Optional.of(e.getName())); 
    System.out.println(str2);   

  }

//isPresent()
  @Test
  public void test33(){
    Optional<Employee> op = Optional.ofNullable(new Employee());

    Optional<Employee> op = Optional.ofNullable(null);
    if(op.isPresent()){
      System.out.println(op.get());
    }

//orElse(T t); t is default Object
    // op is null, emp is ["zz",18,888,Status.FREE]
    Employee emp = op.orElse(new Employee("zz",18,888,Status.FREE));
    System.out.println(emp);

//orElseGet(Supplier s)
    op.orElseGet(()->{}); 
    Employee emp2 = op.orElseGet(()->new Employee());
    System.out.println(emp2);
    
  }
  
//Optional.ofNullable(T t); is empty() and of()
  @Test
  public void test3(){
    //new a instance
    Optional<Employee> op = Optional.ofNullable(New Employee);

    //a null, message: NoSuchElement.exception: No value 
    //Optional<Employee> op = Optional.ofNullable(null);

    System.out.println(op.get());
  }

//Optional.empty();
  @Test
  public void test2(){
    Optional<Employee> op = Optional.empty();
    System.out.println(op.get());
  }
//Optional.of
  @Test
  public void test1(){

    Optional<Employee> op = Optional.of(new Employee());
 
    //if null, must throw java.lang.NoPointerException.
    //Optional<Employee> op = Optional.of(null); 

    
    Employee emp = op.get();
    System.out.println(emp);
  }
}