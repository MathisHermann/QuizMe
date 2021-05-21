package com.example.quizme.questions;

public class QuizQuestion {
    private String question;
    private String[] answers;

    public QuizQuestion(String[] answers, String question) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
