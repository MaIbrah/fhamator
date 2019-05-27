package com.sqli.chatbot.naivebayes.util.properties;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YmlProperties {

    private static Map<String, String>  loadFile() {
        Yaml yaml = new Yaml();
        InputStream inputStream = new YmlProperties().getClass()
            .getClassLoader()
            .getResourceAsStream("application.yml");
        Map<String, String> obj = yaml.load(inputStream);
        return obj;
    }

    public static Object getProbClassificationNaiveBayesDomain(){
        return loadFile().get("prob.classification.naiveBayes.domain");
    }

    public static Object getProbClassificationNaiveBayesKeywords(){
        return loadFile().get("prob.classification.naiveBayes.keywords");
    }

}
