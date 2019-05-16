package com.sqli.chatUI.models;

import java.io.Serializable;
import java.util.Map;

public class Information implements Serializable {

    private String _id;
    private String type;
    private String name;
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
        return "Information : " +
            "_id= " + _id +"\t" +
            ", type= " + type + '\t' +
            ", name= " + name + '\t' +
            ", attributes=" + attributes+ '\n';
    }
}