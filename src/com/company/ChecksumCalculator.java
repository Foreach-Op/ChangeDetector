package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ChecksumCalculator {

    public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        MessageDigest digest = MessageDigest.getInstance("SHA3-256");
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

        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static String addChecksums(List<String> strings) {
        byte[] sum=new byte[64];
        assert strings != null;
        for (String str:strings) {
            byte[] bytes=str.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < bytes.length; i++) {
                sum[i]+=bytes[i];
            }
        }
        return new String(sum, StandardCharsets.UTF_8);
    }
}
