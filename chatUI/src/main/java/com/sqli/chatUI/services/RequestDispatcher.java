package com.sqli.chatUI.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqli.chatUI.enums.ResponseCode;
import com.sqli.chatUI.models.Information;
import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.parsers.SearchInformationRequestImpl;

import models.Question;

@Service
public class RequestDispatcher implements RequestDispatcherInter {

    @Autowired
    InformationServiceInter informationService;

    @Autowired
    private StackOverFlowService stackOverFlowService;

    public String requestDispatcher(String request, String bearerToken, String sender) {

        if (request.toLowerCase().contains("help")) {
            return "help infos";
        } else {
            SearchRequest searchRequest;
            SearchInformationRequestImpl searchInformationRequest = new SearchInformationRequestImpl();
            try {
                searchRequest = searchInformationRequest.InformationRequestParser(request, bearerToken).get();
                if (!searchRequest.getPossibleDomains().isEmpty() || "none".equalsIgnoreCase(searchRequest.getBestDomainFound().getIntent())) {
                    return getTheBestAnswer(bearerToken, sender, searchRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseCode.NO_DATA_FOUND.getValue();
    }

    private String getTheBestAnswer(String bearerToken, String sender, SearchRequest searchRequest) {
        String returnedValue;
        if ("greetings".equalsIgnoreCase(searchRequest.getBestDomainFound().getIntent())) {
            String greetings = "\"Greetings " + sender + ", Hi, FHAMATOR Greets You!\" ";
            returnedValue = new JSONObject("{GREETINGS :" + greetings + "}").toString();
        } else {
            if ("whereabouts".equalsIgnoreCase(searchRequest.getBestDomainFound().getIntent())) {
                returnedValue =
                    new JSONObject("{WHEREABOUTS : \"I'm fine, thank you, but could you please state your request instead of chit-chatting!!\" }").toString();
            } else {
                if ("goodbye".equalsIgnoreCase(searchRequest.getBestDomainFound().getIntent())) {
                    returnedValue = new JSONObject("{GOODBYE : 'The user attempted to logout'}").toString();
                } else {
                    if ((!searchRequest.getPossibleKeywords().isEmpty()) && searchRequest.getPossibleDomains().size() == 1) {
                        returnedValue = new JSONObject("{INFORMATION :" + getResponse(searchRequest, bearerToken) + "}").toString();
                    } else {
                        returnedValue = new JSONObject("{DOMAINS : " + new JSONArray(searchRequest.getPossibleDomains()).toString() + "}").toString();
                    }
                }
            }
        }
        return returnedValue;
    }

    private JSONArray searchQuestion(String request, String token) {
        List<Question> questions = stackOverFlowService.getQuestions(request, token);
        return new JSONArray(questions);
    }

    private JSONArray getResponse(SearchRequest searchRequest, String token) {
        return new JSONArray(getResponseByTypeAndKeyWords(searchRequest, token));
    }

    private List<Information> filterByStartAndEndDate(final List<Information> informations, final LocalDateTime startDate, final LocalDateTime endDate) {
        return informations.stream().filter(info -> {
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            final LocalDateTime dateDu = LocalDateTime.parse(info.getAttributes().get("Date Du"), format);
            final LocalDateTime dateAu = LocalDateTime.parse(info.getAttributes().get("Date Au"), format);
            // return startDate.compareTo(dateDu)  dateDu.compareTo(endDate) >=0 || endDate.compareTo(dateAu)  dateAu.compareTo(endDate) >=0;
            return (dateDu.isAfter(startDate) && dateDu.isBefore(endDate)) || (dateAu.isAfter(startDate) && dateDu.isBefore(endDate));
        }).collect(Collectors.toList());
    }

    private List<Information> getResponseByTypeAndKeyWords(SearchRequest searchRequest, String token) {
        List<Information> informations = getByTypeAndKeywords(searchRequest.getBestDomainFound().getIntent(), String.join(" ",
            searchRequest.getPossibleKeywords().stream().map(key -> key.getIntent()).collect(Collectors.toList())), token);
        return informations;
    }

    private List<Information> getAll(String token) {
        return informationService.getInformation(token);
    }

    private List<Information> getByName(String name, String token) {
        return informationService.getInformationByName(name, token);
    }

    private List<Information> getByValues(String values, String token) {
        return informationService.getInformationByValues(values, token);
    }

    private List<Information> getByTypeAndKeywords(String type, String keywords, String token) {
        return informationService.getInformationByTypeAndkeywords(type, keywords, token);
    }

    private List<Information> getByTypeAndName(String type, String name, String token) {
        return informationService.getInformationByTypeAndName(type, name, token);
    }
}
