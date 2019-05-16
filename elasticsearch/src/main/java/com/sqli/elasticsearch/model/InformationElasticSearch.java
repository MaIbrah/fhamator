package com.sqli.elasticsearch.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "information-sqli", type = "default")
public class InformationElasticSearch {
    @Id
    public String id;
    public String type;
    public String name;
    public List<Attribute> attributes;
    public Set<String> keyWords=new HashSet<>();

    public Set<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<String> keyWords) {
        this.keyWords = keyWords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public InformationElasticSearch() {
    }

    public InformationElasticSearch(String id, String type, String name, List<Attribute> attributes, Set<String> keyWords) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.attributes = attributes;
        this.keyWords = keyWords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InformationElasticSearch{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", attributes=" + attributes +
            ", keyWords=" + keyWords +
            '}';
    }
}
