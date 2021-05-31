package com.example.demo.amenity.restcontroller;

import com.example.demo.amenity.model.dto.PoliceResponse;
import com.example.demo.amenity.service.PoliceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api")
public class AmenityRestController {

    private PoliceService pointService;

    public AmenityRestController(PoliceService pointService){
        this.pointService = pointService;
    }



    @ApiOperation(value = "편의시설 위치 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "type", value = "편의시설 타입", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "x", value = "현재 위치 위도", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "y", value = "현재 위치 경도", required = true, dataType = "string", paramType = "query")

    })
    @GetMapping("/v1/amenity/{type}")
    public String getPointInfo(@PathVariable String type, @RequestParam String x, @RequestParam String y){

        JsonObject obj =new JsonObject();


        try{

            List<PoliceResponse> pointResponses = pointService.getPoint(x,y);
            JsonArray jArray = new JsonArray();

            for(int i=0; i<pointResponses.size();i++){

                JsonObject sObj = new JsonObject();
                sObj.addProperty("name",pointResponses.get(i).getName());
                sObj.addProperty("address",pointResponses.get(i).getAddress());
                sObj.addProperty("x",pointResponses.get(i).getLatitude());
                sObj.addProperty("y",pointResponses.get(i).getLongitude());


                jArray.add(sObj);
            }


            obj.addProperty("code","200");
            obj.addProperty("message","좌표 조회가 성공하였습니다.");
            obj.add("data",jArray);



        }catch(Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message","좌표 조회가 실패하였습니다.");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(obj);

        return json;

    }



}
