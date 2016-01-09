/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.template;

import java.io.File;

/**
 *
 * @author Kevan
 */
public class FindWord {
    public static String findWord() {
        File programFiles86 = new File("C:\\Program Files (x86)");
        String path = find(programFiles86); 
        if (path != null) {
            return path;
        }
        File programFiles  = new File("C:\\Program Files");
        path = find(programFiles); 
        return path;
    }
    
    public static String find(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File temp : files) {
            if (temp.isDirectory()) {
                String found =  find(temp);
                if (found != null) {
                    return found;
                }
            } else {
               if (temp.getName().equalsIgnoreCase("winword.exe")) {                   
                   return temp.getPath();
               }
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(findWord());
    }
}
