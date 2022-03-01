package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tree {
    class Node{
        List<Node> siblings=new ArrayList<>();
        File path=null;
        String checksum="";
    }

    private Node root;

    public void insertDirectories(File path){
        root.path=path;

    }


}
