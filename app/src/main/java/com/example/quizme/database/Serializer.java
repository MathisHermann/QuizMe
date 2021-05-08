package com.example.quizme.database;

import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class Serializer {
    public static String serializeQuizSets(Collection<QuizSet> sets) {
        JSONArray rootArr = new JSONArray();
        for (QuizSet set : sets) {
            JSONObject setObj = new JSONObject();
            try {
                setObj = setObj.put("name", set.getName())
                        .put("category", set.getCategory());
                JSONArray questionArr = new JSONArray();
                for (QuizQuestion question : set.questions) {
                    JSONObject questionObj = new JSONObject();
                    questionArr = questionArr.put(questionObj
                            .put("question", question.getQuestion())
                            .put("correctAnswer", question.getCorrectAnswer())
                            .put("wrongAnswers", question.wrongAnswers.toArray(new String[0]))
                    );
                }
                setObj = setObj.put("questions", questionArr);
                rootArr = rootArr.put(setObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rootArr.toString();
    }
}
