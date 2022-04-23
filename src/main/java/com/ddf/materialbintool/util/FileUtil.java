package com.ddf.materialbintool.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static byte[] readAllBytes(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            return data;
        } catch (IOException e) {
            return null;
        }
    }

    public static String readString(File file) {
        return new String(readAllBytes(file), StandardCharsets.UTF_8);
    }

    public static void writeString(File file, String string) {
        write(file, string.getBytes(StandardCharsets.UTF_8));
    }

    public static void write(File file, byte[] bytes) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}