package com.example.demo.user.model.dto;

import java.util.Date;

public interface UserDashBoardDto {

    Long getId();
    String getNickName();
    String getGender();
    Long getCurrentExp();
    Long getCurrentPoint();
    Long getMonthPoint();
    Long getTotalAccmulatePoint();
    Long getWalkCount();
    Float getWalkDistance();
    String getBirth();
//    Date getUserBirth();
    Date getConnectedDate();
    String getWeight();
    String getHeight();
    Long getPetId();
    Long getLevelId();
    String getPetName();
    Long getWeeklyMissionAchievement();

//
//
//    private Long uId;
//    private String userNickName;
//    private Long currentExp;
//    private Long walkCount;
    //    private Float walkDistance;
    //    private Date connectDate;
//    private Pet petId
//    private Level levelId;
//    private String gender;
//    private String weight;
//    private String height;


//
//    @Builder
//    public UserDashBoardDto( String userNickName,Long currentExp,Long walkCount, Float walkDistance, Date connectDate,
//                            Level level,List<String> characterFileName,String gender, String weight, String height){
//
//        this.userNickName = userNickName;
//        this.currentExp = currentExp;
//        this.walkCount = walkCount;
//        this.walkDistance = walkDistance;
//        this.connectDate = connectDate;
//        this.level = level;
//
//        this.weight = weight;
//        this.height = height;
//
//    }
//
//
//    public UserDashBoardDto(User user) {
//        this.userNickName = user.getUserNickName();
//        this.currentExp = user.getCurrentExp();
//        this.walkCount = user.getWalkCount();
//        this.walkDistance = user.getWalkDistance();
//        this.connectDate = user.getConnectedAt();
//        this.level = user.getLevel();
//        this.character
//        this.weight = weight;
//        this.height = height;
//    }
}
