package com.upce.ar;

class Score {

    private static int score;

    static int getScore() {
        return score;
    }


    static void addScore(int score) {
        Score.score += score;
    }

    static void setScoreZero() {
        score = 0;
    }
}
