package com.sqli.sqliadapter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sqli.sqliadapter.FormationAdapter.Information;
import com.sqli.sqliadapter.utils.Hidden;

public class ConnectingClass {
    private static final String LOGIN_PAGE = "https://isc-careers.maroc.sqli.com/j_spring_security_check";
    private static Map<String, String> getCookieAfterLogin(String login, String password) throws IOException {
        Map<String, String> mapParams = new HashMap<>();
        mapParams.put("j_username", login);
        mapParams.put("j_password", password);
        Connection.Response res = Jsoup.connect(LOGIN_PAGE)
            .data(mapParams)
            .method(Connection.Method.POST)
            .execute();
        return res.cookies();
    }

    public static Document getThisPage(String URL) throws IOException {
        Hidden.getUserAndPassword();
        return Jsoup.connect(URL)
            .cookies(getCookieAfterLogin(Hidden.HIDDEN_USERNAME, Hidden.HIDDEN_PASSWORD))
            .get();
    }
}


