package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
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
    private final Class<T> entityType;
    private final Field idField;
    private final List<Field> nonIdFields;

    final String sqlSelectById;
    final String sqlSelectAll;
    final String sqlInsert;
    final String sqlUpdate;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData entitySQLMetaData,
            EntityClassMetaData<T> entityClassMetaData,
            Class<T> entityType) {
        this.dbExecutor = dbExecutor;
        this.entityType = entityType;

        this.sqlSelectById = entitySQLMetaData.getSelectByIdSql();
        this.sqlSelectAll = entitySQLMetaData.getSelectAllSql();
        this.sqlInsert = entitySQLMetaData.getInsertSql();
        this.sqlUpdate = entitySQLMetaData.getUpdateSql();

        this.idField = entityClassMetaData.getIdField();
        this.nonIdFields = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, sqlSelectById, List.of(id), rs -> {
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
                .executeSelect(connection, sqlSelectAll, Collections.emptyList(), rs -> {
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
            var generatedId = dbExecutor.executeStatement(connection, sqlInsert, getInsertFieldValues(entity));
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
            dbExecutor.executeStatement(connection, sqlUpdate, getFieldValuesForUpdate(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getFieldValuesForUpdate(T client) {
        try {
            var fieldValues = new ArrayList<>(nonIdFields.stream()
                    .map(field -> {
                        try {
                            return field.get(client);
                        } catch (IllegalAccessException e) {
                            throw new DataTemplateException(e);
                        }
                    })
                    .toList());
            fieldValues.add(idField.get(client));
            return fieldValues;
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getInsertFieldValues(T client) {
        return nonIdFields.stream()
                .map(field -> {
                    try {
                        return field.get(client);
                    } catch (IllegalAccessException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .toList();
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
