package com.jin.tool.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class testGson {
    public static void main(String[] args) {

    }
    static void format2String(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Employee employeeObj = new Employee(1, "Lokesh", "Gupta", "howtogoinjava@gmail.com");
        System.out.println(gson.toJson(employeeObj));
    }
    static void string2Class(){
        String jsonString1 = "{'id':1001, 'firstName':'Lokesh', 'lastName':'Gupta', 'email':'howtodoinjava@gmail.com'}";
        Gson gson1 = new Gson();
        Employee empObject = gson1.fromJson(jsonString1, Employee.class);
        System.out.println(empObject);
    }
    static void clase2String(){
        Employee emp = new Employee(1001, "Lokesh", "Gupta", "howtodoinjava@gmail.com");
        Gson gson = new Gson();
        String jsonString = gson.toJson(emp);
        System.out.println(jsonString);

//        作者：三分恶
//        链接：https://juejin.cn/post/6844904175835283469
    }
}


