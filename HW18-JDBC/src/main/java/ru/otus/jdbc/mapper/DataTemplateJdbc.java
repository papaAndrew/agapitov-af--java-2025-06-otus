package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.flywaydb.core.internal.jdbc.RowMapper;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

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
        return dbExecutor.executeSelect(connection, "select id, name from client where id  = ?", List.of(id), rs -> {
            try {
                T instance = entityType.getDeclaredConstructor().newInstance();
                if (rs.next()) {
                    var metaData = rs.getMetaData();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        var name = metaData.getColumnName(i);
                        entityType.getDeclaredField(name).set(instance, rs.getObject(i));
                    }
                    return instance;
                }
                return null;
            } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchFieldException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Connection connection, T client) {
        try {
//            dbExecutor.executeStatement(
//                    connection, "update client set name = ? where id = ?", List.of(client.getName(), client.getId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }

    }

    private  Optional<T> transformMap(Map<String, Object> map, Function<Map<String, Object>, T> transformer) {
        try {
            T result = transformer.apply(map);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private <T> T mapResultSet (ResultSet rs) {
        try {
            T instance = entityType.getDeclaredConstructor().newInstance();
            var metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                var name = metaData.getColumnName(i);
                entityType.getDeclaredField(name).set(instance, rs.getObject(i));
            }
            return instance;
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}
