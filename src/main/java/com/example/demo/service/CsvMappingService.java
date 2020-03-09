package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class CsvMappingService {
    public <T> void writeToCsv(List<T> result, Class<T> outputClass, OutputStream outputStream) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(outputClass);

        schema.withColumnSeparator(',').withHeader();

        ObjectWriter myObjectWriter = mapper.writer(schema);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, result);
    }
}
