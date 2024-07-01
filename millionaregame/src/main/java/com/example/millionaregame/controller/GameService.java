package com.example.millionaregame.controller;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    private List<Question> questions;
    private int currentQuestionIndex;
    private List<Question> correctAnswers; // List to store correct answers
    private int points;
    private boolean lifelineUsed = false;
    private Player player;

    public GameService() {

        // Initialize questions
        questions = new ArrayList<>();
        List<Option> options;

        options = new ArrayList<>(
                List.of(new Option("Paris"), new Option("London"), new Option("Berlin"), new Option("Madrid")));
        Collections.shuffle(options);
        questions
                .add(new Question("What is the capital of France?", options, findCorrectOptionIndex(options, "Paris")));

        options = new ArrayList<>(
                List.of(new Option("Earth"), new Option("Mars"), new Option("Jupiter"), new Option("Venus")));
        Collections.shuffle(options);
        questions.add(new Question("What is the largest planet in the solar system?", options,
                findCorrectOptionIndex(options, "Jupiter")));

        options = new ArrayList<>(List.of(new Option("Ernest Hemingway"), new Option("Harper Lee"),
                new Option("Mark Twain"), new Option("F. Scott Fitzgerald")));
        Collections.shuffle(options);
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", options,
                findCorrectOptionIndex(options, "Harper Lee")));

        options = new ArrayList<>(List.of(new Option("7"), new Option("8"), new Option("9"), new Option("10")));
        Collections.shuffle(options);
        questions.add(new Question("What is the square root of 81?", options, findCorrectOptionIndex(options, "9")));

        options = new ArrayList<>(List.of(new Option("Gd"), new Option("Au"), new Option("Ag"), new Option("Go")));
        Collections.shuffle(options);
        questions.add(
                new Question("What is the chemical symbol for gold?", options, findCorrectOptionIndex(options, "Au")));

        options = new ArrayList<>(List.of(new Option("Marie Curie"), new Option("Alexander Fleming"),
                new Option("Louis Pasteur"), new Option("Isaac Newton")));
        Collections.shuffle(options);
        questions.add(new Question("Who discovered penicillin?", options,
                findCorrectOptionIndex(options, "Alexander Fleming")));

        options = new ArrayList<>(
                List.of(new Option("Sydney"), new Option("Melbourne"), new Option("Canberra"), new Option("Perth")));
        Collections.shuffle(options);
        questions.add(new Question("What is the capital of Australia?", options,
                findCorrectOptionIndex(options, "Canberra")));

        options = new ArrayList<>(List.of(new Option("Vincent van Gogh"), new Option("Pablo Picasso"),
                new Option("Leonardo da Vinci"), new Option("Claude Monet")));
        Collections.shuffle(options);
        questions.add(new Question("Who painted the Mona Lisa?", options,
                findCorrectOptionIndex(options, "Leonardo da Vinci")));

        options = new ArrayList<>(List.of(new Option("K2"), new Option("Mount Everest"), new Option("Kilimanjaro"),
                new Option("Mount Fuji")));
        Collections.shuffle(options);
        questions.add(new Question("What is the tallest mountain in the world?", options,
                findCorrectOptionIndex(options, "Mount Everest")));

        options = new ArrayList<>(
                List.of(new Option("Cheetah"), new Option("Lion"), new Option("Elephant"), new Option("Giraffe")));
        Collections.shuffle(options);
        questions.add(
                new Question("What is the fastest land animal?", options, findCorrectOptionIndex(options, "Cheetah")));

        currentQuestionIndex = 0;
        correctAnswers = new ArrayList<>(); // Initialize correctAnswers
        points = 0; // Initialize points
        player = new Player();
        player.addSafeHaven(5); // safe haven thresholds
        player.addSafeHaven(10);
        Collections.shuffle(questions);

    }

    public Question getNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public boolean checkAnswer(String chosenAnswer) {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return false; // false if index is out of bounds
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getOptions().get(currentQuestion.getCorrectOptionIndex()).getText();

        System.out.println("Correct answer: " + correctAnswer);
        System.out.println("Chosen answer: " + chosenAnswer);

        boolean correct = correctAnswer.equals(chosenAnswer);
        if (correct) {
            correctAnswers.add(currentQuestion);
            player.incrementScore(1); // Increase the player's score
            System.out.println("Correct! Your current score is: " + player.getScore()); // Print the current score
        }

        currentQuestionIndex++;
        return correct;
    }

    public void incrementPoints() {
        points++;
    }

    public int getPoints() {
        return points;
    }

    public boolean checkIfUserWon() {
        // The user wins if they answered all questions correctly
        return correctAnswers.size() == questions.size();
    }

    public int useLifeline() {
        if (!lifelineUsed && currentQuestionIndex < questions.size()) {
            lifelineUsed = true;
            return questions.get(currentQuestionIndex).getCorrectOptionIndex();
        }
        return -1; // Return -1 if the lifeline has already been used or there are no more
                   // questions
    }

    public String getCorrectAnswer() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            return currentQuestion.getOptions().get(currentQuestion.getCorrectOptionIndex()).getText();
        }
        return null;
    }

    public List<String> getTwoIncorrectAnswers() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            List<String> incorrectAnswers = new ArrayList<>();
            for (int i = 0; i < currentQuestion.getOptions().size(); i++) {
                if (i != currentQuestion.getCorrectOptionIndex()) {
                    incorrectAnswers.add(currentQuestion.getOptions().get(i).getText());
                }
            }
            Collections.shuffle(incorrectAnswers);
            return incorrectAnswers.subList(0, 2);
        }
        return null;
    }

    private int findCorrectOptionIndex(List<Option> options, String correctAnswer) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getText().equals(correctAnswer)) {
                return i;
            }
        }
        return -1; // Should never happen if correctAnswer is in options
    }

    public void restartGame() {
        // Reset the game state
        currentQuestionIndex = 0;
        correctAnswers.clear();
        points = 0;
        lifelineUsed = false;

        // Shuffle the options for each question
        for (Question question : questions) {
            Collections.shuffle(question.getOptions());
        }
    }

    public GameController getPlayer() {
        throw new UnsupportedOperationException("Unimplemented method 'getPlayer'");
    }

    // krish
}
