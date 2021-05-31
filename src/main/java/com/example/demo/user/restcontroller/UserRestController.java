package com.example.demo.user.restcontroller;

import com.example.demo.item.model.dto.BuyitemInfo;
import com.example.demo.item.model.dto.EquipItemInfo;
import com.example.demo.item.model.dto.ItemInfo;
import com.example.demo.item.service.ItemService;
import com.example.demo.pet.service.PetService;
import com.example.demo.user.service.UserService;
import com.example.demo.user.model.dto.UserSignUpDto;
import com.example.demo.user.model.dto.*;
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

@RestController
@RequestMapping("api")
@Log4j2
public class UserRestController {

    private final UserService userService;
    private final ItemService itemService;
    private final PetService petService;

    public UserRestController(UserService userService, ItemService itemService, PetService petService) {


        this.userService = userService;
        this.itemService = itemService;
        this.petService = petService;

    }

//
//    @GetMapping("/v1/user")
//    public Response selectAllUser() {
//        Response response = new Response();
//        try {
//            response.setCode(200);
//            response.setMessage("조회가 성공되었습니다.");
//            response.setData(userService.selectAllUser());
//
//        } catch (Exception e) {
//            response.setCode(400);
//            response.setMessage("조회가 실패하였습니다");
//        }
//        return response;
//    }


    @ApiOperation(value = "로그인",produces = "application/json; charset=UTF-8")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "uid", value = "카카오 유저 uid", required = true, dataType = "string", paramType = "body"),
//    })
//
//    @PostMapping (value = "/v1/login",consumes =MediaType.APPLICATION_JSON_VALUE)

    @PostMapping(value = "/v1/login")
    public String loginForFormRequest(@RequestBody UserLogin userLogin) {
        JsonObject obj = new JsonObject();

        System.out.println(userLogin);
//        System.out.println("유저uid값"+userLogin.getP());

        try {
            UserLoginResponse userLoginResponse = userService.getUserId(userLogin.getId());


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

    }


//    @ApiImplicitParams({
//
//            @ApiImplicitParam(name = "uid", value = "카카오 발급 유저id", required = true, dataType = "string", paramType = "body"),
//            @ApiImplicitParam(name = "userNickName", value = "유저닉네임", required = true, dataType = "string", paramType = "body"),
//            @ApiImplicitParam(name = "gender", value = "성별 ex)남/여", required = true, dataType = "string", paramType = "body"),
//            @ApiImplicitParam(name = "userBirth", value = "일자 ex)YYYY-MM-DD", required = true, dataType = "string", paramType = "body"),
//            @ApiImplicitParam(name = "weight", value = "몸무게(kg)", required = true, dataType = "string", paramType = "body"),
//            @ApiImplicitParam(name   = "height", value = "신장(cm)", required = true, dataType = "string", paramType = "body")
//
//    })

    @ApiOperation(value = "회원가입")
    @PostMapping(value="/v1/signup",produces = "application/json; charset=UTF-8")
    public String login(@RequestBody UserSignUpDto userSignUpDto) {
        JsonObject obj = new JsonObject();


        try {
            UserLoginResponse userLoginResponse = userService.saveUser(userSignUpDto);

//            userService.getUserId(userSignUpDto.getUserUid());


            JsonObject sObject = new JsonObject();
            JsonArray jArray = new JsonArray();

            sObject.addProperty("userId", userLoginResponse.getUserId());
            jArray.add(sObject);


            obj.addProperty("code", "200");
            obj.addProperty("message", "회원가입 성공");
            obj.add("data", jArray);

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "회원가입 실패");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }


    @ApiOperation(value = "유저 정보 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
    })

    @GetMapping("/v1/user/{id}")
    public String selectUser(@PathVariable Long id) {

        JsonObject obj = new JsonObject();
        try {

            UserDashBoardDto userDashBoardDto = userService.getUserInfo(id);

            System.out.println(userDashBoardDto.getId());


            JsonArray jArray = new JsonArray();
            JsonObject sObject = new JsonObject();

            sObject.addProperty("id", userDashBoardDto.getId());
            sObject.addProperty("nickName", userDashBoardDto.getNickName());
            sObject.addProperty("gender", userDashBoardDto.getGender());
            sObject.addProperty("currentExp", userDashBoardDto.getCurrentExp());
            sObject.addProperty("walkCount", userDashBoardDto.getWalkCount());
            sObject.addProperty("walkDistance", userDashBoardDto.getWalkDistance());
            sObject.addProperty("connectedDate", userDashBoardDto.getConnectedDate().toString());
            sObject.addProperty("height", userDashBoardDto.getHeight());
            sObject.addProperty("weight", userDashBoardDto.getWeight());
            sObject.addProperty("petId", userDashBoardDto.getPetId());
            sObject.addProperty("levelId", userDashBoardDto.getLevelId());
            sObject.addProperty("currentPoint", userDashBoardDto.getCurrentPoint());
            sObject.addProperty("birth", userDashBoardDto.getBirth());
            sObject.addProperty("petName",userDashBoardDto.getPetName());
            sObject.addProperty("petName",userDashBoardDto.getPetName());
            sObject.addProperty("weeklyMissionAchievement",userDashBoardDto.getWeeklyMissionAchievement());
            jArray.add(sObject);

            obj.addProperty("code", "200");
            obj.addProperty("message", "조회가 성공하였습니다.");
            obj.add("data",jArray);


        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();


    }

//
//
//    @ApiOperation(value = "회원가입")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "유저id", required = true, dataType = "string", paramType = "path"),
//    })
//
//    @PostMapping("/v1/user")
//    public Response createUser(@RequestBody UserSignUpDto userSignUpDto) {
//        Response response = new Response();
//        try {
//            System.out.println(userSignUpDto.getGender());
//            System.out.println(userSignUpDto.toString());
//            userService.createUser(userSignUpDto);
//            response.setMessage("회원가입이 성공적으로 되었습니다.");
//            response.setCode(200);
//
//        } catch (Exception e) {
//            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
//            response.setCode(400);
//            System.out.println(e.toString());
//        }
//        return response;
//    }


    @ApiOperation(value = "아이템 장착 정보 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("/v1/user/{id}/item/equip")
    public String getEquip(@PathVariable Long id) {

        JsonObject obj = new JsonObject();

        try {

            JsonArray jArray = new JsonArray();
            List<ItemInfo> itemInfos = itemService.getEquipInfo(id);
            for (int i = 0; i < itemInfos.size(); i++) {
                JsonObject sObject = new JsonObject();
                sObject.addProperty("itemType", itemInfos.get(i).getItemType());
                sObject.addProperty("itemId", itemInfos.get(i).getItemId());
                sObject.addProperty("itemFilename",itemInfos.get(i).getItemFileName());
                sObject.addProperty("itemPrice",itemInfos.get(i).getItemPrice());
                sObject.addProperty("itemName",itemInfos.get(i).getItemName());
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


    @ApiOperation(value = "펫 외형 리소스 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("v1/user/{id}/pet/appearance")
    public String getPetAppearance(@PathVariable Long id) {

        JsonObject obj = new JsonObject();

        try {
            String path="pet/";
            String appearInfo = "";

            List<ItemInfo> userEquipItemInfos = itemService.getEquipInfo(id);


            for (int i = 0; i < userEquipItemInfos.size(); i++)
                if(!(userEquipItemInfos.get(i).getItemName().equals("")))
                    appearInfo = appearInfo + userEquipItemInfos.get(i).getItemName() + "_";

            System.out.println("appearInfo값" + appearInfo);

            JsonArray jArray = new JsonArray();
            List<UserPetAppearanceInfo> userPetAppearanceInfos = petService.getPetAppearanceInfo(appearInfo, id);
            for (int i = 0; i < userPetAppearanceInfos.size(); i++) {
                JsonObject sObject = new JsonObject();
                sObject.addProperty("filename", path+userPetAppearanceInfos.get(i).getPetApperanceFileName());
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


    @ApiOperation(value = "아이템 장착 미리보기 ")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "faceItemId", value = "얼굴부위 아이템 id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "headItemId", value = "머리부위 아이템 id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("v1/user/{id}/item/view")
    public String getEquipPreview(@PathVariable Long id, @RequestParam(value = "headItemId", required = false) String headItemId, @RequestParam(value = "faceItemId", required = false) String faceItemId) {
        JsonObject obj = new JsonObject();

        String path="pet/";
        try {
            String appearInfo = "";

            System.out.println("faceItemId"+faceItemId);

            if (headItemId!=null)
                appearInfo = appearInfo + itemService.getItemInfo(headItemId).getItemName() + "_";
            if (faceItemId!=null)
                appearInfo = appearInfo + itemService.getItemInfo(faceItemId).getItemName() + "_";

            System.out.println(appearInfo);
            JsonArray jArray = new JsonArray();
            List<UserPetAppearanceInfo> userPetAppearanceInfos = petService.getPetAppearanceInfo(appearInfo, id);
            for (int i = 0; i < userPetAppearanceInfos.size(); i++) {
                JsonObject sObject = new JsonObject();
                sObject.addProperty("filename", path+userPetAppearanceInfos.get(i).getPetApperanceFileName());
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


    @GetMapping("v1/aa")
    public String ttt(){

        JsonObject obj = new JsonObject();

        obj.addProperty("message","aa");

        if(obj.get("message").getAsString()=="aa") {
            System.out.println("aa");
            log.info("일치함{}",obj.get("message"));

        }
        else
            log.info("불일치함{}",obj.get("message"));


        return "test";

    }


    @ApiOperation(value = "아이템 구매")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path")
//
    })
    @PostMapping("v1/user/{id}/item")
    public String buyItem(@PathVariable Long id, @RequestBody BuyitemInfo buyItemInfo) {

        JsonObject obj = new JsonObject();

        obj.addProperty("message","");

        try
        {

            List<Long> buyItems = buyItemInfo.getItemId();
            JsonArray buySueccessMessages = new JsonArray();
            JsonArray pointLackMessages = new JsonArray();
            JsonArray alreadyHasMessage = new JsonArray();
            for (int i = 0; i < buyItems.size(); i++) {


                Integer result = userService.buyItem(id, buyItems.get(i));

                if (result == 1) {
                    obj.addProperty("code", "200");
                    if (!(obj.get("message").getAsString()=="구매가 완료되었습니다.") && !(obj.get("message").getAsString()=="포인트가 부족합니다.") &&  !(obj.get("message").getAsString()=="이미 보유하고 있는 아이템입니다.")) {
                        obj.addProperty("message", "구매가 완료되었습니다.");
                        log.info("일치함");

                    }
                     else if (pointLackMessages.size() == 0 || alreadyHasMessage.size() == 0 && buySueccessMessages.size() ==0 && !(obj.get("message").getAsString()=="구매가 완료되었습니다.")) {
                        buySueccessMessages.add("구매가 완료되었습니다.");
                        obj.add("data", buySueccessMessages);
                    }

                    
                } else if (result == -1) {
                    obj.addProperty("code", "400");
                    if (!(obj.get("message").getAsString()=="구매가 완료되었습니다.") && !(obj.get("message").getAsString()=="이미 보유하고 있는 아이템입니다.") && !(obj.get("message").getAsString()=="포인트가 부족합니다."))
                        obj.addProperty("message", "포인트가 부족합니다.");
                    else if (buySueccessMessages.size() == 0 && alreadyHasMessage.size() == 0 && pointLackMessages.size() ==0 && !(obj.get("message").getAsString()=="포인트가 부족합니다.")) {
                        pointLackMessages.add("포인트가 부족합니다.");
                        obj.add("data", pointLackMessages);
                    }

                } else if (result == 2) {

                    obj.addProperty("code", "400");
                    if (!(obj.get("message").getAsString()=="이미 보유하고 있는 아이템입니다.") && !(obj.get("message").getAsString()=="구매가 완료되었습니다.") && !(obj.get("message").getAsString()=="포인트가 부족합니다.")  ) {
                        obj.addProperty("message", "이미 보유하고 있는 아이템입니다.");
                   
                    }
                    else if (buySueccessMessages.size() == 0 && pointLackMessages.size() == 0 && alreadyHasMessage.size() ==0 && !(obj.get("message").getAsString()=="이미 보유하고 있는 아이템입니다.")) {

                        alreadyHasMessage.add("이미 보유하고 있는 아이템입니다.");
                        obj.add("data", alreadyHasMessage);
                    }

                }

            }
        }

      catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "구매하기가 실패하였습니다");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }

    @ApiOperation(value = "아이템 버리기")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "itemId", value = "아이템 id", required = false, dataType = "string", paramType = "path"),

    })
    @DeleteMapping("v1/user/{id}/item/{itemId}")
    public String trashItem(@PathVariable Long id, @PathVariable Long itemId) {
        JsonObject obj = new JsonObject();

        try {
            userService.trashItem(id, itemId);

            obj.addProperty("code", "200");
            obj.addProperty("message", "아이템 버리기가 완료되었습니다.");

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "아이템 버리기가 실패하였습니다 ");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }


//    @DeleteMapping("v1/user/{id}/item/{itemId}")
//    public String trashItem(@PathVariable Long id,@RequestBody TrashItemInfo trashItemInfo){
//        JsonObject obj  = new JsonObject();
//
//        try {
//            System.out.println(trashItemInfo.getItemId());
//            userService.trashItem(id,trashItemInfo.getItemId());
//
//            obj.addProperty("code", "200");
//            obj.addProperty("message", "아이템 버리기가 완료되었습니다.");
//
//        }
//        catch (Exception e){
//            obj.addProperty("code", "400");
//            obj.addProperty("message", "아이템 버리기가 실패하였습니다 ");
//
//        }
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(obj);
//
//        return json.toString();
//
//    }


    @ApiOperation(value = "아이템 장착")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),
//            @ApiImplicitParam(name = "itemId", value = "아이템 id", required = false, dataType = "string", paramType = "form"),

    })

    //장비 장착
    @PutMapping("v1/user/{id}/item")
    public String equipItem(@PathVariable Long id, @RequestBody EquipItemInfo equipItemInfo) {
        JsonObject obj = new JsonObject();

        try {

            System.out.println(equipItemInfo.getHairItemId());
            System.out.println(equipItemInfo.getFaceItemId());
            if (equipItemInfo.getHairItemId() != 0)
                userService.equipOnItem(id, equipItemInfo.getHairItemId());
            else
                userService.equipOffItem(id, "hair");

            if (equipItemInfo.getFaceItemId() != 0) {
                userService.equipOnItem(id, equipItemInfo.getFaceItemId());
            } else{

                userService.equipOffItem(id, "face");
          }
            obj.addProperty("code", "200");
            obj.addProperty("message", "상태가 저장되었습니다.");

        } catch (Exception e) {
            obj.addProperty("code", "400");
            obj.addProperty("message", "상태 저장에 실패하였습니다.");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }

    //아이템 소유 정보 조회
    @ApiOperation(value = "아이템 소유 목록 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("/v1/user/{id}/item")
    public String getHasItem(@PathVariable Long id) {

        JsonObject obj = new JsonObject();
//        String filePathName ="/static/img/";
        String path="item/";

        try {
            List<ItemInfo> itemInfos = userService.getHasItem(id);
            JsonArray jAarray = new JsonArray();
            for (int i = 0; i < itemInfos.size(); i++) {
                JsonObject sObj = new JsonObject();
                sObj.addProperty("itemName", itemInfos.get(i).getItemName());
                sObj.addProperty("itemId", itemInfos.get(i).getItemId());
                sObj.addProperty("itemPrice", itemInfos.get(i).getItemPrice().toString());
                sObj.addProperty("itemType", itemInfos.get(i).getItemType());
                sObj.addProperty("itemFilename", path+itemInfos.get(i).getItemFileName());
                jAarray.add(sObj);
            }
            obj.addProperty("code", "200");
            obj.addProperty("message", "아이템 소유 정보 조회가 성공하였습니다.");
            obj.add("data", jAarray);


        } catch (Exception e) {

            obj.addProperty("code", "400");
            obj.addProperty("message", "아이템 소유 정보 조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }


    @ApiOperation(value="전체 랭킹 조회")
    @GetMapping(value="/v1/user/rank/{type}")
    public String getAllRankInfo(@PathVariable Long type){

        JsonObject obj= new JsonObject();


        try{

            if(type==0) {
                List<UserRank> userRanks = userService.getMonthRankAllUserInfo();
                JsonArray jAarray = new JsonArray();
                for (int i = 0; i < userRanks.size(); i++) {
                    JsonObject sObj = new JsonObject();

                    sObj.addProperty("nickName", userRanks.get(i).getNickName());
                    sObj.addProperty("rank", userRanks.get(i).getRank());
                    sObj.addProperty("point", userRanks.get(i).getUserMonthPoint());

                    jAarray.add(sObj);
                }
                obj.addProperty("code", "200");
                obj.addProperty("message", "월별 포인트 전체 랭킹 조회가 성공하였습니다.");
                obj.add("data", jAarray);
            }

            else if(type==1) {

                List<UserRank> userRanks = userService.getAccumulateRankAllUserInfo();
                JsonArray jAarray = new JsonArray();
                for (int i = 0; i < userRanks.size(); i++) {
                    JsonObject sObj = new JsonObject();
                    sObj.addProperty("nickName", userRanks.get(i).getNickName());
                    sObj.addProperty("rank",userRanks.get(i).getRank());
                    sObj.addProperty("point", userRanks.get(i).getTotalAccumulatePoint());
                    jAarray.add(sObj);
                }
                obj.addProperty("code", "200");
                obj.addProperty("message", "누적 포인트 전체 랭킹 조회가 성공하였습니다.");
                obj.add("data", jAarray);


            }

        }catch (Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message","전체 랭킹 조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }


    //월별 포인트 랭킹 조회
//    @ApiOperation(value = "월별 포인트 전체 랭킹 조회")
//    @GetMapping("/v1/user/rank/{type}")
//    public String getMontheRankInfo() {
//
//        JsonObject obj = new JsonObject();
//
//
//        try{
//            List<UserRank> userRanks = userService.getMonthRankAllUserInfo();
//            JsonArray jAarray = new JsonArray();
//            for(int i=0; i<userRanks.size();i++){
//                JsonObject sObj = new JsonObject();
//
//                sObj.addProperty("nickName",userRanks.get(i).getNickName());
//                sObj.addProperty("rank",userRanks.get(i).getRank());
//                sObj.addProperty("monthPoint",userRanks.get(i).getUserMonthPoint());
//
//                jAarray.add(sObj);
//            }
//            obj.addProperty("code","200");
//            obj.addProperty("message","월별 포인트 랭킹 조회가 성공하였습니다.");
//            obj.add("data",jAarray);
//
//
//        }catch (Exception e){
//
//            obj.addProperty("code","400");
//            obj.addProperty("message","월별 포인트 랭킹 조회가 실패하였습니다.");
//
//        }
//
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(obj);
//
//        return json.toString();
//
//    }

    //월별 포인트 랭킹 조회
    @ApiOperation(value = "월별 포인트 개인 랭킹 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),

    })
    @GetMapping("/v1/user/{id}/rank/month")
    public String getMontheRankInfo(@PathVariable Long id) {

        JsonObject obj = new JsonObject();


        try{
            UserRank userRanks = userService.getMonthRankUserInfo(id);
            JsonObject sObj = new JsonObject();
            JsonArray jArray = new JsonArray();

            sObj.addProperty("nickName",userRanks.getNickName());
            sObj.addProperty("rank",userRanks.getRank());
            sObj.addProperty("monthPoint",userRanks.getUserMonthPoint());
            jArray.add(sObj);

            obj.addProperty("code","200");
            obj.addProperty("message","월별 포인트 랭킹 조회가 성공하였습니다.");
            obj.add("data",jArray);


        }catch (Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message","월별 포인트 랭킹 조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }


//    //누적 포인트 랭킹 조회
//    @ApiOperation(value = "누적 포인트 전체 랭킹 조회")
//    @GetMapping("/v1/user/rank/accumulate")
//    public String getAccumulateAllRankInfo() {
//
//        JsonObject obj = new JsonObject();
//
//
//        try {
//            List<UserRank> userRanks = userService.getAccumulateRankAllUserInfo();
//            JsonArray jAarray = new JsonArray();
//            for (int i = 0; i < userRanks.size(); i++) {
//                JsonObject sObj = new JsonObject();
//                sObj.addProperty("nickName", userRanks.get(i).getNickName());
//                sObj.addProperty("rank",userRanks.get(i).getRank());
//                sObj.addProperty("totalAccumulatePoint", userRanks.get(i).getTotalAccumulatePoint());
//                jAarray.add(sObj);
//            }
//            obj.addProperty("code", "200");
//            obj.addProperty("message", "누적 포인트 랭킹 조회가 성공하였습니다.");
//            obj.add("data", jAarray);
//
//
//        } catch (Exception e) {
//
//            obj.addProperty("code", "400");
//            obj.addProperty("message", "누적 포인트 랭킹 조회가 실패하였습니다.");
//
//        }
//
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(obj);
//
//        return json.toString();
//    }


    //누적 포인트 개인 랭킹 조회
    @ApiOperation(value = "누적 포인트 개인 랭킹 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),
    })
    @GetMapping("/v1/user/{id}/rank/accumulate")
    public String getAccumulateRankInfo(@PathVariable Long id ) {

        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();

        try {
            UserRank userRanks = userService.getAccumulateRankUserInfo(id);
            JsonObject sObj = new JsonObject();
            sObj.addProperty("nickName", userRanks.getNickName());
            sObj.addProperty("rank",userRanks.getRank());
            sObj.addProperty("totalAccumulatePoint", userRanks.getTotalAccumulatePoint());

            jArray.add(sObj);
            obj.addProperty("code", "200");
            obj.addProperty("message", "누적 포인트 랭킹 조회가 성공하였습니다.");
            obj.add("data", jArray);


        } catch (Exception e) {

            obj.addProperty("code", "400");
            obj.addProperty("message", "누적 포인트 랭킹 조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();
    }


    @ApiOperation(value = "레벨업 경험치 정보 조회")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "유저 ID", required = true, dataType = "string", paramType = "path"),
    })
    @GetMapping("/v1/user/{id}/exp")
    public String getExpInfo(@PathVariable Long id ) {

        JsonObject obj = new JsonObject();



        try {
            UserExpInfo userExpInfo = userService.getNextLevelNeedExpInfo(id);

            JsonObject sObj = new JsonObject();

            JsonArray jArray = new JsonArray();

            sObj.addProperty("currentLevelNeedExp", userService.getCurrentLevelNeedExpInfo(id).getCurrentLevelNeedExp());
            sObj.addProperty("nextLevelNeedExp", userExpInfo.getNexLevelNeedExp());
            sObj.addProperty("nextLevel", userExpInfo.getNextLevel());
            sObj.addProperty("nextLevelExp",userExpInfo.getNextLevelExp());
            jArray.add(sObj);

            obj.addProperty("code", "200");
            obj.addProperty("message", "레벨업 경험치 정보 조회가 성공하였습니다.");
            obj.add("data", jArray);


        } catch (Exception e) {

            obj.addProperty("code", "400");
            obj.addProperty("message", "레벨업 경험치 정보 조회가 실패하였습니다.");

        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();
    }

}


