package ru.javatest.javautility.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.javatest.javautility.data.type.DataType;
import ru.javatest.javautility.data.type.DebugType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class SortDataWalker {

    public static final String RESULT_EXTENSION = ".txt";

    private final Map<DataType<?>, List<String>> resultData = new HashMap<>();
    private final DebugType debugType;

    public void readFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

            if(debugType != DebugType.NONE) {
                System.out.println(" - Reading file " + file + " -> " + lines.size() + " elements.");
            }

            lines.forEach(stringData -> {
                DataType<?> dataType = DataType.getDataType(stringData);

                resultData.putIfAbsent(dataType, new ArrayList<>());
                resultData.get(dataType).add(stringData);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void distributeToFiles(@NonNull File outputDir, String prefix, boolean replaceExists) {
        if(!outputDir.isDirectory()) {
            outputDir.mkdir();
        }

        resultData.forEach((dataType, stringData) -> {
            File file = new File(outputDir, (prefix == null ? dataType.getName() : prefix + dataType.getName()) + RESULT_EXTENSION);

            try {
                if(!file.exists()) {
                    file.createNewFile();
                }
                Files.write(file.toPath(), stringData, replaceExists ? StandardOpenOption.TRUNCATE_EXISTING : StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if(debugType != DebugType.NONE) {
                System.out.println(" - Saved file " + file + " -> " + stringData.size() + " elements.");

                if(debugType == DebugType.FULL) {
                    dataType.printResults(stringData);
                }
            }
        });
    }
}
