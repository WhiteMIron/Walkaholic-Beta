package com.example.demo.user.model.entity;


import com.example.demo.level.model.entity.Level;
import com.example.demo.pet.model.entity.Pet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table( name = "user")
@Builder
public class User {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Long id;
//    @Column(name="user_uid")
//    private String userUid;
    @Column(name="nickname")
    private String nickName;
    private String gender;
    @Column(name="current_exp", columnDefinition = "Long default 0")
    private Long currentExp;
    @Column(name="current_point", columnDefinition = "Long default 0")
    private Long currentPoint;
    @Column(name="total_accumulate_point", columnDefinition = "Long default 0")
    private Long totalAccumulatePoint;
    @Column(name="walk_count", columnDefinition = "Long default 0")
    private Long walkCount;
    @Column(name="month_point", columnDefinition = "Long default 0")
    private Long monthPoint;
    @Column(name="walk_distance", columnDefinition = "Decimal default 0.0")
    private Float walkDistance;

    @Column(name="weekly_mission_achievement", columnDefinition = "Long default 0")
    private Long weeklyMissionAcievement;

    @CreatedDate
    @Column(name="user_connected_at")
    private Date connectedAt;
    @CreatedDate
    @Column(name="user_registered_at")
    private Date registeredAt;
    private String height;
    private String weight;

    @Column(name="birth")
    private Date birth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "level_id")
    @JsonBackReference
    private Level levelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn (name = "pet_id")
    private Pet petId;


//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//    @JsonBackReference
//    private List<WalkRecord> walkRecord = new ArrayList<WalkRecord>();;

    public Level getLevel() {
        return levelId;
    }



    @PrePersist
    public void prePersist(){
        this.currentExp = this.currentExp ==null ? 0:this.currentExp;
        this.currentPoint = this.currentPoint ==null ? 0:this.currentPoint;
        this.totalAccumulatePoint = this.totalAccumulatePoint ==null ? 0:this.totalAccumulatePoint;
        this.walkCount = this.walkCount ==null ? 0:this.walkCount;
        this.weeklyMissionAcievement =this.weeklyMissionAcievement == null ? 0: this.weeklyMissionAcievement;
        this.monthPoint = this.monthPoint ==null ? 0:this.monthPoint;
        this.walkDistance = this.walkDistance == null ? 0.0F: this.walkDistance;

    }

}
