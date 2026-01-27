package com.neetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class TimeMap<T,V> {

    final Map<T, TreeMap<Integer, V>> map = new HashMap<>();
    public TimeMap() {

    }

    public void set(T key, V value, int timestamp) {
        if (!map.containsKey(key))
            map.put(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public V get(T key, int timestamp) {
        final TreeMap<Integer, V> timeSeries = map.getOrDefault(key, new TreeMap<>());
        return timeSeries.containsKey(timestamp)
                ? timeSeries.get(timestamp)
                : timeSeries.lowerEntry(timestamp) == null
                ? null
                : timeSeries.lowerEntry(timestamp).getValue();
    }
}
