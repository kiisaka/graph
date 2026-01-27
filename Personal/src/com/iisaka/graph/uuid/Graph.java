package com.iisaka.graph.uuid;

import java.util.UUID;

public final class Graph extends com.iisaka.graph.Graph<UUID> {

    public Graph() {
        super(Entity.DEFAULT_ID_SUPPLIER);
    }

    @Override
    protected Vertex newVertex(UUID id, String createdBy) {
        return (Vertex) createVertex(currentActor());
    }

    @Override
    protected Edge newEdge(UUID id, UUID from, UUID to, String type, String createdBy) {
        return (Edge) createEdge(from, to, type, currentActor());
    }
}
