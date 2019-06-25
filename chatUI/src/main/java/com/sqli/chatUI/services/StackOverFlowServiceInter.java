package com.sqli.chatUI.services;

import java.util.List;

import models.Answer;
import models.Question;

public interface StackOverFlowServiceInter {
    List<Question> getQuestions(String question,String token);
    List<Answer> getAnswers(int questionID,String token);
}
