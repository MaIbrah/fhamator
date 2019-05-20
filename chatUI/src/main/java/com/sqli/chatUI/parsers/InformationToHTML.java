package com.sqli.chatUI.parsers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sqli.chatUI.models.Information;

public class InformationToHTML implements ResponseToHTMLParser{
    private List<Information> informations;
    private List<String> keyWords;

    private InformationToHTML()
    {
        informations = new ArrayList<>();
        keyWords = new ArrayList<>();
    }
    public InformationToHTML(List<Information> informations, List<String> keyWords)
    {
        this.informations = informations;
        this.keyWords = keyWords;
    }

    @Override
    public String toHTML() {
        List<String> informationsHTML = new ArrayList<>();
        informationsHTML.add("<p>");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        int responseTracker = 0;
        informationsHTML.add("<div class=\"fas fa-chevron-circle-down fa-3x chervon-up\" id=\"showResponseButton"+ time.getTime() + "\" onclick=\"showResponse('" + time.getTime() + "')\" ></div>");
        informationsHTML.add("<ul style=\"overflow-y: hidden; display:none \" id=\"reponses" +time.getTime() + "\">");
        for(Information information : informations)
        {
            StringBuilder html = new StringBuilder();
            String responseIndex = String.valueOf(time.getTime()) + responseTracker++;
            html.append("<li style=\"color: darkgreen\"><div class=\"fas fa-chevron-up fa-3x chervon-up\" id=\"upResponse")
                .append(responseIndex).append("\"")
                .append("onclick=\"upThis('")
                .append(information.get_id() ).append("','").append(keyWords).append("',").append( responseIndex).append(")\"></div>")
                .append("<u style=\"vertical-align: 50%;\">Response number ").append(responseTracker).append("</u></li>");
            html.append("<li>")
                .append("<ul style=\"overflow-y: hidden;\">")
                .append("<li><strong> Type : </strong>").append(information.getType()).append("</li>")
                .append("<li><strong> Name : </strong>").append(information.getName()).append("</li>");
            for(Map.Entry<String, String> entry: information.getAttributes().entrySet())
            {
                if("Details".equalsIgnoreCase(entry.getKey()))
                    html.append("<li><strong>").append(entry.getKey()).append(" : </strong><a href=\"'").append(entry.getValue()) .append("'\">Link</a></li>");
                else
                    html.append("<li><strong>").append(entry.getKey()).append(" : </strong>").append(entry.getValue()) .append("</li>");
            }

            informationsHTML.add(html.toString());
            informationsHTML.add("</ul>");
        }
        informationsHTML.add("</ul>");
        informationsHTML.add("</p>");
        return String.join(" ",informationsHTML);
    }
}
