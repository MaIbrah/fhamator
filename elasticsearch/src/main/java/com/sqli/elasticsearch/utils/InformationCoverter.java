package com.sqli.elasticsearch.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sqli.elasticsearch.model.Attribute;
import com.sqli.elasticsearch.model.Information;
import com.sqli.elasticsearch.model.InformationElasticSearch;

public class InformationCoverter {
    public static InformationElasticSearch convertToInformationElasticSearch(Information information) {
        InformationElasticSearch infoelastic = new InformationElasticSearch();
        infoelastic.setId(information.get_id());
        infoelastic.setName(information.getName());
        infoelastic.setType(information.getType());
        infoelastic.setAttributes(convertFromMapToListOfValues(information.getAttributes()));
        return infoelastic;
    }

    public static Information convertToInformation(InformationElasticSearch information) {
        Information info = new Information();
        info.set_id(information.getId());
        info.setName(information.getName());
        info.setType(information.getType());
        info.setAttributes(convertFromAttributeListToMap(information.getAttributes()));
        return info;
    }

    private static List<Attribute> convertFromMapToListOfValues(Map<String, String> map) {
        return map.entrySet().stream().map(attribute -> new Attribute(attribute.getKey(), attribute.getValue())).collect(Collectors.toList());
    }

    private static Map<String, String> convertFromAttributeListToMap(List<Attribute> attributes) {

        Map<String, String> attributesMap = new HashMap<>();
        for (Attribute attribute : attributes) {
            attributesMap.put(attribute.getKey(), attribute.getValue());
        }
        return attributesMap;
    }
}
