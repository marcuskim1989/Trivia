package com.marcuskim.trivia.Data;

import com.marcuskim.trivia.Model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
