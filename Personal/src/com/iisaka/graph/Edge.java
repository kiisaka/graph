package com.iisaka.graph;

import java.util.Objects;

public final class Edge<T> {

    private final Node<T> from;
    private final Node<T> to;
    private final int weight;
    private final boolean directed;

    Edge(final Node<T> from, final Node<T> to, final int weight, final boolean directed) {
        this.from = Objects.requireNonNull(from, "from");
        this.to = Objects.requireNonNull(to, "to");
        this.weight = weight;
        this.directed = directed;

        if (from.graph() != to.graph()) {
            throw new IllegalArgumentException("Edge endpoints must belong to the same graph");
        }
    }

    public Node<T> from() { return from; }
    public Node<T> to() { return to; }
    public int weight() { return weight; }
    public boolean isDirected() { return directed; }

}
