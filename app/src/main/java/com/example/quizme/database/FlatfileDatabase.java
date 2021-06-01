package com.example.quizme.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            Log.e("DBank", "Set " + set.getName());
            //BTW: Das was in de Doku stoht über SQLite isch in jedem Projekt mit Sicherheitsrequirements
            //brandgeförlich. Machet euri Applikatione SQL Injection sicher im Name vo Terry A. Davis und Dennis Ritchie
            //süchscht gits Handlige gäge die Genfer Konvention :))
            Cursor cursor2 = dbHandler.getReadableDatabase().
                    rawQuery(String.format("SELECT * FROM quizQuestion WHERE setUUID = '%s';",
                            set.getUUID()), null);
            Log.e("DBank", "QEntry begin");
            if (cursor2.moveToFirst()) {
                do {
                    Log.e("DBank", "QEntry");
                    QuizQuestion question = new QuizQuestion(
                            cursor2.getString(cursor2.getColumnIndex("questionUUID")),
                            cursor2.getString(cursor2.getColumnIndex("questionText")),
                            cursor2.getString(cursor2.getColumnIndex("correctAnswer")));
                    Cursor cursor3 = dbHandler.getReadableDatabase().
                            rawQuery(String.format("SELECT * FROM quizWrongAnswers WHERE questionUUID = '%s';",
                                    question.uuid), null);
                    if (cursor3.moveToFirst()) {
                        do {
                            question.wrongAnswers.add(cursor3
                                    .getString(cursor3.getColumnIndex("answer")));
                        } while (cursor3.moveToNext());
                    }
                    cursor3.close();
                    Log.e("DBank", "Set " + set.getName());
                    Log.e("DBank", "Qu "+ set.questions.size());
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
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.execSQL("DELETE FROM quizSet;");
        db.execSQL("DELETE FROM quizQuestion;");
        db.execSQL("DELETE FROM quizWrongAnswers;");
        for (QuizSet set : sets) {
            db.execSQL(String
                    .format("INSERT INTO quizSet (setUUID, name, category) " +
                            "VALUES ('%s', '%s', '%s')", set.getUUID(), set.getName(), set.getCategory()));
            for (QuizQuestion question : set.questions) {
                for (String wrongAnswer : question.wrongAnswers) {
                    db.execSQL(String
                            .format("INSERT INTO quizWrongAnswers (answer, questionUUID) " +
                                    "VALUES ('%s', '%s')", wrongAnswer, question.uuid));
                }
                db.execSQL(
                        String.format("INSERT INTO quizQuestion (questionUUID, questionText, " +
                        "correctAnswer, setUUID) VALUES ('%s', '%s', '%s', '%s')", question.uuid,
                                question.getQuestion(), question.getCorrectAnswer(), set.getUUID()));
            }
        }
    }

    public void writeHighscore(String uuid, String name, int score) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.execSQL(String.format("INSERT INTO highscore (setUUID, name, score) " +
                "VALUES ('%s', '%s', %d);", uuid, name, score));
    }

    public int getHighscoreScore(String uuid) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        int score = 0;
        Cursor cursor = dbHandler.getReadableDatabase().
                rawQuery(String.format("SELECT score, strftime('%%s', timestamp) as time FROM highscore WHERE setUUID = '%s' " +
                        "ORDER BY score DESC, time DESC LIMIT 1;", uuid), null);
        if (cursor.moveToFirst()) {
            do {
                score = cursor.getInt(cursor.getColumnIndex("score"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return score;
    }

    public String getHighscoreName(String uuid) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String name = "User";
        Cursor cursor = dbHandler.getReadableDatabase().
                rawQuery(String.format("SELECT name, strftime('%%s', timestamp) as time FROM highscore WHERE setUUID = '%s' " +
                        "ORDER BY score DESC, time DESC LIMIT 1;", uuid), null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return name;
    }
}
