package com.sqli.informationsREST.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import constants.Order;
import constants.QuestionSort;
import constants.SearchSort;
import constants.StackQuestionFilter;
import constants.StackSite;
import exceptions.StackExchangeException;
import generic.RequestObject;
import io.swagger.annotations.Api;
import models.Answer;
import models.Question;

@RestController
@RequestMapping("/REST/stackoverflow")
@Api(value = "StackOverFlow api", description = "StackOverFlow's search Operations", tags = {"StackOverFlow Rest"})
public class StackOverFlowController {
    private static Logger log = Logger.getLogger(StackOverFlowController.class);
    @GetMapping("questions/{request}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable("request") String request) {
        try {
            RequestObject<Question> requestObject = new RequestObject<>();
            requests.SearchRequest search =
                new requests.SearchRequest.Builder(request).sort(SearchSort.RELEVANCE).order(Order.DESC).addSite(StackSite.StackOverflow).addBody().build();
            List<Question> questions = requestObject.getObjects(search);
            this.log.info("get question["+request+"] from stackOverFlow");
            return ResponseEntity.ok(questions);
        } catch (StackExchangeException e) {
            this.log.info(e.getMessage());
            return ResponseEntity.status(300).build();
        }
    }

    @GetMapping("answers/{id}")
    public ResponseEntity<List<Answer>> getAnswers(@PathVariable("id") int id) {
        try {
            RequestObject<Answer> requestObject = new RequestObject<>();
            requests.QuestionApi answerRequest = new requests.QuestionApi.Builder().answers(id)
                .addBody().addFilter(StackQuestionFilter.pagesize, "5")
                .addSort(QuestionSort.VOTES)
                .addFilter(StackQuestionFilter.order, "desc")
                .addSite(StackSite.StackOverflow).build();
            List<Answer> answers = requestObject.getObjects(answerRequest);
            this.log.info("get answer with id ["+id+"] from stackOverFlow");
            return ResponseEntity.ok(answers);
        } catch (StackExchangeException e) {
            this.log.info(e.getMessage());
            return ResponseEntity.status(300).build();
        }
    }
}
