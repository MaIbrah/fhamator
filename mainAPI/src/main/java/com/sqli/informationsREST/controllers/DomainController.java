package com.sqli.informationsREST.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqli.informationsREST.models.Domain;
import com.sqli.informationsREST.repositories.DomainRepository;

@RestController
@RequestMapping("/REST")
public class DomainController {
    @Autowired
    private DomainRepository domainRepo;

    @GetMapping("/domains")
    private ResponseEntity<List<String>> getAllDomains()
    {
        List<String> domains = domainRepo.findAll().stream().map(domain -> domain.getDomain()).collect(Collectors.toList());
        return ResponseEntity.ok(domains);
    }
    @PostMapping("/domains")
    private ResponseEntity<String> addNewDomain(@RequestBody List<Domain> domains) {
       domains.forEach(domain -> domainRepo.save(domain));
        return ResponseEntity.ok("You have added successfully new domains");
    }
}
