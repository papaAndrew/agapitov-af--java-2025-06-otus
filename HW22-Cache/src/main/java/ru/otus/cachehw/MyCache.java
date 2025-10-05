package ru.otus.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<String, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        notify("put", key, value);
        cache.put(getRealKey(key), value);
    }

    @Override
    public void remove(K key) {
        var value = getValue(key);
        notify("remove", key, value);
        cache.remove(getRealKey(key));
    }

    @Override
    public V get(K key) {
        var value = getValue(key);
        notify("get", key, value);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(String action, K key, V value) {
        for (var listener : listeners) {
            listener.notify(key, value, action);
        }
    }

    private String getRealKey(K key) {
        return String.valueOf(key);
    }

    private V getValue(K key) {
        return cache.get(getRealKey(key));
    }
}
