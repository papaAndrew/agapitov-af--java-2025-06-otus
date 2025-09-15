package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
            return mapper.readValue(getFile(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private File getFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new FileProcessException("file not found! " + fileName);
        }
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new FileProcessException(e);
        }
    }
}
