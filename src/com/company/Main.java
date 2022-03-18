package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File file = new File("C:\\Users\\oguzt\\OneDrive\\Masaüstü\\University Lectures\\Comp 341");
        File file2 = new File("C:\\Users\\oguzt\\OneDrive\\Masaüstü\\University Lectures\\Elec 201\\Matlab Projects\\Project 2");

        Tree tree=new Tree(file2);
        tree.includeFiles(tree.getRoot());
        //tree.traversal(tree.getRoot());
        //System.out.println(tree.getSize());
        tree.listenChange();
        System.out.println(file.getName());
    }
}
