package com.sqli.sqliadapter.utils;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Hidden {
    public static String HIDDEN_USERNAME;
    public static  String HIDDEN_PASSWORD;


    public static void getUserAndPassword() {
        Yaml yaml = new Yaml();
        InputStream inputStream = new Hidden().getClass()
            .getClassLoader()
            .getResourceAsStream("application.yml");
        Map<String, String> obj = yaml.load(inputStream);
        HIDDEN_USERNAME = obj.get("username");
        HIDDEN_PASSWORD =obj.get("password");
    }

}
