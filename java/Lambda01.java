packe com.dip.java8

import org.junit.Test;

//Lambda for @FunctionalInterface
//()->{};  (parameters list)->{};
//()->{xx;  return v;}
public class Lambda01{
 
  @Test
  public void test1(){
    int num = 0;         //for jdk1.8 default is final
    final int num02 = 0; //for jdk1.7

    Runnable r = new Runnable(){
      @Override
      public void run(){
        System.out.println("Hello World!"+ num);
        // num default is final type. num++ is error.
        //System.out.println("Hello World!"+ num++);  
      }
    };

    r.run();

    //Lambda 1 ()->{};  (parameters list)->{};
    Runnable r1 = () -> System.out.println("Hello Lambda!");
    r1.run();

  }

  //@FunctionalInterface Consumer<T>
  @Test
  public void test2(){
    Consumer<String> con=(x)->System.out.println(x);
    con.accept("This is from li he fei");
  }

  //(x,y)->{};
  @Test
  public void test3(){
     Comparator<Integer> com = (x,y) -> {
        System.out.println("FunctionalInterface");
        return Integer.compare(x,y);
     };
  }

  @Test
  public void test4(){
     //ignore the return word in the sentence
     Comparator<Integer> com = (x,y)->Integer.compare(x,y);
     
  }

  public void show(Map<String, Integer> map){}

  @Test
  public void test5(){
     //data type default ingore 
     //Comparator<Integer> com = (Integer x, Integer y)->Integer.compare(x,y);

     String[] strs ={"a","b","cc"};
     //
     //String[] strs2;
     //strs2 = {"a","b","cc"}; // error
     //data type of ArrayList ingore
     List<String> list = new ArrayList<>();
  
     show (new HashMap<>()); // In JDK 1.7, it is error but in JDK1.8 it is ok.

  } 

  @Test
  public void Test6(){

    Integer num = opteration(100, (x)->x*x);
    num = opteration(100, (x)->{x*x});       //  ok too
    num = opteration(100, (x)->{return x*x}); // ok too
    System.out.println(num);

    System.out.println(operation(200,(y)->y+200));

  }

  public Integer operation(Integer num, MyFun mf){
    return mf.getValue(num);
  }

}



@FunctionalInterface
public interface MyFun{
  public Integer getValue(Integer num);
}
@FunctionalInterface
// There is only a function.
public interface MyPredicate<T>{

  public boolean test(T t);

}


