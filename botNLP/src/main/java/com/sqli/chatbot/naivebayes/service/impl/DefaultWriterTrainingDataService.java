package com.sqli.chatbot.naivebayes.service.impl;

import static com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPCore.generateModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesDomainService;
import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import com.sqli.chatbot.naivebayes.service.WriterTrainingDataService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.NoExistDomainRequest;
import com.sqli.chatbot.naivebayes.util.dto.NoExistKeywordRequest;
import com.sqli.chatbot.naivebayes.util.writers.DomainOrKeywordWriter;

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
        String result;
        if (domain.toLowerCase().contains("none")) {
            result = request.getClientDomain() + " " + request.getSearchQuery();
            domain = request.getClientDomain();
        } else {
            result = domain + " " + request.getSearchQuery();
        }
        domainOrKeywordWriter.writeInFileDomainOrKeyword(Constant.TRAINING_DOMAIN_FILE_PATH, result);
        generateModel(Constant.TRAINING_DOMAIN_FILE_PATH,Constant.TRAINING_DOMAIN_MODEL_PATH);
        return domain;
    }

    @Override
    public String writeKeywordIfDoesntExist(NoExistKeywordRequest request) throws IOException {
        //File file = null;
         DomainOrKeywordWriter domainOrKeywordWriter = new DomainOrKeywordWriter();
         String keywords = String.join(" ",request.getClientKeywords());
         String keyword = keywordService.getMostPredicatedKeywordFromSearchQuery(keywords);
         List<String> keyWordsToADD = new ArrayList<>();
         if (keyword.toLowerCase().contains("none")) {
            request.getClientKeywords().forEach(k -> keyWordsToADD.add(k + " " + k));
         } else {
            keyWordsToADD.add(keyword + " " + keyword);
         }
         keyWordsToADD.forEach( k -> {
            try {
                domainOrKeywordWriter.writeInFileDomainOrKeyword(Constant.TRAINING_KEYWORD_FILE_PATH, k);
            } catch (IOException e) {
                e.printStackTrace();
            }
         });
        generateModel(Constant.TRAINING_KEYWORD_FILE_PATH,Constant.TRAINING_KEYWORD_MODEL_PATH);
         return keywords;
    }

}
