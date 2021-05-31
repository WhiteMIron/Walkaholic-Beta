package com.example.demo.walkrecord;

import com.example.demo.walkrecord.model.dto.WalkRecordTotalResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordDateDetailResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordMonthResponse;
import com.example.demo.walkrecord.model.dto.WalkRecordRegister;
import com.example.demo.walkrecord.model.entity.WalkRecord;
import com.example.demo.walkrecord.repositroy.WalkRecordRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalkRecordService {
    private final WalkRecordRepository walkRecordRepository;
//    private User user;

    @PersistenceContext
    EntityManager em;
    public WalkRecordService(WalkRecordRepository walkRecordRepository){
        this.walkRecordRepository = walkRecordRepository;
    }



//    @Transactional
//    public Long recordSave(WalkRecordRegister walkRecordRegister){
//        return walkRecordRepository.save(walkRecordRegister.toEntity()).getId();
//    }
//
////    public List<WalkRecordDateDetailResponse> selectWalkRecordDateDetail(
////            @Param("date") String date,
////            @Param("userId") String user_id)
//
//    @Transactional
//    public List<WalkRecord> selectWalkRecordDateDetail(
//            String date, String userId)
//    {
//
//        List<WalkRecord> walkRecordDateDetailResponses = walkRecordRepository.findByWalkDateAndUser(date,userId);
//        return walkRecordDateDetailResponses;
//    }
//    @Transactional
//    public List<WalkRecord> selectWalkRecordMonth(String year,String month,Long userId){
//
//        List<WalkRecord> walkRecordMonthResponses = walkRecordRepository.findByYearAndMonthAndUser(year,month,userId);
//        return walkRecordMonthResponses;
//    }


//    @Transactional
//    public List<WalkRecordMonthResponse> test(Long userId){
//
//        System.out.println("테스트");
//        WalkRecordMonthResponse walkRecordMonthResponse = walkRecordRepository.findByUser(userId);
////        List<WalkRecordMonthResponse> walkRecordMonthResponses = walkRecordRepository.findByUser(userId);
//        return walkRecordMonthResponse;
//    }


//    @Transactional
//    public WalkRecord test(Long id){
//
//        System.out.println("테스트");
////        Optional<WalkRecord> WalkRecord = walkRecordRepository.findById(id);
//        List<WalkRecord> walkRecords = walkRecordRepository.findByUser(id);
//
//        return walkRecords;
////        return new WalkRecordMonthResponse(WalkRecord.get());
//    }

    @Transactional
    public  List<WalkRecordMonthResponse> getWalkRecordDateList(String year, String month, Long id){

        System.out.println("테스트");
//        Optional<WalkRecord> WalkRecord = walkRecordRepository.findById(id);
        System.out.println(year+" "+month+id);
        List<WalkRecord> walkRecords = walkRecordRepository.findByYearAndMonthAndUser("2021","05",id);
        List<WalkRecordMonthResponse> walkRecordMonthResponses = new ArrayList<>();

        for(WalkRecord walkRecord: walkRecords){
            System.out.println(walkRecord.getWalkCalorie());
            WalkRecordMonthResponse walkRecordMonthResponse = new WalkRecordMonthResponse(walkRecord);
            walkRecordMonthResponses.add(walkRecordMonthResponse);

        }


        return walkRecordMonthResponses;
    }

    @Transactional
    public List<WalkRecordDateDetailResponse> getWalkRecordDateDetailList(String date, Long id){
//
//        System.out.println(walkRecordTotalResponse.getWalkDay());

//        WalkRecordTotalResponse walkRecordTotalResponse = walkRecordRepository.totalRecordByDateAndUser(date,id);


        List<WalkRecord> walkRecords = walkRecordRepository.findByDateAndUser(date,id);
        List<WalkRecordDateDetailResponse> walkRecordDateDetailResponses = new ArrayList<>();

        for(WalkRecord walkRecord: walkRecords){
            WalkRecordDateDetailResponse walkRecordDateDetailResponse = new WalkRecordDateDetailResponse(walkRecord);
            walkRecordDateDetailResponses.add(walkRecordDateDetailResponse);
        }

        return walkRecordDateDetailResponses;

    }

    @Transactional
    public WalkRecordTotalResponse getWalkRecordTotal(String date, Long id){
        return walkRecordRepository.totalRecordByDateAndUser(date,id);
    }

    @Transactional
    public void putWalkReocord(Long userId, WalkRecordRegister walkRecordRegister, String filename){
        String walk_date = walkRecordRegister.getWalkDate();
        Float walk_distance = walkRecordRegister.getWalkDistance();
        Long walk_count = walkRecordRegister.getWalkCount(),walkCalorie= walkRecordRegister.getWalkCalorie();
        String walkStratTime = walkRecordRegister.getWalkStartTime(),walkEndTime=walkRecordRegister.getWalkEndTime(),walkTime=walkRecordRegister.getWalkTime();

        walkRecordRepository.saveWalkRecord(walk_date,walk_distance,walkTime,walk_count,filename,walkStratTime,walkEndTime,walkCalorie,userId);

    }

//    WalkRecordTotalResponse walkRecordTotalResponse = walkRecordRepository.totalRecordByDateAndUser(date,id);

}
