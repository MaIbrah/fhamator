package com.sqli.chatbot.naivebayes.util.factory;

import com.sqli.chatbot.naivebayes.util.dto.SearchQueryResponse;

import java.util.List;

public class SearchQueryResponseFactory {
    private SearchQueryResponseFactory(){}
    public static SearchQueryResponse createSearchQueryResponse(String domain, List<String> keyword){
        return new SearchQueryResponse(domain,keyword);
    }
}
