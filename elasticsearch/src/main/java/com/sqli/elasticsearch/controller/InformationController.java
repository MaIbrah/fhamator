package com.sqli.elasticsearch.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqli.elasticsearch.model.Information;
import com.sqli.elasticsearch.model.InformationElasticSearch;
import com.sqli.elasticsearch.repository.InformationRepository;
import com.sqli.elasticsearch.utils.InformationCoverter;

@RestController
@RequestMapping("/informations")
public class InformationController {

    @Autowired
    InformationRepository repository;

    @PostMapping("/add")
    public InformationElasticSearch addInformation(@RequestBody Information information) {
        return repository.save(InformationCoverter.convertToInformationElasticSearch(information));
    }

    @PutMapping("/update")
    public InformationElasticSearch updateInformation(@RequestBody HashMap<String, Object> params) {
        List<String> keyWords = (ArrayList<String>) params.get("keyWords");
        Optional<InformationElasticSearch> informationElasticSearch = repository.findById(params.get("id").toString());
        if (informationElasticSearch.isPresent()) {
            if (informationElasticSearch.get().getKeyWords() == null) {
                informationElasticSearch.get().setKeyWords(new HashSet<>());
            }
            for (String keyWord : keyWords) {
                informationElasticSearch.get().getKeyWords().add(keyWord);
            }
        }
        return repository.save(informationElasticSearch.get());
    }



    @PostMapping("/addAll")
    public Iterable<InformationElasticSearch> addInformations(@RequestBody List<Information> informations) {
        List<InformationElasticSearch> informationElasticSearches = new ArrayList<>();
        for (Information information : informations) {
            informationElasticSearches.add(repository.save(InformationCoverter.convertToInformationElasticSearch(information)));
        }
        return informationElasticSearches;
    }

    @PostMapping("/addKeyWords")
    public void addKeyWords(@RequestBody String id, @RequestBody Set<String> keyWords) {

    }

    @GetMapping("/all")
    public List<Information> getInformations() {
        List<Information> informations = new ArrayList<>();
        Iterable<InformationElasticSearch> informationElasticSearches = repository.findAll();
        for (InformationElasticSearch infoelastic : informationElasticSearches) {
            informations.add(InformationCoverter.convertToInformation(infoelastic));
        }
        System.out.println(informations);
        return informations;
    }

    @GetMapping("/getMatches/{values}")
    public List<Information> getByNames(@PathVariable String values) {
        List<Information> informations = new ArrayList<>();
        for (InformationElasticSearch infoelastic : repository.listinformationMatches(values)) {
            informations.add(InformationCoverter.convertToInformation(infoelastic));
        }
        return informations;

    }

    @GetMapping("/getMatches/test/{values}")
    public List<InformationElasticSearch> getByNamesTest(@PathVariable String values) {
        return repository.listinformationMatches(values);

    }

    @GetMapping("/attribut/{name}")
    public List<Information> getByAttributes(@PathVariable String name) {
        List<Information> informations = new ArrayList<>();
        for (InformationElasticSearch infoElastic : repository.getByAttributes(name)) {
            informations.add(InformationCoverter.convertToInformation(infoElastic));
        }
        return informations;
    }
    @GetMapping("/getMatches/{type}/{values}")
    public List<Information> getByAttributesByTypeAndKeywords(@PathVariable("type") String type,@PathVariable("values") String name) {
        List<Information> informations = new ArrayList<>();
        for (InformationElasticSearch infoElastic : repository.listinformationMatchesTypeAndKeywords(type,name)) {
            informations.add(InformationCoverter.convertToInformation(infoElastic));
        }
        return informations;
    }
}
