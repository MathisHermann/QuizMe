package com.example.quizme.questions;

import java.util.ArrayList;

public class QuizSet {
    private String name;
    private ArrayList<QuizQuestion> questions;
    private byte numQuestions;


    private final static byte MAXLENGTH = 100;
    private static ArrayList<String> errorMessages;
    private static String[] newQuestion = new String[5]; // used to store the input data for a new question


    public QuizSet(String name, byte numQuestions) {
        this.name = name;
        this.numQuestions = numQuestions;
        this.questions = new ArrayList<QuizQuestion>();
    }

    public QuizSet(String name, byte numQuestions, ArrayList<QuizQuestion> questions) {
        this.name = name;
        this.numQuestions = numQuestions;
        this.questions = questions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(byte numQuestions) {
        this.numQuestions = numQuestions;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
    }
}
