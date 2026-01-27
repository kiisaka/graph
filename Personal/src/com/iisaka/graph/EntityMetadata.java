package com.iisaka.graph;

import java.time.Instant;
import java.util.Objects;

public final class EntityMetadata {

    private final Instant createdAt;
    private final String createdBy;

    private Instant updatedAt;
    private String updatedBy;

    private long version;

    public EntityMetadata(String actor) {
        Objects.requireNonNull(actor, "actor");
        Instant now = Instant.now();
        this.createdAt = now;
        this.createdBy = actor;
        this.updatedAt = now;
        this.updatedBy = actor;
        this.version = 1;
    }

    public void touch(String actor) {
        Objects.requireNonNull(actor, "actor");
        this.updatedAt = Instant.now();
        this.updatedBy = actor;
        this.version++;
    }

    public Instant createdAt() { return createdAt; }
    public String createdBy() { return createdBy; }
    public Instant updatedAt() { return updatedAt; }
    public String updatedBy() { return updatedBy; }
    public long version() { return version; }
}
