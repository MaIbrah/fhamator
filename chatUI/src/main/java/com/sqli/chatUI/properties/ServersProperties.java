package com.sqli.chatUI.properties;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ServersProperties {

    public ServersProperties() {
    }

    public static String getRestServer() {
        Yaml yaml = new Yaml();
        InputStream inputStream = new ServersProperties().getClass()
            .getClassLoader()
            .getResourceAsStream("application.yml");
        Map<String, String> obj = yaml.load(inputStream);
        return obj.get("RestServers");
    }

}
