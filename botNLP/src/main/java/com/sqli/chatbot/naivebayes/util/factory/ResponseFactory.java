package com.sqli.chatbot.naivebayes.util.factory;

import java.util.Collections;
import java.util.List;

import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.Reponse;

public class ResponseFactory {
    public static Reponse responseMaker(String request, List<Entity> domains, List<Entity> keyWords) {
        Collections.sort(domains, (domain1, domain2) -> {
            if(domain1.getScore() == domain2.getScore()) return 0;
            else return domain1.getScore() < domain2.getScore() ? 100 : -100;
        });
        return new Reponse(request, (!domains.isEmpty()) ? domains.get(0) : new Entity("none", 1.0), domains, keyWords);
    }
}
