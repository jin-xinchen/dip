package servlet;

import beans.SqlCheck;
import beans.User;

import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
  
public class dashboardIndex extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
              
        String name=request.getParameter("name");  
        out.print("Dashboard <br/>");
        out.print("Welcome "+name);  

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)  
        throws ServletException, IOException {  
        doGet(request, response);
    }  
  
}  