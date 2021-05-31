package com.example.demo.walkrecord.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalkRecordRegister {

    private String walkDate;
    private String walkStartTime;
    private String walkEndTime;
    private String walkTime;
    private Float walkDistance;
    private Long walkCount;
    private Long walkCalorie;

//
//    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//    DateFormat timeFomat  = new SimpleDateFormat("HH:MM");
//
//    Date date = dateFormat.parse(walkDate);
//    Date startTime = dateFormat.parse(walkStartTime);
//    Date endTime = dateFormat.parse(walkEndTIme);

//    Date walkDate =(Date) walkDate;
//    @Builder
//    public WalkRecord toEntity(){
//        WalkRecord build = WalkRecord.builder()
//                .walkDate(walkDate)
//                .walkDistance(walkDistance)
//                .walkTime(walkTime)
//                .walkStartTime(walkStartTime)
//                .walkEndTime(walkEndTime)
//                .walkCount(walkCount)
//                .walkCalorie(walkCalorie)
//                .walkRecordFilename(walkRecordFilename)
//                .build();
//
//        return build;
//    }

}
