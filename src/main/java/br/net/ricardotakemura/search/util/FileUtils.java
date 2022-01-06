package br.net.ricardotakemura.search.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class FileUtils {
    
    public static <T extends Serializable> void createFileData(File file, T data) throws IOException {
        try (var output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(data);
        }
    }

    public static boolean canRead(File file) {
        return file.isFile() && file.canRead() && !file.isHidden();
    }

    public static String readFully(File file) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.lines().reduce((l1, l2) -> l1 + ' ' + l2).orElse("");
        }
    }

    public static <T extends Serializable> T readFileData(File file) throws IOException, ClassNotFoundException {
        try (var output = new ObjectInputStream(new FileInputStream(file))) {
            return (T) output.readObject();
        }
    }
}
