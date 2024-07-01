package com.example.millionaregame.controller;

public class CorrectAnswerLifeline implements Lifeline {
    private boolean used = false;

    @Override
    public void execute(Question question) {
        if (!used) {
            // Highlight the correct answer
            int correctOptionIndex = question.getCorrectOptionIndex();
            Option correctOption = question.getOptions().get(correctOptionIndex);
            correctOption.setHighlighted(true);

            // Marks the lifeline as used personal notes
            used = true;
        }
    }

    public boolean isUsed() {
        return used;
    }
}
// krish