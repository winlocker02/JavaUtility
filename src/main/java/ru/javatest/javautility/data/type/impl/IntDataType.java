package ru.javatest.javautility.data.type.impl;

import lombok.NonNull;

import java.util.List;

public class IntDataType extends NumberDataType<Integer> {

    @Override
    public String getName() {
        return "integers";
    }

    @Override
    public Integer getData(@NonNull String stringData) {
        return Integer.parseInt(stringData);
    }

    @Override
    public void printNumbers(List<Integer> numbers) {
        int min = 0, max = 0, sum = 0;

        for (int number : numbers) {
            if(number < min) {
                min = number;
            }

            if(number > max) {
                max = number;
            }
            sum += number;
        }
        printResults(min, max, sum / 2, sum);
    }
}
