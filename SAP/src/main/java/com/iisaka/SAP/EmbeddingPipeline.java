package com.iisaka.SAP;

import java.util.ArrayList;
import java.util.List;

public class EmbeddingPipeline {
    private final TextChunker chunker;
    private final EmbeddingClient embeddingClient;
    private final EmbeddingReducer reducer;

    public EmbeddingPipeline(
            TextChunker chunker,
            EmbeddingClient embeddingClient,
            EmbeddingReducer reducer
    ) {
        this.chunker = chunker;
        this.embeddingClient = embeddingClient;
        this.reducer = reducer;
    }

    public float[] extractEmbeddings(String text) {
        List<String> chunks = chunker.chunk(text);
        List<float[]> embeddings = new ArrayList<>();

        for (String chunk : chunks) {
            embeddings.add(embeddingClient.embed(chunk));
        }

        return reducer.reduce(embeddings);
    }
}
