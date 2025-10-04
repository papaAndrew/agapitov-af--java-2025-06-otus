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

    final String[] allSortedNames;
    final String[] nonIdSortedNames;
    final String idFieldName;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData entitySQLMetaData,
            EntityClassMetaData<T> entityClassMetaData,
            Class<T> entityType) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityType = entityType;

        this.allSortedNames = entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .sorted()
                .toArray(String[]::new);
        this.nonIdSortedNames = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .sorted()
                .toArray(String[]::new);
        this.idFieldName = entityClassMetaData.getIdField().getName();
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
                    connection, entitySQLMetaData.getInsertSql(), getInsertFieldValues(entity));

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
                    connection, entitySQLMetaData.getSelectByIdSql(), getFieldValuesForUpdate(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldValuesForUpdate(T client) {
        try {
            List<Object> fieldValues = new ArrayList<>(Arrays.stream(nonIdSortedNames)
                    .map(fieldName -> {
                        try {
                            return entityType.getDeclaredField(fieldName).get(client);
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            throw new DataTemplateException(e);
                        }
                    })
                    .toList());
            fieldValues.add(entityType.getDeclaredField(idFieldName));
            return fieldValues;
        } catch (NoSuchFieldException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getInsertFieldValues(T client) {
        return Arrays.stream(nonIdSortedNames)
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
