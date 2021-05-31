package com.example.demo.global_resource.restcontroller;

import com.example.demo.global_resource.model.dto.GlobalResourceResponse;
import com.example.demo.global_resource.service.GlobalResourceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api")
@RestController
@Log4j2
public class GlobalResourceRestController {


    private final GlobalResourceService globalResourceService;

    public GlobalResourceRestController(GlobalResourceService globalResourceService){
        this.globalResourceService =globalResourceService;
    }


    @ApiOperation(value = " 테마 리소스 조회")
//    테마 카테고리 리소스 조회
    @GetMapping("/v1/global/resource/theme")
    public String getTheme () {

        String path = "theme/";
        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();

        try {

            List<GlobalResourceResponse> globalResourceResponses = globalResourceService.getResourceFileName("테마");

            for (int i = 0; i < globalResourceResponses.size(); i++) {
                JsonObject sObj = new JsonObject();
                sObj.addProperty("themeFilename", path + globalResourceResponses.get(i).getResourceFileName());
                jArray.add(sObj);
            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "테마 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "테마 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }


    @ApiOperation(value = " 테마명 조회")
//    테마 카테고리 리소스 조회
    @GetMapping("/v1/global/resource/theme/name")
    public String getThemeName () {

        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();

        try {

            List<GlobalResourceResponse> globalResourceResponses = globalResourceService.getReourseName("테마");

            for (int i = 0; i < globalResourceResponses.size(); i++) {

                JsonObject sObj = new JsonObject();
                sObj.addProperty("themeName", globalResourceResponses.get(i).getResourceName());

                jArray.add(sObj);
            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "테마 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "테마 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }


    //날씨 리소스 조회

    @ApiOperation(value = "로고 리소스 조회")
    @GetMapping("/v1/global/resource/logo")
    public String getlogo () {

        String path = "logo/";
        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();
        JsonObject sObj = new JsonObject();

        try {

            List<GlobalResourceResponse> globalResourceResponses = globalResourceService.getResourceFileName("로고");
            ;
            for (int i = 0; i < globalResourceResponses.size(); i++) {


                sObj.addProperty("logoFilename", path + globalResourceResponses.get(i).getResourceFileName());
                jArray.add(sObj);
            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "로고 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "로고 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }

    @ApiOperation(value = "날씨 리소스 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "code", value = "날씨 코드값", required = true, dataType = "string", paramType = "path"),

    })


    @GetMapping("/v1/global/resource/weather/{code}")
    public String getWeather(@PathVariable String code) {

        String path = "weather/";
        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();
        JsonObject sObj = new JsonObject();

        try {

            GlobalResourceResponse globalResourceResponse = globalResourceService.getReourseWeatherFileName("날씨",code);

            sObj.addProperty("weatherFilename", path + globalResourceResponse.getResourceFileName());
            jArray.add(sObj);


            obj.addProperty("code", "200");
            obj.addProperty("message", "날씨 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "날씨 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }

    // 스플래쉬 리소스 조회
    @ApiOperation(value = "스플래쉬 리소스 조회")
    @GetMapping("/v1/global/resource/splash")
    public String getSplash () {

        String path = "splash/";
        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();

        try {

            List<GlobalResourceResponse> globalResourceResponses = globalResourceService.getResourceFileName("스플래쉬");

            for (int i = 0; i < globalResourceResponses.size(); i++) {
                JsonObject sObj = new JsonObject();
                sObj.addProperty("splashFilename", path + globalResourceResponses.get(i).getResourceFileName());
                jArray.add(sObj);

            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "스플래쉬 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "스플래쉬 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }




    @ApiOperation(value = "튜토리얼 리소스 조회")
    @GetMapping("/v1/global/resource/tutorial")
    public String getTutorial () {

        String path = "tutorial/";
        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();

        try {

            List<GlobalResourceResponse> globalResourceResponses = globalResourceService.getResourceFileName("튜토리얼");

            for (int i = 0; i < globalResourceResponses.size(); i++) {


                JsonObject sObj = new JsonObject();
                sObj.addProperty("tutorialFilename", path + globalResourceResponses.get(i).getResourceFileName());
                jArray.add(sObj);

            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "튜토리얼 리소스 파일 조회가 성공했습니다.");
            obj.add("data", jArray);



        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "튜토리얼 리소스 파일 조회가 실패했습니다.");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);


        return json.toString();
    }
}
