package com.sqli.chatbot.naivebayes.service.impl;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesDomainService;
import com.sqli.chatbot.naivebayes.util.constantes.Constant;
import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;
import com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPService;
import com.sqli.chatbot.naivebayes.util.properties.YmlProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DefaultClassificationNaiveBayesDomainService implements ClassificationNaiveBayesDomainService {
    @Autowired
    private OpenNLPService nlpService;
    public static final String stackoverflow= "stackoverflow";
    @Override
    public String getDomainFromSearchQuery(String saerchQuery) throws IOException {
            OpenNlpResponse response = nlpService.getMostPredicatedResult(Constant.TRAINING_DOMAIN_FILE_PATH, Constant.TRAINING_DOMAIN_MODEL_PATH, saerchQuery);
            return this.geDomain(response, saerchQuery);

    }


    private String geDomain(OpenNlpResponse nlpResponse, String searchQuery) {

//        return nlpResponse.getProb() > 0.7 ? nlpResponse.getPredicatedResponse()+" "+nlpResponse.getProb() : "None" + nlpResponse.getProb();
        Double prob=(Double)YmlProperties.getProbClassificationNaiveBayesDomain();
        return nlpResponse.getProb() > prob ? nlpResponse.getPredicatedResponse(): "none";
    }

}
