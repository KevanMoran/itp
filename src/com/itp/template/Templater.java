/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.template;

import com.itp.images.Area;
import com.itp.images.Wheel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Kevan
 */
public class Templater {

    private static Model model;

    public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        model = new Model(args[0]);
        processWordTemplate();
        processRadarTemplate();
        String nextFileName = getNextFileName(new File("c:\\itp"));
        zipDirectory(new File("C:\\ITP\\Template"), new File(nextFileName));
        Runtime.getRuntime().exec(FindWord.findWord() + " " + nextFileName);
    }

    private static void processRadarTemplate() throws FileNotFoundException, IOException {
        ArrayList<Area> areas = Wheel.getAreas();
        Path path = Paths.get("c:\\itp\\chart1.xml");
        FileOutputStream fos = new FileOutputStream(new File("c:\\itp\\template\\word\\charts\\chart1.xml"));
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            List<String> lines = Files.readAllLines(path);
            int cvAreaNumber = 0;
            int valueAreaNumber = 0;
            DecimalFormat twoDP = new DecimalFormat("0.00");        
            for (String line : lines) {
                try {
                    if (line.contains("<c:v>")) {
                        double score = areas.get(cvAreaNumber).getScore();
                        String value = twoDP.format(score);
                        line = "                                    <c:v>" + value + "</c:v>";
                        cvAreaNumber++;
                    }
//                    if (line.contains("[[Value]]")) {
//                        double score = areas.get(valueAreaNumber).getScore();
//                        String value = twoDP.format(score);
//                        line = "                                            <a:t>" + value + "</a:t>";
//                        valueAreaNumber++;
//                    }
                    bw.write(line);
                    //System.out.println(line);
                } catch (IOException ex) {
                }
            }
        }
    }

    private static void processWordTemplate() throws IOException, XPathExpressionException {
        Path path = Paths.get("c:\\itp\\document.xml");
        FileOutputStream fos = new FileOutputStream(new File("c:\\itp\\template\\word\\document.xml"));
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            List<String> lines = Files.readAllLines(path);
            lines.stream().forEach((line) -> {
                try {
                    line = processLine(line);
                    bw.write(line);
                    //System.out.println(line);
                } catch (IOException ex) {
                } catch (XPathExpressionException ex) {
                    Logger.getLogger(Templater.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private static String processLine(String line) throws XPathExpressionException {

        String newLine = "";
        int bra = line.indexOf("[[");
        while (bra != -1) {
            newLine += line.substring(0, bra);
            int ket = line.indexOf("]]");
            String keyWord = line.substring(bra + 2, ket);
            newLine += model.replace(keyWord);
            if (line.length() == ket) {
                line = "";
            } else {
                line = line.substring(ket + 2);
            }
            bra = line.indexOf("[[");
        }
        newLine += line;
        return newLine;
    }

    public static void zipDirectory(File dir, File zipFile) throws IOException {
        FileOutputStream fout = new FileOutputStream(zipFile);
        ZipOutputStream zout = new ZipOutputStream(fout);
        zipSubDirectory("", dir, zout);
        zout.close();
    }

    private static void zipSubDirectory(String basePath, File dir, ZipOutputStream zout) throws IOException {
        byte[] buffer = new byte[4096];
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String path = basePath + file.getName() + "/";
                zout.putNextEntry(new ZipEntry(path));
                zipSubDirectory(path, file, zout);
                zout.closeEntry();
            } else {
                FileInputStream fin = new FileInputStream(file);
                zout.putNextEntry(new ZipEntry(basePath + file.getName()));
                int length;
                while ((length = fin.read(buffer)) > 0) {
                    zout.write(buffer, 0, length);
                }
                zout.closeEntry();
                fin.close();
            }
        }
    }

    private static String getNextFileName(File dir) {
        String prefix = "ITPReport";
        int maxNo = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.startsWith(prefix)) {
                    int dot = name.indexOf(".");
                    int digits = Integer.parseInt(name.substring(dot - 2, dot));
                    if (maxNo < digits) {
                        maxNo = digits;
                    }
                }
            }
        }
        return "c:\\itp\\" + prefix + String.format("%02d", maxNo + 1) + ".docx";
    }

}
