package com.sqli.informationsREST.models;

import java.io.Serializable;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;



public class Information implements Serializable {
    @Id
    public ObjectId _id;
    public String type;
    public String name;
    public Map<String,String> attributes;

    public String get_id() { return String.valueOf(_id); }

    public void set_id(String _id) {
        this._id = new ObjectId(_id);
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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Information() {
    }

    public Information(ObjectId _id, String type, String name, Map<String, String> attributes) {
        this._id = _id;
        this.type = type;
        this.name = name;
        this.attributes = attributes;
    }

}
