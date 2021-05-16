package com.example.quizme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "quiz.db";

    /**
     * Erstellt einen Datenbankhandler, welcher von der Androidsuperklasse erbt. Diese Klasse erstellt die Tabellten für die Quizapp
     * @param context ApplicationProvider.getApplicationContext();
     */
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE quizSet (setUUID TEXT unique, name TEXT, category TEXT);");
        db.execSQL("CREATE TABLE quizQuestion (questionUUID TEXT unique, questionText TEXT, " +
                "correctAnswer TEXT, setUUID TEXT, FOREIGN KEY(setUUID) REFERENCES quizSet(setUUID) ON DELETE CASCADE);");
        db.execSQL("CREATE TABLE quizWrongAnswers (answer TEXT, questionUUID TEXT, " +
                "FOREIGN KEY(questionUUID) REFERENCES quizQuestion(questionUUID) " +
                "ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE quizSet;");
        db.execSQL("DROP TABLE quizQuestion;");
        db.execSQL("DROP TABLE quizWrongAnswers;");
        onCreate(db);
    }
}
