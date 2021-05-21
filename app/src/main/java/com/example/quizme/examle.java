package com.example.quizme;

import java.util.ArrayList;

import java.util.stream.Collectors;

import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.database.DBHandler;

import android.content.Context;



import com.example.quizme.database.Serializer;
import com.example.quizme.database.Deserializer;

public class examle {


    public void useAppContext() {

        FlatfileDatabase fdb = new FlatfileDatabase(new DBHandler(null));

        fdb.getAllSets().forEach(quizSet -> {
            System.out.println(quizSet.getName());

            System.out.println(quizSet.getCategory());

            System.out.println(quizSet.getUUID());

            quizSet.questions.forEach(quizQuestion -> {

                System.out.println(quizQuestion.getQuestion());
                System.out.println(quizQuestion.getCorrectAnswer());

                System.out.println(quizQuestion.wrongAnswers);
                System.out.println(quizQuestion.uuid);

            });
        });

        Serializer.serializeQuizSets(fdb.getAllSets().stream().filter(set -> {

            return set.getUUID().equals("akikafladt");
        }).collect(Collectors.toList()));


    }
}
