package com.sqli.chatbot.naivebayes.service;

import java.io.IOException;
import java.util.List;

import com.sqli.chatbot.naivebayes.util.dto.Entity;

public interface ClassificationNaiveBayesDomainService {
    String getDomainFromSearchQuery(String searchQuery) throws IOException;
    List<Entity> getDomainEntities(String searchQuery) throws IOException;

}
