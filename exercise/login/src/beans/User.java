package beans;

public class User {
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private String name;
    private String password;

    public boolean verify() {
        boolean result = false;
        if (this.checkUserName() && this.checkPassword()) {
            result = true;
        }

        return result;
    }

    public boolean checkUserName() {
        boolean result = false;
        String pattern1 = "^[a-zA-Z0-9]{3,20}$"; 
        result = this.name.matches(pattern1);
        return result;
    }

    public boolean checkPassword() {
        boolean result = false;
        String specialchars = "!\"#$%&'()\\*\\+,-\\./:;<=>\\?@\\[\\]\\^_`{\\|}";
        String pattern1 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + specialchars + "])(?=\\S+$).{6,}";
        result = this.password.matches(pattern1);
        return result;
    }

    @Override
    public String toString() {
        return "Name: " + this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static void main(String[] args) {
        User user = new User("userOne", "");
        System.out.println(user);
        user.test1();

    }
    
    public void test1(){
            String namePatt = "^[a-zA-Z0-9]{3,20}$";
            String names[] = {
                "12", "123456","123456a8901234567890","123456a8901234567890a", "a12345", "Aa1234", "A1234^", "Aa1@34", "_= As123", "!aA123",
                "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}Aa123" };
            for (String name : names) {
                System.out.println(name + " meet requirement? " + name.matches(namePatt));
            }
            //special Characters
            String specialchars = "!\"#$%&'()\\*\\+,-\\./:;<=>\\?@\\[\\]\\^_`{\\|}";
            String passwd[] = {
                "12", "123456", "a12345", "Aa1234", "A1234^", "Aa1@34", "_= As123", "!aA123",
                "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}Aa123" };

            System.out.println("no space============================================");
            String pattern1 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + specialchars + "])(?=\\S+$).{6,}";
            for (String pwd : passwd) {
                System.out.println(pwd + " meet requirement? " + pwd.matches(pattern1));
            }

            System.out.println("----------------------------\n");
            System.out.println("space be=================================");
            String pattern2 = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[" + specialchars + "])(?=).{6,}";
            for (String pwd : passwd) {
                System.out.println(pwd + " meet requirement? " + pwd.matches(pattern2));
            }
        }
    

}
