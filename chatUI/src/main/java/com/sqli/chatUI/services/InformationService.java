package com.sqli.chatUI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.chatUI.models.Information;
import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.properties.YmlProperties;

@Service
public class InformationService implements InformationServiceInter {

    public static final String LINK = YmlProperties.getRestServer() + "/REST/";

    public List<Information> getInformation(String token) {

        RestTemplate restTemplate = new RestTemplate();
        String request = LINK + "informations";
        return requestExecute(restTemplate, request, token);
    }

    public List<Information> getInformationByName(String name, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String request = LINK + "elasticsearch/" + name;
        return requestExecute(restTemplate, request, token);
    }

    @Override
    public List<Information> getInformationByTypeAndName(String type, String name, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String request = LINK + "elasticsearch/" + type + "/" + name;
        return requestExecute(restTemplate, request, token);
    }

    @Override
    public List<Information> getInformationByTypeAndkeywords(String type, String keywords, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String request = LINK + "/elasticsearch/" + type + "/" + keywords;
        return requestExecute(restTemplate, request, token);
    }

    @Override
    public List<Information> getInformationByValues(String values, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String request = LINK + "elasticsearch/" + values;
        return requestExecute(restTemplate, request, token);
    }

    private List<Information> requestExecute(RestTemplate restTemplate, String request, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(restTemplate.exchange(request, HttpMethod.GET, entity, ArrayList.class).getBody(), new TypeReference<List<Information>>() {
        });
    }
}
