package com.sqli.sqliadapter.utils;

import static com.sqli.sqliadapter.utils.ConnectingClass.getThisPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sqli.sqliadapter.FormationAdapter.Information;

public class ExtractingClass {
    private static final String BASE_URL = "https://isc-careers.maroc.sqli.com";
    private static final String URL = "https://isc-careers.maroc.sqli.com/main/formation/liste?agence=0&businessUnit=&statut=0&annee=2019";
    public static List<Information> getTable() {
        List<Information> informations = new ArrayList();
        try {
            Document doc = getThisPage(URL);
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tbody>tr");
            for (Element row : rows)
                informations.add(new Information(getTableHeaders(table), getTableValues(row)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return informations;
    }
    private static List<String> getTableHeaders(Element table)
    {
        return  table.select("thead>tr>th").eachText();
    }
     private static List<String> getTableValues(Element row)
     {
         List<String>  values = row.select("td").eachText();
         String href = BASE_URL+row.select("td").last().selectFirst("a").attr("href");
         values.add(href);
         return values;
     }
}
