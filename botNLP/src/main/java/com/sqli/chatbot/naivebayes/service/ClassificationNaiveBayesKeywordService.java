package com.sqli.chatbot.naivebayes.service;

import java.io.IOException;
import java.util.List;

import com.sqli.chatbot.naivebayes.util.dto.Entity;

public interface ClassificationNaiveBayesKeywordService {
    List<String> getKeywordsFromSearchQuery(String searchQuery) throws IOException;

    String getMostPredicatedKeywordFromSearchQuery(String searchQuery) throws IOException;

    String getMostPredicatedKeywordToAdd(String searchQuery) throws IOException;

    List<Entity> getKeywordsEntities(String searchQuery) throws IOException;
}
