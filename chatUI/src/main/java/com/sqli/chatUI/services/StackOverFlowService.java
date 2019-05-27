package com.sqli.chatUI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Answer;
import models.Question;

@Service
public class StackOverFlowService implements StackOverFlowServiceInter {
    private final String QUESTIONS_LINK = "http://localhost:8081/REST/stackoverflow/questions/";
    private final String ANSWERS_LINK = "http://localhost:8081/REST/stackoverflow/answers/";

    @Override
    public List<Question> getQuestions(String question) {
        return questionRequest(QUESTIONS_LINK + question);
    }

    @Override
    public List<Answer> getAnswers(int questionID) {
        return answerRequest(ANSWERS_LINK + questionID);
    }

    private List<Question> questionRequest(String request) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(new RestTemplate().getForObject(request, ArrayList.class), new TypeReference<List<Question>>() {
        });
    }

    private List<Answer> answerRequest(String request) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(new RestTemplate().getForObject(request, ArrayList.class), new TypeReference<List<Answer>>() {
        });
    }
}
