package beans;

import java.sql.*;

import javax.servlet.ServletException;
//import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

public class SqlCheck {
    private boolean ok = false;
    private String name = null;
    private String pass = null;

    public boolean checkAlreadyExist() throws Exception {
        checkSQL(this.name, this.pass);
        return this.ok;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    private void checkSQL(String name, String pass) throws Exception {
//        Class.forName("com.mysql.jdbc.Driver");
//        String url = "jdbc:mysql://localhost:3306/user?user=root&password=x?&characterEncoding=UTF-8";
//        Connection con = DriverManager.getConnection(url);
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery("select * from users where name='" + name + "' and pass='" + pass + "'");
//        if (rs.next()) {
//            this.ok = true;
//
//        } else {
//            this.ok = false;
//        }
        this.ok=true;
    }
}
