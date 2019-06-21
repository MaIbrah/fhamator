package com.sqli.chatUI.parsers;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sqli.chatUI.models.SearchRequest;
import com.sqli.chatUI.properties.YmlProperties;

public class SearchInformationRequestImpl implements SearchInformationRequest {

    private final static String URL = YmlProperties.getRestServer();

    @Override
    public Optional<SearchRequest> InformationRequestParser(String request, String token) throws Exception {
        if (!request.isEmpty()) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = URL + "/api/naivesBayes/domain/" + request;
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                HttpEntity<String> entity = new HttpEntity<>("", headers);
                ResponseEntity<SearchRequest> response = restTemplate.exchange(url, HttpMethod.GET, entity, SearchRequest.class);

                return Optional.of(response.getBody());
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            throw new Exception();
        }
    }
}
