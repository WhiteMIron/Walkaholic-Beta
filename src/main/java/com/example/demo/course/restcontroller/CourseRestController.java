package com.example.demo.course.restcontroller;

import com.example.demo.course.service.CourseService;
import com.example.demo.course.model.dto.CourseThemeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(value="api")
@Log4j2
public class CourseRestController {
    private final CourseService courseService;

    public CourseRestController(CourseService courseService){
        this.courseService = courseService;
    }


    @ApiOperation(value = "테마별 리스트 조회")
    @ApiImplicitParams({
          @ApiImplicitParam(name = "themeCode", value = "일자 ex)000,001,002,003 부가 설명:000-테마x 001-힐링,002-데이트,003-운동", required = true, dataType = "string", paramType = "query")
    })

    @GetMapping(value="/v1/course/theme",produces = "application/json; charset=UTF-8")
    public String getThemeListInfo(@RequestParam String themeCode){

        JsonObject obj = new JsonObject();
        SimpleDateFormat timeformat= new SimpleDateFormat("HH");

        try {
            List<CourseThemeInfo> courseThemaInfos = courseService.getCourseThemeInfos(themeCode);
            JsonArray jArray = new JsonArray();
            String path = "course/";

            if(courseThemaInfos.size()!=0) {


                for (int i = 0; i < courseThemaInfos.size(); i++) {
                    JsonObject sObj = new JsonObject();
                    sObj.addProperty("courseId", courseThemaInfos.get(i).getCourseId());
                    sObj.addProperty("courseName", courseThemaInfos.get(i).getCourseName());
                    sObj.addProperty("courseTitle", courseThemaInfos.get(i).getCourseTitle());
                    sObj.addProperty("courseAddress", courseThemaInfos.get(i).getCourseAddress());
                    sObj.addProperty("courseFilename", path+courseThemaInfos.get(i).getCourseFileName());
                    sObj.addProperty("courseTime", timeformat.format(courseThemaInfos.get(i).getCourseTime()));
                    sObj.addProperty("courseDistance", courseThemaInfos.get(i).getCourseDistance().toString());
                    //                sObj.addProperty("coursePoint", courseThemaInfos.get(i).getCoursePoint().toString());
                    jArray.add(sObj);
                }

                obj.addProperty("code", 200);
                obj.addProperty("message", "테마정보 조회가 성공하였습니다.");
                obj.add("data", jArray);
            }
            else {

                obj.addProperty("code", 400);
                obj.addProperty("message", "테마정보 조회가 실패하였습니다.");

            }
        } catch (Exception e){

                obj.addProperty("code","400");
                obj.addProperty("message","테마정보 조회가 실패하였습니다");

            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(obj);

            return json.toString();

    }

    @ApiOperation(value = "테마코스 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "코스 ID", required = true, dataType = "string", paramType = "path"),
    })

    @GetMapping(value="/v1/course/theme/{id}")
    public String getCourseInfo(@PathVariable Long id){

        JsonObject obj = new JsonObject();
        SimpleDateFormat timeformat= new SimpleDateFormat("HH");
        SimpleDateFormat timeformat2= new SimpleDateFormat("mm");


       try {
            CourseThemeInfo courseThemaInfo = courseService.getCourseThemaInfo(id);

            String hour=timeformat.format(courseThemaInfo.getCourseTime());
            String minute=timeformat2.format(courseThemaInfo.getCourseTime());

            if(hour.indexOf("0")==0){
                log.info("시간 앞자리");
                hour=hour.substring(1,2);
            }

            if(minute.indexOf("0")==0){
                log.info("분 앞자리");
                minute=minute.substring(1,2);
            }

           log.info("값 확인:{}",hour);
           log.info("값 확인:{}",minute);

           JsonObject sObj = new JsonObject();


                sObj.addProperty("courseId", courseThemaInfo.getCourseId());
                sObj.addProperty("courseName", courseThemaInfo.getCourseName());
//                sObj.addProperty("courseTitle", courseThemaInfo.getCourseTitle());
                sObj.addProperty("courseContents",courseThemaInfo.getCourseContents());
                sObj.addProperty("courseRouteInfo",courseThemaInfo.getCourseRouteInfo());
                sObj.addProperty("courseAddress", courseThemaInfo.getCourseAddress());
                sObj.addProperty("courseFilename", courseThemaInfo.getCourseFileName());
                if(hour.indexOf("0")==0)
                    sObj.addProperty("courseTime", minute+"분");
                else if(minute.indexOf("0")==0)
                    sObj.addProperty("courseTime", hour+"시간");
                else
                    sObj.addProperty("courseTime", hour+"시간"+minute+"분");


                sObj.addProperty("courseDistance", courseThemaInfo.getCourseDistance().toString());
                sObj.addProperty("coursePoint", "임시용 더미 데이터");

                obj.addProperty("code", 200);
                obj.addProperty("message", "테마정보 조회가 성공하였습니다.");
                obj.add("data", sObj);


            }catch (Exception e){

            obj.addProperty("code","400");
            obj.addProperty("message","테마정보 조회가 실패하였습니다");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        return json.toString();

    }

    @ApiOperation(value = "테마코스 경로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "코스 타입", required = true, dataType = "string", paramType = "path"),
    })

    @GetMapping("/v1/course/theme/route/{type}")
    public String getTemeRoute(@PathVariable String type){
        JsonObject obj = new JsonObject();

        try {
            List<CourseThemeInfo> courseThemeInfos = courseService.getThemePoint(type);
            JsonArray jAarray = new JsonArray();

            for(int i=0; i<courseThemeInfos.size();i++){


                JsonObject sObj=new JsonObject();
                sObj.addProperty("name",courseThemeInfos.get(i).getCourseName());
                sObj.addProperty("x",courseThemeInfos.get(i).getLatitude());
                sObj.addProperty("y",courseThemeInfos.get(i).getLongitude());
                jAarray.add(sObj);
            }

            obj.addProperty("code","200");
            obj.addProperty("message","경로 조회가 성공하였습니다.");
            obj.add("data",jAarray);
        }
        catch(Exception e){
            obj.addProperty("code","400");
            obj.addProperty("message","경로 조회가 실패하였습니다.");
        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(obj);

        return json;

    }

    @ApiOperation(value = "코스 추천 경로 조회")


    @GetMapping("/v1/course/route/")
    public String getCourseRoute(){


        JsonObject obj = new JsonObject();
        JsonArray jArray = new JsonArray();
        JsonArray jArray2 = new  JsonArray();

        try {
            List<CourseThemeInfo> courseThemeInfos1 = courseService.getCourseList();

            SimpleDateFormat timeformat= new SimpleDateFormat("HH");
            SimpleDateFormat timeformat2= new SimpleDateFormat("mm");

            for(int i=0;i <courseThemeInfos1.size(); i++) {



                Long id = courseThemeInfos1.get(i).getCourseId();

                JsonObject sObj = new JsonObject();
                sObj.addProperty("name",courseThemeInfos1.get(i).getCourseName());
                sObj.addProperty("address",courseThemeInfos1.get(i).getCourseAddress());
                sObj.addProperty("filename",courseThemeInfos1.get(i).getCourseFileName());

                String hour=timeformat.format(courseThemeInfos1.get(i).getCourseTime());
                String minute=timeformat2.format(courseThemeInfos1.get(i).getCourseTime());


                if(hour.indexOf("0")==0)
                    sObj.addProperty("courseTime", minute+"분");
                else if(minute.indexOf("0")==0)
                    sObj.addProperty("courseTime", hour+"시간");
                else
                    sObj.addProperty("courseTime", hour+"시간"+minute+"분");

                List<CourseThemeInfo> courseThemeInfos = courseService.getCoursePoint(id);

                //코스 경유지
                for (int j = 0; j < courseThemeInfos.size(); j++) {
                    JsonObject tObj = new JsonObject();
                    tObj.addProperty("name", courseThemeInfos.get(i).getCourseName());
                    tObj.addProperty("x", courseThemeInfos.get(i).getLatitude());
                    tObj.addProperty("y", courseThemeInfos.get(i).getLongitude());
                    tObj.addProperty("address", courseThemeInfos.get(i).getCourseAddress());
                    jArray2.add(tObj);
                }
                sObj.add("stopoverData",jArray2);
                jArray.add(sObj);

            }



            obj.addProperty("code", "200");
            obj.addProperty("message", "코스경로 조회가 성공하였습니다.");
            obj.add("data", jArray);
        }catch (Exception e){
            obj.addProperty("code","400");
            obj.addProperty("message","경로 조회가 실패하였습니다.");

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        return json;
    }
}
