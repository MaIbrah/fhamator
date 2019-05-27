package com.sqli.informationsREST.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqli.informationsREST.models.Information;

public class UtilsFunctions {
    public static final String ELASTIC_SEARCH_LINK;
    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = UtilsFunctions.class
            .getClassLoader()
            .getResourceAsStream("application.yml");
        Map<String, String> obj = yaml.load(inputStream);
        ELASTIC_SEARCH_LINK = obj.get("RestServers") + obj.get("elasticsearch");
    }
    public static List<Information> requestExecute(String request) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(new RestTemplate().getForObject(request, ArrayList.class), new TypeReference<List<Information>>() {
        });
    }
}

