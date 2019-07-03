package com.sqli.chatbot.naivebayes.util.factory;

import java.util.List;

import com.sqli.chatbot.naivebayes.util.dto.Entity;
import com.sqli.chatbot.naivebayes.util.dto.Reponse;

public class ResponseFactory {
    public static Reponse reponseMaker(String request, List<Entity> domains, List<Entity> keyWords) {
//        Collections.sort(domains, (domain1, domains2) ->
//            domain1.getScore() > domains2.getScore() ? 1 : domain1.getScore() < domains2.getScore() ? -1 : 0);
//        Collections.sort(domains, Comparator.comparingDouble(Entity::getScore));
//        Collections.sort(keyWords, Comparator.comparingDouble(Entity::getScore));
        return new Reponse(request,domains.get(0),domains,keyWords);
    }
}
