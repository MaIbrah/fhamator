package com.sqli.chatbot.naivebayes;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NaivebayesApplication {
    @Autowired
    private ClassificationNaiveBayesKeywordService keywordService;

    public static void main(String[] args) {
        SpringApplication.run(NaivebayesApplication.class, args);
    }

}
