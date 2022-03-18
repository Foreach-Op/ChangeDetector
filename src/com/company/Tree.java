package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {

    private Node root=null;
    private int size=1;

    public Tree(File file){
        this.root= new Node(file);
        this.root.isFolder=!file.isFile();
        if(file.isDirectory()){
            this.root.numOfFile=file.listFiles().length;
        }
        Arrays.stream(file.listFiles()).filter(s->!s.getName().startsWith("~$")).forEach(s-> System.out.println(s.getName()));
    }

    public Node getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void traversal(Node rootNode){
        if(rootNode==null)
            return;
        System.out.println(rootNode);
        if(rootNode.siblings.size()==0){
            return;
        }
        for (Node node : rootNode.siblings) {
            traversal(node);
        }
    }

    public String includeFiles(Node rootNode) throws IOException, NoSuchAlgorithmException {
        File file=rootNode.path;
        if(file.isFile()){
            //Node newNode=new Node(file);
            //ootNode.siblings.add(newNode);
            String checkSum=ChecksumCalculator.getFileChecksum(file);
            //newNode.checksum=checkSum;
            //size++;
            return checkSum;
        }
        File[] files=file.listFiles();
        if(files == null){
            return new String(new byte[64], StandardCharsets.UTF_8);
        }

        List<String> checks=new ArrayList<>();
        for (File tempFile : files) {
            Node newNode=new Node(tempFile);
            size++;
            rootNode.siblings.add(newNode);
            newNode.isFolder=!tempFile.isFile();
            if(newNode.isFolder)
                newNode.numOfFile=tempFile.listFiles().length;
            String returnedCheck=includeFiles(newNode);
            newNode.checksum=returnedCheck;
            checks.add(returnedCheck);
        }
        String rootCheck=ChecksumCalculator.addChecksums(checks);
        rootNode.checksum=rootCheck;
        return rootCheck;
    }

    public String getCheckSum(Node rootNode) throws IOException, NoSuchAlgorithmException {
        if(rootNode==null)
            return new String(new byte[64], StandardCharsets.UTF_8);
        if(!rootNode.path.exists()){
            rootNode=null;
            return new String(new byte[64], StandardCharsets.UTF_8);
        }

        if(rootNode.siblings.size()==0){
            String oldSum=rootNode.checksum;
            String currentSum=ChecksumCalculator.getFileChecksum(rootNode.path);
            if(!oldSum.equals(currentSum)){
                System.out.println(rootNode.path.getName()+" is changed!");
                rootNode.checksum=currentSum;
            }
            return rootNode.checksum;
        }

        List<String> checks=new ArrayList<>();
        for (Node node : rootNode.siblings) {
            String checkSum=getCheckSum(node);
            checks.add(checkSum);
        }
        String currentRootCheck=ChecksumCalculator.addChecksums(checks);
        String oldRootCheck=rootNode.checksum;
        if(!oldRootCheck.equals(currentRootCheck)){
            System.out.println(rootNode.path.getName()+" is changed!");
            rootNode.checksum=currentRootCheck;
        }
        return rootNode.checksum;
    }

    public void listenChange() throws IOException, NoSuchAlgorithmException {
        boolean isChange=false;
        while (!isChange){
            String check=getCheckSum(root);
            if(!check.equals(root.checksum)){
                System.out.println("Root has changed");
                isChange=true;
            }
        }
    }
}

class Node{
    Node(File path){
        this.path=path;
    }
    List<Node> siblings=new ArrayList<>();
    File path=null;
    String checksum=new String(new byte[64], StandardCharsets.UTF_8);
    boolean isFolder=false;
    int numOfFile=0;

    @Override
    public String toString() {
        return "Node{" +
                ", path=" + path +
                ", checksum='" + checksum + '\'' +
                ", isFolder=" + isFolder +
                ", numOfFile=" + numOfFile +
                '}';
    }
}