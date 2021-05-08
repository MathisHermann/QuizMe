package com.example.quizme.database;

import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Deserializer {
    public static Set<QuizSet> deserializeSets(String json) throws DeserializationException {
        Set<QuizSet> quizSets = new HashSet<>();
        try {
            JSONArray rootArr = new JSONArray(json);
            for (int i = 0 ; i<rootArr.length() ; i++) {
                try {
                    JSONObject setObj = rootArr.getJSONObject(i);
                    QuizSet set = QuizSet.createNewSet(setObj.getString("name"),
                            setObj.getString("category"));
                    JSONArray questionArr = setObj.getJSONArray("questions");
                    for (int j = 0 ; j < questionArr.length() ; j++) {
                        JSONObject questionObj = questionArr.getJSONObject(j);
                        QuizQuestion question = new QuizQuestion(UUID.randomUUID().toString(),
                                questionObj.getString("question"),
                                questionObj.getString("correctAnswer"));
                        JSONArray wrongAnswersArr = questionObj.getJSONArray("wrongAnswers");
                        for (int k = 0 ; k <  wrongAnswersArr.length(); k++) {
                            question.wrongAnswers.add(wrongAnswersArr.getString(k));
                        }
                        set.questions.add(question);
                    }
                    quizSets.add(set);
                } catch (JSONException e) {
                    throw new DeserializationException("Set "+i+" ist nicht syntaktisch gültig.");
                }
            }
        } catch (JSONException e) {
            throw new DeserializationException("JSON-Eingabe ist in der Wurzel kein Array.");
        }
        return quizSets;
    }

    public static class DeserializationException extends Exception {
        public DeserializationException(String error) {
            super(error);
        }
    }
}
