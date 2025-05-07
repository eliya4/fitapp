package com.example.myapplication.model;

import java.util.List;

public class ChatRequest {
    public String model = "gpt-3.5-turbo";
    public List<Message> messages;

    public ChatRequest(List<Message> messages) {
        this.messages = messages;
    }
}
