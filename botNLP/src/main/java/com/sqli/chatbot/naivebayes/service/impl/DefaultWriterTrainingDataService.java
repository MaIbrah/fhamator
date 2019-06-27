package com.sqli.chatbot.naivebayes.service.impl;

import static com.sqli.chatbot.naivebayes.util.opennlp.OpenNLPCore.generateModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        generateModel(Constant.TRAINING_DOMAIN_FILE_PATH, Constant.TRAINING_DOMAIN_MODEL_PATH);
        return domain;
    }

    @Override
    public String writeKeywordIfDoesntExist(NoExistKeywordRequest request) throws IOException {
        //File file = null;
        DomainOrKeywordWriter domainOrKeywordWriter = new DomainOrKeywordWriter();
        List<String> keywords = request.getClientKeywords();
        //Arrays.asList(request.getClientKeywords().trim().split(","));
        for (String keyword : keywords) {
            String keywordFinder = keywordService.getMostPredicatedKeywordFromSearchQuery(keyword);
            if (keywordFinder.toLowerCase().contains("none")) {

                domainOrKeywordWriter.writeInFileDomainOrKeyword(Constant.TRAINING_KEYWORD_FILE_PATH, keyword + " " + shuffledKeywords(keyword,10));
            }

        }
        generateModel(Constant.TRAINING_KEYWORD_FILE_PATH, Constant.TRAINING_KEYWORD_MODEL_PATH);
        return String.join(" ", keywords);
    }

private String shuffleKeyWord(String keyWord){
    List<Character> strs = new ArrayList<>();
    char[] x=keyWord.toCharArray();
        for (char c : x) {
        strs.add(c);
    }
        Collections.shuffle(strs);
    return strs.stream()
        .map(String::valueOf)
        .collect(Collectors.joining());
    }


    private String shuffledKeywords(String keyWord , int numberOfShuffle){
        StringBuilder keyWords = new StringBuilder().append(keyWord);
        while (numberOfShuffle-- > 0)
            keyWords.append(" ").append(shuffleKeyWord(keyWord));
        return keyWords.toString();
    }

}
