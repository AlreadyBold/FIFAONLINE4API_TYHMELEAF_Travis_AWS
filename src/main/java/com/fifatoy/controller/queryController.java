package com.fifatoy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

import com.fifatoy.util.APICALLUTIL;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/member")
public class queryController {

    // API KEY 값
    @Value("${NexonApiKey}")
    private String apiKey;

    // UTIL
    APICALLUTIL apicallutil = new APICALLUTIL();

    /*
     * JPA SERVICE
     * 
     * @Autowired
     * private getUserService getUserService;
     * 
     * @Autowired
     * private newUserService newUserService;
     * 
     * @Autowired
     * private updateUserService updateuserservice;
     * 
     * @Autowired
     * private deleteUserService deleteuserservice;
     */

    // private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/query")
    public String query(HttpSession session, Model model, @RequestParam(value = "name", required = false) String name) {

        // 공식경기 등급 가져오기 API 호출 하여 맵에 저장
        String url = "https://static.api.nexon.co.kr/fifaonline4/latest/division.json";
        ArrayList<Map<String, Object>> divisionMap = new ArrayList<Map<String, Object>>();
        divisionMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        // 매치타입 가져오기 API 호출 하여 맵에 저장
        url = "https://static.api.nexon.co.kr/fifaonline4/latest/matchtype.json";
        ArrayList<Map<String, Object>> matchTypeMap = new ArrayList<Map<String, Object>>();
        matchTypeMap = (ArrayList<Map<String, Object>>) apicallutil.NotKeyArray(url);

        // 닉네임으로 회원정보 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=" + name + "";
        Map<String, Object> userinfo = new HashMap<String, Object>();
        userinfo = (Map<String, Object>) apicallutil.UseKeyObject(url, apiKey);

        // 세션에 유저정보 담기
        session.setAttribute("nickname", String.valueOf(userinfo.get("nickname")));
        session.setAttribute("level", String.valueOf(userinfo.get("level")));
        session.setAttribute("accessId", String.valueOf(userinfo.get("accessId")));
        String accessId = String.valueOf(userinfo.get("accessId"));

        // 회원정보로 최고등급 조회
        url = "https://api.nexon.co.kr/fifaonline4/v1.0/users/" + accessId + "/maxdivision";
        ArrayList<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();
        resultListMap = (ArrayList<Map<String, Object>>) apicallutil.UseKeyArray(url, apiKey);

        // 모델에 삽입
        ArrayList<Map<String, Object>> matchInfo = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < resultListMap.size(); i++) {
            Map<String, Object> userInfo = new HashMap<String, Object>();
            for (int j = 0; j < matchTypeMap.size(); j++) {
                if (resultListMap.get(i).get("matchType").equals(matchTypeMap.get(j).get("matchtype"))) {
                    userInfo.put("matchType", String.valueOf(matchTypeMap.get(j).get("desc")));
                    break;
                }
            }
            for (int j = 0; j < divisionMap.size(); j++) {
                if (resultListMap.get(i).get("division").equals(divisionMap.get(j).get("divisionId"))) {
                    userInfo.put("grade", String.valueOf(divisionMap.get(j).get("divisionName")));
                    break;
                }
            }
            userInfo.put("matchTypeCd", String.valueOf(matchTypeMap.get(i).get("matchtype")));
            matchInfo.add(userInfo);
        }
        model.addAttribute("matchInfo", matchInfo);
        log.info(matchInfo);

        /*
         * JPA TEST CODE (CREATE)
         * String[] v = new String[2];
         * v[0] = accessId;
         * v[1] = name;
         * SaveRequest request = new SaveRequest(v[0], v[1]);
         * try {
         * newUserService.saveUser(request);
         * logger.info("새 사용자 저장: {}", request.getEmail());
         * } catch (DupException e) {
         * logger.info("사용자가 이미 존재함: {}", request.getEmail());
         * }
         * 
         * JPA TEST CODE ( READ )
         * String email = "aaa@aaa.com";
         * try {
         * User user = getUserService.getUser(email);
         * logger.info("사용자 정보: {}, {}", user.getEmail(), user.getName());
         * } catch (NoUserException e) {
         * logger.info("사용자가 존재하지 않음: {}", email);
         * }
         * 
         * JPA TEST CODE ( UPDATE )
         * String[] v = new String[2];
         * v[0] = accessId;
         * v[1] = name;
         * SaveRequest request = new SaveRequest(v[0], v[1]);
         * try {
         * updateuserservice.updateUser(request);
         * logger.info("기존 사용자 수정 : {}", request.getEmail());
         * } catch (DupException e) {
         * logger.info("사용자가 존재하지 않음 : {}", request.getEmail());
         * }
         * 
         * JPA TEST CODE ( DELETE )
         * String email = "aaa@aaa.com";
         * try {
         * deleteuserservice.deleteUser(email);
         * logger.info("기존 사용자 삭제됨 ");
         * } catch (NoUserException e) {
         * logger.info("사용자가 존재하지 않음: {}", email);
         * }
         */

        return "query";
    }

    @GetMapping("/queryhome")
    public String queryhome(Model model) {
        return "query";
    }
}
