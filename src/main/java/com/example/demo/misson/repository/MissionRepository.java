package com.example.demo.misson.repository;

import com.example.demo.misson.model.dto.MissonRewardinfo;
import com.example.demo.misson.model.dto.MissionProgressInfo;
import com.example.demo.misson.model.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Long> {


    //미션 진행과정 조회
//    @Query(value="SELECT  mission_id as MissionId, mission_name as MissionName, complete_yn as CompleteYN, current_achievement as CurrentAchievement FROM mission_progress mp LEFT OUTER JOIN mission m " +
//            " ON mp.mission_id = m.id left outer join user u ON u.id =mp.user_id where u.id=:id and m.mission_type= :missionType" ,nativeQuery = true)
//      List<MissionProgressInfo> findMissionProgressInfoByUserId(Long id,Long missionType);

//    @Query(value="SELECT mission_reward_point as RewardPoint, mission_id as MissionId, mission_name as MissionName, complete_yn as CompleteYN, current_achievement as CurrentAchievement FROM mission_progress mp LEFT OUTER JOIN mission m " +
//            " ON mp.mission_id = m.id left outer join user u ON u.id =mp.user_id where u.id=:id and m.mission_type= :missionType" ,nativeQuery = true)
//    List<MissionProgressInfo> findMissionProgressInfoByUserId(Long id, Long missionType);

//
//    Long getMissionName();
//    Long getRewardPoint();
//    Long getRewardExp();

    @Query(value="select mission_name as MissionName, mission_reward_point as RewardPoint from mission where id=:missionId ", nativeQuery = true)
    MissonRewardinfo findMissionInfobyMissionId(Long missionId);


    @Query(value="select mp.mission_id as MissionId, mp.user_id as UserId, m.mission_reward_point as MissionRewardPoint " +
            ", u.walk_count as CurrentAchievement, mp.complete_yn as CompleteYN FROM  mission_progress mp " +
            "left outer join mission_condition mc ON mp.mission_id = mc.mission_id left outer join mission m ON mc.mission_id = m.id" +
            " left outer join user u ON mp.user_id = u.id  where  m.id=:missionId and mp.user_id=:id"
            ,nativeQuery = true)
    MissionProgressInfo findDailyMissionProgressInfoByUserIdAndMissionId(Long id, Long missionId);


    @Query(value="select mp.mission_id as MissionId, mp.user_id as UserId, m.mission_reward_point as MissionRewardPoint " +
            ", u.weekly_mission_achievement as CurrentAchievement, mp.complete_yn as CompleteYN FROM  mission_progress mp " +
            "left outer join mission_condition mc ON mp.mission_id = mc.mission_id left outer join mission m ON mc.mission_id = m.id" +
            " left outer join user u ON u.id = mp.user_id where  m.id=:missionId and mp.user_id=:id"
            ,nativeQuery = true)
    MissionProgressInfo findWeelkyMissionProgressInfoByUserIdAndMissionId(Long id, Long missionId);




    @Query(value="SELECT mission_reward_point as MissionRewardPoint, mission_id as MissionId, mission_name as MissionName, " +
            " mp.complete_yn as CompleteYN ,mp.take_reward_yn as takeRewardYN, u.walk_count as CurrentAchievement, u.weekly_mission_achievement as CurrentWeeklyAchievement  FROM mission_progress mp LEFT OUTER JOIN mission m " +
            " ON mp.mission_id = m.id left outer join user u ON u.id =mp.user_id where u.id=:id and m.mission_type= :missionType" ,nativeQuery = true)
    List<MissionProgressInfo> findDailyMissionProgressInfosByUserIdAndMissionType(Long id, Long missionType);


    @Query(value="SELECT mission_reward_point as MissionRewardPoint, mission_id as MissionId, mission_name as MissionName, " +
            "mp.complete_yn as CompleteYN, mp.take_reward_yn as takeRewardYN, u.weekly_mission_achievement as CurrentAchievement, FROM mission_progress mp LEFT OUTER JOIN mission m " +
    " ON mp.mission_id = m.id left outer join user u ON u.id =mp.user_id where u.id=:id and m.mission_type= :missionType" ,nativeQuery = true)
    List<MissionProgressInfo> findWeeklyMissionProgressInfosByUserIdAndMissionType(Long id, Long missionType);





    @Query(value="Update mission_progress set mission_progress.complete_yn =1 where user_id = :uId and mission_id=:mId", nativeQuery=true)
    void completeMissionUpdate(Long uId,Long mId);


//    @Query(value="update mission_progress set  mission_progress.current_achievement = mission_progress.current_achievement +:current_achievement where mission_progress.user_id=:uId" +
//            " and mission_progress.mission_id=:mId and mission_progress.complete_yn=0",nativeQuery = true)
//    void currentAchievementUpdate(Long uId,Long mId, Long current_achievement);

    @Query(value="update user set  walk_count = walk_count +:current_achievement where id=:uId",nativeQuery = true)
    void currentDailyAchievementUpdate(Long uId, Long current_achievement);


    @Query(value="update user set  weekly_mission_achievement = weekly_mission_achievement +:current_achievement where id=:uId",nativeQuery = true)
    void currentWeeklyAchievementUpdate(Long uId, Long current_achievement);



    @Query(value="Update mission_progress set  mission_progress.current_achievement =0,  mission_progress.complete_yn=0",nativeQuery = true)
    void inititalMissonProgressUpdate();


    //미션완료비교조회 타입줘서 해야겠군


//    @Query(value="select count(*) as ResultRow , mp.mission_id as MissionId, mp.user_id as UserId FROM  mission_progress mp " +
//        "left outer join mission_condition mc ON mp.mission_id = mc.mission_id left outer join mission m ON mc.mission_id = m.id" +
//        " where  mp.current_achievement = mc.mission_condition_value or mp.current_achievement = mc.mission_condition_value And mp.user_id=:uId and m.mission_type =:missionType" +
//        " and mp.user_id=:uId"
//        ,nativeQuery = true)
//    List<MissionCompleteInfo cmpCompleteResult(Long missionType, Long uId);

                                                            
    @Query(value="select count(*) as ResultRow , mp.mission_id as MissionId, mp.user_id as UserId, m.mission_reward_point as MissionRewardPoint " +
            ", mp.current_achievement as CurrentAchievement, mp.complete_yn as CompleteYN FROM  mission_progress mp " +
            "left outer join mission_condition mc ON mp.mission_id = mc.mission_id left outer join mission m ON mc.mission_id = m.id"
           +" left outer join user u ON mp.user_id = u.id"+
            " where  m.id=:missionId and mp.user_id=:uId and (u.walk_count= mc.mission_condition_value  or  u.walk_count > mc.mission_condition_value) "
            ,nativeQuery = true)
    MissionProgressInfo cmpDailyCompleteResult(Long missionId, Long uId);



    @Query(value="select count(*) as ResultRow , mp.mission_id as MissionId, mp.user_id as UserId, m.mission_reward_point as MissionRewardPoint " +
            ", mp.current_achievement as CurrentAchievement, mp.complete_yn as CompleteYN FROM  mission_progress mp " +
            "left outer join mission_condition mc ON mp.mission_id = mc.mission_id left outer join mission m ON mc.mission_id = m.id"
            +" left outer join user u ON mp.user_id = u.id"+
            " where  m.id=:missionId and mp.user_id=:uId and (u.weekly_mission_achievement= mc.mission_condition_value  or  u.weekly_mission_achievement > mc.mission_condition_value) "
            ,nativeQuery = true)
    MissionProgressInfo cmpWeeklyCompleteResult(Long missionId, Long uId);



    //미션완료를 통한  포인트 증감
    @Query(value="Update user SET current_point = user.current_point+(select m.mission_reward_point from mission m where m.id =:missionId) where user.id=:userId", nativeQuery = true)
    void PlusUserPointAndExpByMission (Long userId,Long missionId);

//    포인트 경험치 증가
//    @Query(value="Update user SET user.current_point = +(select m.mission_reward_point from mission m where m.id =:missionId and user.id=:userId"+
//            ", user.current_exp = +(select m.mission_reward_exp from mission m where m.id=:missionId and user.id=:userId)"
//            , nativeQuery = true)
//    void PlusUserPointAndExpByMission (Long userId,Long missionId);
//

    //걸었을 시 일정 걷기수마다 증감
    @Query(value="Update user SET current_point = current_point+(5*:walkCount) , current_exp=current_exp+100 WHERE id =:userId", nativeQuery = true)
    void PlusUserPointAndExpByWalk(Long userId,Long walkCount);


    //레벨 업
    @Query(value="update user SET user.level_id = (select l.id from level l where l.need_exp= user.current_exp OR " +
            " l.need_exp < user.current_exp and user.id=:userId Order by l.id DESC LIMIT 1)",nativeQuery = true)
    void UpdateLevel(Long userId);



    @Query(value="select mp.mission_id as MissionId ,m.mission_reward_point as MissionRewardPoint from mission_progress mp " +
            "left outer join mission m ON mp.mission_id =m.id" +
            " where mp.user_id = :id and mp.take_reward_yn =0 and m.mission_type=:missionType" +
            " and mp.complete_yn=1"
            ,nativeQuery = true)
    List<MissionProgressInfo> findTakeRewardYNbyIdAndType(Long id,Long missionType);

    @Query(value="select count(*) as ResultRow from mission_progress where user_id=:userId and mission_id=:missionId and complete_yn=1",nativeQuery = true)
    MissionProgressInfo findNotTakeRewardYNbyUserIdAndMissionId(Long userId, Long missionId);



    @Query(value="update mission_progress set  take_reward_yn =1 where mission_progress.user_id=:userId and mission_progress.mission_id=:missionId",nativeQuery = true)
    void setTakeRewarYNbyIdandMissionId(Long userId, Long missionId);
}
