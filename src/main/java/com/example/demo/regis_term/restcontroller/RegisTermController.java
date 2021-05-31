package com.example.demo.regis_term.restcontroller;

import com.example.demo.regis_term.model.entity.RegisTermResponse;
import com.example.demo.regis_term.service.RegisTermService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class RegisTermController {


    private final RegisTermService regisTermService;

    
    
    public RegisTermController(RegisTermService regisTermService){
        this.regisTermService = regisTermService;
    }

    @ApiOperation(value = "개인정보보호 약관 조회 ")
    
    @GetMapping(value="/v1/term/privacy")
    public String getTermPrivacyContent(){

        JsonObject obj  = new JsonObject();

        try {

            JsonObject sObj = new JsonObject();

            JsonArray jArray = new JsonArray();

            RegisTermResponse regisTermResponse = regisTermService.getRegisTermContent("privacy_term");
            sObj.addProperty("privacyTermContent",regisTermResponse.getRegisTermContent());
            jArray.add(sObj);



            obj.addProperty("code",200);
            obj.addProperty("message","개인정보보호 약관 조회가 성공하였습니다.");

            obj.add("data",jArray);

        }
        catch(Exception e){

            obj.addProperty("code",400);
            obj.addProperty("message","개인정보보호 약관 조회가 실패하였습니다.");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();


    }


    @ApiOperation(value = "서비스 이용 약관 조회 ")

    @GetMapping(value="/v1/term/service")
    public String getTermServiceContent(){

        JsonObject obj  = new JsonObject();

        try {

            JsonObject sObj = new JsonObject();

            JsonArray jArray = new JsonArray();

            RegisTermResponse regisTermResponse = regisTermService.getRegisTermContent("service_term");
            sObj.addProperty("serviceTermContent",regisTermResponse.getRegisTermContent());
            jArray.add(sObj);



            obj.addProperty("code",200);
            obj.addProperty("message","서비스이용 약관 조회가 성공하였습니다.");

            obj.add("data",jArray);

        }
        catch(Exception e){

            obj.addProperty("code",400);
            obj.addProperty("message","서비스이용 약관 조회가 실패하였습니다.");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();


    }

}
