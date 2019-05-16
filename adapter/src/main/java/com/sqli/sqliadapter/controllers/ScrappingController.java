package com.sqli.sqliadapter.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sqli.sqliadapter.utils.ExtractingClass;

@RestController
public class ScrappingController {
    private final String PAGE_URL = "http://localhost:8081/REST/information";

    @GetMapping("/populateFormations")
    public @ResponseBody
    String populateFormation() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        Gson jsonObject = new Gson();
        String json = jsonObject.toJson(ExtractingClass.getTable());
        try {
            HttpPost request = new HttpPost(PAGE_URL);
            StringEntity entity = new StringEntity(json, "UTF-8");
            entity.setContentType("application/json");
            request.getRequestLine();
            request.setHeader("Content-Type", "application/json;charset=UTF-8");
            request.setEntity(entity);
            httpClient.execute(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
