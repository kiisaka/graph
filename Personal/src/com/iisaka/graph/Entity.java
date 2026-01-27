package com.iisaka.graph;

import java.util.*;

public abstract class Entity<ID> {

    private final ID id;
    private final EntityMetadata meta;
    private final Map<String, Object> properties = new HashMap<>();

    private static final Set<String> RESERVED_KEYS = Set.of(
            "id",
            "createdAt", "createdBy", "updatedAt", "updatedBy", "version",
            "labels", "type", "weight", "from", "to"
    );

    protected Entity(ID id, String createdBy) {
        this.id = Objects.requireNonNull(id, "id");
        this.meta = new EntityMetadata(createdBy);
    }

    public final ID id() {
        return id;
    }

    public final EntityMetadata meta() {
        return meta;
    }

    public final Map<String, Object> propertiesView() {
        return Collections.unmodifiableMap(properties);
    }

    public final Object getProperty(String key) {
        validateKey(key);
        return properties.get(key);
    }

    public final void setProperty(String key, Object value, String actor) {
        validateKey(key);
        properties.put(key, value);
        meta.touch(actor);
    }

    public final void removeProperty(String key, String actor) {
        validateKey(key);
        properties.remove(key);
        meta.touch(actor);
    }

    protected final void touch(String actor) {
        meta.touch(actor);
    }

    private void validateKey(String key) {
        Objects.requireNonNull(key, "key");
        if (RESERVED_KEYS.contains(key)) {
            throw new IllegalArgumentException("Reserved property key: " + key);
        }
    }
}
