package com.sqli.chatUI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
    public List<Question> getQuestions(String question,String token) {
        return questionRequest(QUESTIONS_LINK + question,token);
    }

    @Override
    public List<Answer> getAnswers(int questionID, String token) {
        return answerRequest(ANSWERS_LINK + questionID,token);
    }

    private List<Question> questionRequest(String request,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ObjectMapper mapper = new ObjectMapper();
//        return mapper.convertValue(new RestTemplate().getForObject(request, ArrayList.class), new TypeReference<List<Question>>() {
//        });
        return mapper.convertValue(new RestTemplate().exchange(request, HttpMethod.GET, entity, ArrayList.class).getBody(), new TypeReference<List<Question>>() {
        });
    }

    private List<Answer> answerRequest(String request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ObjectMapper mapper = new ObjectMapper();
//        return mapper.convertValue(new RestTemplate().getForObject(request, ArrayList.class), new TypeReference<List<Answer>>() {
//        });
        return mapper.convertValue(new RestTemplate().exchange(request, HttpMethod.GET, entity, ArrayList.class).getBody(), new TypeReference<List<Answer>>() {
        });
    }
}
