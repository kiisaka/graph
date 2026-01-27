package com.iisaka.graph;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Node<T> {

    private final Set<Edge<T>> outgoingEdges = new LinkedHashSet<>();
    public final T id;

    public Node(T id) {
        this.id = id;
    }

    // -------------------------
    // Accessors
    // -------------------------

    void addOutgoingEdge(final Edge<T> edge) {
        outgoingEdges.add(edge);
    }

    public Set<Edge<T>> outgoingEdges() {
        return Collections.unmodifiableSet(outgoingEdges);
    }

    // -------------------------
    // (1) neighbors(node)
    // -------------------------

    public Set<Node<T>> neighbors() {
        final Set<Node<T>> neighbors = new LinkedHashSet<>();
        for (final Edge<T> edge : outgoingEdges) {
            neighbors.add(edge.to());
        }
        return Collections.unmodifiableSet(neighbors);
    }

    // -------------------------
    // Traversals
    // -------------------------

    public Stream<Node<T>> bfs() {
        final Iterator<Node<T>> iterator = new Iterator<>() {
            private final Set<Node<T>> visited = new HashSet<>();
            private final ArrayDeque<Node<T>> queue = new ArrayDeque<>();

            {
                visited.add(Node.this);
                queue.addLast(Node.this);
            }

            @Override public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override public Node<T> next() {
                if (queue.isEmpty()) throw new NoSuchElementException();

                final Node<T> current = queue.removeFirst();

                for (final Edge<T> edge : current.outgoingEdges) {
                    final Node<T> neighbor = edge.to();
                    if (visited.add(neighbor)) {
                        queue.addLast(neighbor);
                    }
                }

                return current;
            }
        };

        return streamFromIterator(iterator);
    }

    public Stream<Node<T>> dfs() {
        final Iterator<Node<T>> iterator = new Iterator<>() {
            private final Set<Node<T>> visited = new HashSet<>();
            private final ArrayDeque<Node<T>> stack = new ArrayDeque<>();

            {
                stack.push(Node.this);
            }

            @Override public boolean hasNext() {
                while (!stack.isEmpty() && visited.contains(stack.peek())) {
                    stack.pop();
                }
                return !stack.isEmpty();
            }

            @Override public Node<T> next() {
                while (!stack.isEmpty()) {
                    final Node<T> current = stack.pop();
                    if (!visited.add(current)) continue;

                    final List<Edge<T>> edges = new ArrayList<>(current.outgoingEdges);
                    for (int i = edges.size() - 1; i >= 0; i--) {
                        final Node<T> neighbor = edges.get(i).to();
                        if (!visited.contains(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                    return current;
                }

                throw new NoSuchElementException();
            }
        };

        return streamFromIterator(iterator);
    }

// Node.java (add these methods)

    public boolean hasPathTo(final Node<T> destination) {

        if (this == destination) return true;

        final Set<Node<T>> visited = new HashSet<>();
        final ArrayDeque<Node<T>> queue = new ArrayDeque<>();

        visited.add(this);
        queue.addLast(this);

        while (!queue.isEmpty()) {
            final Node<T> current = queue.removeFirst();

            for (final Edge<T> edge : current.outgoingEdges()) {
                final Node<T> neighbor = edge.to();
                if (neighbor == destination) return true;
                if (visited.add(neighbor)) {
                    queue.addLast(neighbor);
                }
            }
        }

        return false;
    }

    public Optional<List<Node<T>>> shortestPathUnweightedTo(final Node<T> destination) {

        if (this == destination) {
            return Optional.of(List.of(this));
        }

        final Map<Node<T>, Node<T>> parentByNode = new HashMap<>();
        final Set<Node<T>> visited = new HashSet<>();
        final ArrayDeque<Node<T>> queue = new ArrayDeque<>();

        visited.add(this);
        parentByNode.put(this, null);
        queue.addLast(this);

        while (!queue.isEmpty()) {
            final Node<T> current = queue.removeFirst();

            for (final Edge<T> edge : current.outgoingEdges()) {
                final Node<T> neighbor = edge.to();
                if (!visited.add(neighbor)) continue;

                parentByNode.put(neighbor, current);

                if (neighbor == destination) {
                    return Optional.of(reconstructPath(parentByNode, destination));
                }

                queue.addLast(neighbor);
            }
        }

        return Optional.empty();
    }

    private List<Node<T>> reconstructPath(final Map<Node<T>, Node<T>> parentByNode, final Node<T> destination) {
        final LinkedList<Node<T>> path = new LinkedList<>();
        Node<T> current = destination;

        while (current != null) {
            path.addFirst(current);
            current = parentByNode.get(current);
        }

        return Collections.unmodifiableList(path);
    }

    // -------------------------
    // Distances and paths
    // -------------------------

    public long dijkstraDistanceTo(final Node<T> destination) {

        if (this == destination) return 0L;

        final long infinity = Long.MAX_VALUE / 4;
        final Map<Node<T>, Long> distanceByNode = new HashMap<>();
        final Set<Node<T>> settled = new HashSet<>();

        distanceByNode.put(this, 0L);

        while (true) {
            Node<T> closestNode = null;
            long closestDistance = infinity;

            for (final Map.Entry<Node<T>, Long> entry : distanceByNode.entrySet()) {
                final Node<T> node = entry.getKey();
                final long distance = entry.getValue();

                if (settled.contains(node)) continue;
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestNode = node;
                }
            }

            if (closestNode == null) break;
            if (closestNode == destination) return closestDistance;

            settled.add(closestNode);

            for (final Edge<T> edge : closestNode.outgoingEdges()) {
                final Node<T> neighbor = edge.to();
                final long currentDistance = distanceByNode.getOrDefault(neighbor, infinity);
                final long candidateDistance = closestDistance + (long) edge.weight();

                if (candidateDistance < currentDistance) {
                    distanceByNode.put(neighbor, candidateDistance);
                }
            }
        }

        return -1L;
    }

    public long zeroOneBfsDistanceTo(final Node<T> destination) {

        if (this == destination) return 0L;

        final long infinity = Long.MAX_VALUE / 4;
        final Map<Node<T>, Long> distanceByNode = new HashMap<>();
        final ArrayDeque<Node<T>> deque = new ArrayDeque<>();

        distanceByNode.put(this, 0L);
        deque.addFirst(this);

        while (!deque.isEmpty()) {
            final Node<T> current = deque.removeFirst();
            final long currentDistance = distanceByNode.getOrDefault(current, infinity);

            if (current == destination) return currentDistance;

            for (final Edge<T> edge : current.outgoingEdges()) {
                final int weight = edge.weight();
                if (weight != 0 && weight != 1) {
                    throw new IllegalArgumentException("0-1 BFS requires all edge weights to be 0 or 1");
                }

                final Node<T> neighbor = edge.to();
                final long candidateDistance = currentDistance + (long) weight;
                final long knownDistance = distanceByNode.getOrDefault(neighbor, infinity);

                if (candidateDistance < knownDistance) {
                    distanceByNode.put(neighbor, candidateDistance);
                    if (weight == 0) {
                        deque.addFirst(neighbor);
                    } else {
                        deque.addLast(neighbor);
                    }
                }
            }
        }

        return -1L;
    }

    public long bellmanFordDistanceTo(final Node<T> destination) {

        if (this == destination) return 0L;

        final long infinity = Long.MAX_VALUE / 4;

        final List<Node<T>> reachableNodes = bfs().toList();
        final Map<Node<T>, Long> distanceByNode = new HashMap<>();
        for (final Node<T> node : reachableNodes) {
            distanceByNode.put(node, infinity);
        }
        distanceByNode.put(this, 0L);

        final int nodeCount = reachableNodes.size();

        for (int iteration = 0; iteration < nodeCount - 1; iteration++) {
            boolean changed = false;

            for (final Node<T> node : reachableNodes) {
                final long nodeDistance = distanceByNode.getOrDefault(node, infinity);
                if (nodeDistance >= infinity) continue;

                for (final Edge<T> edge : node.outgoingEdges()) {
                    final Node<T> neighbor = edge.to();
                    if (!distanceByNode.containsKey(neighbor)) continue;

                    final long candidateDistance = nodeDistance + (long) edge.weight();
                    final long knownDistance = distanceByNode.get(neighbor);

                    if (candidateDistance < knownDistance) {
                        distanceByNode.put(neighbor, candidateDistance);
                        changed = true;
                    }
                }
            }

            if (!changed) break;
        }

        // Negative cycle check within the reachable subgraph
        for (final Node<T> node : reachableNodes) {
            final long nodeDistance = distanceByNode.getOrDefault(node, infinity);
            if (nodeDistance >= infinity) continue;

            for (final Edge<T> edge : node.outgoingEdges()) {
                final Node<T> neighbor = edge.to();
                if (!distanceByNode.containsKey(neighbor)) continue;

                final long candidateDistance = nodeDistance + (long) edge.weight();
                if (candidateDistance < distanceByNode.get(neighbor)) {
                    throw new IllegalStateException("Negative cycle reachable from source");
                }
            }
        }

        final long answer = distanceByNode.getOrDefault(destination, infinity);
        return answer >= infinity ? -1L : answer;
    }

    public Stream<Node<T>> shortestPathStreamTo(final Node<T> destination) {

        if (this == destination) return Stream.of(this);

        // Unweighted shortest path as a default "shortest path stream".
        // If you want weighted shortest paths, implement Dijkstra parents and reconstruct.
        final Map<Node<T>, Node<T>> parentByNode = new HashMap<>();
        final Set<Node<T>> visited = new HashSet<>();
        final ArrayDeque<Node<T>> queue = new ArrayDeque<>();

        visited.add(this);
        parentByNode.put(this, null);
        queue.addLast(this);

        while (!queue.isEmpty()) {
            final Node<T> current = queue.removeFirst();

            for (final Edge<T> edge : current.outgoingEdges()) {
                final Node<T> neighbor = edge.to();
                if (!visited.add(neighbor)) continue;

                parentByNode.put(neighbor, current);

                if (neighbor == destination) {
                    return reconstructPathAsStream(parentByNode, destination);
                }

                queue.addLast(neighbor);
            }
        }

        return Stream.empty();
    }

    private Stream<Node<T>> reconstructPathAsStream(final Map<Node<T>, Node<T>> parentByNode,
                                                    final Node<T> destination) {
        final LinkedList<Node<T>> path = new LinkedList<>();
        Node<T> current = destination;

        while (current != null) {
            path.addFirst(current);
            current = parentByNode.get(current);
        }

        return path.stream();
    }

    // -------------------------
    // Cycle detection
    // -------------------------

    enum VisitState { UNVISITED, VISITING, VISITED }

    /**
     * Directed cycle reachable from this node.
     */
    public boolean hasDirectedCycle() {
        final Map<Node<T>, VisitState> state = new IdentityHashMap<>();
        return hasDirectedCycle(state);
    }

    /**
     * Undirected cycle reachable from this node.
     */
    public boolean hasUndirectedCycle() {
        final Set<Node<T>> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        return hasUndirectedCycle(visited, null);
    }

    // Package-private helpers used by Graph for whole-graph checks

    boolean hasDirectedCycle(final Map<Node<T>, VisitState> state) {
        final VisitState current = state.get(this);
        if (current == VisitState.VISITING) return true;
        if (current == VisitState.VISITED) return false;

        state.put(this, VisitState.VISITING);

        for (final Edge<T> edge : outgoingEdges) {
            if (edge.to().hasDirectedCycle(state)) return true;
        }

        state.put(this, VisitState.VISITED);
        return false;
    }

    boolean hasUndirectedCycle(final Set<Node<T>> visited, final Node<T> parent) {
        visited.add(this);

        for (final Edge<T> edge : outgoingEdges) {
            final Node<T> neighbor = edge.to();

            if (neighbor == parent) continue;

            if (visited.contains(neighbor)) return true;

            if (neighbor.hasUndirectedCycle(visited, this)) return true;
        }

        return false;
    }

    // -------------------------
    // Validation
    // -------------------------

    private static <E> Stream<E> streamFromIterator(final Iterator<E> iterator) {
        final Spliterator<E> spliterator =
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false);
    }

    @Override public String toString() {
        return String.valueOf(id);
    }
}
