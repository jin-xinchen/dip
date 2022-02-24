package com.jin.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class handleTxt02 {
    public static void main1(String[] args) {
        //step 1 分行 row file to create output/zipcodes01/FSA-02.txt
//        Path pathFolderTarget;
//        pathFolderTarget = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-02.txt");
//        Path pathFolderSrc;
//        pathFolderSrc = Paths.get(System.getProperty("user.dir"), "zipcodes/FSA-02.txt");
//        handleTxt02.createFSA01(pathFolderSrc.toString(),pathFolderTarget.toString());

//      step 2 copy a file for manually handle
        Path t0 = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-02-01.txt");
        String src2 = t0.toString();
//        try {
//            copy(pathFolderTarget.toString(),src2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//https://www.postalcodesincanada.com/
        //step 3 //output/zipcodes01/FSA-01-01.txt, create FSA-E-NB.txt,FSA-G-QC.txt
        //    	NB 	QC 	ON 	MB 	SK 	AB 	BC 	NU/NT 	YT
        //   	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y
        Path pathFolder2;
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-E-NB.txt");
        String wf = String.valueOf(pathFolder2);
        handleFSA0101(src2, wf,"E");

        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-G-QC.txt");
        wf = String.valueOf(pathFolder2);
        handleFSA0101(src2,wf,"G");
    }
    public static void main2(String[] args) {
        Path pathFolder2;
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-B-NS.txt");
        String src = String.valueOf(pathFolder2);
        String targetFileName = "FSA-B-NS";//System.nanoTime();
        try {
            createExcel(src,targetFileName,"FSA-B-NS");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        createFSAxlsx("FSA-G-QC-02");
//        createFSAxlsx("FSA-C-PE");
    }
    public static void createFSAxlsx(String tag){
        Path pathFolder2;
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/"+tag+".txt");
        String src = String.valueOf(pathFolder2);
        String targetFileName = tag;//"FSA-C-PE";//System.nanoTime();
        try {
            createExcel(src,targetFileName,tag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //LISTING OF FORWARD SORTATION AREA (FSA) CODES
    public static void createExcel(String src,String targetFileName,String sheetName) throws FileNotFoundException {
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
    public static void createFSA01(String src, String wf){
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
                String s = data.replaceAll(" E",line+"E");
                String s2= s.replaceAll(" G",line+"G");
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

    public static void handleFSA0101(String src, String wf, String key){
        try {

            File myObj = new File(src);
            Scanner myReader = new Scanner(myObj);
            FileWriter myWriter = new FileWriter(wf);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.substring(0,1).equals(key)) {
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
