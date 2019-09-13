package com.sqli.elasticsearch.model;

import java.util.Map;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Information {
    @JsonProperty
    @Id
    public String _id;
    @JsonProperty
    private String type;
    @JsonProperty
    private String name;
    @JsonProperty
    private Map<String,String> attributes;



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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Information() {
    }

    public Information(String _id, String type, String name, Map<String, String> attributes) {
        this._id = _id;
        this.type = type;
        this.name = name;
        this.attributes = attributes;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "{"+ _id +","+ type +","+ name +","+ attributes+"}";
    }
}
