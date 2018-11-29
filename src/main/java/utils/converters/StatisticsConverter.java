package utils.converters;

import entities.Relations;

import javax.persistence.AttributeConverter;
import java.util.List;

public class StatisticsConverter implements AttributeConverter<List<Relations>, String> {
    @Override
    public String convertToDatabaseColumn(List<Relations> relations) {
        return null;
    }

    @Override
    public List<Relations> convertToEntityAttribute(String s) {
        return null;
    }
}
