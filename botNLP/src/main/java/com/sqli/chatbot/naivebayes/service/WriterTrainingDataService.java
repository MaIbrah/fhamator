package com.sqli.chatbot.naivebayes.service;

import com.sqli.chatbot.naivebayes.util.dto.NoExistDomainRequest;
import com.sqli.chatbot.naivebayes.util.dto.NoExistKeywordRequest;

import java.io.IOException;

public interface WriterTrainingDataService {
    String writeDomainIfDoesntExist(NoExistDomainRequest request) throws IOException;
    String writeKeywordIfDoesntExist(NoExistKeywordRequest request) throws IOException;
}
