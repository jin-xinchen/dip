packe com.dip.java8

import java.util.Comparator;
import java.util.TreeSet;
import org.junit.Test;

public class TestLambda{
  //inner class
  @Test
  public void test1(){
    Comparator<Integer> com= new Comparator<Integer>(){
     @Override
     public int compare(Integer i1, Integer i2){
        return Integer.compare(i1,i2);
     }
    };
    
    TreeSet<Integer> ts = new TreeSet<>(com);
  }
  
  //Lambda
  @Test
  public void test2(){
    Comparator<Integer> com=(x,y)=>Integer.compare(x,y);
    TreeSet<Integer> ts = new TreeSet<>(com);
  }

  //Retrieve employee coming 10
  @Test 
  public void test3(){
    List<Employee> list = filterEmployee(employees);
    for(Employee employee: list){
       System.out.println(employee);
    }
  } 


  List<Employee> employees =Arrays.asList(
     new Employee("c1",18, 3000.00),
     new Employee("c2",180, 31000.00)
  );

  public List<Employee> filterEmployee(List<Employee> list){
     List<Employee> emps = new ArrayList<>();
     for(Employee emp: list){
        if(emp.getAge()>=10){
           emps.add(emp);
         }
     }
     return emps;
  }
 
//option 1 using interface
  public List<Employee> filterEmployee(List<Employee> list, 
     MyPredicate<Employee> mp){
     List<Employee> emps = new ArrayList<>();
     for(Employee employee: list){
       if(mp.test(employee)){
          emps.add(employee);
       }
     }
   }    
  @Test 
  public void test4(){
    List<Employee> list = filterEmployee(employees, new FilterEmployeeByAge());

    for(Employee employee: list){
      System.out.println(employee);
    }
//
    List<Employee> list2 = filterEmployee(employees, new FilterEmployeeBySalary());

    for(Employee employee: list2){
      System.out.println(employee);
    }


  }

//option 2：Anonymous inner class
  @Test
  public void test5(){
    List<Employee> list = filterEmployee(Employee, 
       new MyPredicate<Employee>(){
       @Override
        public boolean test(Employee t){
         return t.getSalary() <= 1000;
        }
    });

    for(Employee employee: list){
       System.out.println(employee);
    }
  }

//option3 
// Lambda

  @Test 
  public void test6(){
    List<Employee> list = filterEmployee(employees, (e) => e.getSalary() <= 1000;);
    
    list.forEach(System.out::println);
  }
//option 4 Stream API
  @Test
  public void test7(){
    employees.stream()
             .filter((e)=>e.getSalary()>=1000)
             .limit(2)
             .forEach(System.out::println);

    //----------------
    employees.stream()
             .map(Employee::getName)
             .forEach(System.out:println);
  }



}

//option 1 using interface
//Strategy Design Pattern策略设计模式
public interface MyPredicate<T>{
  public boolean test(T t);
}

public class FilterEmployeeByAge implements MyPredicate<Employee>{
  @Overide
  public boolean test(Employee t){
    return t.getAge() >=10;
  }
}

public class FilterEmployeeBySalary implements MyPredicate<Employee>{
  @Overide
  public boolean test(Employee t){
    return t.getSalary() >=1000;
  }
}

