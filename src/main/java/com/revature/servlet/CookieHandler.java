package com.revature.servlet;

import javax.servlet.http.Cookie;

public class CookieHandler {
    public static String getCookieValue(String name, Cookie[] cookies){
        String value = "";
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name))
                     value = cookie.getValue();
            }
        }
        return value;
    }
}
