package com.fifatoy.SocialLoginTestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/social")
public class socialLoginTestController<googleOauthParams> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${KakaoApiKey}")
    private String kakaoApiKey;

    @Value("${KakaoRedirectURL}")
    private String KakaoRedirectURL;

    /**
     * kakao callback
     * [GET] /social/kakaotest
     */
    @ResponseBody
    @GetMapping("/kakaotest")

    public void kakaoCallback(@RequestParam String code) {
        // code = 인가 코드

        // accessToken
        String access_Token = "";
        String refresh_Token = "";

        // Token 발급 url
        String reqURL = "https://kauth.kakao.com/oauth/token";

        // API 통신해서 accessToken 발급
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + kakaoApiKey); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + KakaoRedirectURL); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 토큰값 저장
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // access_token을 이용하여 사용자 정보 조회
        String reqURL2 = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_Token); // 전송할 header 작성, access_token전송

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * 결과
         * response body :
         * {
         * "id":2883786726,
         * "connected_at":"2023-07-02T15:23:48Z",
         * "properties":
         * {
         * "nickname":"이준형",
         * "profile_image":
         * "http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_640x640.jpg",
         * "thumbnail_image":
         * "http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_110x110.jpg"
         * },
         * "kakao_account":{
         * "profile_nickname_needs_agreement":false,
         * "profile_image_needs_agreement":false,
         * "profile":
         * {
         * "nickname":"이준형",
         * "thumbnail_image_url":
         * "http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_110x110.jpg",
         * "profile_image_url":
         * "http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_640x640.jpg",
         * "is_default_image":false
         * },
         * "has_email":true,
         * "email_needs_agreement":false,
         * "is_email_valid":true,
         * "is_email_verified":true,
         * "email":"junheong@nate.com",
         * "has_age_range":true,
         * "age_range_needs_agreement":false,
         * "age_range":"20~29",
         * "has_birthday":true,
         * "birthday_needs_agreement":false,
         * "birthday":"0623",
         * "birthday_type":"SOLAR",
         * "has_gender":true,
         * "gender_needs_agreement":false,"gender":"male"
         * }
         * }
         * 
         * {"id":2893376035,
         * "connected_at":"2023-07-04T07:27:25Z",
         * "properties":{
         * "nickname":"정민영",
         * "profile_image":
         * "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
         * "thumbnail_image":
         * "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"
         * },
         * "kakao_account":{
         * "profile_nickname_needs_agreement":false,
         * "profile_image_needs_agreement":false,
         * "profile":{
         * "nickname":"정민영",
         * "thumbnail_image_url":
         * "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg",
         * "profile_image_url":
         * "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
         * "is_default_image":true
         * },
         * "has_email":true,
         * "email_needs_agreement":true,
         * "has_age_range":true,
         * "age_range_needs_agreement":true,
         * "has_birthday":true,
         * "birthday_needs_agreement":true,
         * "has_gender":true,
         * "gender_needs_agreement":true
         * }
         * }
         */
    }

    @Value("${GoogleClientId}")
    private String GoogleClientId;

    @Value("${GoogleClientSecret}")
    private String GoogleClientSecret;

    @Value("${GoogleRedirectURL}")
    private String GoogleRedirectURL;

    /**
     * google callback
     * [GET] /social/googletest
     */

    @ResponseBody
    @GetMapping("/googletest")
    public void googleCallback(@RequestParam String code) {
        // code = 인가 코드

        // JWT 토큰 발급을 위한 Header값 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // JWT 토큰 발급을 위한 Body값 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", GoogleClientId);
        params.add("client_secret", GoogleClientSecret);
        params.add("redirect_uri", GoogleRedirectURL);
        params.add("grant_type", "authorization_code");

        // 하나로 묶어주기
        HttpEntity entity = new HttpEntity(params, headers);

        // API 전송하여 Token / id_token 발급
        ResponseEntity<JsonNode> responseNode = restTemplate.exchange("https://oauth2.googleapis.com/token",
                HttpMethod.POST, entity,
                JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();

        // 저장
        String access_token = accessTokenNode.get("access_token").asText();
        String id_token = accessTokenNode.get("id_token").asText();

        // id_token 활용 하여 token_info 가져오기
        HttpHeaders headers2 = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, String> map = new HashMap<>();
        map.put("id_token", id_token);
        HttpEntity entity2 = new HttpEntity(map, headers2);
        ResponseEntity<JSONObject> resulttokeninfo = restTemplate.exchange(
                "https://oauth2.googleapis.com/tokeninfo",
                HttpMethod.POST,
                entity2,
                JSONObject.class);

        // access_token 활용 하여 user_info 가져오기
        HttpHeaders headers3 = new HttpHeaders();
        headers3.add("Authorization", "Bearer " + access_token);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers3);
        ResponseEntity<JSONObject> resultuserinfo = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                request,
                JSONObject.class);

        /*
         * TOKEN _ INFO
         * {
         * at_hash":"q8VS9Ic4wVihJ4SaI9Kpqw",
         * "sub":"104860162525889304382",
         * "email_verified":"true",
         * "kid":"9341dedeee2d1869b657fa930300082fe26b3d92",
         * "iss":"https:\/\/accounts.google.com",
         * "typ":"JWT",
         * "given_name":"Junhyoung",
         * "locale":"ko",
         * "picture":
         * "https:\/\/lh3.googleusercontent.com\/a\/AAcHTtfwjk_WV3PC1X1r5y0VI4QZw_lBSDn1che5NAzbduB7=s96-c",
         * "aud":
         * "1004079416790-gqvuicfa9v85afjhnl7omuo3s7iu0843.apps.googleusercontent.com",
         * "azp":
         * "1004079416790-gqvuicfa9v85afjhnl7omuo3s7iu0843.apps.googleusercontent.com",
         * "name":"Junhyoung Lee",
         * "exp":"1688432358",
         * "family_name":"Lee",
         * "iat":"1688428758",
         * "alg":"RS256",
         * "email":"junheong.winscore@gmail.com"
         * },
         * [
         * Date:"Mon, 03 Jul 2023 23:59:18 GMT",
         * Pragma:"no-cache", Cache-Control:
         * "no-cache, no-store, max-age=0, must-revalidate",
         * Expires:"Mon, 01 Jan 1990 00:00:00 GMT",
         * Content-Type:"application/json;
         * charset=UTF-8", Vary:"X-Origin",
         * "Referer",
         * "Origin,Accept-Encoding",
         * Server:"ESF",
         * X-XSS-Protection:"0",
         * X-Frame-Options:"SAMEORIGIN",
         * X-Content-Type-Options:"nosniff",
         * Alt-Svc:"h3=":443"; ma=2592000,h3-29=":443"; ma=2592000",
         * Accept-Ranges:"none",
         * Transfer-Encoding:"chunked"
         * ]
         * 
         * USER _ INFO
         * {"name":"Junhyoung Lee",
         * "id":"104860162525889304382",
         * "verified_email":true,
         * "given_name":"Junhyoung",
         * "locale":"ko",
         * "family_name":"Lee",
         * "email":"junheong.winscore@gmail.com",
         * "picture":
         * "https:\/\/lh3.googleusercontent.com\/a\/AAcHTtfwjk_WV3PC1X1r5y0VI4QZw_lBSDn1che5NAzbduB7=s96-c"
         * }
         * [
         * Date:"Tue, 04 Jul 2023 01:20:53 GMT",
         * Cache-Control:"no-cache, no-store, max-age=0, must-revalidate",
         * Expires:"Mon, 01 Jan 1990 00:00:00 GMT",
         * Pragma:"no-cache",
         * Content-Type:"application/json; charset=UTF-8",
         * Vary:"X-Origin", "Referer", "Origin,Accept-Encoding",
         * Server:"ESF",
         * X-XSS-Protection:"0",
         * X-Frame-Options:"SAMEORIGIN",
         * X-Content-Type-Options:"nosniff",
         * Alt-Svc:"h3=":443"; ma=2592000,h3-29=":443"; ma=2592000",
         * Accept-Ranges:"none",
         * Transfer-Encoding:"chunked"
         * ]
         */

    }

    @Value("${NaverClientId}")
    private String NaverClientId;

    @Value("${NaverClientSecret}")
    private String NaverClientSecret;

    private String NaverSESSION_STATE = "oauth_state";

    /* 프로필 조회 API URL */
    private String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";

    /**
     * naver callback
     * [GET] /social/navertest
     */
    @ResponseBody
    @GetMapping("/navertest")
    public void naverCallback(@RequestParam String code) {
        // code = 인가 코드

        // 토큰 정보 얻기 위한 정보 입력
        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", "authorization_code");
        accessTokenParams.add("client_id", NaverClientId);
        accessTokenParams.add("client_secret", NaverClientSecret);
        accessTokenParams.add("code", code); // 응답으로 받은 코드
        accessTokenParams.add("state", NaverSESSION_STATE);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams,
                accessTokenHeaders);

        // API 통신
        ResponseEntity<JSONObject> accessTokenResponseJson = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                JSONObject.class);

        String accessTokenResponse = (String) accessTokenResponseJson.getBody().get("access_token");

        // header를 생성해서 access token을 넣어줍니다.
        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + accessTokenResponse);

        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);

        // profile api로 생성해둔 헤더를 담아서 요청을 보냅니다.
        ResponseEntity<String> profileResponse = restTemplate.exchange(
                PROFILE_API_URL,
                HttpMethod.POST,
                profileHttpEntity,
                String.class);

        /*
         * 결과조회
         * "response":
         * {
         * "id":"VaZTe11QQS3hPPyAayRlBxVxaT_QtgwrFO3hAL7RfMc" ,
         * "nickname":"HUMON",
         * "profile_image":
         * "https:\/\/phinf.pstatic.net\/contact\/20220512_297\/165232881520953nCk_PNG\/avatar_profile.png",
         * "age":"20-29",
         * "gender":"M",
         * "email":"1187410@naver.com",
         * "mobile":"010-6388-9706",
         * "mobile_e164":"+821063889706",
         * "name":"\uc774\uc900\ud615" ( UNICODE ),
         * "birthday":"06-23",
         * "birthyear":"1997"
         * }
         */

    }

}
