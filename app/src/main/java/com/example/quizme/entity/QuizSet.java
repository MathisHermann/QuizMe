package com.example.quizme.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QuizSet {
    private final String setUUID;
    private String name;
    private String category;
    public final Set<QuizQuestion> questions = new HashSet<>();

    /**
     * Erstellt einen neuen Quizsatz
     * @param name Name des Satzes
     * @param category Kategorie des Satzes
     * @return Quizsatz
     */
    public static QuizSet createNewSet(String name, String category) {
        return new QuizSet(UUID.randomUUID().toString(), name, category);
    }

    /**
     * Konstruiert einen Quizsatz aus der DB. Dies ist eine Hilfsmethode und nicht zur Implementation von Dritten vorgesehen.
     * @param setUUID UUID des Satzes
     * @param name Name des Satzes
     * @param category Kategorie des Satzes
     * @return Quizsatz
     */
    public static QuizSet constructSet(String setUUID, String name, String category) {
        return new QuizSet(setUUID, name, category);
    }

    private QuizSet(String uuid, String name, String category) {
        this.setUUID = uuid;
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUUID() {
        return setUUID;
    }
}
