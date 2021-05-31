package com.example.demo.walkrecord.repositroy;

import com.example.demo.walkrecord.model.dto.WalkRecordTotalResponse;
import com.example.demo.walkrecord.model.entity.WalkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalkRecordRepository extends JpaRepository<WalkRecord,Long> {
//
//    List<WalkRecord> findByWalkDateAndUser(String walkDate, String userId);

//    SELECT * FROM test WHERE regdate LIKE '%-05-%';
//
    @Query(value="select * from walk_record w where w.user_id = :userId and walk_date  like concat(:year,'%-',:month,'-%')" , nativeQuery =true)
    List<WalkRecord> findByYearAndMonthAndUser(String year,String month, Long userId);
//
//    w.walk_date, TIME_FORMAT(w.walk_start_time,'%H:%i'), TIME_FORMAT(w.walk_end_time,'%H:%i)', TIME_FORMAT(w.walk_time,'%H:%i')" +
//            ",w.walk_distance, w.walkCalorie,w.walk_filename, w.user_id

//    select id,DATE_FORMAT(w.walk_date,'%y.%m.%d') as walk_date, TIME_FORMAT(w.walk_time,'%H:%i') as walk_time,TIME_FORMAT(w.walk_start_time,'%H:%i') as walk_start_time ,TIME_FORMAT(w.walk_end_time,'%H:%i') as walk_end_time"+
//            ",walk_distance, walk_calorie,walk_record_filename, user_id, walk_count
    @Query(value="select * from walk_record w where w.user_id = :userId and walk_date like :date"
            ,nativeQuery =true)
    List<WalkRecord> findByDateAndUser(String date,Long userId);

    @Query(value="SELECT DATE_FORMAT(w.walk_date, '%Y%m%d\') as walkDate ,case DAYOFWEEK(w.walk_date) when '1' then '일' when '2' then '월' when '3' then '화' when '4' then '수' when '5' then '목' when '6' then '금' when '7' then '토' end as walkDay," +
            "Round(sum(w.walk_distance),1) as walkDistance ,TIME_FORMAT(SEC_TO_TIME(sum(TIME_TO_SEC(TIMEDIFF(w.walk_end_time,w.walk_start_time)))),'%H:%i') as totalWalkTime," +
            "SUM(w.walk_count) AS totalWalkCount, SUM(walk_calorie) AS totalWalkCaloire FROM walk_record w WHERE w.walk_date = :date AND w.user_id = :userId"
            ,nativeQuery=true)
    WalkRecordTotalResponse totalRecordByDateAndUser(String date, Long userId);


    @Query(value="Insert into walk_record (walk_date,walk_distance,walk_time,walk_count,walk_record_filename,walk_start_time, walk_end_time,user_id,walk_calorie)" +
            " values(:walkDate,:walkDistance,:walkTime,:walkCount,:walkRecordFilename,:walkStratTime,:walkEndTime,:userId,:walkCalorie)",nativeQuery = true)
    void saveWalkRecord(String walkDate, Float walkDistance,String walkTime, Long walkCount,String walkRecordFilename,String walkStratTime,String walkEndTime,Long walkCalorie, Long userId);


//    @Query(value="select w.walk_date as WalkDate, w.walk_distance as walkDistance, w.walk_count as walkCount, w.walk_" +
//            "  from walk_record w where w.user_id = :userId and walk_date like :date" , nativeQuery =true)
//    List<WalkRecordDateDetail> findtest(String date,Long userId);
//    WalkRecordMonthResponse findByLong id);
//
//    @Query(value="select * from walk_record w", nativeQuery= true)
//    List<WalkRecord> findByYearAndMonthAndUser(Long id);
}
