package com.example.millionaregame.controller;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int score;
    private List<Integer> safeHavens;

    public Player() {
        this.score = 0;
        this.safeHavens = new ArrayList<>();
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int increment) {
        this.score += increment;
    }

    public List<Integer> getSafeHavens() {
        return safeHavens;
    }

    public void addSafeHaven(int score) {
        this.safeHavens.add(score);
    }
}
// krish