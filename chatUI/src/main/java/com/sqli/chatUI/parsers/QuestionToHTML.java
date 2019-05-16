package com.sqli.chatUI.parsers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import constants.StackQuestionFilter;
import constants.StackSite;
import exceptions.StackExchangeException;
import generic.RequestObject;
import models.Answer;
import models.Question;

public class QuestionToHTML implements ResponseToHTMLParser {
    private List<Question> questions;

    private QuestionToHTML() {
        questions = new ArrayList<>();
    }

    public QuestionToHTML(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toHTML() {
        List<String> questionsHTML = new ArrayList<>();
        questionsHTML.add("<p>");

        Timestamp time = new Timestamp(System.currentTimeMillis());
        int answerTracker = 0;
        for (Question question : questions) {
            int responseTracker = 0;
            String className = "moreText" + (new Timestamp(System.currentTimeMillis()).getTime());
            questionsHTML.add("<li>");

            questionsHTML.add("<u style=\"vertical-align: 50%;\">Question number " + ++answerTracker + "</u>");
            questionsHTML.add("<div style=\"display: flex;\">");
            questionsHTML.add("<h4>" + question.getTitle() + " (" + question.getAnswer_count() + " Answers)</h4>");
            questionsHTML.add("<a class=\"moreless-button\" id=\"moreless"+answerTracker+"\" href=\"#\" onclick=\"showThis(\'#" + className + "\',\'#moreless"+answerTracker+"\')\">More</a>");
            questionsHTML.add("</div>");
            questionsHTML.add("<div style=' display: none; ' id=\"" + className + "\" >" + question.getBody() + "</div>");


            questionsHTML.add("<div class=\"fas fa-chevron-circle-down fa-3x chervon-up\" id=\"showResponseButton"
                + time.getTime() + answerTracker + "\" onclick=\"showResponse('" + time.getTime() + answerTracker + "')\" ></div>");
            questionsHTML.add("<ul style=\"overflow-y: hidden; display:none \" id=\"reponses" + time.getTime() + answerTracker + "\">");
            RequestObject<Answer> requestObject = new RequestObject<>();
            requests.QuestionApi answerRequest = null;
            try {
                answerRequest = new requests.QuestionApi.Builder().answers(question.getQuestion_id())
                    .addBody().addFilter(StackQuestionFilter.pagesize, "5")
                    .addSite(StackSite.StackOverflow).build();
            } catch (StackExchangeException e) {
                e.printStackTrace();
            }
            List<Answer> answers = requestObject.getObjects(answerRequest);
            for (Answer answer : answers) {

                StringBuilder html = new StringBuilder();
                html.append("<li style=\"color: darkgreen\">")
                    .append("<u style=\"vertical-align: 50%;\">Response number ").append(++responseTracker).append("</u></li>")
                    .append("<li class=\"response\"><strong> Answer : </strong>").append(answer.getBody()).append("</li>")
                    .append("</li>");
                questionsHTML.add(html.toString());
            }
            questionsHTML.add("</ul></li>");
        }
        questionsHTML.add("</p>");

        return String.join(" ", questionsHTML);
    }
}
