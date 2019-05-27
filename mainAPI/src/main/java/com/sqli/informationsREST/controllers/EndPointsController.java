package com.sqli.informationsREST.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sqli.informationsREST.models.Information;
import com.sqli.informationsREST.repositories.InfoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/REST")

@Api(value = "Nespresso information", description = "Information's CRUD Operations", tags = {"Information Rest"})
public class EndPointsController {
     private static Logger log= Logger.getLogger(EndPointsController.class);

    @Autowired
    private InfoRepository repository;

    @ApiOperation(value = "View a list of informations", response = Iterable.class)
    @GetMapping("/informations")
    public List<Information> getAllInformations() {
        this.log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss")) + " -- get All informations in database");
        return repository.findAll();
    }

    @ApiOperation(value = "View a list of informations that contains the type and name given")
    @GetMapping("/information/{type}/{name}")
    public List<Information> getInformationbyTypeAndName(@PathVariable("type") String type, @PathVariable("name") String name) {
        this.log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss")) + " -- get All informations with the type: "+type+ " and the name: "+name);
        return repository.findByTypeAndName(type, name);
    }

    @ApiOperation(value = "Add a List Of Informations")
    @PostMapping("/information")
    public ResponseEntity insertInformation(@RequestBody List<Information> informations) {
        for (Information information : informations) {
            repository.insert(information);
            this.log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss")) + " -- insert information " + information.get_id());
        }

        return new ResponseEntity("Informations created successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update Information")
    @PutMapping("/information")
    public ResponseEntity updateInformation(@RequestBody Information information) {
        repository.save(information);
        this.log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss")) + " -- update information " + information.get_id());
        return new ResponseEntity("Information updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an information that has an ID given")
    @DeleteMapping("/information/{id}")
    public ResponseEntity deleteInformation(@PathVariable String id) {
        repository.deleteById(id);
        this.log.info(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss")) + " -- delete information " + id);
        return new ResponseEntity("Information with ID :" + id + " was deleted successfully", HttpStatus.OK);
    }
}
