package com.iisaka.graph;

import java.util.*;
import java.util.function.Supplier;

public abstract class Graph<VID, EID> {

    private final Supplier<VID> vertexIdFactory;
    private final Supplier<EID> edgeIdFactory;

    private final Map<VID, Vertex<VID>> vertices = new HashMap<>();
    private final Map<EID, Edge<EID>> edges = new HashMap<>();

    private final Map<VID, Set<EID>> outgoing = new HashMap<>();
    private final Map<VID, Set<EID>> incoming = new HashMap<>();

    protected Graph(final Supplier<VID> vertexIdFactory, final Supplier<EID> edgeIdFactory) {
        this.vertexIdFactory = Objects.requireNonNull(vertexIdFactory, "vertexIdFactory");
        this.edgeIdFactory = Objects.requireNonNull(edgeIdFactory, "edgeIdFactory");
    }

    /* =========================
       Factory hooks
       ========================= */

    protected abstract Vertex<VID> newVertex(final VID id, final String createdBy);

    protected abstract Edge<EID> newEdge(final EID id,
                                              final VID from,
                                              final VID to,
                                              final String type,
                                              final String createdBy);

    /* =========================
       Actor convenience
       ========================= */

    protected String currentActor() {
        return System.getProperty("user.name", "unknown");
    }

    /* =========================
       Vertex creation
       ========================= */

    public Vertex<VID> createVertex(final String actor) {
        Objects.requireNonNull(actor, "actor");
        final VID id = generateUniqueVertexId();
        return createVertexWithId(id, actor);
    }

    public Vertex<VID> createVertex() {
        return createVertex(currentActor());
    }

    public Vertex<VID> createVertexWithId(final VID id, final String actor) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(actor, "actor");

        if (vertices.containsKey(id)) {
            throw new IllegalArgumentException("Vertex already exists: " + id);
        }

        final Vertex<VID> v = Objects.requireNonNull(newVertex(id, actor), "newVertex returned null");
        vertices.put(id, v);
        outgoing.put(id, new HashSet<>());
        incoming.put(id, new HashSet<>());
        return v;
    }

    /* =========================
       Edge creation
       ========================= */

    public Edge<EID> createEdge(final VID from, final VID to, final String type, final String actor) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(actor, "actor");

        final EID id = generateUniqueEdgeId();
        return createEdgeWithId(id, from, to, type, actor);
    }

    public Edge<EID> createEdge(final VID from, final VID to, final String type) {
        return createEdge(from, to, type, currentActor());
    }

    public Edge<EID> createEdge(final Vertex<VID> from, final Vertex<VID> to, final String type, final String actor) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        return createEdge(from.id(), to.id(), type, actor);
    }

    public Edge<EID> createEdge(final Vertex<VID> from, final Vertex<VID> to, final String type) {
        return createEdge(from, to, type, currentActor());
    }

    public Edge<EID> createEdgeWithId(final EID id,
                                           final VID from,
                                           final VID to,
                                           final String type,
                                           final String actor) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(actor, "actor");

        if (!vertices.containsKey(from)) {
            throw new NoSuchElementException("Missing from-vertex: " + from);
        }
        if (!vertices.containsKey(to)) {
            throw new NoSuchElementException("Missing to-vertex: " + to);
        }
        if (edges.containsKey(id)) {
            throw new IllegalArgumentException("Edge already exists: " + id);
        }

        final Edge<EID> e = Objects.requireNonNull(newEdge(id, from, to, type, actor), "newEdge returned null");
        edges.put(id, e);
        outgoing.get(from).add(id);
        incoming.get(to).add(id);
        return e;
    }

    /* =========================
       Basic accessors (optional but useful)
       ========================= */

    public boolean hasVertex(final VID id) {
        Objects.requireNonNull(id, "id");
        return vertices.containsKey(id);
    }

    public Vertex<VID> getVertex(final VID id) {
        Objects.requireNonNull(id, "id");
        final Vertex<VID> v = vertices.get(id);
        if (v == null) throw new NoSuchElementException("Vertex not found: " + id);
        return v;
    }

    public boolean hasEdge(final EID id) {
        Objects.requireNonNull(id, "id");
        return edges.containsKey(id);
    }

    public Edge<EID> getEdge(final EID id) {
        Objects.requireNonNull(id, "id");
        final Edge<EID> e = edges.get(id);
        if (e == null) throw new NoSuchElementException("Edge not found: " + id);
        return e;
    }

    public Collection<Vertex<VID>> verticesView() {
        return Collections.unmodifiableCollection(vertices.values());
    }

    public Collection<Edge<EID>> edgesView() {
        return Collections.unmodifiableCollection(edges.values());
    }

    public List<Edge<EID>> outgoingEdges(final VID from) {
        Objects.requireNonNull(from, "from");
        if (!vertices.containsKey(from)) throw new NoSuchElementException("Vertex not found: " + from);

        final List<Edge<EID>> result = new ArrayList<>();
        for (final EID eid : outgoing.getOrDefault(from, Set.of())) {
            final Edge<EID> e = edges.get(eid);
            if (e != null) result.add(e);
        }
        return result;
    }

    public List<Edge<EID>> incomingEdges(final VID to) {
        Objects.requireNonNull(to, "to");
        if (!vertices.containsKey(to)) throw new NoSuchElementException("Vertex not found: " + to);

        final List<Edge<EID>> result = new ArrayList<>();
        for (final EID eid : incoming.getOrDefault(to, Set.of())) {
            final Edge<EID> e = edges.get(eid);
            if (e != null) result.add(e);
        }
        return result;
    }

    /* =========================
       ID generation
       ========================= */

    private VID generateUniqueVertexId() {
        final VID id = Objects.requireNonNull(vertexIdFactory.get(), "vertexIdFactory returned null");
        if (vertices.containsKey(id)) {
            throw new IllegalStateException("Vertex ID collision: " + id);
        }
        return id;
    }

    private EID generateUniqueEdgeId() {
        final EID id = Objects.requireNonNull(edgeIdFactory.get(), "edgeIdFactory returned null");
        if (edges.containsKey(id)) {
            throw new IllegalStateException("Edge ID collision: " + id);
        }
        return id;
    }
}
