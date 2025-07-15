package com.zyf.bigmodel;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;              // 新增
import org.springframework.ai.chat.model.ChatResponse;           // 新增
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BigModelController {

    @Autowired
    private OllamaChatModel chatModel;

    @PostMapping(
            value = "/ai/chat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> chat(@RequestBody ChatRequest req) {
        // 1) 构建一组 Message 对象
        List<Message> messages = new ArrayList<>();

        // 1a) 系统上下文
        messages.add(new SystemMessage(req.getSystemContext()));

        // 1b) 历史上下文
        if (req.getHistory() != null) {
            for (ChatRequest.ChatMessage h : req.getHistory()) {
                if ("user".equalsIgnoreCase(h.getRole())) {
                    messages.add(new UserMessage(h.getContent()));
                } else {
                    messages.add(new AssistantMessage(h.getContent()));
                }
            }
        }

        // 1c) 本次用户输入
        messages.add(new UserMessage(req.getUserMessage()));

        // 2) 构建 Prompt
        Prompt prompt = new Prompt(messages);

        // 3) 调用模型，拿到 ChatResponse
        ChatResponse resp = this.chatModel.call(prompt);

        // 4) 从 ChatResponse 中提取最终文本
        //    下面这一行的链根据你用的 spring-ai 版本可能略有不同，
        //    关键是要拿到 generate() 或 getText() 的内容。
        String answer = resp
                .getResult()          // ModelResponse<Generation>
                .getOutput()          // AssistantMessage
                .getText();           // 里面的 String 文本

        // 5) 返回
        return Map.of("generation", answer);
    }

    @GetMapping(
            value = "/ai/generate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public Map<String, String> generate(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message
    ) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    private int number = 0;
    @GetMapping("/getData")
    @ResponseBody
    public String getData() {
        return "你好啊 " + (number++);
    }
}
