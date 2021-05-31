package com.example.demo.walkrecord.model.dto;

import java.sql.Time;
import java.util.Date;

public interface walkRecordUploadRequest {

    Date walkDate();
    Time walkStartTime();
    Time walkEndTime();
    Time walkTime();
    Float walkDistance();
    Long walkCount();
    Long walkCalorie();
    String walkFileName();
}
