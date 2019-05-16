package com.sqli.sqliadapter.FormationAdapter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Information implements Serializable {
    public String type;
    public String name;

    public Map<String, String> attributes;

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

    public Information(List<String> th, List<String> td) {
        this.type = "Formation";
        this.name = td.get(0);
        attributes = new LinkedHashMap();
        attributes.put(th.get(1), td.get(1));
        attributes.put(th.get(2), td.get(2));
        attributes.put(th.get(3), td.get(3));
        attributes.put("Date " +th.get(4), td.get(4));
        attributes.put("Date " +th.get(5), td.get(5));
        attributes.put("Details",td.get(6));
    }
}
