package beans;
import java.sql.*;

import javax.servlet.ServletException;
import javax.mail.Session;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Request;

public class SqlCheck {
   private boolean ok=false;//状态变量
   private String name=null;
   private String pass=null;

   public boolean check()throws Exception{
       checkSQL(this.name,this.pass);
       return this.ok;
   }
   public void setName(String name){
           this.name = name;
   }
   public String getName(){
           return name;
   }
   public void setPass(String pass){
           this.pass = pass;
   }
   public String getPass(){
           return pass;
   }

   //查询数据库，如果数据库没有这个用户就把状态变量设置为false，如果有则设置为true
  private void checkSQL(String name,String pass) throws Exception{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/user?user=root&password=数据库连接密码&characterEncoding=UTF-8";
        Connection con=DriverManager.getConnection(url);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from users where name='"+name+"' and pass='"+pass+"'");
        if(rs.next()){//表示有记录

//          
                   this.ok=true;

                }else{
                   this.ok=false;
                }
   }
}