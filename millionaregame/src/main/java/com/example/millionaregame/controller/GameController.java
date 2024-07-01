package com.example.millionaregame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String startPage() {
        return "start";
    }

    @PostMapping("/answer")
    public String submitAnswer(@ModelAttribute Answer answer, Model model) {
        // Extract the chosen option from the Answer object
        String chosenOption = answer.getChosenOption();
        // Pass the chosen option as a String to the checkAnswer method
        gameService.checkAnswer(chosenOption);
        // No need to increment correctAnswersCount here, it's handled in the
        // GameService
        return "redirect:/game";
    }

    @GetMapping("/end")
    public String endPage(Model model) {
        boolean won = gameService.checkIfUserWon();
        model.addAttribute("won", won);
        return "end";
    }

    @GetMapping("/correctAnswer")
    public ResponseEntity<String> getCorrectAnswer() {
        return ResponseEntity.ok(gameService.getCorrectAnswer());
    }

    @GetMapping("/twoIncorrectAnswers")
    public ResponseEntity<List<String>> getTwoIncorrectAnswers() {
        return ResponseEntity.ok(gameService.getTwoIncorrectAnswers());
    }

    @PostMapping("/restart")
    public String restartGame() {
        gameService.restartGame();
        return "redirect:/game";
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> getScore() {
        return ResponseEntity.ok(gameService.getPoints());
    }

    @GetMapping("/game")
    public String showQuestion(Model model) {
        Question question = gameService.getNextQuestion();
        if (question == null) {
            return "redirect:/end";
        }
        model.addAttribute("question", question);
        model.addAttribute("answer", new Answer()); // Initialize an empty Answer object
        model.addAttribute("score", gameService.getPoints()); // Add the score to the model
        return "question";
    }

}
// krish
