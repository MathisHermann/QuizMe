package com.example.quizme.database;

import android.database.Cursor;

import com.example.quizme.entity.QuizQuestion;
import com.example.quizme.entity.QuizSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FlatfileDatabase {
    private final DBHandler dbHandler;
    /**
     * Erstellt eine Helferklasse zur Verwaltung von Quizentitäten
     * @param dbHandler Datenbankhandler
     */
    public FlatfileDatabase(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    /**
     * Gibt alle Quizsätze aus der Datenbank zurück
     * @return Sammung von Quizsätzen
     */
    public Set<QuizSet> getAllSets() {
        Set<QuizSet> sets = new HashSet<>();
        Cursor cursor = dbHandler.getReadableDatabase().
                rawQuery("SELECT * FROM quizSet;", null);
        if (cursor.moveToFirst()) {
            do {
                sets.add(QuizSet.constructSet(
                        cursor.getString(cursor.getColumnIndex("setUUID")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("category"))));
            } while (cursor.moveToNext());
        }
        for (QuizSet set : sets) {
            //BTW: Das was in de Doku stoht über SQLite isch in jedem Projekt mit Sicherheitsrequirements
            //brandgeförlich. Machet euri Applikatione SQL Injection sicher im Name vo Terry A. Davis und Dennis Ritchie
            //süchscht gits Handlige gäge die Genfer Konvention :))
            Cursor cursor2 = dbHandler.getReadableDatabase().
                    rawQuery(String.format("SELECT * FROM quizQuestion WHERE setUUID = '%s';",
                            set.getUUID()), null);
            if (cursor2.moveToFirst()) {
                do {
                    QuizQuestion question = new QuizQuestion(
                            cursor.getString(cursor.getColumnIndex("questionUUID")),
                            cursor.getString(cursor.getColumnIndex("questionText")),
                            cursor.getString(cursor.getColumnIndex("correctAnswer")));
                    Cursor cursor3 = dbHandler.getReadableDatabase().
                            rawQuery(String.format("SELECT * FROM quizWrongAnswers WHERE questionUUID = '%s';",
                                    question.uuid), null);
                    if (cursor3.moveToFirst()) {
                        do {
                            question.wrongAnswers.add(cursor
                                    .getString(cursor.getColumnIndex("answer")));
                        } while (cursor3.moveToNext());
                    }
                    cursor3.close();
                    set.questions.add(question);
                } while (cursor2.moveToNext());
            }
            cursor2.close();
        }
        cursor.close();
        return sets;
    }

    /**
     * Überschreibt die Datenbank mit der Sammlung von Quizsätzen
     * @param sets Sammlung von Quizsätzen zum Überschreiben
     */
    public void overrideSetsToDB(Collection<QuizSet> sets) {
        dbHandler.getWritableDatabase().execSQL("DELETE FROM quizSet;");
        for (QuizSet set : sets) {
            dbHandler.getWritableDatabase().execSQL(String
                    .format("INSERT INTO quizSet (setUUID, name, category) " +
                            "VALUES (%s, %s, %s)", set.getUUID(), set.getName(), set.getCategory()));
            for (QuizQuestion question : set.questions) {
                for (String wrongAnswer : question.wrongAnswers) {
                    dbHandler.getWritableDatabase().execSQL(String
                            .format("INSERT INTO wrongAnswers (answer, questionUUID) " +
                                    "VALUES (%s, %s)", wrongAnswer, question.uuid));
                }
                dbHandler.getWritableDatabase().execSQL(
                        String.format("INSERT INTO quizQuestion (questionUUID, questionText, " +
                        "correctAnswer, setUUID) VALUES (%s, %s, %s, %s)", question.uuid,
                                question.getQuestion(), question.getCorrectAnswer(), set.getUUID()));
            }
        }
    }
}
