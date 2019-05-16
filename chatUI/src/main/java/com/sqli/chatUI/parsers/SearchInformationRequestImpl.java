package com.sqli.chatUI.parsers;

import java.util.Optional;

import org.springframework.web.client.RestTemplate;

import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.properties.ServersProperties;

public class SearchInformationRequestImpl implements SearchInformationRequest {

    private final static String URL = ServersProperties.getRestServer();

    @Override
    public Optional<SearchRequest> InformationRequestParser(String request) throws Exception {
        if (!request.isEmpty()) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = URL + "/api/naivesBayes/domain/" + request;
                SearchRequest searchRequest = restTemplate.getForObject(url, SearchRequest.class);
                return Optional.of(searchRequest);
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            throw new Exception();
        }
    }
}
