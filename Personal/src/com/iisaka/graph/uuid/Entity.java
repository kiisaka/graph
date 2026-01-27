package com.iisaka.graph.uuid;

import java.util.UUID;
import java.util.function.Supplier;

public interface Entity {

    Supplier<UUID> DEFAULT_ID_SUPPLIER = UUID::randomUUID;

    default Supplier<UUID> idSupplier() {
        return DEFAULT_ID_SUPPLIER;
    }
}

