package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);

    private final ObjectMapper mapper;
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        this.mapper = JsonMapper.builder().build();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try {
            return mapper.readValue(getUrl(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getUrl() {
        return this.getClass().getClassLoader().getResource(fileName);
    }
}
