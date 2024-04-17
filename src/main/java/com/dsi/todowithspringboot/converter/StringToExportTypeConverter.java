package com.dsi.todowithspringboot.converter;

import com.dsi.todowithspringboot.constants.ExportType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExportTypeConverter implements Converter<String, ExportType> {

    @Override
    public ExportType convert(String source) {
        try {
            return ExportType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ExportType: " + source);
        }
    }
}
