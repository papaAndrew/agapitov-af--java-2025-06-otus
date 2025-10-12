package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;
    private final Field fieldWithId;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.name = entityClass.getSimpleName().toLowerCase();
        this.constructor = getDefaultConstructor(entityClass);
        this.allFields = getAllDeclaredFields(entityClass);
        this.fieldsWithoutId = getFieldsWithoutId(allFields);
        this.fieldWithId = getFieldWithId(allFields);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return fieldWithId;
    }

    @Override
    public List<Field> getAllFields() {
        return new ArrayList<>(allFields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return new ArrayList<>(fieldsWithoutId);
    }

    private Constructor<T> getDefaultConstructor(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Class " + clazz.getName() + " must have a no-args constructor", e);
        }
    }

    private List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = clazz;

        while (currentClass != null && currentClass != Object.class) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            Arrays.stream(declaredFields).forEach(field -> {
                field.setAccessible(true);
                fields.add(field);
            });
            currentClass = currentClass.getSuperclass();
        }

        return List.copyOf(
                fields.stream().sorted(Comparator.comparing(Field::getName)).toList());
    }

    private Field getFieldWithId(List<Field> fields) {
        var fieldsWithId = fields.stream().filter(this::isFieldIdAnnotated).toList();
        if (fieldsWithId.size() == 1) {
            return fieldsWithId.getFirst();
        }
        throw new IllegalStateException("Class " + name + " must have exactly one @Id field");
    }

    private List<Field> getFieldsWithoutId(List<Field> fields) {
        return fields.stream()
                .filter(field -> !isFieldIdAnnotated(field))
                .sorted(Comparator.comparing(Field::getName))
                .toList();
    }

    private boolean isFieldIdAnnotated(Field field) {
        return Arrays.stream(field.getAnnotations())
                .anyMatch(annotation ->
                        annotation.annotationType().getSimpleName().equals("Id"));
    }
}
