package com.sqli.chatUI.services;

import static com.sqli.chatUI.enums.Domains.FORMATION;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqli.chatUI.enums.Domains;
import com.sqli.chatUI.enums.ResponseCode;
import com.sqli.chatUI.models.Information;
import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.parsers.SearchInformationRequestImpl;

import models.Question;

@Service
public class RequestDispatcher implements RequestDispatcherInter {

    private final String NO_DATA_FOUND = "No data found !";

    @Autowired
    InformationServiceInter informationService;

    @Autowired
    private StackOverFlowService stackOverFlowService;

    public String requestDispatcher(String request,String bearerToken) {

        if (request.toLowerCase().contains("help")) {
            return "help infos";
        } else {
            SearchRequest searchRequest;
            SearchInformationRequestImpl searchInformationRequest = new SearchInformationRequestImpl();
            try {
                searchRequest = searchInformationRequest.InformationRequestParser(request,bearerToken).get();
                if (Domains.STACKOVERFLOW.toString().equalsIgnoreCase(searchRequest.getDomain())) {
                    return new JSONObject("{'STACKOVERFLOW':"+searchQuestion(request)+"}").toString();
                } else if ("none".equalsIgnoreCase(searchRequest.getDomain())
                    || searchRequest.getKeyword().contains("none")) {
                    return ResponseCode.NO_DOMAIN_FOUND.getValue();
                } else {
                    return new JSONObject("{\"FORMATION\":"+getResponse(searchRequest,bearerToken)+"}").toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseCode.NO_DATA_FOUND.getValue();
    }

    private JSONArray searchQuestion(String request){
        List<Question> questions = stackOverFlowService.getQuestions(request);
        return new JSONArray(questions);
    }

    private JSONArray getResponse(SearchRequest searchRequest, String token) {
        if (!searchRequest.getKeyword().contains("None")) {
            if (FORMATION.toString().equalsIgnoreCase(searchRequest.getDomain())) {
                List<Information> informations = getByTypeAndKeywords(searchRequest.getDomain(), String.join(" ", searchRequest.getKeyword()),token);
                if (searchRequest.getKeyword().contains("next")) {
                    final LocalDateTime startDate = LocalDateTime.now();
                    final LocalDateTime endDate = startDate.plusWeeks(1);
                    informations = filterByStartAndEndDate(informations, startDate, endDate);
                }
                if (searchRequest.getKeyword().contains("previous")) {
                    final LocalDateTime endDate = LocalDateTime.now();
                    final LocalDateTime startDate = endDate.minusWeeks(1);
                    informations = filterByStartAndEndDate(informations, startDate, endDate);
                }
                return new JSONArray(informations);
            }
            return new JSONArray(getResponseByTypeAndKeyWords(searchRequest, token));
        }
        return new JSONArray(NO_DATA_FOUND);
    }

    private List<Information> filterByStartAndEndDate(final List<Information> informations, final LocalDateTime startDate,final LocalDateTime endDate) {
        return informations.stream().filter(info -> {
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            final LocalDateTime dateDu = LocalDateTime.parse(info.getAttributes().get("Date Du"),format);
            final LocalDateTime dateAu = LocalDateTime.parse(info.getAttributes().get("Date Au"),format);
            // return startDate.compareTo(dateDu)  dateDu.compareTo(endDate) >=0 || endDate.compareTo(dateAu)  dateAu.compareTo(endDate) >=0;
            return (dateDu.isAfter(startDate) && dateDu.isBefore(endDate)) || (dateAu.isAfter(startDate) && dateDu.isBefore(endDate));
        }).collect(Collectors.toList());
    }

    private List<Information> getResponseByTypeAndKeyWords(SearchRequest searchRequest , String token ) {
        List<Information> informations = getByTypeAndKeywords(searchRequest.getDomain(), String.join(" ", searchRequest.getKeyword()),token);
        return informations;
    }


    private List<Information> getAll(String token) {
        return informationService.getInformation(token);
    }

    private List<Information> getByName(String name,String token) {
        return informationService.getInformationByName(name, token);
    }

    private List<Information> getByValues(String values, String token) {
        return informationService.getInformationByValues(values, token);
    }

    private List<Information> getByTypeAndKeywords(String type, String keywords , String token) {
        return informationService.getInformationByTypeAndkeywords(type, keywords, token);
    }

    private List<Information> getByTypeAndName(String type, String name , String token) {
        return informationService.getInformationByTypeAndName(type, name, token);
    }
}
