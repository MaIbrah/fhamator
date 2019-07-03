package com.sqli.chatbot.naivebayes.service.impl;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesDomainService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;
import com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPService;
import com.sqli.chatbot.naivebayes.util.properties.YmlProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DefaultClassificationNaiveBayesDomainService implements ClassificationNaiveBayesDomainService {
    @Autowired
    private OpenNLPService nlpService;

    @Override
    public String getDomainFromSearchQuery(String searchQuery) throws IOException {
            OpenNlpResponse response = nlpService.getMostPredicatedResult(Constant.TRAINING_DOMAIN_FILE_PATH, Constant.TRAINING_DOMAIN_MODEL_PATH, searchQuery);
            return this.getDomain(response);

    }

    @Override
    public List<Entity> getDomainEntities(String searchQuery) throws IOException {
        return nlpService.getEntityModelsFromSearchQuery(Constant.TRAINING_DOMAIN_FILE_PATH, Constant.TRAINING_DOMAIN_MODEL_PATH, searchQuery);
    }

    private String getDomain(OpenNlpResponse nlpResponse) {

//        return nlpResponse.getProb() > 0.7 ? nlpResponse.getPredicatedResponse()+" "+nlpResponse.getProb() : "None" + nlpResponse.getProb();
        Double prob=(Double)YmlProperties.getProbClassificationNaiveBayesDomain();
        return nlpResponse.getProb() > prob ? nlpResponse.getPredicatedResponse(): "none";
    }

}
