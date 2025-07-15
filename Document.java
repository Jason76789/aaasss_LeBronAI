package com.zyf.bigmodel;

/**
 * VectorStore 中使用的文档实体，
 * 包含一个唯一 id 和文本内容 content。
 */
public class Document {
    private final String id;
    private final String content;

    public Document(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
