package com.example.quizme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizme.entity.QuizQuestion;

import java.util.ArrayList;

public class QuestionAdapter extends ArrayAdapter<QuizQuestion> {
    public QuestionAdapter(Context context, ArrayList<QuizQuestion> questions) {
        super(context, 0, questions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        QuizQuestion quesTion = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_create_or_edit_set, parent, false);
        }
        // Lookup view for data population
        EditText etQuestion = (EditText) convertView.findViewById(R.id.editTitleQuestion);
        EditText etRightAnswer = (EditText) convertView.findViewById(R.id.editCorrectAnswer);
        EditText etWrongAnswer1 = (EditText) convertView.findViewById(R.id.editWrongAnswerOne);
        EditText etWrongAnswer2 = (EditText) convertView.findViewById(R.id.editWrongAnswerTwo);
        EditText etWrongAnswer3 = (EditText) convertView.findViewById(R.id.editWrongAnswerThree);


        // Populate the data into the template view using the data object
        etQuestion.setText(quesTion.getQuestion());
        etRightAnswer.setText(quesTion.getCorrectAnswer());

        // Converting HashSet to Array
        String[] arrayWrongAnswers = quesTion.wrongAnswers.toArray(new String[quesTion.wrongAnswers.size()]);

        etWrongAnswer1.setText(arrayWrongAnswers[0]);
        etWrongAnswer2.setText(arrayWrongAnswers[1]);
        etWrongAnswer3.setText(arrayWrongAnswers[2]);


        // Return the completed view to render on screen
        return convertView;
    }
}
