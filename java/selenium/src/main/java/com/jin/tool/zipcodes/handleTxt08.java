package com.jin.tool.zipcodes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jin.tool.WriterExcelUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class handleTxt08 {
    static int n =8;
    static Path pathFolderSrc = Paths.get(System.getProperty("user.dir"), "zipcodes/FSA-0"+n+".txt");//origin txt file
    static Path pathFolderTarget = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-0"+n+".txt");
    static Path t0 = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-0"+n+"-01.txt"); // copy for origin txt file
    //step 1 create FSA-0n.txt with Single Col
    public static void main1(String[] args) {
        //step 1 分行 row file to create output/zipcodes01/FSA-02.txt
        String [] keys= {"L","M","N"};
        createFSAtoSingleCol(pathFolderSrc.toString(),pathFolderTarget.toString(),keys[0],keys[1],keys[2]);
    }
    //step 2 to step3 make a copy for manually handle
    public static void main2(String[] args) {
////      step 2 copy a file for manually handle
        String src2 = t0.toString();
        try {
            copy(pathFolderTarget.toString(),src2);
        } catch (IOException e) {
            e.printStackTrace();
        }

//https://www.postalcodesincanada.com/
//        step 3 //output/zipcodes01/FSA-01-01.txt, create FSA-E-NB.txt,FSA-G-QC.txt
//            	NB 	QC 	ON 	MB 	SK 	AB 	BC 	NU/NT 	YT
//           	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y

        Path pathFolder2;
//        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-E-NB.txt");
//        String wf = String.valueOf(pathFolder2);
//        handleFSA0101(src2, wf,"E");
//
//        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-G-QC-02.txt");
//        String wf2 = String.valueOf(pathFolder2);
//        handleFSA0101(src2,wf2,"G");

    }
    //Step 3 create FSA-J-MB-02.txt with single Letter
    public static void main3(String[] args) {
        String src2 = t0.toString();
//https://www.postalcodesincanada.com/
//        step 3 //output/zipcodes01/FSA-01-01.txt, create FSA-E-NB.txt,FSA-G-QC.txt
//            	NB 	QC 	        ON 	                MB 	SK 	AB 	BC 	NU/NT 	YT
//           	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y
//      ON:  K 	L 	M 	N 	P
        //QC:G 	H 	J
        Path pathFolder2;String wf2;
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-L-ON-03.txt");
        wf2 = String.valueOf(pathFolder2);
        handleFSAwithSingleLetter(src2,wf2,"L");

        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-M-ON.txt");
        wf2 = String.valueOf(pathFolder2);
        handleFSAwithSingleLetter(src2,wf2,"M");

        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-N-ON-01.txt");
        wf2 = String.valueOf(pathFolder2);
        handleFSAwithSingleLetter(src2,wf2,"N");
    }
    //step 4 create xlsx file
    public static void main4(String[] args) {
        //step 1 分行 row file to create output/zipcodes01/FSA-02.txt
////      step 2 copy a file for manually handle
//https://www.postalcodesincanada.com/
//        step 3 //output/zipcodes01/FSA-01-01.txt, create FSA-E-NB.txt,FSA-G-QC.txt
//            	NB 	QC 	ON 	MB 	SK 	AB 	BC 	NU/NT 	YT
//           	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y
        Path pathFolder2;
        String name="FSA-L-ON-03";
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/"+name+".txt");
        String src2 = String.valueOf(pathFolder2);
        String targetFileName2 = name;//System.nanoTime();
        try {
            createExcel(src2,targetFileName2,name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        name="FSA-M-ON";
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/"+name+".txt");
        src2 = String.valueOf(pathFolder2);
        targetFileName2 = name;//System.nanoTime();
        try {
            createExcel(src2,targetFileName2,name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        name="FSA-N-ON-01";
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/"+name+".txt");
        src2 = String.valueOf(pathFolder2);
        targetFileName2 = name;//System.nanoTime();
        try {
            createExcel(src2,targetFileName2,name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public static void createExcel(String src,String targetFileName, String sheetName) throws FileNotFoundException {
        Path pathFolder;
        pathFolder = Paths.get(System.getProperty("user.dir"), "output/zipcodesexcel/");
        if (Files.notExists(pathFolder)) {
//                Files.createDirectory(screenshotFolder);
            com.jin.tool.fileToolForWindow.createDirectory(pathFolder.toString());
        }
        Path excelPath = Paths.get(pathFolder.toString(), targetFileName + ".xlsx");
        String path = excelPath.toString();//"E://demo.xlsx";
        String name = sheetName;//"A-NL";
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
    public static void createFSAtoSingleCol(String src, String wf,String key1,String key2){
        try {
            File srcFile = new File(src);
            Scanner myReader = new Scanner(srcFile);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
//                data.replaceAll("/^ [BC][0-9][A-Z]/g",",B");
                String line = System.lineSeparator();
                String s = data.replaceAll(" "+key1,line+key1);
                if(!key2.equals("")) {
                    String s2 = s.replaceAll(" " + key2, line + key2);
                    myWriter.append(s2);
                }else{
                    myWriter.append(s);
                }
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
    public static void createFSAtoSingleCol(String src, String wf,String key1,String key2,String key3){
        try {
            File srcFile = new File(src);
            Scanner myReader = new Scanner(srcFile);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z] ");
                Matcher m = p.matcher(data);
                String key11 = key1; key1=""; key2="";key3="";
                List<String> keys = new ArrayList<String>();
                while(m.find()){
                    String k = m.group();
                    keys.add(k);
                }
//                Character.isDigit()//Character.isLetterOrDigit(char c);
                String line = System.lineSeparator();
                for(int i=0;i<keys.size();i++ ) {
                    String s = keys.get(i);
                    data = data.replaceAll(s,line+s);
                }
                myWriter.append(data);
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

    public static void createFSAtoSingleColForCol01(String src, String wf,String key1,String key2,String key3){
        try {
            File srcFile = new File(src);
            Scanner myReader = new Scanner(srcFile);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z] ");
                Matcher m = p.matcher(data);
                int n =0;String key="";int colStart=0;int colEnd=0;int col =0;
                while(m.find()){
                    if(n==col) {
                        key = m.group();
                        colStart = m.start();
                    }
                    if(n==col+1) {
                        colEnd = m.start();
                    }
                    n++;
                }
                String line = System.lineSeparator();
                String s = data.substring(colStart,colEnd);
                String s2 = s.replaceAll(" "+key,line+key);
                myWriter.append(s2);
                myWriter.append(line);
            }
            myReader.reset();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z] ");
                Matcher m = p.matcher(data);
                int n =0;String key="";int colStart=0;int colEnd=0;int col =1;
                while(m.find()){
                    if(n==col) {
                        key = m.group();
                        colStart = m.start();
                    }
                    if(n==col+1) {
                        colEnd = m.start();
                    }
                    n++;
                }
                String line = System.lineSeparator();
                String s = data.substring(colStart,colEnd);
                String s2 = s.replaceAll(" "+key,line+key);
                myWriter.append(s2);
                myWriter.append(line);
            }
            myReader.reset();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(" ")) {
                    data = data.substring(1);
                }
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z] ");
                Matcher m = p.matcher(data);
                int n =0;String key="";int colStart=0;int colEnd=0;int col =2;
                while(m.find()){
                    if(n==col) {
                        key = m.group();
                        colStart = m.start();
                    }
                    if(n==col+1) {
                        colEnd = m.start();
                    }
                    n++;
                }
                String line = System.lineSeparator();
                String s = data.substring(colStart,colEnd);
                String s2 = s.replaceAll(" "+key,line+key);
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

    public static void handleFSAwithSingleLetter(String src, String wf, String key){
        try {

            File myObj = new File(src);
            Scanner myReader = new Scanner(myObj);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.equals("")){
                    continue;
                }
                if(data.substring(0,1).equals("*")){
                    System.out.println(data);
                    if(data.substring(1,2).equals(key)) {
                        String line = System.lineSeparator();
                        myWriter.append(data.substring(1) + " *");
                        myWriter.append(line);
                    }
                }else{
                    if(data.substring(0,1).equals(key)) {
                        String line = System.lineSeparator();
                        myWriter.append(data);
                        myWriter.append(line);
                    }
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
    //    @Test
    public static void copy(String strOriginal,String strCopied)
            throws IOException {
        File copied = new File(strCopied);
        File original = new File(strOriginal);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(original));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }

//        assertThat(copied).exists();
//        assertThat(Files.readAllLines(original.toPath())
//                .equals(Files.readAllLines(copied.toPath())));
    }
}
