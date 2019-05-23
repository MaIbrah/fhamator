package com.sqli.chatbot.naivebayes.service.impl;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;
import com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
        OpenNlpResponse response = nlpService.getMostPredicatedResult(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH, searchQuery);
        return this.geBestKeyword(response);

    }


    private String geBestKeyword(OpenNlpResponse nlpResponse) {
//        return nlpResponse.getProb() > 0.7 ? nlpResponse.getPredicatedResponse()+" "+nlpResponse.getProb() : "None" + nlpResponse.getProb();
        return nlpResponse.getProb() > 0.4 ? nlpResponse.getPredicatedResponse(): "none" ;
    }
}
