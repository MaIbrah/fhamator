package com.sqli.chatUI.services;

import java.util.List;

import com.sqli.chatUI.models.Information;

public interface InformationServiceInter {
     List<Information> getInformation();
     List<Information> getInformationByName(String name);
     List<Information> getInformationByTypeAndName(String type, String name);

     List<Information> getInformationByTypeAndkeywords(String type, String keywords);

     List<Information> getInformationByValues(String values);
}
