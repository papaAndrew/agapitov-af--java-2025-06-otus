package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exception.SomethingWrongException;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    @SuppressWarnings("this-escape")
    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        var componentCreators = Arrays.stream(configClass.getDeclaredMethods())
                .filter(item -> item.isAnnotationPresent(AppComponent.class))
                .sorted((item1, item2) -> Integer.compareUnsigned(
                        item1.getDeclaredAnnotation(AppComponent.class).order(),
                        item2.getDeclaredAnnotation(AppComponent.class).order()))
                .toList();

        var configInstance = createNoArgsInstance(configClass);
        for (Method method : componentCreators) {
            var key = method.getDeclaredAnnotation(AppComponent.class).name();
            addComponent(key, createComponent(method, configInstance));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var components =
                appComponents.stream().filter(componentClass::isInstance).toList();
        if (components.size() == 1) {
            return (C) components.getFirst();
        }
        throw new SomethingWrongException("Component unbounded " + componentClass.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(String componentName) {
        var optionalComponent = Optional.ofNullable(appComponentsByName.get(componentName));
        return (C) optionalComponent.orElseThrow(
                () -> new SomethingWrongException("Component unavailable " + componentName));
    }

    private static <C> C createNoArgsInstance(Class<C> componentClass) {
        try {
            return componentClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                | NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException e) {
            throw new SomethingWrongException("Context create no-args Instance failure", e);
        }
    }

    private void addComponent(String key, Object component) {
        if (appComponentsByName.containsKey(key)) {
            throw new SomethingWrongException("Duplicating key " + key);
        }
        appComponentsByName.put(key, component);
        appComponents.add(component);
    }

    private Object createComponent(Method method, Object configInstance) {
        var args = Arrays.stream(method.getParameterTypes())
                .map(this::getAppComponent)
                .toList();
        try {
            return args.isEmpty() ? method.invoke(configInstance) : method.invoke(configInstance, args.toArray());
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            throw new SomethingWrongException("Component Creator failure " + method.getName(), e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }
}
