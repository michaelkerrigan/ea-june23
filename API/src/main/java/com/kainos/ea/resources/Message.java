package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Message {
    private String text;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    @JsonCreator
    public Message(@JsonProperty("text") String text) {
        this.setText(text);
    }

}
