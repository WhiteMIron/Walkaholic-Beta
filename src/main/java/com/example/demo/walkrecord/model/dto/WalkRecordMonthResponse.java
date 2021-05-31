package com.example.demo.walkrecord.model.dto;

import com.example.demo.walkrecord.model.entity.WalkRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WalkRecordMonthResponse {
//    private Long id;

    private Long id;
    private String walkYear;
    private String walkMonth;
    private String walkDate;

    public WalkRecordMonthResponse(WalkRecord walkRecord) {
        this.id = walkRecord.getId();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        this.walkYear = simpleDateFormat.format(walkRecord.getWalkDate());

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM");
        this.walkMonth = simpleDateFormat2.format(walkRecord.getWalkDate());

        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("dd");
        this.walkDate = simpleDateFormat3.format(walkRecord.getWalkDate());



    }
}
