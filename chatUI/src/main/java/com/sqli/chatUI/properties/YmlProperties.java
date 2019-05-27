package com.sqli.chatUI.properties;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YmlProperties {

    public YmlProperties() {
    }
    private static  Map<String, String>  loadFile(){
        Yaml yaml = new Yaml();
        InputStream inputStream = new YmlProperties().getClass()
            .getClassLoader()
            .getResourceAsStream("application.yml");
        return  yaml.load(inputStream);
    }

    public static String getRestServer() {
        return loadFile().get("RestServers");
    }

    public static Object getStackoverflowResponses() {
        return loadFile().get("number-of-stackoverflow-responses");
    }

}
