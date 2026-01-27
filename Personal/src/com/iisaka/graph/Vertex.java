package com.iisaka.graph;

import java.util.*;

public class Vertex<VID> extends Entity<VID> {

    private final Set<String> labels = new HashSet<>();

    public Vertex(VID id, String createdBy) {
        super(id, createdBy);
    }

    public Set<String> labelsView() {
        return Collections.unmodifiableSet(labels);
    }

    public void addLabel(String label, String actor) {
        Objects.requireNonNull(label, "label");
        if (labels.add(label)) {
            touch(actor);
        }
    }

    public void removeLabel(String label, String actor) {
        Objects.requireNonNull(label, "label");
        if (labels.remove(label)) {
            touch(actor);
        }
    }
}
