package com.fifatoy.SocialLoginTestController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/social")
public class socialLoginTestController {


    // API KEY 값
    @Value("${KakaoApiKey}")
    private String kakaoApiKey;

    /**
     * kakao callback
     * [GET] /social/kakaotest
     */
    @ResponseBody
    @GetMapping("/kakaotest")
    public void kakaoCallback(@RequestParam String code) {
        // 인가 코드
        // 컨트롤러 접속 시 로그인 후 인가코드 발급확인
        System.out.println(code);


        // accessToken 받기 
        String access_Token = "";
        String refresh_Token = "";
        // Token 발급 url
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+kakaoApiKey); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/social/kakaotest"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(access_Token);

        String reqURL2 = "https://kapi.kakao.com/v2/user/me";

    //access_token을 이용하여 사용자 정보 조회
        try {
        URL url = new URL(reqURL2);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + access_Token); //전송할 header 작성, access_token전송

        //결과 코드가 200이라면 성공
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);

        br.close();

        } catch (IOException e) {
                e.printStackTrace();
        }



       /*
       결과 
        response body : 
        {   "id":2883786726,
            "connected_at":"2023-07-02T15:23:48Z",
            "properties":
                {"nickname":"이준형",
                "profile_image":"http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_640x640.jpg",
                "thumbnail_image":"http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_110x110.jpg"},
                "kakao_account":{"profile_nickname_needs_agreement":false,
                "profile_image_needs_agreement":false,
                "profile":
                    {"nickname":"이준형",
                    "thumbnail_image_url":"http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_110x110.jpg",
                    "profile_image_url":"http://k.kakaocdn.net/dn/iSN9j/btslafjB4C8/3OSpThTQw7AU4AT4380a30/img_640x640.jpg",
                    "is_default_image":false},
                "has_email":true,
                "email_needs_agreement":false,
                "is_email_valid":true,
                "is_email_verified":true,
                "email":"junheong@nate.com",
                "has_age_range":true,
                "age_range_needs_agreement":false,
                "age_range":"20~29",
                "has_birthday":true,
                "birthday_needs_agreement":false,
                "birthday":"0623",
                "birthday_type":"SOLAR",
                "has_gender":true,
                "gender_needs_agreement":false,"gender":"male"
            }
        }
        */
    }



    /**
     * google callback
     * [GET] /social/googletest
     */
    @ResponseBody
    @GetMapping("/googletest")
    public void googleCallback(@RequestParam String code) {
        // 인가 코드 
        System.out.println(code);
    }


    /**
     * naver callback
     * [GET] /social/navertest
     */
    @ResponseBody
    @GetMapping("/navertest")
    public void naverCallback(@RequestParam String code) {
        // 인가 코드 
        System.out.println(code);
    }

}


