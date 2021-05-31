package com.example.demo.item.restcontroller;

import com.example.demo.item.service.ItemService;
import com.example.demo.item.model.dto.ItemInfo;
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
public class ItemRestController {

    private final ItemService itemService;

    public ItemRestController (ItemService itemService){
        this.itemService = itemService;
    }

    @ApiOperation(value = "상점 판매 아이템 목록 조회")

    @GetMapping("v1/item")
    public String getInfoShopItemList(){
        JsonObject obj = new JsonObject();

        try {

            String path = "item/";

            JsonArray jArray = new JsonArray();

            List<ItemInfo> itemInfos = itemService.getAllItemInfo();

            for (int i = 0; i < itemInfos.size(); i++) {
                JsonObject sObject = new JsonObject();
                sObject.addProperty("itemName", itemInfos.get(i).getItemName());
                sObject.addProperty("itemPrice",itemInfos.get(i).getItemPrice());
                sObject.addProperty("itemType", itemInfos.get(i).getItemType());
                sObject.addProperty("itemId", itemInfos.get(i).getItemId());
                sObject.addProperty("itemFilename", path+itemInfos.get(i).getItemFileName());

                jArray.add(sObject);
            }
            obj.addProperty("code","200");
            obj.addProperty("message"," 아이템 조회가 성공하였습니다");
            obj.add("data",jArray);


        }catch (Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message"," 아이템 조회가 실패하였습니다");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();


    }

    @ApiOperation(value = "아이템 정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "아이템 ID", required = true, dataType = "string", paramType = "path"),
    })
    @GetMapping("v1/item/{id}")
    public String getInfoItem(@PathVariable String id){
        JsonObject obj = new JsonObject();

        try {



            ItemInfo itemInfo = itemService.getItemInfo(id);

            JsonObject sObject = new JsonObject();
            sObject.addProperty("itemName", itemInfo.getItemName());
            sObject.addProperty("itemPrice",itemInfo.getItemPrice());
            sObject.addProperty("itemType", itemInfo.getItemType());
            sObject.addProperty("itemId", itemInfo.getItemId());
            sObject.addProperty("itemFilename",itemInfo.getItemFileName());

            obj.addProperty("code","200");
            obj.addProperty("message"," 아이템 조회가 성공하였습니다");
            obj.add("data",sObject);


        }catch (Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message"," 아이템 조회가 실패하였습니다");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();
    }


}
