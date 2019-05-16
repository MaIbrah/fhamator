package com.sqli.chatbot.naivebayes.service;

import com.sqli.chatbot.naivebayes.util.dto.NoExistDomainRequest;

import java.io.IOException;

public interface ClassificationNaiveBayesDomainService {
    String getDomainFromSearchQuery(String saerchQuery) throws IOException;

}
