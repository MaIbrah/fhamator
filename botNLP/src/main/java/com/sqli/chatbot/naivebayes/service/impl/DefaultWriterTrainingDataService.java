package com.sqli.chatbot.naivebayes.service.impl;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesDomainService;
import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import com.sqli.chatbot.naivebayes.service.WriterTrainingDataService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.NoExistDomainRequest;
import com.sqli.chatbot.naivebayes.util.dto.NoExistKeywordRequest;
import com.sqli.chatbot.naivebayes.util.writers.DomainOrKeywordWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class DefaultWriterTrainingDataService implements WriterTrainingDataService {
    @Autowired
    private ClassificationNaiveBayesDomainService domainService;
    @Autowired
    private ClassificationNaiveBayesKeywordService keywordService;
    @Override
    public String writeDomainIfDoesntExist(NoExistDomainRequest request) throws IOException {
        //File file = null;
        DomainOrKeywordWriter domainOrKeywordWriter = new DomainOrKeywordWriter();

        String domain = domainService.getDomainFromSearchQuery(request.getClientDomain());
        String result = "";
        if (domain.contains("None")) {
            result = request.getClientDomain() + " " + request.getSearchQuery();
            domain = request.getClientDomain();
        } else {
            result = domain + " " + request.getSearchQuery();
        }
        domainOrKeywordWriter.writeInFileDomainOrKeyword(Constant.TRAINING_DOMAIN_FILE_PATH, result);
        return domain;
    }

    @Override
    public String writeKeywordIfDoesntExist(NoExistKeywordRequest request) throws IOException {
        //File file = null;
       DomainOrKeywordWriter domainOrKeywordWriter = new DomainOrKeywordWriter();

        String keyword = keywordService.getMostPredicatedKeywordFromSearchQuery(request.getClientKeyword());
        String result = "";
        if (keyword.contains("None")) {
            result = request.getClientKeyword() + " " + request.getSearchQuery();
            keyword = request.getClientKeyword();
        } else {
            result = keyword + " " + request.getSearchQuery();
        }
        domainOrKeywordWriter.writeInFileDomainOrKeyword(Constant.TRAINING_KEYWORD_FILE_PATH, result);
        return keyword;
    }

}
