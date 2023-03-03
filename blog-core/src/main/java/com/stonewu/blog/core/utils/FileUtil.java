package com.stonewu.blog.core.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void uploadCsvFile(String fileContent, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

//        File file = new File(filePath + fileName);
//        BufferedWriter writer = null;
//        FileOutputStream writerStream = new FileOutputStream(file);
//        writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
//        writer.write(239);  // 0xEF
//        writer.write(187);  // 0xBB
//        writer.write(191);  // 0xBF
//        writer.write(fileContent);
//        IOUtils.closeQuietly(writer);
//        IOUtils.closeQuietly(writerStream);

        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath + fileName), "utf-8");
        BufferedWriter writer = new BufferedWriter(write);
        writer.write("\ufeff");
        writer.write(fileContent);
        IOUtils.closeQuietly(writer);
    }

}
