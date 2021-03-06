packe com.dip.java8

import java.util.Optional;
import org.junit.Test;


public class InterfaceTips{
 
  default String getName(){
     return "My first interface function!";
   }

  public static void show(){
     System.out.println("show static function!");
  }   
   

}

public class MyClass{
   public String getName(){
     return "My Class function!";
   } 
}


public class SubClass extends MyClass implements InterfaceTips{
  public static void main(String[] agrs){

//class function is power
     SubClass sc = new SubClass();
     System.out.println(sc.getName());// out: "My Class function!";

//static function
    InterfaceTips.show();   
    
  }
}

//Multi Interface
public class MyInterface2{
 
  default String getName(){
     return "My second interface function!";
   } 

}


public class SubClass2  implements InterfaceTips,MyInterface2{
  @Override 
  public String getName(){
    return InterfaceTips.super.getName();
    //or
    //return MyInterface2.super.getName();
  }
}

/*
1.Default Methods in Interfaces

Java 8 introduced a new keyword default. Now you’re allowed to write a default implementation of a method in the interface. Such a method won’t be considered abstract anymore, and you won’t be forced to implement it in your class.


2.Static Methods in Interfaces

Starting from Java 8, interfaces are also allowed to include static methods, which are not specific to any instance and can be used only internally by other methods of the interface.

public interface testInter{
    public void test1(int i);
    public default void test2(int i){
        System.out.println("Default Method!");

        if(isJUNE()){System.out.println("There is a Static Method.");} 
    };

   //Static Method,which are not specific to any instance
   //and can be used only internally by other methods of the interface.
    static boolean isJUNE(){
        Month month = LocalDate.now().getMonth();
        if (month == Month.JUNE){
            return true;
        } else{
            return false;
        }
    } 
}


3.Java 8 introduced a special way of implementing functional interfaces using lambda expressions

public interface test1ForLambdaInter {
    public String test(String s);
}

public class MainTest{
  public static void main(String[] args) {

   test1ForLambdaInter  t= (arg) -> {
         return  "The arg is " + args;
     }; 

    String name="demo";    
    t(name); 
  }
}

 

System.out.println("JVM has a default character set:"
+Charset.defaultCharset());
The Java classes FileReader and FileWriter from the package java.io,
they work only with default character encoding. 

 

SortedMap map = Charset.availableCharsets();
  String[] charSets = new String[map.size()];
  int i = 0;
  for (Iterator iter = map.keySet().iterator(); iter.hasNext(); i++) {
    charSets[i] = (String)iter.next();
  } 

 

https://www.infoq.com/articles/Java9-New-HTTP-2-and-REPL

https://adopt-openjdk.ci.cloudbees.com/view/OpenJDK/job/langtools-1.9-linux-x86_64-kulla-dev/

http://hg.openjdk.java.net/kulla/dev

 
*/