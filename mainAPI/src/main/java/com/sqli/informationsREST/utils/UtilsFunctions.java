package com.sqli.informationsREST.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.informationsREST.models.Information;

public class UtilsFunctions {
    public static final String ELASTIC_SEARCH_LINK = "http://localhost:8081/informations/";

    public static List<Information> requestExecute(RestTemplate restTemplate, String request) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(restTemplate.getForObject(request, ArrayList.class), new TypeReference<List<Information>>() {
        });
    }
}

