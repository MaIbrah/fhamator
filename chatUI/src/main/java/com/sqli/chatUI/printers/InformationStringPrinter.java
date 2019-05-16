package com.sqli.chatUI.printers;

import java.util.List;
import java.util.Map;

import com.sqli.chatUI.models.Information;

public class InformationStringPrinter implements InformationPrinter {

    public static final String NO_DATA_FOUND = "No data found !";

    @Override
    public String informaionPrinter(List<Information> informations) {
        StringBuilder report = new StringBuilder();
        if (informations.isEmpty()) {
            report.append(NO_DATA_FOUND);
        } else {
            for (Information information : informations) {
                report.append("\nInformation :").append("\t")
                    .append(information.getType()).append("\t-\t")
                    .append(information.getName()).append("\n\t")
                    .append("Attributes : \n\t")
                    .append(attributsPrinter(information.getAttributes()))
                    .append('\n');
            }
        }
        return report.toString();
    }

    private String attributsPrinter(Map<String, String> attributs) {
        StringBuilder attributsString = new StringBuilder();
        for (Map.Entry<String, String> entry : attributs.entrySet()) {
            if (!"".equals(entry.getValue())) {
                attributsString.append("-")
                    .append(entry.getKey())
                    .append("\t:\t")
                    .append(entry.getValue())
                    .append("\n");
            }
        }
        return attributsString.toString();
    }

    ;
}
