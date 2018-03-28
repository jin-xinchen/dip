package beans;

public class User {
    public User() {

    }
    
    private String user;
    private String password;


    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static void main(String[] args) {
        User user = new User();
    }
    
}
