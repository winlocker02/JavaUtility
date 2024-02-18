package ru.javatest.javautility.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.*;

@UtilityClass
public class FileUtils {

    private static final String[] ALLOWED_EXTENSIONS = { ".txt" };

    public static String getAllowedExtensionsToString() {
        return String.join(", ", ALLOWED_EXTENSIONS);
    }

    public static boolean isAllowedExtension(@NonNull File file) {
        for (String extension : ALLOWED_EXTENSIONS) {
            if(file.getName().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDirectory(File file) {
        return file != null && file.isDirectory();
    }

    public static Optional<Collection<File>> getFiles(File file) {
        if(file == null || !file.exists()) return Optional.empty();

        if(!file.isDirectory()) {
            if(isAllowedExtension(file)) {
                return Optional.of(Collections.singleton(file));
            }
        }

        File[] listFiles = file.listFiles();
        if(listFiles == null) return Optional.empty();

        List<File> files = new ArrayList<>();

        for (File dirFile : listFiles)
            getFiles(dirFile).ifPresent(files::addAll);

        return !files.isEmpty() ? Optional.of(files) : Optional.empty();
    }
}
