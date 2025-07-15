package com.zyf.bigmodel;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

//这个是注解：主要告诉虚拟机，以下是服务代码
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //服务代码的入口
        SpringApplication.run(Main.class, args);
        System.out.println("Hello, World!");
    }

    //模拟一个向量数据库
}