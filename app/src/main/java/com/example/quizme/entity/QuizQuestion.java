package com.example.quizme.entity;

import java.util.HashSet;
import java.util.Set;

public class QuizQuestion {
    public final String uuid;
    private String question;

    private String correctAnswer;
    public final Set<String> wrongAnswers = new HashSet<>();

    /**
     * Erstellt eine Quizfrage als Entität
     * @param uuid UUID
     * @param question Fragestellung
     * @param correctAnswer Richtige Antwort
     */
    public QuizQuestion(String uuid, String question, String correctAnswer) {
        this.uuid = uuid;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
