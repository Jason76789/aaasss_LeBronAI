package com.zyf.bigmodel;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class VectorStoreService {

    private final EmbeddingModel embeddingModel;
    private final Map<String, float[]> index = new ConcurrentHashMap<>();
    private final Map<String, Document> docs  = new ConcurrentHashMap<>();

    public VectorStoreService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public void addDocument(Document doc) {
        float[] vec = embeddingModel.embed(doc.getContent());
        docs.put(doc.getId(), doc);
        index.put(doc.getId(), vec);
    }

    public List<Document> similaritySearch(String query, int topK) {
        float[] qv = embeddingModel.embed(query);
        return index.entrySet().stream()
                .map(e -> Map.entry(docs.get(e.getKey()),
                        cosineSimilarity(qv, e.getValue())))
                .sorted((a,b) -> Float.compare(b.getValue(), a.getValue()))
                .limit(topK)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private float cosineSimilarity(float[] a, float[] b) {
        float dot=0, na=0, nb=0;
        for(int i=0;i<a.length;i++){
            dot += a[i]*b[i]; na+=a[i]*a[i]; nb+=b[i]*b[i];
        }
        return (float)(dot/(Math.sqrt(na)*Math.sqrt(nb)+1e-10));
    }

    @PostConstruct
    public void init() {
        // 文档 1
        addDocument(new Document("streams-1",
                "Java Streams API是Java 8引入的一种用于处理集合的高阶迭代抽象，" +
                        "支持map、filter、reduce等操作，可并行执行，提高代码可读性和性能。"));

        // 文档 2
        addDocument(new Document("streams-2",
                "Stream的执行分为创建流（source）、中间操作（intermediate）和终结操作（terminal）。" +
                        "中间操作惰性执行，终结操作触发整个流水线的执行。"));

        // 你可以继续 addDocument(...) 添加更多段落
    }
}

