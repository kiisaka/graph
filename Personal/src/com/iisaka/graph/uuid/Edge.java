package com.iisaka.graph.uuid;

import java.util.UUID;

public final class Edge extends com.iisaka.graph.Edge<UUID, UUID> implements Entity {

    public Edge(UUID id, UUID from, UUID to, String type, String createdBy) {
        super(id, from, to, type, createdBy);
    }
}
