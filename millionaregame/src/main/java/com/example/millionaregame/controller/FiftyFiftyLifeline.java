package com.example.millionaregame.controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class FiftyFiftyLifeline implements Lifeline {
    @Override
    public void execute(Question question) {
        // Get the correct option index
        int correctOptionIndex = question.getCorrectOptionIndex();

        // Create a list of incorrect option indices
        List<Integer> incorrectOptionIndices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != correctOptionIndex) {
                incorrectOptionIndices.add(i);
            }
        }

        // Randomly remove one incorrect option
        Random random = new Random();
        int removeIndex = random.nextInt(incorrectOptionIndices.size());
        question.getOptions().set(incorrectOptionIndices.get(removeIndex), null);
    }
}
// krish
