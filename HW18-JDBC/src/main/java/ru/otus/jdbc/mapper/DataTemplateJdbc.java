package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
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
    //    private final Class<T> entityType;

    final EntitySQLMetaData entitySQLMetaData;
    final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        //        this.entityType = entityType;

        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntityFromResult(rs);
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
                            clientList.add(createEntityFromResult(rs));
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
            var idField = entityClassMetaData.getIdField();
            if (idField.getType() == Long.class) {
                idField.setAccessible(true);
                idField.set(entity, generatedId);
            }
            return generatedId;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getFieldValuesForUpdate(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldValuesForUpdate(T client) {
        try {
            var fieldValues = new ArrayList<>(entityClassMetaData.getFieldsWithoutId().stream()
                    .map(field -> {
                        try {
                            return field.get(client);
                        } catch (IllegalAccessException e) {
                            throw new DataTemplateException(e);
                        }
                    })
                    .toList());
            fieldValues.add(entityClassMetaData.getIdField().get(client));
            return fieldValues;
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getInsertFieldValues(T client) {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> {
                    try {
                        return field.get(client);
                    } catch (IllegalAccessException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .toList();
    }

    private T createEntityFromResult(ResultSet rs) {
        try {
            T instance = entityClassMetaData.getConstructor().newInstance();
            var metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                var name = metaData.getColumnName(i + 1);
                var field = entityClassMetaData.getAllFields().stream()
                        .filter(item -> name.equals(item.getName()))
                        .findAny();
                if (field.isPresent()) {
                    field.get().setAccessible(true);
                    field.get().set(instance, rs.getObject(i + 1));
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            throw new DataTemplateException(e);
        }
    }
}
