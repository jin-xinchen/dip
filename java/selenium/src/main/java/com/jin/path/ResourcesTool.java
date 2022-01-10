package com.jin.path;

import java.io.File;
import java.io.IOException;

/**
 * From : http s : / / blog.csdn.net/oschina_40188932/article/details/78833754
 */
public class ResourcesTool {
    public static void main(String[] args) throws IOException {
     ResourcesTool rt = new ResourcesTool();
        rt.getPath();
    }
    public void getPath() throws IOException {
        String fileName = this.getClass().getClassLoader().getResource("resources-txt.txt").getPath();
//        String fileUtl = this.getClass().getResource("resources-txt.txt").getFile();//no work

        File directory = new File("");
        String courseFile = directory.getCanonicalPath();
        String author = directory.getAbsolutePath();

        java.net.URL uri = this.getClass().getResource("/");
        String property = System.getProperty("user.dir");


    }
}
