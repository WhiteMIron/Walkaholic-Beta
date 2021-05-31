package com.example.demo.walkrecord.restcontroller;

import com.example.demo.util.S3FileUploadService;
import com.example.demo.walkrecord.WalkRecordService;
import com.example.demo.walkrecord.model.dto.WalkRecordTotalResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordDateDetailResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordMonthResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("api")
public class
WalkRecordRestController {

    private final WalkRecordService walkRecordService;

    private final S3FileUploadService s3Uploader;



    public WalkRecordRestController(WalkRecordService walkRecordService, S3FileUploadService s3Uploader) {
        this.walkRecordService = walkRecordService;
        this.s3Uploader =s3Uploader;
    }




    @ApiOperation(value = "산책 월별 기록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "date", value = "일자 ex)YYYY-MM", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/v1/user/{id}/walk-record")
    public String selectWalkRecord(@PathVariable Long id, @RequestParam(value = "date") String date) {



        JsonObject obj = new JsonObject();

        try {

            String[] str = date.split("-");
            String year = str[0];
            String month = str[1];

            JsonArray jArray = new JsonArray();

            List<WalkRecordMonthResponse> walkRecordMonthResponses = walkRecordService.getWalkRecordDateList(year,month,id);
            for (int i = 0; i < walkRecordMonthResponses.size(); i++) {
                JsonObject sObject = new JsonObject();

                sObject.addProperty("year",walkRecordMonthResponses.get(i).getWalkYear());
                sObject.addProperty("month",walkRecordMonthResponses.get(i).getWalkMonth());
                sObject.addProperty("date", walkRecordMonthResponses.get(i).getWalkDate());
                jArray.add(sObject);
            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "조회 결과 성공");

            obj.add("data", jArray);

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "조회 결과 실패");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }



    //일자 산책  상세 기록

    @ApiOperation(value = "산책 일자별 상세기록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "date", value = "일자 ex)YYYY-MM-DD", required = true, dataType = "date", paramType = "query")
    })
    @GetMapping("/v1/user/{id}/detail/walk-record")
    public String test3(@PathVariable Long id, @RequestParam(value = "date") String date) {
        JsonObject obj = new JsonObject();

        try {
            WalkRecordTotalResponse walkRecordTotalResponse = walkRecordService.getWalkRecordTotal(date,id);


            JsonArray jArray = new JsonArray();
            List<WalkRecordDateDetailResponse> walkRecordDateDetailResponses = walkRecordService.getWalkRecordDateDetailList(date, id);

            if(walkRecordDateDetailResponses.size()>0) {
                for (int i = 0; i < walkRecordDateDetailResponses.size(); i++) {
                    JsonObject sObject = new JsonObject();
                    sObject.addProperty("walkDate", walkRecordDateDetailResponses.get(i).getWalkDate());
                    sObject.addProperty("walkStartTime", walkRecordDateDetailResponses.get(i).getWalkStartTime());
                    sObject.addProperty("walkEndTime", walkRecordDateDetailResponses.get(i).getWalkEndTime());
                    sObject.addProperty("walkTime", walkRecordDateDetailResponses.get(i).getWalkTime());
                    sObject.addProperty("walkDistance", walkRecordDateDetailResponses.get(i).getWalkDistance().toString());
                    sObject.addProperty("walkCount", walkRecordDateDetailResponses.get(i).getWalkCount().toString());
                    sObject.addProperty("walkCalorie", walkRecordDateDetailResponses.get(i).getWalkCalorie().toString());
                    sObject.addProperty("walkFileName", walkRecordDateDetailResponses.get(i).getWalkRecordFilename());
                    jArray.add(sObject);
                }


                JsonObject data = new JsonObject();

                data.addProperty("walkDate",walkRecordTotalResponse.getWalkDate());
                data.addProperty("walkDay",walkRecordTotalResponse.getWalkDay());
                data.addProperty("totalWalkTime",walkRecordTotalResponse.getTotalWalkTime());
                data.addProperty("totalDistance",walkRecordTotalResponse.getWalkDistance().toString());
                data.addProperty("totalWalkCount",walkRecordTotalResponse.getTotalWalkCount().toString());
                data.addProperty("totalWalkCalorie",walkRecordTotalResponse.getTotalWalkCaloire().toString());

                obj.add("totalRecord", data);
            }
            else{

                DateFormat dateFormat  = new SimpleDateFormat("yyyyMMdd");
                Date walkDate = dateFormat.parse(date);

                Calendar calendar =  Calendar.getInstance();
                calendar.setTime( walkDate);

                String walkDate2 = dateFormat.format(walkDate);

                JsonObject data = new JsonObject();
                data.addProperty("walkDate",walkDate2);
                data.addProperty("walkDay",calendar.get(Calendar.DAY_OF_WEEK));

                data.addProperty("totalWalkTime","0");
                data.addProperty("totalDistance","0");
                data.addProperty("totalWalkCount","0");
                data.addProperty("totalWalkCalorie","0");
                obj.add("totalRecord", data);
            }
            obj.addProperty("code", "200");
            obj.addProperty("message", "조회 결과 성공");

            obj.add("data",jArray);

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "조회 결과 실패");




        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json;

    }


    @ApiOperation(value = "산책 기록 업로드")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
//            @ApiImplicitParam(name = "walkDate", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkCalorie", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkCount", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkDistance", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkEndTime", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkStartTime", value = "유저id", required = true, dataType = "Date", paramType = "post"),
//            @ApiImplicitParam(name = "walkRecordFilename", value = "유저id", required = true, dataType = "Date", paramType = "post")

    })
    @PostMapping("/v1/user/{id}/walk-record")
    public String uploadWalkRecord(@PathVariable Long id, @RequestParam("data") MultipartFile file, WalkRecordRegister walkRecordRegister){

        JsonObject obj = new JsonObject();

        log.info("/upload 도착!@");


        try {
            String savedName =s3Uploader.upload(file, "dev-board");

            walkRecordService.putWalkReocord(id, walkRecordRegister,savedName);
            obj.addProperty("code","200");
            obj.addProperty("message","산책기록이 저장되었습니다.");

        }
        catch(Exception e){
            obj.addProperty("code","400");
            obj.addProperty("message","산책기록 저장이 실패하였습니다.");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }

}
