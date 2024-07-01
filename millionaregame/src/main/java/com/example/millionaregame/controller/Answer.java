package com.example.millionaregame.controller;

public class Answer {
    private String chosenOption;

    // Default constructor
    public Answer() {
    }

    public Answer(String chosenOption) {
        this.chosenOption = chosenOption;
    }

    // Getters and setters
    public String getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(String chosenOption) {
        this.chosenOption = chosenOption;
    }
}

// krish