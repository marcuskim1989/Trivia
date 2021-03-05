package com.marcuskim.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
    private int pointCounter = 0;

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

                binding.pointCounterTextView.setText(pointCounter + "/" + questionList.size() + " Points");
                updateQuestion();

            }

        });

        binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);

                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();


                updateQuestion();
            }
        });

        binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void checkAnswer(boolean userChoice) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswer();
        int snackMessageId = 0;
        if (userChoice == answer) {
            snackMessageId = R.string.display_answer_correct;
            if(currentQuestionIndex >= questionList.size() - 1) {
                pointCounter = 0;
            } else {
                pointCounter++;
                animateScale();
            }
        } else {
            snackMessageId = R.string.display_answer_incorrect;
            animateShake();
        }
        binding.pointCounterTextView.setText(pointCounter + "/" + questionList.size() + " Points");

        Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }


    private void updateQuestion() {

        String question = questionList.get(currentQuestionIndex).getStatement();
        binding.questionTextView.setText(question);

        updateCounter((ArrayList<Question>) questionList);


    }

    private void animateScale() {
        Animation scale = AnimationUtils.loadAnimation(MainActivity.this, R.anim.correct_answer_zoom_pop);

        binding.pointCounterTextView.setAnimation(scale);
    }

    private void animateShake() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);

        binding.cardView.setAnimation(shake);
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.questionCounterTextView.setText(("Question: " + (currentQuestionIndex + 1) + "/" + questionArrayList.size()));
    }
}