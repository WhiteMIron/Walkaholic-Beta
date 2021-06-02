package com.example.demo.oauth.restcontroller;

import com.example.demo.oauth.GetAccessTokenService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.model.dto.UserLoginResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("oauth")
public class OauthRestController {

    private final GetAccessTokenService getAccessTokenService;
    private final UserService userService;

    public OauthRestController(GetAccessTokenService getAccessTokenService, UserService userService){
        this.getAccessTokenService =getAccessTokenService;
        this.userService = userService;
    }

//
    @GetMapping(value="/kakao/login")
    public RedirectView kakaoConnect() {
//        640bb23f919c524ca369ea3a51427638
//        6bbcbb7e909f4fc6af433b29fb9cb225
        String RestApiKey="640bb23f919c524ca369ea3a51427638";
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + RestApiKey);
        url.append("&redirect_uri=http://localhost:8080/oauth/kakao/callback");
        url.append("&response_type=code");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url.toString());
        return redirectView;
    }

    @RequestMapping(value="/kakao/callback",produces="application/json",method= {RequestMethod.GET, RequestMethod.POST})
    public String kakaoLogin(@RequestParam("code")String authorizationCode, RedirectAttributes ra, HttpSession session, HttpServletResponse response, Model model)throws IOException {
        System.out.println("kakao code:"+authorizationCode);
        log.info("ra값:{}",ra);
        log.info("session:{}",session);
        log.info("response:{}",response);

    


        final String RequestUrl = "https://kauth.kakao.com/oauth/token"; // Host
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", "")); // REST API KEY
        postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/oauth/kakao/callback")); // 리다이렉트 URI
        postParams.add(new BasicNameValuePair("code", authorizationCode)); // 로그인 과정중 얻은 code 값

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(RequestUrl);

        JsonNode returnNode = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams));

            final HttpResponse res = client.execute(post);

            final int responseCode = res.getStatusLine().getStatusCode();
            System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code : " + responseCode);

            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();

            returnNode = mapper.readTree(res.getEntity().getContent());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

//        return returnNode;
        JsonNode jsonToken = returnNode ;
        // 여러 json객체 중 access_token을 가져온다
        JsonNode accessToken = jsonToken.get("access_token");

        log.info("{}",returnNode);
        log.info("액세스 토큰 값 확인:{}",accessToken.toString());

        String Token = accessToken.textValue();
        log.info("액세스 토큰 값 확인:{}",Token);
        final String RequestUrl2 = "https://kapi.kakao.com/v2/user/me"; // Host
        final List<NameValuePair> postParams2 = new ArrayList<NameValuePair>();

//        postParams2.add(new BasicNameValuePair("Authorization","Bearer "+ Token));
//        postParams2.add(new BasicNameValuePair("Content-type", "application/x-www-form-urlencoded;charset=utf-8"));

        final HttpClient client2 = HttpClientBuilder.create().build();
        final HttpPost post2 = new HttpPost(RequestUrl2);
        post2.addHeader("Authorization","Bearer "+Token);
//
        JsonNode returnNode2 = null;

        try {
//            post2.setEntity(new UrlEncodedFormEntity(postParams2));

            final HttpResponse res2 = client2.execute(post2);

            final int responseCode2 = res2.getStatusLine().getStatusCode();
            System.out.println("\nSending 'POST' request to URL : " + RequestUrl2);
            System.out.println("Post parameters : " + postParams2);
            System.out.println("Response Code : " + responseCode2);

            // JSON 형태 반환값 처리
            ObjectMapper mapper = new ObjectMapper();

            returnNode2 = mapper.readTree(res2.getEntity().getContent());
            JsonNode jsonToken2 = returnNode2 ;
            log.info("returnNode 값 확인:{}",jsonToken2);
            // 여러 json객체 중 access_token을 가져온다
            JsonNode id = jsonToken2.get("id");
            log.info("값 확인:{}",id);

            JsonObject obj = new JsonObject();
            Long userId = id.longValue();
            try{

            log.info("id값 화인:",userId);
            UserLoginResponse userLoginResponse = userService.getUserId(userId);


            JsonObject sObject = new JsonObject();

            JsonArray jArray =new JsonArray();

            sObject.addProperty("userId", userLoginResponse.getUserId());
            jArray.add(sObject);

            obj.addProperty("code", "200");
            obj.addProperty("message", "로그인 성공");
            obj.add("data", jArray);

            } catch (Exception e) {

                obj.addProperty("code", "400");
                obj.addProperty("message", "로그인 실패");

            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(obj);

            return json.toString();



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


        return "테스트";
    }

    @RequestMapping(value="/kakao/callback2",produces="application/json",method= {RequestMethod.GET, RequestMethod.POST})
    public String getToken(){


        return "테스트2";
    }





}
