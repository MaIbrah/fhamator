package com.sqli.chatUI.services;

import java.util.List;

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

import constants.Order;

import constants.SearchSort;
import constants.StackSite;
import generic.RequestObject;
import models.Question;

@Service
public class RequestDispatcher implements RequestDispatcherInter {

    private final String NO_DATA_FOUND = "No data found !";

    @Autowired
    InformationServiceInter informationService;

    public String requestDispatcher(String request) {

        if (request.toLowerCase().contains("help")) {
            return "help infos";
        } else {
            SearchRequest searchRequest;
            SearchInformationRequestImpl searchInformationRequest = new SearchInformationRequestImpl();
            try {
                searchRequest = searchInformationRequest.InformationRequestParser(request).get();
                if("none".equalsIgnoreCase(searchRequest.getDomain())){
                   return ResponseCode.NO_DOMAIN_FOUND.getValue();
                }
                if (Domains.STACKOVERFLOW.toString().equalsIgnoreCase(searchRequest.getDomain())) {
                    return searchQuestion(request);
                } else {
                    return getResponse(searchRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseCode.NO_DATA_FOUND.getValue();
    }

    private String searchQuestion(String request) throws Exception {
        RequestObject<Question> requestObject = new RequestObject<>();
        requests.SearchRequest search =new requests.SearchRequest.Builder(request).sort(SearchSort.RELEVANCE).order(Order.DESC).addSite(StackSite.StackOverflow).addBody().build();
        List<Question> questions = requestObject.getObjects(search);
        ResponseToHTMLParser questionParser = new QuestionToHTML(questions.size() >= 3 ? questions.subList(0, 3) : questions);
        return questionParser.toHTML();
    }

    private String getResponse(SearchRequest searchRequest) {
        if (!searchRequest.getKeyword().contains("None")) {
            List<Information> informations = getByTypeAndKeywords(searchRequest.getDomain(), String.join(" ", searchRequest.getKeyword()));
            if (informations.isEmpty()) {
                return "<strong style='color: #ff4743'>No Data Found !</strong>";
            } else {
                ResponseToHTMLParser informationParser = new InformationToHTML(informations, searchRequest.getKeyword());
                return informationParser.toHTML();
            }
        }
        return NO_DATA_FOUND;
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
