package com.example.quizme;

import com.example.quizme.entity.QuizQuestion;

import java.util.ArrayList;

public class DataHolder {

    final ArrayList<QuizQuestion> arrayOfQuestions = new ArrayList<QuizQuestion>();

    private DataHolder() {
    }

    static DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    private static DataHolder instance;
}
