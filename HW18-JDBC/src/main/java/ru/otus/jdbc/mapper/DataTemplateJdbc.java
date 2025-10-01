package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final Class<T> entityType;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, Class<T> entityType) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityType = entityType;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntityFromResult(rs, entityType);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var clientList = new ArrayList<T>();
                    try {
                        while (rs.next()) {
                            clientList.add(createEntityFromResult(rs, entityType));
                        }
                        return clientList;
                    } catch (SQLException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .orElseThrow(() -> new DataTemplateException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T entity) {
        try {
            var generatedId = dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getInsertSql(), getInsertFieldValues(entity, entityType));

            var idField = Arrays.stream(entityType.getDeclaredFields())
                    .filter(field -> field.getAnnotation(Id.class) != null)
                    .findFirst();
            if (idField.isPresent() && idField.get().getType() == Long.class) {
                idField.get().setAccessible(true);
                idField.get().set(entity, generatedId);
            }
            return generatedId;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(
                    connection, entitySQLMetaData.getSelectByIdSql(), getFieldValuesForUpdate(client, entityType));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private static <T> List<Object> getFieldValuesForUpdate(T client, Class<T> entityType) {
        var sortedNames = new EntityClassMetaDataImpl<>(entityType)
                .getAllFields().stream().map(Field::getName).sorted().toArray(String[]::new);

        Object id = null;
        List<Object> fieldValues = new ArrayList<>();
        for (var fieldName : sortedNames) {
            try {
                var field = entityType.getDeclaredField(fieldName);
                if (field.getAnnotation(Id.class) == null) {
                    fieldValues.add(field.get(client));
                } else {
                    id = field.get(client);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        if (id != null) {
            fieldValues.add(id);
        } else {
            throw new DataTemplateException(
                    "No id field in " + entityType.getName() + " for update. Add @Id annotation to id field");
        }
        return fieldValues;
    }

    private static <T> List<Object> getInsertFieldValues(T client, Class<T> entityType) {
        return new EntityClassMetaDataImpl<>(entityType)
                .getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .sorted()
                        .map(fieldName -> {
                            try {
                                var field = entityType.getDeclaredField(fieldName);
                                field.setAccessible(true);
                                return field.get(client);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                throw new DataTemplateException(e);
                            }
                        })
                        .collect(Collectors.toList());
    }

    private static <T> T createEntityFromResult(ResultSet rs, Class<T> entityType) {
        try {
            T instance = entityType.getDeclaredConstructor().newInstance();
            var metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                var name = metaData.getColumnName(i + 1);
                var field = entityType.getDeclaredField(name);
                field.setAccessible(true);
                field.set(instance, rs.getObject(i + 1));
            }
            return instance;
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | NoSuchFieldException
                | SQLException e) {
            throw new DataTemplateException(e);
        }
    }
}
