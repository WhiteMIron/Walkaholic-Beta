package com.example.demo.walkrecord.model.dto;

import lombok.Builder;
import lombok.Getter;

public interface WalkRecordTotalResponse {
    String getWalkDate();
    String getWalkDay();
    Double getWalkDistance();
    String getTotalWalkTime();
    String getTotalWalkCount();
    String getTotalWalkCaloire();


}

