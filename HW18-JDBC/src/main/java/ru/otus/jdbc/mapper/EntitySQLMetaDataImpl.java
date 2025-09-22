package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT " + getAllFieldNames() + " FROM " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " WHERE " + entityClassMetaData.getIdField().getName() + "=?";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO " + entityClassMetaData.getName() + " (" + getUpdatableFieldNames(",") + ") VALUES ("
                + getUpdatableFieldValues() + ")";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE " + entityClassMetaData.getName() + " SET " + getUpdatableFieldNames("=?,") + "=? WHERE "
                + entityClassMetaData.getIdField().getName() + "=?";
    }

    private String getAllFieldNames() {
        return String.join(
                ",",
                entityClassMetaData.getAllFields().stream()
                        .map(Field::getName)
                        .sorted()
                        .toArray(String[]::new));
    }

    private String getUpdatableFieldNames(String delimiter) {
        return String.join(
                delimiter,
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .sorted()
                        .toArray(String[]::new));
    }

    private String getUpdatableFieldValues() {
        return String.join(
                ",",
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(item -> "?")
                        .toArray(String[]::new));
    }
}
