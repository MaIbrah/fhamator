package com.sqli.chatbot.naivebayes.util.factory;

import com.sqli.chatbot.naivebayes.util.dto.OpenNlpResponse;

public class OpenNlpResponseFactory {
    private OpenNlpResponseFactory(){}
    public static OpenNlpResponse createOpenNlpDOmainResponse(String domain, double prob){
        return  new OpenNlpResponse(domain,prob);
    }
}
