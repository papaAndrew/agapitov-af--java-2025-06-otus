package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entityClassMetaData;
    private final String sqlSelectAll;
    private final String sqlSelectById;
    private final String sqlInsert;
    private final String sqlUpdate;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;

        this.sqlSelectAll = "SELECT " + getAllFieldNames() + " FROM " + entityClassMetaData.getName();
        this.sqlSelectById =
                getSelectAllSql() + " WHERE " + entityClassMetaData.getIdField().getName() + "=?";
        this.sqlInsert = "INSERT INTO " + entityClassMetaData.getName() + " (" + getUpdatableFieldNames(",")
                + ") VALUES (" + getUpdatableFieldValues() + ")";
        this.sqlUpdate = "UPDATE " + entityClassMetaData.getName() + " SET " + getUpdatableFieldNames("=?,")
                + "=? WHERE " + entityClassMetaData.getIdField().getName() + "=?";
    }

    @Override
    public String getSelectAllSql() {
        return sqlSelectAll;
    }

    @Override
    public String getSelectByIdSql() {
        return sqlSelectById;
    }

    @Override
    public String getInsertSql() {
        return sqlInsert;
    }

    @Override
    public String getUpdateSql() {
        return sqlUpdate;
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
