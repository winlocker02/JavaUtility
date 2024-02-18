package ru.javatest.javautility.data.type.impl;

import lombok.NonNull;
import ru.javatest.javautility.data.type.DataType;

import java.util.List;

public class StringDataType implements DataType<String> {

    @Override
    public String getName() {
        return "strings";
    }

    @Override
    public String getData(@NonNull String stringData) {
        return stringData;
    }

    @Override
    public boolean isValidData(@NonNull String stringData) {
        return true;
    }

    @Override
    public void printResults(@NonNull List<String> stringList) {
        if(stringList.isEmpty()) return;

        String minimumString = null, maximumString = null;

        for (String stringData : stringList) {

            if(minimumString == null || stringData.length() < minimumString.length()) {
                minimumString = stringData;
            }

            if(maximumString == null || stringData.length() > maximumString.length()) {
                maximumString = stringData;
            }
        }

        System.out.println(" --> Minimum string: " + minimumString + " -> " + minimumString.length() + " length");
        System.out.println(" --> Maximum string: " + maximumString + " -> " + maximumString.length() + " length");
    }
}
