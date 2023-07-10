package com.fifatoy.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/matchInfo")
@Log4j2
public class matchController {
    // API키
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    @GetMapping()
    public String MatchInfo(HttpSession session, Model model, @RequestParam int code) {
        String accessId = (String) session.getAttribute("accessId");
        ArrayList<Object> matchInfoArray = apicallutil.matchInfo(accessId, code, apiKey);

        model.addAttribute("matchInfo", apicallutil.matchDetailInfo(matchInfoArray, apiKey, accessId));

        log.info(model);

        return "matchinfo";
    }

}
