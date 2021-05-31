package com.example.demo.misson.restcontroller;

import com.example.demo.misson.model.dto.MissonRewardinfo;
import com.example.demo.misson.model.dto.MissionInfoUpdateRequest;
import com.example.demo.misson.model.dto.MissionProgressInfo;
import com.example.demo.misson.service.MissionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value="api")
public class MissonRestController {

    private final MissionService missionService;

    public MissonRestController(MissionService missionService) {
        this.missionService = missionService;
    }



    @ApiOperation(value = "미션 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "미션 유형 ex일일미션:0, 주가미션:1", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("/v1/user/{id}/mission/{type}")
    public String getMissionInfo(@PathVariable Long id, @PathVariable Long type) {

        JsonObject obj = new JsonObject();
        List<MissionProgressInfo> missionProgressInfos;
        try {

            JsonArray jArray = new JsonArray();
            if(type<4 && type!=null)
                missionProgressInfos = missionService.geDailyMissionProgressInfos(id, type);

            else
                missionProgressInfos = missionService.getWeelkyMissionProgressInfos(id,type);

            for (int i = 0; i < missionProgressInfos.size(); i++) {
                JsonObject sObject = new JsonObject();
                sObject.addProperty("missionId", missionProgressInfos.get(i).getMissionId());
                sObject.addProperty("missionName", missionProgressInfos.get(i).getMissionName());
                sObject.addProperty("completeYN", missionProgressInfos.get(i).getCompleteYN());
                sObject.addProperty("takeRewardYN", missionProgressInfos.get(i).getTakeRewardYN());
                sObject.addProperty("missionReward", missionProgressInfos.get(i).getMissionRewardPoint());
//                sObject.addProperty("currentAchievement", missionProgressInfos.get(i).getCurrentAchievement());

                jArray.add(sObject);
            }

            obj.addProperty("code", "200");
            obj.addProperty("message", "조회 결과 성공");
//            obj.add("allTakeRewrardYN",);
            obj.add("data", jArray);

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "조회 결과 실패");


        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }

// 미션 성취증가
// 성취를 갱신하면서 미션 완료도 같이하므로 미션 완료를 따로 둘 필요가 없음 ok?
// 완료버튼 눌렀을 시 보상을 주는 부분도 만들어야지


    @ApiOperation(value = "미션 성취도 갱신")

    @PutMapping(value = "/v1/user/{id}/mission")
    public String updateCurrentAchievement(@PathVariable Long id,@RequestBody MissionInfoUpdateRequest missionInfoUpdateRequest) {

        JsonObject obj = new JsonObject();

        try {

            Long currentAchievement = missionInfoUpdateRequest.getCurrentAchievement();
            Long mId = missionInfoUpdateRequest.getMissionId();

            MissionProgressInfo missionProgressInfo;
            JsonArray jArray = new JsonArray();

            if(mId<4 && mId!=null) {
                missionService.setCurrentDailyAchievementUpdate(id, currentAchievement);
                missionProgressInfo = missionService.cmpDailyMissionComplete(id, mId, currentAchievement);


//
//                JsonObject sObject = new JsonObject();
//                sObject.addProperty("missionId", missionProgressInfo.getMissionId());
//                sObject.addProperty("userId", missionProgressInfo.getUserId());
//                sObject.addProperty("completeYN", missionProgressInfo.getCompleteYN());
//                sObject.addProperty("missionReward",missionProgressInfo.getMissionRewardPoint());
//                sObject.addProperty("currentAchievement",missionProgressInfo.getCurrentAchievement());
//                jArray.add(sObject);
            }




            else if(mId>3 && mId!=null) {
                missionService.setCurrentWeeklyAchievementUpdate(id, currentAchievement);
                missionProgressInfo = missionService.cmpWeeklyMissionComplete(id, mId, currentAchievement);

//                JsonObject sObject = new JsonObject();
//
//
//                sObject.addProperty("missionId", missionProgressInfo.getMissionId());
//                sObject.addProperty("userId", missionProgressInfo.getUserId());
//                sObject.addProperty("completeYN", missionProgressInfo.getCompleteYN());
//                sObject.addProperty("missionReward",missionProgressInfo.getMissionRewardPoint());
//                sObject.addProperty("currentAchievement",missionProgressInfo.getCurrentAchievement());
//                jArray.add(sObject);
            }
//

            obj.addProperty("code", "200");
            obj.addProperty("message", "성취도 갱신 결과 성공");
//            obj.add("data", jArray);

        }catch (Exception e) {
                obj.addProperty("code", "400");
                obj.addProperty("message", "성취도 갱신 결과 실패");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }



    @ApiOperation(value = "미션 모든 보상받기")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "missionType", value = "미션 type", required = true, dataType = "Long", paramType = "path"),

    })


    @PutMapping("/v1/user/{id}/Allreward/{missionType}")
        public String getAllReward(@PathVariable Long id,@PathVariable Long missionType){

        List<MissionProgressInfo>missionProgressInfos = missionService.getNotTakeRewardInfos(id,missionType);
        String  mId;
        Integer totalRewardPoint=0;
        JsonObject obj  = new JsonObject();
        JsonArray jArray =new JsonArray();

        try {


            for (int i = 0; i < missionProgressInfos.size(); i++) {

                JsonObject sObj = new JsonObject();

                mId = missionProgressInfos.get(i).getMissionId();

                sObj.addProperty("missionId",missionProgressInfos.get(i).getMissionId());
                sObj.addProperty("rewardPoint",missionProgressInfos.get(i).getMissionRewardPoint());
                totalRewardPoint+=Integer.parseInt(missionProgressInfos.get(i).getMissionRewardPoint());
                jArray.add(sObj);

                missionService.takeMissionReward(id, Long.parseLong(mId));
            }
            obj.addProperty("code", "200");
            obj.addProperty("message", "모든 미션을 달성하여 "+ totalRewardPoint+" 포인트를 받았습니다.");

        }catch (Exception e){

            obj.addProperty("code", "400");
            obj.addProperty("message", "모든 미션 보상받기 실패");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();
    }

    @ApiOperation(value = "미션 보상")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "missionId", value = "미션 id", required = true, dataType = "string", paramType = "path"),

    })



    @PutMapping("/v1/user/{id}/reward/{missionId}")
    public String takeMissonReward(@PathVariable Long id, @PathVariable Long missionId){

        JsonObject obj  = new JsonObject();

        try {

            MissionProgressInfo missionProgressInfo =missionService.getNotTakeRewardInfo(id,missionId);
            log.info("값:{}",missionProgressInfo.getResultRow());
            if(missionProgressInfo.getResultRow().equals("1")) {

                log.info("1");
                missionService.takeMissionReward(id, missionId);
                MissonRewardinfo missonRewardinfo = missionService.getRewardInfo(missionId);

                obj.addProperty("code", "200");
                obj.addProperty("message",missonRewardinfo.getMissionName()+" 달성하여 " +missonRewardinfo.getRewardPoint()+" 포인트를 받았습니다.");

            }
            else if(missionProgressInfo.getResultRow().equals("0")) {
                log.info("2");
                obj.addProperty("code", "400");
                obj.addProperty("message", "미션달성 조건을 충족하지 못하였습니다.");
            }




        }catch (Exception e){

            obj.addProperty("code", "400");
            obj.addProperty("message", "미션 보상 실패");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();
    }

    @ApiOperation(value = "걷기 보상")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),

    })
    @PutMapping("/v1/user/{id}/walk/reward")
    public String takeWalkReward(@PathVariable Long id,@RequestBody Long walkCount){

        JsonObject obj  = new JsonObject();

        try {

            obj.addProperty("code", "200");
            obj.addProperty("message", "걷기 보상 수령 성공");

            missionService.takeWalkReward(id,walkCount);
            missionService.userLevelUp(id);


        }catch (Exception e){

            obj.addProperty("code", "400");
            obj.addProperty("message", "걷기 보상 실패");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }




}




