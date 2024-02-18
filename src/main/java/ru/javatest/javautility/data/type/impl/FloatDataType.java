package ru.javatest.javautility.data.type.impl;

import lombok.NonNull;

import java.util.List;

public class FloatDataType extends NumberDataType<Float> {

    @Override
    public String getName() {
        return "floats";
    }

    @Override
    public Float getData(@NonNull String stringData) {
        return Float.parseFloat(stringData);
    }

    @Override
    public boolean isValidData(@NonNull String stringData) {
        try {
            getData(stringData);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void printNumbers(List<Float> numbers) {
        float min = 0, max = 0, sum = 0;

        for (float number : numbers) {
            if(number < min) {
                min = number;
            }
            if(number > max) {
                max = number;
            }
            sum += number;
        }
        printResults(min, max, sum / 2F, sum);
    }
}
