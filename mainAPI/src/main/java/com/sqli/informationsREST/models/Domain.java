package com.sqli.informationsREST.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Domain {
    @Id
    private ObjectId _id;
    private String domain;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
