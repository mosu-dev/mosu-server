package life.mosu.mosuserver.global.util.excel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import life.mosu.mosuserver.global.annotation.ExcelColumn;

public class SimpleExcelMetadata<T> {

    private final List<String> dataFieldNames;
    private final Map<String, String> excelHeaderNames;

    public SimpleExcelMetadata(Class<T> type) {
        this.dataFieldNames = extractFieldNames(type);
        this.excelHeaderNames = extractHeaderNames(type);
    }

    public List<String> getDataFieldNames() {
        return dataFieldNames;
    }

    public String getExcelHeaderName(String fieldName) {
        return excelHeaderNames.getOrDefault(fieldName, fieldName);
    }

    private List<String> extractFieldNames(Class<T> type) {
        System.out.println(type);
        return Arrays.stream(type.getDeclaredFields())
                .map(Field::getName)
                .toList();
    }

    private Map<String, String> extractHeaderNames(Class<T> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ExcelColumn.class))
                .collect(Collectors.toMap(
                        Field::getName,
                        f -> f.getAnnotation(ExcelColumn.class).headerName()
                ));
    }

}
