package com.example.demo.walkrecord.model.dto;

import java.sql.Time;

public interface WalkRecordDateDetail {

    String  getWalkDate();
    Double getWalkDistance();
    Time getWalkStartTime();
    Time getWalkEndTime();
    Time getWalkTakenTime();
    Long getWalkCount();
    Long getWalkCalorie();
    String getWalkRecordFilename();

}
