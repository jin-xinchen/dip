package com.jin.tool.json.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class testGson02 {
    public static void main(String[] args) {
        Set<String> userSet = new HashSet<String>();
        userSet.add("Alex");
        userSet.add("Brian");
        userSet.add("Charles");

        Gson gson = new Gson();

        String jsonString= gson.toJson(userSet);

        System.out.println(jsonString);

//        作者：三分恶
//        链接：https://juejin.cn/post/6844904175835283469
        String jsonString1 = "['Alex','Brian','Charles','Alex']";

        Gson gson1 = new Gson();

        java.lang.reflect.Type setType = new TypeToken<HashSet<String>>(){}.getType();

        Set<String> userSet1 = gson1.fromJson(jsonString, setType);

        System.out.println(userSet1);
        //要配置Gson实例以输出null，我们必须使用GsonBuilder对象的serializeNulls（）。
        //Gson中实现的默认行为是忽略空对象字段。
        Gson gson2 = new GsonBuilder()
                .serializeNulls()
                .create();

    }
    static void String2ClassWithProperties(){
        String departmentJson = "{'id' : 1, "
                + "'name': 'HR',"
                + "'users' : ["
                + "{'name': 'Alex','id': 1}, "
                + "{'name': 'Brian','id':2}, "
                + "{'name': 'Charles','id': 3}]}";
        Gson gson = new Gson();
        Department department = gson.fromJson(departmentJson, Department.class);
        System.out.println(department);
    }
    static void Array2List(){
        String userJson = "[{'name': 'Alex','id': 1}, " + "{'name': 'Brian','id':2}, " + "{'name': 'Charles','id': 3}]";

        Gson gson = new Gson();

        java.lang.reflect.Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();

        ArrayList<User> userArray = gson.fromJson(userJson, userListType);

        for (User user : userArray) {
            System.out.println(user);
        }
    }
    static void Array2ClassArray(){
        String userJson = "[{'name': 'Alex','id': 1}, "
                + "{'name': 'Brian','id':2}, "
                + "{'name': 'Charles','id': 3}]";

        Gson gson = new Gson();

        User[] userArray = gson.fromJson(userJson, User[].class);

        for(User user : userArray) {
            System.out.println(user);
        }
    }
}
