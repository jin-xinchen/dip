package com.jin.tool.zipcodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testPatern {
    public static void main(String[] args) {
        String s1="L2H NIAGARA FALLS ON LCD MAIN L5C MISSISSAUGA ON STN LCD 3 L7R BURLINGTON ON LCD 1";
        Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z] ");
        //A 	B 	C 	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y
        Matcher m = p.matcher(s1);
        int n =0;
        while(m.find()){
            System.out.println(++n);
            System.out.println(m.groupCount());
            System.out.println(m.group());//
            System.out.println(m.start());
        }
//        main2();
//        String s = "A876x";
//        Pattern pattern = Pattern.compile("(\\d)[^\\d]*$");
//        Matcher matcher = pattern.matcher(s);
//        if(matcher.find()){
//            System.out.println(matcher.group(1));//6
//            System.out.println(matcher.group());//
//        }

    }

    /**
     * Found value: This order was placed for QT3000! OK?
     * Found value: This order was placed for QT
     * Found value: 3000
     * Found value: ! OK?
     */
    public static void main2(  ){

        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }
    /*
    Mathcer.start()/ Matcher.end()/ Matcher.group()
当使用matches(),lookingAt(),find()执行匹配操作后,就可以利用以上三个方法得到更详细的信息.
start()返回匹配到的子字符串在字符串中的索引位置.
end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置.
group()返回匹配到的子字符串
Java代码示例:
Pattern p=Pattern.compile(“\d+”);
Matcher m=p.matcher(“aaa2223bb”);
m.find();//匹配2223
m.start();//返回3
m.end();//返回7,返回的是2223后的索引号
m.group();//返回2223

Mathcer m2=p.matcher(“2223bb”);
m2.lookingAt(); //匹配2223
m2.start(); //返回0,由于lookingAt()只能匹配前面的字符串,所以当使用lookingAt()匹配时,start()方法总是返回0
m2.end(); //返回4
m2.group(); //返回2223

Matcher m3=p.matcher(“2223”); //如果Matcher m3=p.matcher(“2223bb”); 那么下面的方法出错，因为不匹配返回false
m3.matches(); //匹配整个字符串
m3.start(); //返回0
m3.end(); //返回3,原因相信大家也清楚了,因为matches()需要匹配所有字符串
m3.group(); //返回2223

说了这么多,相信大家都明白了以上几个方法的使用,该说说正则表达式的分组在java中是怎么使用的.
start(),end(),group()均有一个重载方法它们是start(int i),end(int i),group(int i)专用于分组操作,Mathcer类还有一个groupCount()用于返回有多少组.
Java代码示例:
Pattern p=Pattern.compile(“([a-z]+)(\d+)”);
Matcher m=p.matcher(“aaa2223bb”);
m.find(); //匹配aaa2223
m.groupCount(); //返回2,因为有2组
m.start(1); //返回0 返回第一组匹配到的子字符串在字符串中的索引号
m.start(2); //返回3
m.end(1); //返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置.
m.end(2); //返回7
m.group(1); //返回aaa,返回第一组匹配到的子字符串
m.group(2); //返回2223,返回第二组匹配到的子字符串

现在我们使用一下稍微高级点的正则匹配操作,例如有一段文本,里面有很多数字,而且这些数字是分开的,我们现在要将文本中所有数字都取出来,利用java的正则操作是那么的简单.
Java代码示例:
Pattern p=Pattern.compile(“\d+”);
Matcher m=p.matcher(“我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com”);
while(m.find()) {
System.out.println(m.group());
}

输出:
456456
0532214
123

如将以上while()循环替换成
while(m.find()) {
System.out.println(m.group());
System.out.print(“start:”+m.start());
System.out.println(” end:”+m.end());
}
则输出:
456456
start:6 end:12
0532214
start:19 end:26
123
start:36 end:39

现在大家应该知道,每次执行匹配操作后start(),end(),group()三个方法的值都会改变,改变成匹配到的子字符串的信息,以及它们的重载方法,也会改变成相应的信息.
注意:只有当匹配操作成功,才可以使用start(),end(),group()三个方法,否则会抛出java.lang.IllegalStateException,也就是当matches(),lookingAt(),find()其中任意一个方法返回true时,才可以使用。


catoop
关注

————————————————
版权声明：本文为CSDN博主「catoop」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/catoop/article/details/50630143
     */
}
