packe com.dip.java8

import java.util.Collections;
import org.junit.Test;

/**
Consumer<T> void accept(T t);
Supplier<T> T get();
Function<T, R> R apply(T t);
Predicater<T>  boolean test(T t);

**/
//**
BiFunction<T,U,R>  R apply(T t, U u);
BinaryOperater<T>  T apply(T t1, T t2);

UnaryOperater<T> T apply(T t);


**/

public class Lambda03{
  
  //Consumer
  @Test
  public void test1(){
    play(100,(m)->System.out.println("play "+m));    

  }
  public void play(double dbPlay, Consumer<Double> con){
    con.accept(dbPlay);
  }

  //Supplier
  @Test
  public void test2(){
   List<Integer> numList = getNumList(10,()->(int)(Math.random()*100));
   for(Integer num:numList){
     System.out.println(num);
   }
  }

  public List<Integer> getNumList(int num, Supplier<Integer> sup ){
    List<Integer> list = new ArrayList<>();
    for(int i=0; i<num;i++){
      Integer n = sup.get();
      list.add(n);
    }
  }

  @Test
  public void test3(){
   String s=strHandler("\t\t\t my shang gui gu",(str)->{str.trim()});
   System.out.println(s);
   String s1=strHandler("",(str)->str.substring(2,3));
   System.out.println(s1);
  }
  public String strHandler(String str, Function<String,String> fun){
    return fun.apply(str);
  }

  //Predicate
  @Test
  public void test4(){

    List<String> list = Array.asList("Hi","Hello","Scarborough");

    List<String> strList = filterStr(list,(s)->s.length()>3);

    for(String str:strList){
       System.out.println(str);
    }
  
  }

  public List<String> filterStr(List<String> list, Predicate<String> pre){
    List<String> strList = new ArrayList<>();
    for(String str:list){
      if(pre.test(str)){
        strList.add(str);
      }
    }
    return strList;
  }    

}
