package com.jin.tool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class fileToolForWindow {
    /**
     *
     * @param dir
     */
    public static void createDirectory(String dir){
//        String dir = "C:\\\\temp\\\\temp1\\\\temp2\\\\temp3";
        File directory = new File(dir);
        if (directory.mkdir()) {
//            System.out.println("Success using alternative 1");
        }
        else {
            if (directory.mkdirs()) {
//                System.out.println("Success using alternative 2");
            } else {
//                System.out.println("Failed using both alternative 1 and alternative 2");
            }
        }
    }
    public static Path getScreenshotPath() {
        Path screenshotFolder;
        screenshotFolder = Paths.get(System.getProperty("user.dir"), "output/screenshots/");

        if (Files.notExists(screenshotFolder)) {
//                Files.createDirectory(screenshotFolder);
            com.jin.tool.fileToolForWindow.createDirectory(screenshotFolder.toString());
        }
        String fileName = "Screenshot" + "_" + System.nanoTime();
        Path screenshotPath = Paths.get(screenshotFolder.toString(), fileName + ".png");
        return screenshotPath;

    }
    /**
     * com.jin.tool.fileToolForWindow.createImageDemo();
     * @throws IOException
     */
    public static void createImageDemo() throws IOException {
        Path folder;
        folder = Paths.get(System.getProperty("user.dir"), "MyFile.png");
        BufferedImage img = new BufferedImage(
                500, 500, BufferedImage.TYPE_INT_RGB );
        String s = folder.toString();
        File f = new File(s);
        int r = 5;
        int g = 25;
        int b = 255;
        int col = (r << 16) | (g << 8) | b;
        for(int x = 0; x < 500; x++){
            for(int y = 20; y < 300; y++){
                img.setRGB(x, y, col);
            }
        }
        r = 0;
        g = 153;
        b = 0;
        col = (r << 16) | (g << 8) | b;
        for(int x = 0; x < 500; x++){
            for(int y = 300; y < 500; y++){
                img.setRGB(x, y, col);
            }
        }
        r = 255;
        g = 5;
        b = 5;
        col = (r << 16) | (g << 8) | b;
        for(int x = 0; x < 500; x++){
            for(int y = 0; y < 20; y++){
                img.setRGB(x, y, col);
            }
        }
        ImageIO.write(img,"PNG", f);
    }
}
