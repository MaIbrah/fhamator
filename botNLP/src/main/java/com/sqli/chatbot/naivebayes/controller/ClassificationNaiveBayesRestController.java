package com.sqli.chatbot.naivebayes.controller;

import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesDomainService;
import com.sqli.chatbot.naivebayes.service.ClassificationNaiveBayesKeywordService;
import com.sqli.chatbot.naivebayes.service.WriterTrainingDataService;
import com.sqli.chatbot.naivebayes.util.dto.NoExistDomainRequest;
import com.sqli.chatbot.naivebayes.util.dto.NoExistKeywordRequest;
import com.sqli.chatbot.naivebayes.util.dto.SearchQueryResponse;
import com.sqli.chatbot.naivebayes.util.factory.SearchQueryResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/naivesBayes")
@CrossOrigin("*")
public class ClassificationNaiveBayesRestController {
    @Autowired
    private ClassificationNaiveBayesDomainService domainService;
    @Autowired
    private ClassificationNaiveBayesKeywordService keywordService;
    @Autowired
    private WriterTrainingDataService writerTrainingDataService;

    @GetMapping(value = "/domain/{query}")
    private ResponseEntity<SearchQueryResponse> getDomainFromSearchQuery(@PathVariable("query") String request) {
        try {
            String domain = domainService.getDomainFromSearchQuery(request);
            List<String> keywords = new ArrayList<>();
            String resultKeyword=keywordService.getMostPredicatedKeywordFromSearchQuery(request);
            if(domain.contains("stackoverflow")) keywords.add(request);
            else if(resultKeyword.contains("None") ) keywords.add("None");
            else  keywords  = keywordService.getKeywordsFromSearchQuery(request);

            return ResponseEntity.ok(SearchQueryResponseFactory.createSearchQueryResponse(domain, keywords));
        } catch (IOException ex) {
            return ResponseEntity.status(301).build();
        }
    }
    @PostMapping("/write/domain")
    private ResponseEntity<String> writeUnexistingDomain(@RequestBody NoExistDomainRequest request) {
        try {
            return ResponseEntity.ok(writerTrainingDataService.writeDomainIfDoesntExist(request));
        } catch (IOException ex) {
            return ResponseEntity.status(301).build();
        }
    }
    @PostMapping("/write/keyword")
    private ResponseEntity<String> writeUnexistingKeyword(@RequestBody NoExistKeywordRequest request) {
        try {
            return ResponseEntity.ok(writerTrainingDataService.writeKeywordIfDoesntExist(request));
        } catch (IOException e) {
            return ResponseEntity.status(301).build();
        }
    }
}
