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

    public boolean checkAlreadyExist(User user) throws Exception {
        checkSQLforAlreadyExist(user.getName(), user.getPassword());
        return this.ok;
    }

    private void checkSQLforAlreadyExist(String name, String pass) throws Exception {
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
