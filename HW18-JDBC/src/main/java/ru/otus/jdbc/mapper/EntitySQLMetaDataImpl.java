package ru.otus.jdbc.mapper;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "";
    }

    @Override
    public String getSelectByIdSql() {
        return "";
    }

    @Override
    public String getInsertSql() {
        return "";
    }

    @Override
    public String getUpdateSql() {
        return "";
    }
}
