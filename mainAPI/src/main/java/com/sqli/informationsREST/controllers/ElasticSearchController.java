package com.sqli.informationsREST.controllers;

import static com.sqli.informationsREST.utils.UtilsFunctions.ELASTIC_SEARCH_LINK;
import static com.sqli.informationsREST.utils.UtilsFunctions.requestExecute;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sqli.informationsREST.models.Information;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/REST/elasticsearch")
@Api(value = "Nespresso elasticsearch api", description = "ElasticSearch Crud Operations", tags = {"elasticsearch Rest"})
public class ElasticSearchController {
    private static Logger log = Logger.getLogger(ElasticSearchController.class.getName());

    @ApiOperation(value = "Add a set of keywords to an Information")
    @PutMapping("/keywords")
    public ResponseEntity updateElasticSearch(@RequestBody HashMap<String,Object> parms) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        Gson jsonObject = new Gson();
        String jsonMap = jsonObject.toJson(parms);
        try {
            HttpPut request = new HttpPut(ELASTIC_SEARCH_LINK+"update");
            StringEntity entity = new StringEntity(jsonMap,"UTF-8");
            entity.setContentType("application/json");
            request.getRequestLine();
            request.setHeader("Content-Type", "application/json;charset=UTF-8");
            request.setEntity(entity);
            httpClient.execute(request);
            log.info("Update elastic search keywords");
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity("Information updated successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "View a list of informations that contains any value given")
    @GetMapping("/{value}")
    public List<Information> getInformationByValues(@PathVariable("value") String value) {
        List<Information> informations = requestExecute(ELASTIC_SEARCH_LINK+"getMatches/"+value.toLowerCase());
        log.info("search request for " + value);
        return informations;
    }
    @ApiOperation(value = "View a list of informations that contains a type and value given")
    @GetMapping("/{type}/{value}")
    public List<Information> getInformationByTypeAndValues(@PathVariable("type") String type,@PathVariable("value") String value) {
        List<Information> informations = requestExecute(ELASTIC_SEARCH_LINK+"getMatches/"+type.toLowerCase()+"/"+value.toLowerCase());
        log.info("search request for the domain " + type + " and the keyword "+ value);
        return  informations;
    }
}
