package servlet;

import beans.SqlCheck;

import beans.User;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Check extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get client parameters, then handle
        //return the length of string is 2 characters when the user already exist.
        //return the length is 1 characters when the user does not exist.
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        SqlCheck sc = new SqlCheck();
        User loginUser = new User(name,pass);
        try {
            if (sc.checkAlreadyExist(loginUser)) {
                out.print("ok"); //return a string that is 2 characters in length
                request.getSession().setAttribute("name", name);

            } else {
                out.print("e"); //return a string that is 1 characters in length
            }
        } catch (Exception e) {
            out.print(e.toString());
        }
        out.flush();
        out.close();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
