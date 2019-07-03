package com.sqli.chatbot.naivebayes.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;
import com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPService;
import com.sqli.chatbot.naivebayes.util.properties.YmlProperties;

@Service
public class DefaultClassificationNativeBayesKeywordService implements ClassificationNaiveBayesKeywordService {
    @Autowired
    private OpenNLPService nlpService;

    @Override
    public List<String> getKeywordsFromSearchQuery(String searchQuery) throws IOException {
        return  nlpService.getMostPredicatedKeywords(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH, searchQuery);

    }

    @Override
    public String getMostPredicatedKeywordFromSearchQuery(String searchQuery) throws IOException {
        Double prob=(Double) YmlProperties.getProbClassificationNaiveBayesKeywords();
        OpenNlpResponse response = nlpService.getMostPredicatedResult(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH, searchQuery);
        return this.getBestKeyword(response,prob);

    }

    @Override
    public String getMostPredicatedKeywordToAdd(String searchQuery) throws IOException {
        OpenNlpResponse response = nlpService.getMostPredicatedResult(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH, searchQuery);
        return this.getBestKeyword(response,0.4);

    }

    @Override
    public List<Entity> getKeywordsEntities(String searchQuery) throws IOException {
        return nlpService.getEntityKeyWordsFromSearchQuery(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH, searchQuery);
    }

    private String getBestKeyword(OpenNlpResponse nlpResponse,Double prob) {
//        return nlpResponse.getProb() > 0.7 ? nlpResponse.getPredicatedResponse()+" "+nlpResponse.getProb() : "None" + nlpResponse.getProb();
        return nlpResponse.getProb() > prob ? nlpResponse.getPredicatedResponse() : "none" ;
    }
}
