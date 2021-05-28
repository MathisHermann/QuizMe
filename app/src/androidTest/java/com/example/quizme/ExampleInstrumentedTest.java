package com.example.quizme;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        QuizSet set = QuizSet.createNewSet("Android", "IT");
        QuizQuestion question = new QuizQuestion(UUID.randomUUID().toString(),
                "Was für ein Codewort hat Android 2.3.6?", "Gingerbread");
        question.wrongAnswers.add("Lollipop");
        question.wrongAnswers.add("Q");
        question.wrongAnswers.add("Jellybean");
        set.questions.add(question);

    }
}