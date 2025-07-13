package com.example.HidayaVoiceAI.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

import static org.springframework.util.StringUtils.trimAllWhitespace;


@Slf4j
public class GeneralMethods {

    public static void addMessage(HttpServletRequest req, boolean success, String message) {
        req.setAttribute("success", success);
        req.setAttribute("message", message);
        log.info("Status: {}, message: {}", success, message);
    }

    public static boolean isEmpty(String value) {
        if (value == null || "".equalsIgnoreCase(value) || "".equals(trimAllWhitespace(value))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Object object) {
        return (object == null ||
                (object instanceof Collection && ((Collection) object).isEmpty()) ||
                (object instanceof Map && ((Map) object).isEmpty()) ||
                (object instanceof String && ("".equals(trimAllWhitespace((String) object)))) || ""
                .equals(object));
    }

}
