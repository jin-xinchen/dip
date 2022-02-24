package com.jin.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class handleTxt01 {
    public static void main1(String[] args) {

//        Path pathFolder;
//        pathFolder = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-01.txt");
//        try {
//            createTxt(String.valueOf(pathFolder));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        handleTxt01 r = new handleTxt01();
//        r.readFSA();
        Path pathFolder2;
            pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-A-NL.txt");
//        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-B-NS.txt");
//        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-C-PE.txt");
        String wf = String.valueOf(pathFolder2);

        r.handleFSA0101(wf,"A");
    }
    public static void main(String[] args) {
        Path pathFolder2;
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-A-NL.txt");
        String src = String.valueOf(pathFolder2);
        String targetFileName = "FSA-A-NL";//System.nanoTime();
        try {
            createExcel(src,targetFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //LISTING OF FORWARD SORTATION AREA (FSA) CODES
    public static void createExcel(String src,String targetFileName) throws FileNotFoundException {
        Path pathFolder;
        pathFolder = Paths.get(System.getProperty("user.dir"), "output/zipcodesexcel/");
        if (Files.notExists(pathFolder)) {
//                Files.createDirectory(screenshotFolder);
            com.jin.tool.fileToolForWindow.createDirectory(pathFolder.toString());
        }
        Path excelPath = Paths.get(pathFolder.toString(), targetFileName + ".xlsx");
        String path = excelPath.toString();//"E://demo.xlsx";
        String name = "A-NL";
        List<String> titles = Lists.newArrayList();
        titles.add("No.");
        titles.add("FSA");
        titles.add("DELIVERY/DISTRIBUTION CENTRE FACILITIES");
        List<Map<String, Object>> values = Lists.newArrayList();
        File myObj = new File(src);
        Scanner myReader = new Scanner(myObj);
        int i =0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Map<String, Object> map = Maps.newHashMap();
            map.put("No.", i + 1D);
            map.put("FSA", data.substring(0,3));
            map.put("DELIVERY/DISTRIBUTION CENTRE FACILITIES", data.substring(4));
            values.add(map);
            i++;
        }
        System.out.println(WriterExcelUtil.writerExcel(path, name, titles, values));

    }
    //output/zipcodes01/FSA-01-01.txt, create FSA-A-NL.txt,FSA-B-NS.txt, FSA-C-PE.txt
//    NL 	NS 	PE 	NB 	QC 	ON 	MB 	SK 	AB 	BC 	NU/NT 	YT
//    A 	B 	C 	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y
    public void handleFSA0101(String wf, String key){
        try {
            Path pathFolder;
            pathFolder = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-01-01.txt");
            File myObj = new File(String.valueOf(pathFolder));
            Scanner myReader = new Scanner(myObj);
//            Path pathFolder2;
//            pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-A-NL.txt");
//            String wf = String.valueOf(pathFolder2);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(key)) {//data.substring(0,1).equals("A")) {
                    String line = System.lineSeparator();
                    myWriter.append(data);
                    myWriter.append(line);
                }
            }
            myReader.close();
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //FSA-01.txt
    public void readFSA(){
        try {
            Path pathFolder;
            pathFolder = Paths.get(System.getProperty("user.dir"), "zipcodes/FSA-01.txt");
            File myObj = new File(String.valueOf(pathFolder));
            Scanner myReader = new Scanner(myObj);
            Path pathFolder2;
            pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-01.txt");
            String wf = String.valueOf(pathFolder2);
//            createTxt(wf);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
//                data.replaceAll("/^ [BC][0-9][A-Z]/g",",B");
                String line = System.lineSeparator();
                String s = data.replaceAll(" B",line+"B");
                String s2= s.replaceAll(" C",line+"C");
//                String[] sArr =  data.split(" ");
//                System.out.println(sArr.length);
//                for (String a: sArr
//                     ) {
//                    System.out.println(a);
//                }
                myWriter.append(s2);
                myWriter.append(line);
            }
            myReader.close();
            myWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTxt(String filenameWithPath) throws IOException {
        try {
            File myObj = new File(filenameWithPath);
            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
            } else {
//                System.out.println("File already exists.");
            }
//            FileWriter myWriter = new FileWriter(filenameWithPath);
//            myWriter.write("Files in Java might be tricky, but it is fun enough!");
//            String line = System.lineSeparator();
////            if ("\r\n".equals(line)) {undefined
////                System.out.println("windows");
////            } else if ("\n".equals(line)) {undefined
////                System.out.println("Mac");
////            }else  if ("\r".equals(line)) {undefined
////                System.out.println("linux/unix");
////            }
//            myWriter.append(line);
//            myWriter.append("\r\ntest");
//            myWriter.close();
        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
            throw e;
        }
    }
}
