package beans;

public class User {
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private String name;
    private String password;

    public boolean checkUserName() {
        boolean result = false;

        return result;
    }
    public boolean checkPassword() {
        boolean result = false;

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
    }

}
