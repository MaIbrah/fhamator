package com.sqli.chatUI.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.chatUI.models.Information;
import com.sqli.chatUI.properties.ServersProperties;

@Service
public class InformationService implements InformationServiceInter {




    public static final String LINK =  ServersProperties.getRestServer()+"/REST/";


    public List<Information> getInformation(){
        RestTemplate restTemplate= new RestTemplate();
        String request= LINK + "informations";
        return requestExecute(restTemplate, request);
    }


    public List<Information> getInformationByName(String name){
        RestTemplate restTemplate= new RestTemplate();
        String request= LINK +"elasticsearch/"+name;
        return requestExecute(restTemplate, request);
    }


    @Override
    public List<Information> getInformationByTypeAndName(String type, String name) {
        RestTemplate restTemplate= new RestTemplate();
        String request= LINK +"elasticsearch/"+type+"/"+name;
        return requestExecute(restTemplate, request);
    }

    @Override
    public List<Information> getInformationByTypeAndkeywords(String type, String keywords) {
        RestTemplate restTemplate= new RestTemplate();
        String request= LINK +"/elasticsearch/"+type+"/"+keywords;
        return requestExecute(restTemplate, request);
    }

    @Override
    public List<Information> getInformationByValues(String values) {
        RestTemplate restTemplate= new RestTemplate();
        String request= LINK +"elasticsearch/"+values;
        return requestExecute(restTemplate, request);
    }

    private List<Information> requestExecute(RestTemplate restTemplate, String request) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(restTemplate.getForObject(request, ArrayList.class), new TypeReference<List<Information>>() {
        });
    }
}
