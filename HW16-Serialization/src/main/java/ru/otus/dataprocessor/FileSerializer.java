package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final ObjectMapper mapper;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.mapper = JsonMapper.builder().build();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try {
            var bytes = mapper.writeValueAsBytes(data);
            Files.write(getPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getPath() {
        return Path.of(URI.create(fileName));
    }
}
