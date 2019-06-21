package com.sqli.chatUI.parsers;

import java.util.Optional;

import com.sqli.chatUI.models.SearchRequest;

public interface SearchInformationRequest {
    Optional<SearchRequest> InformationRequestParser(String request,String token) throws Exception;
}
