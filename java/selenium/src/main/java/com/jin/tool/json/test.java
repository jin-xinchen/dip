package com.jin.tool.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://github.com/alibaba/fastjson
//https://www.runoob.com/w3cnote/java-json-instro.html
public class test {
    public static void main(String[] args) {
        String s = "A0A, A0B, A0C\n";
        Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z]");
        Matcher m = p.matcher(s);
        while(m.find()){
            String k = m.group();
            System.out.println(k);

        }
    }
}

