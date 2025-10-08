package ru.otus.cachehw;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCache<K, V> implements HwCache<K, V> {

    private static final Logger log = LoggerFactory.getLogger(MyCache.class);
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
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                log.warn("Listener notification error `{}`", e.getMessage());
            }
        }
    }

    private String getRealKey(K key) {
        if (key instanceof String) {
            return (String) key;
        }
        try {
            Objects.requireNonNull(key, "Key cannot be null");
            var keyString = String.valueOf(key);
            if (keyString.trim().isEmpty()) {
                keyString = null;
            }
            Objects.requireNonNull(keyString, "Key cannot be null");
            return keyString;
        } catch (Exception e) {
            log.error("Failed to convert key to string: {}", e.getMessage(), e);
            throw new HwCacheException("Key cannot be converted to string", e);
        }
    }

    private V getValue(K key) {
        return cache.get(getRealKey(key));
    }
}
