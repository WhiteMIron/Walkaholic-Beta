package com.example.demo.misson.service;

import com.example.demo.misson.model.dto.MissonRewardinfo;
import com.example.demo.misson.model.dto.MissionProgressInfo;
import com.example.demo.misson.repository.MissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
public class MissionService {

    //    List<MissionProgressInfo> findMissionProgressInfoByUserId(Long id,Long missionType);

    //    void completeMissionUpdate(Long uId,Long mId);

    //    void currentAchievementUpdate(Long uId, Long currentAchievement,Long mId);

    //      void inititalMissonProgressUpdate();
    //  Long cmpCompleteResult(Long mId, Long uId);


    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }


    //    //미션 진행정보 조회
    @Transactional
    public MissionProgressInfo getDailyMissionProgressInfo(Long id, Long missionId) {
        return missionRepository.findDailyMissionProgressInfoByUserIdAndMissionId(id, missionId);

    }

    public MissionProgressInfo getWeeklyMissionProgressInfo(Long id, Long missionId) {
        return missionRepository.findWeelkyMissionProgressInfoByUserIdAndMissionId(id, missionId);

    }

    @Transactional
    public List<MissionProgressInfo> geDailyMissionProgressInfos(Long id, Long missonType) {
        return missionRepository.findDailyMissionProgressInfosByUserIdAndMissionType(id, missonType);

    }


    @Transactional
    public List<MissionProgressInfo> getWeelkyMissionProgressInfos(Long id, Long missionType) {
        return missionRepository.findWeeklyMissionProgressInfosByUserIdAndMissionType(id, missionType);

    }


    @Transactional
    public List<MissionProgressInfo> getNotTakeRewardInfos(Long id, Long missionType){
        return missionRepository.findTakeRewardYNbyIdAndType(id,missionType);
    }

    @Transactional
    public MissionProgressInfo getNotTakeRewardInfo(Long id, Long missionId){
        return missionRepository.findNotTakeRewardYNbyUserIdAndMissionId(id,missionId);
    }


    @Transactional
    public MissonRewardinfo getRewardInfo(Long missionId){
        return missionRepository.findMissionInfobyMissionId(missionId);
    }


//    미션완료 갱신
//    @Transactional
//    public void setCompleteMissionUpdate(Long uId, Long mId){
//
//        missionRepository.completeMissionUpdate(uId,mId);
//
//    }

    //성취도 갱신 + 미션완료 비교 + 미션완료 갱신


    @Transactional
    public void setCurrentDailyAchievementUpdate(Long uId, Long currentAchievement) {
        missionRepository.currentDailyAchievementUpdate(uId, currentAchievement);

    }


    @Transactional
    public void setCurrentWeeklyAchievementUpdate(Long uId, Long currentAchievement) {
        missionRepository.currentWeeklyAchievementUpdate(uId, currentAchievement);

    }



    @Transactional
    public MissionProgressInfo cmpDailyMissionComplete(Long uId, Long mId,Long currentAchievement) {
        MissionProgressInfo missionProgressInfo = missionRepository.cmpDailyCompleteResult(mId, uId);
        String result = missionProgressInfo.getResultRow();

        log.info("result:{}", result);
        log.info("currentAchievement:{}", currentAchievement);


        if (result.equals("1")) {
            log.info("이거 수행됨");
            missionRepository.completeMissionUpdate(uId, mId);

        }
        missionProgressInfo = missionRepository.findDailyMissionProgressInfoByUserIdAndMissionId(uId, mId);

        return missionProgressInfo;
    }

    @Transactional
    public MissionProgressInfo cmpWeeklyMissionComplete(Long uId, Long mId,Long currentAchievement) {
        MissionProgressInfo missionProgressInfo = missionRepository.cmpWeeklyCompleteResult(mId, uId);
        String result = missionProgressInfo.getResultRow();


        log.info("result:{}", result);
        log.info("currentAchievement:{}", currentAchievement);


        if (result.equals("1")) {
            log.info("이거 수행됨");
            missionRepository.completeMissionUpdate(uId, mId);

        }
        missionProgressInfo = missionRepository.findWeelkyMissionProgressInfoByUserIdAndMissionId(uId, mId);

        return missionProgressInfo;
    }





    //일일 미션 갱신 <- 일일/주간 구분 둬야할듯 ( 미션 타입 값을 인자로 넣어주는건 어떨까?)
    @Transactional
    public void setInitalMissionProgressUpdate(){
        missionRepository.inititalMissonProgressUpdate();
    }

//    //미션완료 됐는지 비교
//    @Transactional
//    public List<MissionCompleteInfo> getCmpCompleteResult(Long mId, Long uId){
//        return missionRepository.cmpCompleteResult(mId, uId);
//    }

    @Transactional
    public void takeMissionReward(Long userId, Long missionId) {

         missionRepository.PlusUserPointAndExpByMission( userId,missionId);
         missionRepository.setTakeRewarYNbyIdandMissionId(userId,missionId);
    }



    //걸었을 시 일정 걷기수마다 증감
    @Transactional
    public void  takeWalkReward(Long userId,Long walkCount){
        missionRepository.PlusUserPointAndExpByWalk(userId,walkCount);
    }

    @Transactional
    public void userLevelUp(Long userId){
        missionRepository.UpdateLevel(userId);
    }

}
