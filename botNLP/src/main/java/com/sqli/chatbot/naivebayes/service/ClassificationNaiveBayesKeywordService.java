package com.sqli.chatbot.naivebayes.service;

import java.io.IOException;
import java.util.List;

public interface ClassificationNaiveBayesKeywordService {
    List<String> getKeywordsFromSearchQuery(String searchQuery) throws IOException;
    String getMostPredicatedKeywordFromSearchQuery(String searchQuery) throws  IOException;
}
