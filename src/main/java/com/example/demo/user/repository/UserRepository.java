package com.example.demo.user.repository;

import com.example.demo.item.model.dto.ItemInfo;
import com.example.demo.user.model.dto.*;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {



    
//    회원가입
    @Query(value= "Insert Into user (id,nickname,birth,gender,height,weight,user_connected_at,user_registered_at)" +
            " values(:id,:nickName, :birth,:gender,:height,:weight,DATE_FORMAT(now(),'%Y-%m-%d'),DATE_FORMAT(now(),'%Y-%m-%d'))",nativeQuery = true)
    UserLoginResponse saveUser(Long id, String nickName, String birth, String gender, String weight, String height);
//
//    @Query(value="Insert Into user (user_uid,user_nickname,user_birth,gender,height,weight,user_connected_at,user_registered_at)  " +
//            "VALUES(\"11\",\"만슈르\",\"2021-05-18\",\"남\",\"183\",\"65\",DATE_FORMAT(now(),'%Y-%m-%d'),DATE_FORMAT(now(),'%Y-%m-%d'))",nativeQuery = true)
//    UserLoginResponse saveUser();


    //uid를 기반으로 id값 조회 로그인시 사용
//    @Query(value = "select u.id As UserId  from user u where u.user_uid=:userUid",nativeQuery = true)
//    UserLoginResponse findByUserUid(String userUid);

    @Query(value = "select u.id As UserId  from user u where u.id=:id",nativeQuery = true)
    UserLoginResponse findByUserId(Long id);



    //최근 접속날짜 변경
    @Query(value = "Update user set user_connected_at = DATE_FORMAT(now(),'%Y-%m-%d') where user.id= :userId ",nativeQuery = true)
    void saveConnectedTime(String userId);

    //최근 접속날짜 조회
    @Query(value = "select  user.user_connected_at from user where user.id= :userId ",nativeQuery = true)
    void findConnectedTimeByuserId(String userId);



    @Query(value="select u.level_id as Level from user u where u.id = :id", nativeQuery=true)
    UserLevelInfo levelFindByUser(Long id);


    //유저 정보 조회

    @Query(value="select u.id as Id, u.nickname as NickName," +
            "u.birth as Birth, u.gender as Gender, u.current_exp as CurrentExp, u.current_point as CurrentPoint, u.total_accumulate_point as TotalAccumulatePoint, " +
            " u.walk_count as WalkCount, u.month_point as MonthPoint, u.walk_distance as WalkDistance, u.user_connected_at as  ConnectedDate," +
            " u.height as Height, u.weight as Weight, u.level_id as LevelId,  u.pet_id as PetId, p.pet_name as PetName, u.weekly_mission_achievement as WeeklyMissionAchievement " +
            " from  user u left outer join pet p ON u.pet_id= p.id where u.id = :userId" ,  nativeQuery = true)
    UserDashBoardDto getUserInfo(Long userId);


    //아이템 구매
    @Query(value="Insert into user_has_item  (user_id,item_id) values(:userId,:itemId)", nativeQuery=true)
    void InsertBuyItem(Long userId, Long itemId);

    @Query(value="SELECT u.current_point -(\n" +
            "\n" +
            "SELECT i.item_price from item i WHERE i.id=:itemId)  AS Result   \n" +
            "\n" +
            "FROM user u WHERE u.id= :userId" ,nativeQuery=true)
    UserCmpPointAndPrice CmpPointAndPriceById(Long userId, Long itemId);


    //아이템 구매했을 시 차감
    @Query(value="Update user SET user.current_point = user.current_point -(select i.item_price from item i where i.id=:itemId) where user.id = :userId",nativeQuery = true)
    void MinusUserPointByItemBuy(Long userId, Long itemId);


    //아이템 삭제
    @Query(value="delete from user_has_item  where user_id =:userId and item_id=:itemId",nativeQuery=true)
    void DeleteItem(Long userId, Long itemId);



    //아이템 소유 목록
    @Query(value="select i.id as ItemId, i.item_name as ItemName, i.item_filename as ItemFilename, i.item_price as ItemPrice , i.item_type as ItemType from item i  \n" + "left outer join user_has_item uhi on i.id = uhi.item_id where uhi.user_id=:userId", nativeQuery = true)
    List<ItemInfo> findHasItemByUser(Long userId);

    //아이템 소유 확인
//    SELECT IFNULL(i.item_name, '없는 코드') AS ItemName
//    FROM user_ha_item uhi LEFT OUTER JOIN item i ON uhi.item_id =:itemId where uhi.user_id=:userId"
//    RIGHT OUTER JOIN (SELECT '') AS m_dual
//    ON i.id = '10';


    @Query(value="SELECT COUNT(i.item_name) as ItemCount " +
            "FROM user_has_item uhi LEFT OUTER JOIN item i ON uhi.item_id = i.id where uhi.user_id=:userId " +
            "AND i.id=:itemId",nativeQuery=true)
    ItemInfo findHasItemByUserAndItem(Long userId, Long itemId);


    //장비장착 갱신
    @Query(value="update user_equip_item SET user_equip_item.item_id=:itemId " +
            "where user_equip_item.user_id = :userId " +
            "and user_equip_item.item_type = (select item.item_type from item where item.id =:itemId )",nativeQuery=true)
    void UpdateEquipOnItem(Long userId, Long itemId);

    //장비벗기

    @Query(value="update user_equip_item SET user_equip_item.item_id=null " +
            "where user_equip_item.user_id = :userId " +
            "and user_equip_item.item_type = :itemType",nativeQuery=true)
    void UpdateEquipOffItem(Long userId, String itemType);

    //장비 테이블 삽입  -신규회원
    @Query(value="INSERT INTO user_equip_item(user_id,item_type) VALUES(:userId,:itemType)",nativeQuery=true)
    void InsertEquipItem(Long userId,String itemType);
//
//    String getUserName();
//    String getUserTotalAccumulatePoint();
//    String getUserMonthPoint();
//    getUserTotalAccumulatePoint
    // 누적랭킹
    @Query(value="select u.nickname as NickName , u.total_accumulate_point as TotalAccumulatePoint," +
            " u.month_point as UserMonthPoint ,RANK() OVER (Order BY u.total_accumulate_point DESC) Rank FROM user u LIMIT 100", nativeQuery=true)
    List<UserRank> findAllAccumulateRankInfo();



    // 누적 개인랭킹
    @Query(value="SELECT * FROM (SELECT u.id AS Id,  u.nickname as NickName , u.total_accumulate_point as TotalAccumulatePoint ," +
            "u.month_point as UserMonthPoint, RANK() OVER (Order BY u.total_accumulate_point DESC) as Rank FROM user u)AS A WHERE Id=:id",nativeQuery = true)
    UserRank findAccumulateRankInfobyId(Long id);

//
//    String getNickName();
//    String getTotalAccumulatePoint();
//    String getUserMonthPoint();

    // 월별랭킹

    @Query(value="select u.nickname as NickName , u.total_accumulate_point as TotalAccumulatePoint"+
            ",u.month_point as UserMonthPoint, RANK() OVER (Order BY u.month_point DESC) Rank FROM user u LIMIT 100", nativeQuery=true)
    List<UserRank> findAllMonthRankInfo();


    // 월별 개인랭킹

    @Query(value="SELECT * FROM (SELECT u.id AS Id,  u.nickname as NickName , u.total_accumulate_point as TotalAccumulatePoint ," +
            "u.month_point as UserMonthPoint, RANK() OVER (Order BY u.month_point DESC) as Rank FROM user u)AS A WHERE Id=:id",nativeQuery = true)
    UserRank findMonthRankInfobyId(Long id);


    //경험치 정보

    @Query(value="SELECT l.need_exp-u.current_exp as NexLevelNeedExp, l.id as NextLevel, l.need_exp as NextLevelExp FROM user u " +
            "LEFT OUTER JOIN level l ON u.level_id < l.id WHERE u.id=:id Order BY l.id asc LIMIT 1",nativeQuery = true)
    UserExpInfo findNextLevelNeedExpInfobyId(Long id);


    // 현재 레벨까지 필요한 경험치 정보
    @Query(value="SELECT need_exp as CurrentLevelNeedExp FROM user u LEFT OUTER JOIN level l ON u.level_id = l.id " +
            "WHERE u.id=:id ",nativeQuery = true)
    UserExpInfo findCurrentLevelNeedExpInfobyId(Long id);


    //회원생성시 미션 진행정보 생성
    @Query(value="Insert into mission_progress (mission_id, user_id)" +
            "values(:missionId,:id)",nativeQuery = true)
    void saveMission(Long missionId,Long id);


//    //미션완료를 통한 증감
//    @Query(value="Update user SET user_current_point = "+
//            "(select m.mission_reward_point from mission m where m.mission_id =:missionId)"+
//            ", user_current_current_exp =+(select m.mission_rewar_exp from mission m where m.missio_id=:missionId)"
//            , nativeQuery = true)
//    void PlusUserPointAndExpByMission (Long userId,Long missionId);
//

//    //걸었을 시 일정 걷기수마다 증감
//    @Query(value="Update user SET user.user_current_point = +5 and user.user_current_exp=+10", nativeQuery = true)
//    void PlusUserPointAndExpByWalk(Long userId);


    //경험치 증감 <-이건 미션쪽에서 처리했으니
    //그러면 미션부분에 같이 넣어야겠는데?

//
//    //레벨 업
//    @Query(value="update user SET user.level_id = (select l.id from level l where l.need_exp= user.current_exp OR " +
//            "l.need_exp < user.current_exp and user.id=1 Order by l.id DESC LIMIT 1)",nativeQuery = true)
//    void UpdateLevel(Long userId);

}