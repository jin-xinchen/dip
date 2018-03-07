import org.junit.Test;

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

    //Lambda
    Runnable r1 = () -> System.out.println("Hello Lambda!");
    r1.run();

  }

}