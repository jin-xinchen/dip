package com.jin.tool;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class handleTxt03 {
    public static void main(String[] args) {
        int n = 3;
        //step 1 分行 row file to create output/zipcodes01/FSA-02.txt
        Path pathFolderTarget;
        pathFolderTarget = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-0"+n+".txt");

//        Path pathFolderSrc;
//        pathFolderSrc = Paths.get(System.getProperty("user.dir"), "zipcodes/FSA-0"+n+".txt");
//        handleTxt02.createFSA01(pathFolderSrc.toString(),pathFolderTarget.toString());

////      step 2 copy a file for manually handle

        Path t0 = Paths.get(System.getProperty("user.dir"), "output/zipcodes01/FSA-0"+n+"-01.txt");
        String src2 = t0.toString();
//        try {
//            copy(pathFolderTarget.toString(),src2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//https://www.postalcodesincanada.com/
//        step 3 //output/zipcodes01/FSA-01-01.txt, create FSA-E-NB.txt,FSA-G-QC.txt
//            	NB 	QC 	ON 	MB 	SK 	AB 	BC 	NU/NT 	YT
//           	E 	G 	H 	J 	K 	L 	M 	N 	P 	R 	S 	T 	V 	X 	Y

        Path pathFolder2;
//        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-E-NB.txt");
//        String wf = String.valueOf(pathFolder2);
//        handleFSA0101(src2, wf,"E");
//
        pathFolder2 = Paths.get(System.getProperty("user.dir"), "output/zipcodes02/FSA-G-QC-02.txt");
        String wf2 = String.valueOf(pathFolder2);
        handleFSA0101(src2,wf2,"G");

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
