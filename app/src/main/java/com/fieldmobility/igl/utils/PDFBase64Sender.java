package com.fieldmobility.igl.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
public class PDFBase64Sender {

    public static byte[] readPDFFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] pdfBytes = new byte[(int) file.length()];
        fis.read(pdfBytes);
        fis.close();
        return pdfBytes;
    }

    public static String encodeToBase64(byte[] bytes) {
        //return Base64.getEncoder().encodeToString(bytes);
        return  android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }
}
