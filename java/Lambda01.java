import org.junit.Test;

public class Lambda01{

  @Test
  public void test1(){
    Runnable r = new Runnable(){
      @Override
      public void run(){
        System.out.println("Hello World!");
      }
    };

    r.run();

    //Lambda
    Runnable r1 = () -> System.out.println("Hello Lambda!");
    r1.run();

  }

}