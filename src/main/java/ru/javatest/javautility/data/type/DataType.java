package ru.javatest.javautility.data.type;

import lombok.NonNull;

import ru.javatest.javautility.data.type.impl.FloatDataType;
import ru.javatest.javautility.data.type.impl.IntDataType;
import ru.javatest.javautility.data.type.impl.StringDataType;

import java.util.List;

public interface DataType<V> {

    DataType<?> DEFAULT_DATA_TYPE = new StringDataType();
    List<DataType<?>> DATA_TYPES = List.of(new IntDataType(), new FloatDataType());

    static DataType<?> getDataType(@NonNull String stringData) {
        for (DataType<?> dataType : DATA_TYPES) {
            if(dataType.isValidData(stringData)) {
                return dataType;
            }
        }
        return DEFAULT_DATA_TYPE;
    }

    String getName();

    V getData(@NonNull String stringData);

    boolean isValidData(@NonNull String stringData);

    void printResults(@NonNull List<String> stringList);
}
