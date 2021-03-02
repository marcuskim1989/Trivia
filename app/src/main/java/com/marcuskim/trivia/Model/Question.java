package com.marcuskim.trivia.Model;

public class Question {

    private String answer;
    private boolean AnswerTrue;

    public Question() {
    }

    public Question(String answer, boolean answerTrue) {
        this.answer = answer;
        AnswerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return AnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        AnswerTrue = answerTrue;
    }
}
