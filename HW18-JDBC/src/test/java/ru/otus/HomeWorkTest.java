package ru.otus;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;

@DisplayName("Testing new classes for homework")
class HomeWorkTest {
    private static final Logger logger = LoggerFactory.getLogger(HomeWorkTest.class);

    @Test
    @DisplayName("Testing EntityClassMetaDataImpl")
    public void testEntityClassMetaDataImpl()
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        EntityClassMetaData<Client> entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);

        assertThat(entityClassMetaData.getName()).isEqualTo("Client");
        assertThat(entityClassMetaData.getConstructor().newInstance()).isInstanceOf(Client.class);
        assertThat(entityClassMetaData.getIdField().getName()).isEqualTo("id");
        assertThat(entityClassMetaData.getAllFields().size()).isEqualTo(2);
        assertThat(entityClassMetaData.getFieldsWithoutId().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Testing EntitySQLMetaDataImpl")
    public void testEntitySQLMetaDataImpl() {
        EntityClassMetaData<Manager> entityClassMetaData = new EntityClassMetaDataImpl<>(Manager.class);
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);

        assertThat(entitySQLMetaData.getSelectAllSql()).isEqualTo("SELECT label,no,param1 FROM Manager");
        assertThat(entitySQLMetaData.getSelectByIdSql()).isEqualTo("SELECT label,no,param1 FROM Manager WHERE no=?");
        assertThat(entitySQLMetaData.getInsertSql()).isEqualTo("INSERT INTO Manager (label,no,param1) VALUES (?,?,?)");
        assertThat(entitySQLMetaData.getUpdateSql()).isEqualTo("UPDATE Manager SET label=?,param1=? WHERE no=?");
    }
}
