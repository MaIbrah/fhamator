package com.sqli.chatUI.services;

import static com.sqli.chatUI.enums.Domains.FORMATION;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqli.chatUI.enums.Domains;
import com.sqli.chatUI.enums.ResponseCode;
import com.sqli.chatUI.models.Information;
import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.parsers.InformationToHTML;
import com.sqli.chatUI.parsers.QuestionToHTML;
import com.sqli.chatUI.parsers.ResponseToHTMLParser;
import com.sqli.chatUI.parsers.SearchInformationRequestImpl;
import com.sqli.chatUI.properties.YmlProperties;

import models.Question;

@Service
public class RequestDispatcher implements RequestDispatcherInter {

    private final String NO_DATA_FOUND = "No data found !";

    @Autowired
    InformationServiceInter informationService;

    @Autowired
    private StackOverFlowService stackOverFlowService;

    public String requestDispatcher(String request) {

        if (request.toLowerCase().contains("help")) {
            return "help infos";
        } else {
            SearchRequest searchRequest;
            SearchInformationRequestImpl searchInformationRequest = new SearchInformationRequestImpl();
            try {
                searchRequest = searchInformationRequest.InformationRequestParser(request).get();
                if (Domains.STACKOVERFLOW.toString().equalsIgnoreCase(searchRequest.getDomain())) {
                    return searchQuestion(request);
                } else if ("none".equalsIgnoreCase(searchRequest.getDomain())
                    || searchRequest.getKeyword().contains("none")) {
                    return ResponseCode.NO_DOMAIN_FOUND.getValue();
                } else {
                    return getResponse(searchRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseCode.NO_DATA_FOUND.getValue();
    }

    private String searchQuestion(String request){
        List<Question> questions = stackOverFlowService.getQuestions(request);
        ResponseToHTMLParser questionParser = new QuestionToHTML(questions.size() >= (Integer) YmlProperties.getStackoverflowResponses() ?
            questions.subList(0, (Integer) YmlProperties.getStackoverflowResponses()) : questions);
        return questionParser.toHTML();
    }

    private String getResponse(SearchRequest searchRequest) {
        if (!searchRequest.getKeyword().contains("None")) {
            if (FORMATION.toString().equalsIgnoreCase(searchRequest.getDomain())) {
                List<Information> informations = getByTypeAndKeywords(searchRequest.getDomain(), String.join(" ", searchRequest.getKeyword()));
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
                return getInformationAsString(searchRequest,informations);
            }
            return getResponseByTypeAndKeyWords(searchRequest);
        }
        return NO_DATA_FOUND;
    }

    private List<Information> filterByStartAndEndDate(final List<Information> informations, final LocalDateTime startDate,final LocalDateTime endDate) {
        return informations.stream().filter(info -> {
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            final LocalDateTime dateDu = LocalDateTime.parse(info.getAttributes().get("Date Du"),format);
            final LocalDateTime dateAu = LocalDateTime.parse(info.getAttributes().get("Date Au"),format);
           // return startDate.compareTo(dateDu) * dateDu.compareTo(endDate) >=0 || endDate.compareTo(dateAu) * dateAu.compareTo(endDate) >=0;
            return (dateDu.isAfter(startDate) && dateDu.isBefore(endDate)) || (dateAu.isAfter(startDate) && dateDu.isBefore(endDate));
        }).collect(Collectors.toList());
    }

    private String getResponseByTypeAndKeyWords(SearchRequest searchRequest) {
        List<Information> informations = getByTypeAndKeywords(searchRequest.getDomain(), String.join(" ", searchRequest.getKeyword()));
        return getInformationAsString(searchRequest, informations);
    }

    private String getInformationAsString(SearchRequest searchRequest, List<Information> informations) {
        if (informations.isEmpty()) {
            return "<strong style='color: #ff4743'>No Data Found !</strong>";
        } else {
            ResponseToHTMLParser informationParser = new InformationToHTML(informations, searchRequest.getKeyword());
            return informationParser.toHTML();
        }
    }

    private List<Information> getAll() {
        return informationService.getInformation();
    }

    private List<Information> getByName(String name) {
        return informationService.getInformationByName(name);
    }

    private List<Information> getByValues(String values) {
        return informationService.getInformationByValues(values);
    }

    private List<Information> getByTypeAndKeywords(String type, String keywords) {
        return informationService.getInformationByTypeAndkeywords(type, keywords);
    }

    private List<Information> getByTypeAndName(String type, String name) {
        return informationService.getInformationByTypeAndName(type, name);
    }
}
