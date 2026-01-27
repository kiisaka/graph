package com.iisaka.graph.uuid;

import java.util.UUID;

public final class Vertex extends com.iisaka.graph.Vertex<UUID> implements Entity {

    public Vertex(UUID id, String createdBy) {
        super(id, createdBy);
    }
}
