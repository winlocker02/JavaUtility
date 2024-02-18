package ru.javatest.javautility.data.type.impl;

import lombok.NonNull;
import ru.javatest.javautility.data.type.DataType;

import java.util.List;
import java.util.stream.Collectors;

public abstract class NumberDataType<V extends Number> implements DataType<V> {

    @Override
    public final void printResults(@NonNull List<String> stringList) {

        List<V> numbers = stringList.stream()
                .filter(this::isValidData)
                .map(this::getData)
                .collect(Collectors.toList());

        if(numbers.isEmpty()) return;

        printNumbers(numbers);
    }

    protected final void printResults(V min, V max, V avg, V sum) {
        System.out.println(" --> Minimum number: " + min);
        System.out.println(" --> Maximum number: " + max);
        System.out.println(" --> Average number: " + avg);
        System.out.println(" --> Total amount numbers: " + sum);
    }

    public abstract void printNumbers(List<V> numbers);
}

