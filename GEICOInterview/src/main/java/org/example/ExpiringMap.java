package org.example;

import java.util.HashMap;
import java.util.Map;

public class ExpiringMap<K, V> {
    private static class Entry<V> {
        private final V value;
        private final long expiresAtMillis;

        private Entry(V value, long expiresAtMillis) {
            this.value = value;
            this.expiresAtMillis = expiresAtMillis;
        }
    }

    private final Map<K, Entry<V>> map = new HashMap<>();

    public void put(K key, V value, long ttlMillis, long nowMillis) {
        if (ttlMillis <= 0) {
            map.remove(key);
            return;
        }

        long expiresAtMillis = nowMillis + ttlMillis;
        map.put(key, new Entry<>(value, expiresAtMillis));
    }

    public V get(K key, long nowMillis) {
        Entry<V> entry = map.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.expiresAtMillis <= nowMillis) {
            map.remove(key);
            return null;
        }

        return entry.value;
    }

    public boolean containsKey(K key, long nowMillis) {
        return get(key, nowMillis) != null;
    }

    public void remove(K key) {
        map.remove(key);
    }

    public int size() {
        return map.size();
    }
}