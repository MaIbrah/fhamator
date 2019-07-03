package com.sqli.informationsREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqli.informationsREST.models.FeedBack;
import com.sqli.informationsREST.repositories.FeedBackRepository;

@RestController
@RequestMapping("/REST")
public class FeedBackController {
    @Autowired
    private FeedBackRepository feedBackRepo;

    @PostMapping("/FeedBack")
    private ResponseEntity<String> addNewFeedBack(@RequestBody FeedBack feedback)
    {
        feedBackRepo.save(feedback);
        return ResponseEntity.ok("FeedBack has been added");
    }
}
