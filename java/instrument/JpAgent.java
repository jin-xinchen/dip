package com.jin.instrument;

import java.lang.instrument.Instrumentation;

/**
 * http s : / / juejin.cn/post/6844904035305127950   作者：蒋老湿
 *创建一个普通的类，内含静态方法premain(),这个方法名是java agent内定的方法名，它总会在main函数之前执行
 *
 * 如上有2个premain()方法，当1个参数和2个参数的premain()的方法同事存在的时候，premain(String agentArgs)将被忽略
 *
 * 在resource目录下新建META-INF/MANIFEST.MF文件，内容如下:
 * Manifest-Version: 1.0
 * Premain-Class: com.instrument.JpAgent
 * Can-Redefine-Classes: true
 * Can-Retransform-Classes: true
 * 复制代码以上内容可以通过Maven的org.apache.maven.plugins和maven-assembly-plugin插件配合完成，在mvn install的时候生成MANIFEST.MF文件内容
 *
 */
public class JpAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation)  {
        /*转换发生在 premain 函数执行之后，main 函数执行之前，这时每装载一个类，transform 方法就会执行一次，看看是否需要转换，
        所以，在 transform（Transformer 类中）方法中，程序用 className.equals("TransClass") 来判断当前的类是否需要转换。*/
        // 方式一：
        instrumentation.addTransformer(new JpClassFileTransformerDemo());
        System.out.println("我是两个参数的 Java Agent premain");
    }
    public static void premain(String agentArgs){
        System.out.println("我是一个参数的 Java Agent premain");
    }
}
/*
4. 对当前工程执行mvn clean install 打包，生成了jpAgent.jar文件
5. 新建一个maven工程example01，内含Main.java、Dog.java，并最终打包成example01-1.0-SNAPSHOT.jar
public class Dog { public String say() { return "dog"; } }
public class Main { public static void main(String[] args) { System.out.println("夜太黑"); System.out.println("----"+new Dog().say()); } }
6. 执行 jpAgent.jar 需要通过 -javaagent 参数来指定Java代理包，
> -javaagent 这个参数的个数是不限的，可以指定多个，会按指定的先后顺序执行，执行完各个 agent 后，才会执行主程序的 main 方法。
// 为了执行方便，把jar文件放在同一层级目录下
java -javaagent:jpAgent.jar -cp example01-1.0-SNAPSHOT.jar  cn.jpsite.learning.Main
```
其中`example01-1.0-SNAPSHOT.jar`的`Main()`方法只是简单的输出2行内容，通过agent代理后多输出了一段内容。

addTransformer方法
对 Java 类文件的操作，可以理解为对一个 byte 数组的操作（将类文件的二进制字节流读入一个 byte 数组）。开发者可以在 interface ClassFileTransformer的 transform 方法（通过 classfileBuffer 参数）中得到，操作并最终返回一个类的定义（一个 byte 数组），接下来演示下transform 转换类的用法，采用简单的类文件替换方式。

新建JpClassFileTransformerDemo.java 实现 ClassFileTransformer接口，getBytesFromFile() 方法根据文件名读入二进制字符流，而 ClassFileTransformer 当中的 transform 方法则完成了类定义的替换。import java.io.File;

JpAgent.java 增加instrumentation.addTransformer(new JpClassFileTransformerDemo());内容如下,重新打包public class JpAgent
        此时我们去修改之前的example01工程中的Dog.java, 把say()方法返回的字符串"dog"改为"cat", 然后编译成.class文件，getBytesFromFile("D:\\learning\\Dog.class")就是读取修改后的class文件。
        执行java -javaagent:jpAgent.jar -cp example01-1.0-SNAPSHOT.jar cn.jpsite.learning.Main

转换发生在 premain 函数执行之后，main 函数执行完成之前，这时每装载一个类，transform 方法就会执行一次，看看是否需要转换，所以，在 transform方法中，这里用了 className.equals("cn/jpsite/learning/Dog") 来判断当前的类是否需要转换。
除了用 addTransformer 的方式，Instrumentation 当中还有另外一个方法“redefineClasses”来实现 premain 当中指定的转换。用法类似，如下：
ClassDefinition def = new ClassDefinition(Dog.class, Objects.requireNonNull(JpClassFileTransformerDemo
                .getBytesFromFile("D:\\learning\\Dog.class")));
instrumentation.redefineClasses(new ClassDefinition[] { def });

 */