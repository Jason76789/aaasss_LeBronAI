package com.zyf.bigmodel;

import java.util.List;

public class ChatRequest {
    /** 场景提示等系统级上下文 */
    private String systemContext;
    /** 用户本次新输入 */
    private String userMessage;
    /** 已有的对话历史（role: "user" 或 "assistant"） */
    private List<ChatMessage> history;

    public ChatRequest() {}

    public String getSystemContext() {
        return systemContext;
    }
    public void setSystemContext(String systemContext) {
        this.systemContext = systemContext;
    }

    public String getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public List<ChatMessage> getHistory() {
        return history;
    }
    public void setHistory(List<ChatMessage> history) {
        this.history = history;
    }

    /** 嵌套静态类，用于传输历史消息 */
    public static class ChatMessage {
        private String role;    // "user" 或 "assistant"
        private String content; // 消息文本

        public ChatMessage() {}
        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }
}
