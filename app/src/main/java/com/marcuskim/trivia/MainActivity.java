package com.marcuskim.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.marcuskim.trivia.Data.AnswerListAsyncResponse;
import com.marcuskim.trivia.Data.Repository;
import com.marcuskim.trivia.Model.Question;
import com.marcuskim.trivia.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;

    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionList = new Repository().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {


                binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getStatement());

                updateQuestion();

            }

        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();

            }
        });

        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

    }

    private void checkAnswer(boolean userChoice) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswer();
        int snackMessageId = 0;
        if (userChoice == answer) {
            snackMessageId = R.string.display_answer_correct;
        } else {
            snackMessageId = R.string.display_answer_incorrect;
        }

        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }


    private void updateQuestion() {

        String question = questionList.get(currentQuestionIndex).getStatement();
        binding.questionTextView.setText(question);

        updateCounter((ArrayList<Question>) questionList);


    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.questionCounterTextView.setText(("Question: " + (currentQuestionIndex + 1) + "/" + questionArrayList.size()));
    }
}