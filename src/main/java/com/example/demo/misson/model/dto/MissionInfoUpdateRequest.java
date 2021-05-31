package com.example.demo.misson.model.dto;



import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class MissionInfoUpdateRequest {
    private Long currentAchievement;
    private Long missionId;
}
