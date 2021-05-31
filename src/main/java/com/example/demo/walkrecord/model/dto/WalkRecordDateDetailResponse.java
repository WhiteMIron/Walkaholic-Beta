package com.example.demo.walkrecord.model.dto;

import com.example.demo.walkrecord.model.entity.WalkRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkRecordDateDetailResponse {

    @ApiModelProperty(notes = "산책일자", required = true)
    private String  walkDate;

    @ApiModelProperty(notes = "산책거리", required = true)
    private Float walkDistance;

    @ApiModelProperty(notes = "산책시작시간", required = true)
    private String walkStartTime;

    @ApiModelProperty(notes = "산책종료시간", required = true)
    private String walkEndTime;

    @ApiModelProperty(notes = "산책시간", required = true)
    private String walkTime;

    @ApiModelProperty(notes = "걸음수", required = true)
    private Long walkCount;

    @ApiModelProperty(notes = "소모칼로리", required = true)
    private Long walkCalorie;

    @ApiModelProperty(notes = "산책경로이미지", required = true)
    private String walkRecordFilename;



    public WalkRecordDateDetailResponse(WalkRecord walkRecord) {
//        this.id = walkRecord.getId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

        this.walkDate = simpleDateFormat.format(walkRecord.getWalkDate());
        this.walkDistance = walkRecord.getWalkDistance();
        this.walkStartTime = simpleTimeFormat.format(walkRecord.getWalkStartTime());
        this.walkEndTime = simpleTimeFormat.format(walkRecord.getWalkEndTime());
        this.walkTime = simpleTimeFormat.format(walkRecord.getWalkTime());
        this.walkCount = walkRecord.getWalkCount();
        this.walkCalorie = walkRecord.getWalkCalorie();
        this.walkRecordFilename = walkRecord.getWalkRecordFilename();
    }
}
