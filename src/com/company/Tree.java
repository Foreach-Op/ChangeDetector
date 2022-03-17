package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Tree {

    private Node root=null;
    private int size=1;

    public Tree(File file){
        this.root= new Node(file);
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
            String returnedCheck=includeFiles(newNode);
            newNode.checksum=returnedCheck;
            checks.add(returnedCheck);
        }
        String rootCheck=ChecksumCalculator.addChecksums(checks);
        rootNode.checksum=rootCheck;
        return rootCheck;
    }
}

class Node{
    Node(File path){
        this.path=path;
    }
    List<Node> siblings=new ArrayList<>();
    File path=null;
    String checksum=new String(new byte[64], StandardCharsets.UTF_8);

    @Override
    public String toString() {
        return "Node{" +
                ", path=" + path +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}