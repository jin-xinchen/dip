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

  /*
   public class Trader{private String name; private String city;}
   public calss Transaction{private Trader trader;int year;int value;}


  */

  List<Transaction> transaction = null;
  @Before
  public void before(){
    Trader raoul = new Trader("Raoul","Cambridge");
    Trader mario = new Trader("Mario","Milan");
    Trader alan = new Trader("Alan","Cambridge");
    Trader brian = new Trader("Brian","Cambridge");    

    transaction = Arrays.asList(
       new Transaction(brian,2011,300),
       new Transaction(raoul,2012,1000),
       new Transaction(raoul,2011,400),
       new Transaction(mario,2012,710),
       new Transaction(mario,2012,700),
       new Transaction(alan,2012,950)
    );
  }

//1.  in 2011
   @Test
   public void testTran1{
     transaction.stream()
        .filter((t)->t.getYear==2011)
        //.sorted()
          .sorted((t1,t2)->Integer.compare(t1.getValue(),t2.getValue()))
        .forEach(System.out::println);
   }
//2. list city
   @Test
   public void testTran2{

      transaction.stream()
           .map((t)->t.getTrader().getCity())
           .distinct()
           .forEach(System.out::println);

   }
//3.
   @Test
   public void testTran3{
      transaction.stream()
           .filter((t)->t.getTrader().getCity().equals("Cambridge"))
           .map(Transaction::getTrader)
           .sorted((t1,t1)->t1.getName().compareTo(t2.getName()))
           .distinct()
           .forEach(System.out::println);
   }
//4.
   @Test
   public void testTran4{
           transaction.stream()
           .map((t)->t.getTrader().getName())
           .sorted()
           .forEach(System.out::println);
       System.out.println("------------------");
     String str=  transaction.stream()
           .map((t)->t.getTrader().getName())
           .sorted()
           .reduce("",String::concat);
         
    //       .forEach(System.out::println);

      System.out.println(str);
       System.out.println("------------------");
     transaction.stream()
          .map((t)->t.getTrader().getName())
          .flatmap(StreamTips3.filterCharacter)
          //.sorted()
          .sorted((s1,s2)->s1.compareToIgnoreCase(s2))
          .forEach(System.out::print);
       System.out.println("------------------");     

   }
   public static Stream<String> filterCharacter(String str){
     List<String> list = new ArrayList<>();
     for(Character ch:str.toCharArray()){
       list.add(ch.toString());
     }
     return list.stream();
   }

//5.
   @Test
   public void testTran5{
     boolean b1 = transaction.stream()
              .anyMatch((t)->t.getTrader().getCity().equals("Milan"));
     System.out.println(b1);
   }
//6.
   @Test
   public void testTran6{
     Optional<Integer> sum = transaction.stream()
              .filter((e)->e.getTrader().getCity().equals("Cambridge"))
              .map(Transaction::getValue)
              .reduce(Integer::sum);

     System.out.println(sum.get());
   }
//7.
   @Test
   public void testTran7{
     Optional<Integer> max= transaction.stream()
              .map((t)->t.getValue())
              .max(Integer::compare);

     System.out.println(max.get());

   }
//8.
   @Test
   public void testTran8{
     Optional<Transaction> op= transaction.stream()
              .min((t1,t2)->Integer.compare(t1.getValue(),t2.getValue()));

     System.out.println(op.get());
   }
}
