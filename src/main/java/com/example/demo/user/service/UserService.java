package com.example.demo.user.service;

import com.example.demo.item.model.dto.ItemInfo;
import com.example.demo.level.LevelRepository;
import com.example.demo.pet.repository.PetRepository;
import com.example.demo.user.model.dto.UserSignUpDto;
import com.example.demo.user.model.dto.*;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final LevelRepository levelRepository;
    private User user;


    public UserService(UserRepository userReposioty, PetRepository petRepository, LevelRepository levelRepository){
        this.userRepository = userReposioty;
        this.petRepository = petRepository;
        this.levelRepository = levelRepository;
    }



//    @Transactional
//    public UserDashBoardDto selectUser(Long uid) {
//        Optional<User> maybeUser= userRepository.findById(uid);
//        User user = maybeUser.get();
//        return new UserDashBoardDto(user);
//
//    }
    @Transactional
    public List<User> selectAllUser(){
        List<User> users= new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        System.out.println("크기:"+users.size());
        System.out.println("테스트");
        return users;
    }


    //유정 정보 조회
    @Transactional
    public UserDashBoardDto getUserInfo(Long userId) { return  userRepository.getUserInfo(userId);}


    //아이템 구매
    @Transactional
    public Integer buyItem(Long userId, Long ItemId){


        String result=userRepository.findHasItemByUserAndItem(userId,ItemId).getItemCount();


        if(result=="0") {
            if (userRepository.CmpPointAndPriceById(userId, ItemId).getResult() >= 0) {
                userRepository.MinusUserPointByItemBuy(userId, ItemId);
                userRepository.InsertBuyItem(userId, ItemId);
                return 1;
            } else

                //포인트 부족
                return -1;
        }
        else // 이미 아이템 보유

            return 2;


    }

    @Transactional
    public void trashItem(Long userId, Long ItemId){
        userRepository.DeleteItem(userId,ItemId);
    }

    //장비 장착
    @Transactional
    public void equipOnItem(Long userId, Long ItemId){
        userRepository.UpdateEquipOnItem(userId,ItemId);
    }

    //장비 벗기
    @Transactional
    public void equipOffItem(Long userId, String itemType){
        userRepository.UpdateEquipOffItem(userId,itemType);
    }


    @Transactional
    public UserLevelInfo getLevel(Long id){
        return userRepository.levelFindByUser(id);
    }

    //아이템 소유 정보 조회
    @Transactional
    public List<ItemInfo> getHasItem(Long id) { return userRepository.findHasItemByUser(id);}


    @Transactional
    public UserLoginResponse getUserId(Long userId) { return userRepository.findByUserId(userId);}

//    public UserLoginResponse getUserId(String userUid) { return userRepository.findByUserUid(userUid);}
//    @Transactional
//    public void saveUser(){
//
////        userRepository.saveUser(userUid,userNickName,userBirth,gender,weight,height);
////        userRepository.saveUser(  "11","만슈르","2021-05-18","남","183","65");
//        userRepository.saveUser();
//    }

    @Transactional
    public UserLoginResponse saveUser(UserSignUpDto userSignUpDto){

        Long id=userSignUpDto.getId();
        String nickName=userSignUpDto.getNickName();
        String birth= userSignUpDto.getBirth();
        String gender = userSignUpDto.getGender();
        String weight = userSignUpDto.getWeight();
        String height = userSignUpDto.getHeight();
        userRepository.saveUser(id,nickName,birth,gender,weight,height);
        UserLoginResponse userLoginResponse =userRepository.findByUserId(id);
        userRepository.InsertEquipItem(userLoginResponse.getUserId(),"hair");
        userRepository.InsertEquipItem(userLoginResponse.getUserId(),"face");

        for(int i=1;i<7;i++)
            userRepository.saveMission((long)i,id);


        return userLoginResponse;

    }

    @Transactional
    public List<UserRank> getAccumulateRankAllUserInfo(){

        return userRepository.findAllAccumulateRankInfo();
    }

    @Transactional
    public UserRank getAccumulateRankUserInfo(Long id){

        return userRepository.findAccumulateRankInfobyId(id);
    }

    @Transactional
    public List<UserRank> getMonthRankAllUserInfo(){

        return userRepository.findAllMonthRankInfo();
    }

    @Transactional
    public UserRank getMonthRankUserInfo(Long id){

        return userRepository.findMonthRankInfobyId(id);
    }

    //다음 레벨 필요 경험치 정보
    @Transactional
    public UserExpInfo getNextLevelNeedExpInfo(Long id){

        return userRepository.findNextLevelNeedExpInfobyId(id);
    }

    //현재 레벨까지 필요 경험치 정보

    @Transactional
    public UserExpInfo getCurrentLevelNeedExpInfo(Long id){

        return userRepository.findCurrentLevelNeedExpInfobyId(id);
    }
}
