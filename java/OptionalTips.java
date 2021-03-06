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
    System.out.println(str2.get());   

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


  //Example:
/* //before Java8
  @Test
  public void test5(){
    Man man = new Man();
    String n = getGoddessName(man);//throw NullPointerException
    System.out.println(n);
  }

  public String getGoddessName(Man man){
    return man.getGoddess().getName();

    if(man!=null){
      Goddess gn = man.getGoddess();
      if(gn!=null){
        return gn.getName();
      }
    }
    return "zzz";
  }
*/
//Java8
  @Test
  public void test5(){

    Optional<NewMan> op = Optional.ofNullable(null);
    String str=getGoddessName2(op);
    System.out.println(str);//out: "defaultNullGoddess"

    Optional<NewMan> op2 = Optional.ofNullable(new NewMan());
    String str2=getGoddessName2(op2);
    System.out.println(str2);//out: "defaultNullGoddess"

    Optional<Goddess> gn=Optional.ofNullable(null);
    Optional<NewMan> op3 = Optional.ofNullable(new NewMan(gn));
    String str3=getGoddessName2(op3);
    System.out.println(str3);//out: "defaultNullGoddess"

    Optional<Goddess> gn4=Optional.ofNullable(new Goddess("LiHeFei"));
    Optional<NewMan> op4 = Optional.ofNullable(new NewMan(gn4));
    String str4=getGoddessName2(op4);
    System.out.println(str4);//out: "LiHeFei"

    
  }
  public String getGoddessName2(Optional<NewMan> man){
      return man.orElse(new NewMan())
                .getGoddess()
                .orElse(new Goddess("defaultNullGoddess"))
                .getName();
  }


}

//before Java8
public class Man{
  private Goddess goddess;
  public Man(){}
  public Man(Goddess goddess){this.goddess=goddess;}
  @Override
  public String toString(){
    return "Man [goddess="+goddess+"]";
  }
  //getMan
  //setMan  
}

public class NewMan{
  private Optional<Goddess> goddess = Optional.empty();
  public NewMan(){}
  public NewMan(Optional<Goddess> goddess){
    this.goddess=goddess;
  }
  public Optional<Goddess> getGoddess(){ return goddess;}
  //...
}

public class Goddess{
  private String name;
  public Goddess(){}
  public Goddess(String name){ this.name = name;}
  @Override
  public String toString(){
    return "Goddess [name="+name+"]";
  }
  public String getName(){return name;}
  public void setName(String name){this.name=name;}

}