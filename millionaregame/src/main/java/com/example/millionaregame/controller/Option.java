package com.example.millionaregame.controller;

public class Option {
    private String text;
    private boolean highlighted;

    public Option(String text) {
        this.text = text;
        this.highlighted = false; // Initialize highlighted as false
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
// krish
