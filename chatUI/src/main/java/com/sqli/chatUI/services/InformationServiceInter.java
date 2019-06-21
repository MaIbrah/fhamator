package com.sqli.chatUI.services;

import java.util.List;

import com.sqli.chatUI.models.Information;

public interface InformationServiceInter {
     List<Information> getInformation(String token);
     List<Information> getInformationByName(String name,String token);
     List<Information> getInformationByTypeAndName(String type, String name,String token);

     List<Information> getInformationByTypeAndkeywords(String type, String keywords,String token);

     List<Information> getInformationByValues(String values,String token);
}
