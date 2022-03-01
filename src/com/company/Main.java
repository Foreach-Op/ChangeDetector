package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        File file = new File("C:\\Users\\oguzt\\OneDrive\\Masaüstü\\University Lectures\\Comp 341");
        File file3 = new File("C:\\Users\\oguzt\\OneDrive\\Masaüstü\\University Lectures\\Comp 341");
        File file2 = new File("D:\\Riot Games\\VALORANT\\live\\Engine\\Binaries\\ThirdParty\\CEF3\\Win64\\Resources\\locales\\am.pak");
        recursive(file);

        //Create checksum for this file
//Use MD5 algorithm
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");

//Get the checksum
        String checksum = getFileChecksum(md5Digest, file2);

//see checksum
        System.out.println(file.hashCode());
        System.out.println(file3.hashCode());

    }

    public static void recursive(File file){
        //System.out.println(file);
        if(file.isFile()){
            return;
        }
        File[] files=file.listFiles();
        if(files == null){
            return;
        }
        for (File value : files) {
            recursive(value);
        }
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
